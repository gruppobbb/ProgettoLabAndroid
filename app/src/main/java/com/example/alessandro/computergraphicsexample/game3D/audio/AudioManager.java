package com.example.alessandro.computergraphicsexample.game3D.audio;

import android.content.res.Resources;

import java.util.HashMap;

/**
 * Created by Max on 26/05/2015.
 */
public class AudioManager {

    private static AudioManager audioManager;
    private HashMap<String, Integer> audioResources = new HashMap<>();

    private AudioManager() {};

    public static AudioManager getInstance() {
        if(audioManager == null) {
            audioManager = new AudioManager();
        }
        return audioManager;
    }

    public void addResource(String name, int res) {
        audioResources.put(name, res);
    }

    public int getResource(String name) {
        if(audioResources.keySet().contains(name)) {
            return audioResources.get(name);
        }
        else
            throw new Resources.NotFoundException();
    }
}
