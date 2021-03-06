package com.progettolab.game3D.managers;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * @author Jancarlos.
 */
public class ScreenManager {

    /**
     * Metodo per l'attivazione della modalita' FullScreen standard oppure, se supportato, Immersive.
     * @param activity activity da mandare in FullScreen.
     */
    public static void goFullScreen(Activity activity){
        if( hasKitKat() ){
            //Immersive, per chi ha i softkey
            int mUIFlag =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            activity.getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
        }
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

}
