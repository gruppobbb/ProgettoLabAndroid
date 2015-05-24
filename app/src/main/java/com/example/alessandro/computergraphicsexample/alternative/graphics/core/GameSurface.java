package com.example.alessandro.computergraphicsexample.alternative.graphics.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

import com.example.alessandro.computergraphicsexample.alternative.util.GameUtil;


/**
 * @author Jancarlos.
 */
public class GameSurface extends GLSurfaceView {

    public GameSurface(Context context) {
        super(context);
        init();
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Inizializzazioni varie.
     */
    private void init(){
        //Set OpenGL per l'uso della versione 2.0.
        setEGLContextClientVersion(2);

        //Abilitazione dell'antialiasing.
        enableAntialiasing();
    }

    /**
     * Metodo per l'abilitazione dell rendering su comando tramite requestRender. Utile per vedere le cose step per step.
     * @param isRenderNotAutomatic true per abilitare il render su richiesta.
     */
    public void enableControlledRendering(boolean isRenderNotAutomatic){
        if(isRenderNotAutomatic){
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }else{
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
    }

    /**
     * Metodo per l'abilitazione dell'antialising. Sull'emulatore non ha effetto.
     */
    public void enableAntialiasing(){
        if(!GameUtil.checkIfIsRunningInEmulator()){
            setEGLConfigChooser(new GameSurfaceConfigChooser());
        }
    }

    /**
     * Classe che definisce le impostazioni utili per l'abilitazione del multisempling ( antialiasing ) .
     */
    private class GameSurfaceConfigChooser implements EGLConfigChooser{
        @Override
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int attribs[] = {
                    EGL10.EGL_LEVEL, 0,
                    EGL10.EGL_RENDERABLE_TYPE, 4,
                    EGL10.EGL_COLOR_BUFFER_TYPE, EGL10.EGL_RGB_BUFFER,
                    EGL10.EGL_RED_SIZE, 8,
                    EGL10.EGL_GREEN_SIZE, 8,
                    EGL10.EGL_BLUE_SIZE, 8,
                    EGL10.EGL_DEPTH_SIZE, 16,
                    EGL10.EGL_SAMPLE_BUFFERS, 1,
                    EGL10.EGL_SAMPLES, 4,  // 4x MSAA.
                    EGL10.EGL_NONE
            };
            EGLConfig[] configs = new EGLConfig[1];
            int[] configCounts = new int[1];
            egl.eglChooseConfig(display, attribs, configs, 1, configCounts);

            if (configCounts[0] == 0) {
                return null;
            } else {
                return configs[0];
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause() {
        super.onPause();
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
