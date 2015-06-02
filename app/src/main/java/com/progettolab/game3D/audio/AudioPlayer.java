package com.progettolab.game3D.audio;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * @author Max, Bianca
 * Player per risorse audio. Fa riferimento al componente AudioManager per reperire le risorse richieste.
 * @see AudioManager
 */
public class AudioPlayer{

    private MediaPlayer player;

    /**
     * @param context Contesto
     * @param name Nome della risorsa da associare al player. Viene fornita da AudioManager
     * @param loop Specifica se la traccia debba essere eseguita il loop oppure no
     */
    public AudioPlayer(Context context, String name, boolean loop) {
        player = MediaPlayer.create(context, AudioManager.getInstance().getResource(name));
        player.setLooping(loop);
    }

    /**
     * Riproduce la traccia associata al player.
     */
    public void play() {
        player.start();
    }

    /**
     * Ferma la riproduzione della traccia associata.
     */
    public void stop() {
        if(player.isPlaying()) {
            player.stop();
        }
    }

    /**
     * Mette in pausa la riproduzione della traccia associata.
     */
    public void pause() {
        if(player.isPlaying()) {
            player.pause();
        }
    }

}
