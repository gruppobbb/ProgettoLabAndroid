package com.progettolab.game3D.util;

import android.os.Build;

/**
 * Classe che contiene metodi per uso generale.
 * @author Jancarlos.
 */
public class GameUtil {

    /**
     * Metodo che verifica se stiamo eseguendo l'app nell'emulatore.
     * @return true se si sta eseguendo l'app nell'emulatore.
     */
    public static boolean checkIfIsRunningInEmulator(){
        return (Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
        );
    }

}
