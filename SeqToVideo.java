package com.h3music.videoeditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class takes the rendered effect frame sequences, and the frameList Txt
 * file to create a FFMPEG command to render the video.
 */
public class SeqToVideo {

    /**
     * Creates and sends command to FFMPEG to build the video.
     * @param outputFile A file to export the video to.
     * @param fps the frames per second of the video.
     * @param audio An audio file to include in the video file. (WAV required)
     * @param frameListFile The Txt file listing the frame order.
     */
    public static void export(File outputFile, int fps, File audio, File frameListFile) {

        try {
            // TODO: Change depending on where FFMPEG is installed
            String ffmpegPath = "./ffmpeg/bin/ffmpeg";

            String frameList = frameListFile.getAbsolutePath();
            String outputPath = outputFile.getAbsolutePath();

            String[] command = new String[]{
                    ffmpegPath, "-y", "-r", String.valueOf(fps), "-f", "concat", "-safe",
                    "0", "-i", frameList, "-i", String.valueOf(audio), "-crf", "30", outputPath
            };

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            flushInputStreamReader(process);

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Video export successful!");
            } else {
                System.out.println("Video export failed.");
            }
        } catch (IOException|InterruptedException e) {
            System.out.println("An error occurred in the FFMPEG process");
            e.printStackTrace();
        }
    }

    /**
     * Clears Input Stream so FFMPEG doesn't obstruct Java.
     * @param process Input process to clear input stream for.
     */
    private static void flushInputStreamReader (Process process) {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder s = new StringBuilder();
            while ((line = input.readLine()) != null) {
                s.append(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred in the flushing of FFMPEG");
            e.printStackTrace();
        }
    }
}
