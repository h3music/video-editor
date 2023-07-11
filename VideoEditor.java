package com.h3music.videoeditor;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VideoEditor {
    private static double getAudioDuration(File file) throws UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        return (frames + 0.0) / format.getFrameRate();
    }

    public static void main(String[] args) {

        try {
            System.out.println("H3 Music Video Editor");
            System.out.println("Copyright H3 Music Corp 2023, h3music.com\n\n");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Background Image File Path: ");
            File sourceFile = new File(scanner.nextLine().replace("\"", ""));

            System.out.println("WAV Audio File Path: ");
            File audio = new File(scanner.nextLine().replace("\"", ""));

            System.out.println("Output File Path: ");
            File outputFile = new File(scanner.nextLine().replace("\"", ""));

            System.out.println("\"Impact\" Locations (Comma Separated Frame Values): ");
            String[] framesUnformatted = scanner.nextLine().split(",");

            ArrayList<Integer> frames = new ArrayList<>();

            for (String frame: framesUnformatted) {
                frames.add(Integer.parseInt(frame));
            }

            if (!sourceFile.exists() || !audio.exists() || outputFile.exists()) {
                System.out.println("Error with inputted files");
                System.exit(1);
            }

            VideoRecord videoRecord = new VideoRecord(frames, sourceFile, audio, outputFile);

            File grainResource = new File(".\\src\\com\\h3music\\videoeditor\\grain\\resources\\");

            System.out.println("Processing...");

            Video.render(getAudioDuration(videoRecord.audio()),
                    videoRecord.impactFrames(), videoRecord.sourceFile(),
                    grainResource, videoRecord.audio(), videoRecord.outputFile());

        } catch (Exception e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }
}