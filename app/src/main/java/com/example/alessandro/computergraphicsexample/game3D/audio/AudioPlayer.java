package com.example.alessandro.computergraphicsexample.game3D.audio;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Max on 26/05/2015.
 */
public class AudioPlayer {

    private MediaPlayer player;

    public AudioPlayer(Context context, String name, boolean loop) {

        player = MediaPlayer.create(context, AudioManager.getInstance().getResource(name));
        player.setLooping(loop);
    }

    public void play() {
        player.start();
    }

    public void stop() {
        if(player.isPlaying()) {
            player.stop();
        }
    }

    public void pause() {
        if(player.isPlaying()) {
            player.pause();
        }
    }

}
