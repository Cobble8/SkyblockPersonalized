package com.cobble.sbp.utils;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.music.PlayerStatus;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicUtils {
    public static String path;
    public static double currFrame;
    private final PlayerStatus STATUS = new PlayerStatus();
    public static String songState = "stopped";
    public static float volume = 0.5F;

    public static void setSong(String songPath) {
        path = songPath;
        currFrame=0L;
        try {
            Media songFile = new Media(new File(path).toURI().toString());
            try {
                //clip = new MediaPlayer(songFile);
                //clip.pause();
            } catch(Exception ignored) {  }

            setVolume(DataGetter.findFloat("musicVolume"));
        } catch (Exception ignored) {  }
    }
    public static void playSong() {
        try {
            songState = "playing";
            //clip.setAudioSpectrumInterval(currFrame);
            //clip.play();
        } catch(Exception ignored) { }
    }

    public static void resumeSong(long startTime) {
        currFrame=startTime;
        playSong();
    }

    public static void pauseSong() {
        songState="paused";
        //currFrame = clip.getAudioSpectrumInterval();
        //clip.stop();
    }

    public static void setVolume(float newVol) {
        ConfigHandler.newObject("musicVolume", newVol);
        volume = -32 + (32 * newVol);
        try {
          // clip.setVolume(newVol);
        } catch(Exception ignored) {}
    }

    public static double getVolume() {
        try {
            return volume;
        } catch(Exception e) {
            try {
                float vol = DataGetter.findFloat("musicVolume");
                setVolume(vol);
                return vol;
            } catch(Exception e2) {
                return 1;
            }
        }

    }

    public static void stopSong() {
        songState="stopped";
        path="";
        currFrame=0L;
        try {
            //clip.stop();
        } catch(Exception ignored) {}


    }

    public static String getSong() {
        return path;
    }

    public static void manageSong() {
        if (!DataGetter.findBool("clientMusicToggle")) {
            stopSong();
            return;
        }
        String newSong = path;
        switch (SBP.sbLocation) {
            case "dwarvenmines":
                newSong = "config/" + Reference.MODID + "/music/Dwarven Mines (Dwarven Mines).mp3";
                break;
            case "spider'sden":
                newSong = "config/" + Reference.MODID + "/music/Journey Into The Sky (Spider's Den).mp3";
                break;
            case "thefarmingislands":
                newSong = "config/" + Reference.MODID + "/music/Light From Afar (Farming Islands).mp3";
                break;
            case "deepcaverns":
                newSong = "config/" + Reference.MODID + "/music/Ambient Caves (Deep Caverns).mp3";
                break;
            case "thepark":
                newSong = "config/" + Reference.MODID + "/music/Sky Of Trees (The Park).mp3";
                break;
            case "hub":
                if (SBP.subLocation.equals("wilderness")) {
                    newSong = "config/" + Reference.MODID + "/music/Abstract Ringing (Wilderness).mp3";
                    break;
                }
                break;
            case "jerry'sworkshop":
                newSong = "config/" + Reference.MODID + "/music/Let Them Eat Cake (Jerry's Workshop).mp3";
                break;

        }

        try {
            /*if (!newSong.equals(path) || (clip.getStatus() != MediaPlayer.Status.PLAYING && songState.equals("playing"))) {
                stopSong();
                path = newSong;
                setSong(path);
                playSong();
            }*/
        } catch (Exception ignored) {
            stopSong();
        }


    }
}
