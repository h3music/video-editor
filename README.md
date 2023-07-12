# video-editor
Java Application to automate video editing process.

This application automates the video editing process for H3 Music.

## Examples
Many examples are available at the [H3 Music Youtube Channel](https://www.youtube.com/@H3Music/videos).

## The Problem

H3 Music videos are created daily to advertise custom audio products. The audio is played over a still image background with film grain effects applied over it. At intense moments in the audio (Choruses, Hooks, Drops), a glitch effect is inserted.

Previous to this program, this process was manually completed using Adobe Premiere Pro.
Every day this process would take 5-7 minutes to edit (requires human resources) and 3-4 minutes to export (requires computer resources).
Over a year, 30+ hours would be spent manually editing videos, and for 20+ additional hours, the rendering workstation would be unusable.

This program completes the same job in 4.5 minutes, without requiring any manual editing.

## Build Info
System: Windows 10 64-Bit
IDE: IntelliJ IDEA 2023.1.3 (Community Edition)
JDK: Oracle OpenJDK 20.0.1
FFMPEG: 2023-06-11-git-09621fd7d9-full_build-www.gyan.dev

Instructions: Run the VideoEditor.java file to being the application. From there you will be prompted for file paths and other parameters. Once these are entered the video will render.

## Dependencies
[FFMPEG](https://ffmpeg.org/download.html#releases) - Required for converting frame images into mp3. You MUST change the ffmpegPath String in "SeqToVideo.java to the ffmpeg.exe file. If you do not do this, the video will not render.

## Troubleshooting
1. Memory Heap Errors: change the maxThreads to a lower number. This number should match your CPU, core count. Any more is overkill.
2. ArrayIndex & Index Out Of Bounds Errors: Usually because the background image is bigger than the grain resource images. Lower the resolution of the background image or get higher-quality grain resources.
3. Mp3s do not work: Mp3s do not work, use WAV Files at this time.

## Documentation on Sub-Packages
- [Film Grain Video Effect](https://github.com/aabalke33/film-grain-effect)
- [Glitch Video Effect](https://github.com/aabalke33/glitch-video-effect)
- [Composite Image Blend Modes](https://github.com/aabalke33/blend-modes) ([Video Breakdown](https://www.youtube.com/watch?v=mvTyBnEWVW0))
- [Offset Image/Color Channel Position](https://github.com/aabalke33/rgb-offset) ([Video Breakdown](https://www.youtube.com/watch?v=fP4gSrhVJ30))
