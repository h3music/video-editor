package com.h3music.videoeditor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class creates the FrameSequence file, a txt file that provides an ordered list of image files to
 * concatenate as frames in an FFMPEG video render.
 */
public class FrameSequence {
    /**
     * This method is called to create the frame file, and add the list of frames
     * @param duration The duration, in seconds, of the video.
     * @param fps The frames per second of the video.
     * @param grainFolder The folder to pull "film grain" frame assets
     * @param glitchFolder The folder to pull "glitch effect" frame assets
     * @param impactFrames These are the frame locations where a glitch effect should be initiated
     *  *                     (In a 24 fps video, a value of 24 would occur 1 second into the video).
     * @return The Frame Sequence File
     */
    public static File create(double duration, int fps, File grainFolder, File glitchFolder, ArrayList<Integer> impactFrames) {

        try {
            File frameListFile = createFrameSeqFile();

            int frameCount = (int) Math.ceil(duration * fps);
            addGrainFrames(grainFolder, frameCount, frameListFile);

            addGlitchFrames(glitchFolder, impactFrames, frameListFile);

            return frameListFile;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates the Frame Sequence File to be written to.
     * @return The Frame Sequence File
     * @throws IOException
     */
    private static File createFrameSeqFile() throws IOException {
        File frameListFile = new File("frameList.txt");
        frameListFile.createNewFile();

        // Delete File Contents before writing
        new FileWriter(frameListFile, false).close();

        return frameListFile;
    }


    /**
     * Method to list Grain Frames in the Frame Sequence File
     * @param grainFolder The folder to pull "film grain" frame assets
     * @param frameCount The total number of frames in the video. (fps * duration)
     * @param frameList The Frame Sequence File
     * @throws IOException
     */
    private static void addGrainFrames(File grainFolder, int frameCount, File frameList) throws IOException {
        FileWriter writer = new FileWriter(frameList,true);

        int grainCount = directorySize(grainFolder);

        for (int i = 0; i < frameCount; i++) {
            writer.write("file '" + grainFolder + "\\" + (i % grainCount) + ".jpg'\n");
        }
    }

    /**
     * Method to list Glitch Frames in the Frame Sequence File
     * @param glitchFolder The folder to pull "glitch effect" frame assets
     * @param impactFrames These are the frame locations where a glitch effect should be initiated
     *  *                     (In a 24 fps video, a value of 24 would occur 1 second into the video).
     * @param frameList The Frame Sequence File
     * @throws IOException
     */
    private static void addGlitchFrames(File glitchFolder, ArrayList<Integer> impactFrames, File frameList) throws IOException {
        List<String> lines = Files.readAllLines(frameList.toPath());

        int glitchCount = directorySize(glitchFolder);
        StringBuilder glitchFrames = new StringBuilder();

        for (int i = 0; i < glitchCount; i++) {
            glitchFrames.append("file '").append(glitchFolder).append("\\").append(i).append(".jpg'\n");
        }

        for (int i = 0; i < impactFrames.size(); i++) {

            lines.add(impactFrames.get(i) - (i * glitchCount), String.valueOf(glitchFrames));

            for (int j = 0; j < glitchCount; j++) {
                lines.remove(lines.size() - 1);
            }
        }

        Files.write(frameList.toPath(), lines);
    }

    /**
     * Deletes the inputted directory
     * @param folder The folder to be deleted
     * @return
     */
    private static int directorySize(File folder) {
        return Objects.requireNonNull(folder.listFiles()).length;
    }
}