package com.h3music.videoeditor;

import com.h3music.videoeditor.glitch.GlitchEffect;
import com.h3music.videoeditor.grain.GrainEffect;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class renders the video based on inputted parameters.
 */
public class Video {
    /**
     * Method to render the video.
     * @param duration The duration, in seconds, of the video.
     * @param fps The frames per second of the video.
     * @param impactFrames These are the frame locations where a glitch effect should be initiated
     *                     (In a 24 fps video, a value of 24 would occur 1 second into the video).
     * @param maxThreads Hard limits the amount of threads used by the method.
     *                   If heap memory errors are occurring lower this number.
     *                   Performance has diminishing returns when maxThreads > physical CPU cores.
     * @param sourceFile An image file used as the background of the video.
     * @param grainResource The folder to pull grain resource frames to overlay.
     * @param grainOutput The folder to pull "film grain" frame assets.
     * @param glitchOutput The folder to pull "glitch effect" frame assets.
     * @param audio An audio file to include in the video file. (WAV required).
     * @param outputFile A file to export the video to.
     */
    public static void render(double duration, int fps, ArrayList<Integer> impactFrames,
                              int maxThreads, File sourceFile, File grainResource, File grainOutput,
                              File glitchOutput, File audio, File outputFile) {

        ArrayList<File> grainResourceFiles = new ArrayList<>(
                Arrays.asList(Objects.requireNonNull(grainResource.listFiles())));

        GrainEffect.grain(sourceFile,grainResourceFiles, grainOutput,.3,96, maxThreads);

        ArrayList<File> grainFiles = new ArrayList<>(
                Arrays.asList(Objects.requireNonNull(grainOutput.listFiles())));

        GlitchEffect.glitch(grainFiles, glitchOutput, 5, maxThreads, 48);

        File frameList = FrameSequence.create(duration, fps, grainOutput, glitchOutput, impactFrames);

        SeqToVideo.export(outputFile, fps, audio, frameList);
    }

    /**
     * Overloaded method to render the video. Assumes 24 fps, and 8 maxThreads
     * @param duration The duration, in seconds, of the video.
     * @param impactFrames These are the frame locations where a glitch effect should be initiated
     *                     (In a 24 fps video, a value of 24 would occur 1 second into the video).
     * @param sourceFile An image file used as the background of the video.
     * @param grainResource The folder to pull grain resource frames to overlay.
     * @param grainOutput The folder to pull "film grain" frame assets.
     * @param glitchOutput The folder to pull "glitch effect" frame assets.
     * @param audio An audio file to include in the video file. (WAV required).
     * @param outputFile A file to export the video to.
     */
    public static void render(double duration,  ArrayList<Integer> impactFrames,  File sourceFile,
                              File grainResource, File grainOutput, File glitchOutput, File audio, File outputFile) {

        render(duration, 24, impactFrames, 8, sourceFile, grainResource,
                grainOutput, glitchOutput, audio, outputFile);
    }

    /**
     * Overloaded method to render the video. Assumes 24 fps, and 8 maxThreads,
     * creates temporary directories to store temporary files, and then deletes temp files.
     * @param duration The duration, in seconds, of the video.
     * @param impactFrames These are the frame locations where a glitch effect should be initiated
     *                     (In a 24 fps video, a value of 24 would occur 1 second into the video).
     * @param sourceFile An image file used as the background of the video.
     * @param grainResource The folder to pull grain resource frames to overlay.
     * @param audio An audio file to include in the video file. (WAV required).
     * @param outputFile A file to export the video to.
     */
    public static void render(double duration,  ArrayList<Integer> impactFrames, File sourceFile,
                              File grainResource, File audio, File outputFile) {

        File tmpFolder = new File("./tmp");
        File grainOutput = new File("./tmp/grain");
        File glitchOutput = new File("./tmp/glitch");

        if (tmpFolder.exists()) {
            tmpFolder.delete();
        }

        tmpFolder.mkdir();
        grainOutput.mkdir();
        glitchOutput.mkdir();

        render(duration, 24, impactFrames, 8, sourceFile, grainResource, grainOutput, glitchOutput, audio, outputFile);

        deleteDirectory(tmpFolder);
    }

    /**
     * Method to delete a directory and its contents
     * @param folder The directory to delete
     */
    private static void deleteDirectory(File folder) {
        for (File subfile : Objects.requireNonNull(folder.listFiles())) {

            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }

            subfile.delete();
        }
    }
}
