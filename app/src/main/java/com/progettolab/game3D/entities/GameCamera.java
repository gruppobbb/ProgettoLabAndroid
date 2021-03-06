package com.progettolab.game3D.entities;

import android.opengl.Matrix;

import model.Coordinate;

/**
 * CLasse per la rappresentazione e gestione di una telecamera.
 * @author Jancarlos.
 */
public class GameCamera {

	private float[] mViewMatrix = new float[16];
	private Coordinate cameraPosition;
    private Coordinate centrePosition;
    private boolean needNewMatrix;

    /**
     * Crea uan telecamera situata in cameraPosition, che punta verso centrePosition.
     * <p>
     * Nota: Dopo il posizionamento ad una certa altezza ecc. l'impostazione dell'angolo
     * viene fatta in automatico.
     * </p>
     * @param cameraPosition posizione della telecamera
     * @param centrePosition posizione del centro verso cui la telecamera deve puntare
     */
    public GameCamera(Coordinate cameraPosition, Coordinate centrePosition) {
        this.cameraPosition = cameraPosition;
        this.centrePosition = centrePosition;
        needNewMatrix = true;
    }

    /**
     * Restituisce la view matrix della telecamera.
     * @return view matrix
     */
    public float[] getViewMatrix() {
        if(needNewMatrix){
            Matrix.setLookAtM(mViewMatrix, 0,
                    cameraPosition.getX(),
                    cameraPosition.getY(),
                    cameraPosition.getZ(),
                    centrePosition.getX(),
                    centrePosition.getY(),
                    centrePosition.getZ(),
                    0f, 1.0f, 0.0f);    //Teniamo fisso il l'asse Y (positivo) come UP.
            needNewMatrix = false;
        }
        return mViewMatrix;
    }
}
