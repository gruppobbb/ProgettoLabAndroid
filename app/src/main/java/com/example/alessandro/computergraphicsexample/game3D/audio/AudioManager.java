package com.example.alessandro.computergraphicsexample.game3D.audio;

import android.content.res.Resources;

import java.util.HashMap;

/**
 * @author Max, Bianca
 * Componente che si occupa di contenere i riferimenti alle risorse audio, mappandole con identificatori alfanumerici.
 */
public class AudioManager {  //SINGLETON

    private static AudioManager audioManager;
    private HashMap<String, Integer> audioResources = new HashMap<>();

    private AudioManager() {}

    /**
     * Fornisce l'istanza di AudioManager.
     * Essendo un singleton, questo metodo è l'unico modo per poter accedere all'istanza correntemente in uso.
     * @return istanza di AudioManager
     */
    public static AudioManager getInstance() {
        if(audioManager == null) {
            audioManager = new AudioManager();
        }
        return audioManager;
    }

    /**
     * Aggiunge una risorsa audio, associandole un identificatore.
     * Non è possibile associare lo stesso identificatore a risorse diverse, pertanto una risorsa mappata con un nome già presente non verrà inserita.
     * @param name nome da associare
     * @param res riferimento alla risorsa da mappare
     */
    public void addResource(String name, int res) {
        if(!audioResources.keySet().contains(name)) {
            audioResources.put(name, res);
        }
    }

    /**
     * Fornisce il riferimento alla risorsa corrispondente al nome specificato.
     * @param name identificativo della risorsa da recuperare
     * @return riferimento alla risorsa recuperata
     */
    public int getResource(String name) {
        if(audioResources.keySet().contains(name)) {
            return audioResources.get(name);
        } else
            throw new Resources.NotFoundException();
    }
}
