package com.h3music.videoeditor;

import java.io.File;
import java.util.ArrayList;

/**
 * This record provides a standard interface to store information related to the video editing process.
 * @param impactFrames These are the frame locations where a glitch effect should be initiated
 *                     (In a 24 fps video, a value of 24 would occur 1 second into the video).
 * @param sourceFile An image file used as the background of the video.
 * @param audio An audio file to include in the video file. (WAV required)
 * @param outputFile A file to export the video to.
 */
public record VideoRecord(ArrayList<Integer> impactFrames,
                          File sourceFile, File audio, File outputFile) {}
