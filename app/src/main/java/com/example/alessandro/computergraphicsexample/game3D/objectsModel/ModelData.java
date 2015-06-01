package com.example.alessandro.computergraphicsexample.game3D.objectsModel;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Classe che contiene la descrizione di un oggetto per OpenGL.
 * Created by Jancarlos.
 */
public class ModelData {

    public static final int BYTES_PER_FLOAT = 4;
    public static final int BYTES_PER_SHORT = 2;

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private FloatBuffer normalBuffer;
    private ShortBuffer indicesBuffer;

    private static final int POSITION_DATA_SIZE = 3;
    private static final int NORMAL_DATA_SIZE = 3;
    private static final int TEXTURE_COORDINATE_DATA_SIZE = 2;

    private final int stride = (POSITION_DATA_SIZE + NORMAL_DATA_SIZE + TEXTURE_COORDINATE_DATA_SIZE)
            * BYTES_PER_FLOAT;


    private short indexCount;

    /**
     * Costruttore che trasforma un array float e short Java letti da un file .obj, in un Buffer di float e short,
     * ordinati, in codice nativo per OpenGL.
     *
     * @param objectName       nome oggetto complesso, letto dal file ,obj
     * @param vertexData       vettore contenete le coordinare x t z dei vertici
     * @param textureData      vettore contenente le coordinate s t delle texture associate ai vertici
     * @param normalData       vettore contenente le normali dei vertici
     * @param indicesData      vettore contenente l'ordine di disegno dei vertici
     */
    public ModelData(String objectName, float[] vertexData, float[] textureData, float[] normalData,
                     short[] indicesData) {

        Log.d("ModelData", "Data:\n -loading: "+objectName);

        //Inizializzazione del buffer contenente la posizione dei vertici.
        vertexBuffer = loadFloatBuffer(vertexData);

        if(textureData != null ){
            //Inizializzazione del buffer contentente le coordinate S, T per il disegno delle texture sulle face.
            textureBuffer = loadFloatBuffer(textureData);
        }

        if(normalData != null){
            //Inizializzazione del buffer contenente la posizione dei vertici.
            normalBuffer = loadFloatBuffer(normalData);
        }

        //Inizializzazione del buffer contenente l'ordine di disegno dei vertici delle singole face.
        indicesBuffer = loadShortBuffer(indicesData);

        indexCount = (short)indicesData.length;
    }


    /**
     * Metodo per il caricamento di un vettore float java in un FloatBuffer, oridnato,
     * in codice nativo per OpenGL.
     *
     * @param inFloatData vettore da caricare
     * @return vetore convertito in FloatBuffer
     */
    private FloatBuffer loadFloatBuffer(float[] inFloatData) {
        ByteBuffer vetexByteBuffer = ByteBuffer.allocateDirect(
                inFloatData.length * BYTES_PER_FLOAT);
        vetexByteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferResult = vetexByteBuffer.asFloatBuffer();
        floatBufferResult.put(inFloatData);
        floatBufferResult.position(0);
        return floatBufferResult;
    }

    /**
     * Metodo per il caricamento di un vettore short java in un ShortBuffer, ordinato,
     * in codice nativo per OpenGL.
     *
     * @param inShortData vettore short da caricare
     * @return vettore convertito in ShortBuffer.
     */
    private ShortBuffer loadShortBuffer(short[] inShortData) {
        ByteBuffer vetexByteBuffer = ByteBuffer.allocateDirect(
                inShortData.length * BYTES_PER_SHORT);
        vetexByteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBufferResult = vetexByteBuffer.asShortBuffer();
        shortBufferResult.put(inShortData);
        shortBufferResult.position(0);
        return shortBufferResult;
    }


    /**
     * Metodo per il caricamento del buffer contenente le informazioni dei vertici dell'attuale
     * oggetto 3D da disegnare.
     *
     * @param vertexAttributeLocation reference dell'attributo per le coordinate dei vertici.
     */
    public void directSetVertexAttributePointer(int vertexAttributeLocation) {
        //Set nel punto iniziale, comprensivo di offset, del buffer.
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vertexAttributeLocation, 3, GLES20.GL_FLOAT,
                false, 0, vertexBuffer);

        GLES20.glEnableVertexAttribArray(vertexAttributeLocation);
        //Reset della posizione del buffer per quando verra' richiamato da OpenGL.
        vertexBuffer.position(0);
    }


    /**
     * Metodo per il caricamento del buffer contenente le informazioni delle coordinate delle texture
     * per attuale oggetto 3D da disegnare.
     *
     * @param textureAttributeLocation reference dell'attributo per le corrdinate della texture.
     */
    public void directSetTextureAttributePointer(int textureAttributeLocation) {
        //Set nel punto iniziale, comprensivo di offset, del buffer.
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(textureAttributeLocation, 2, GLES20.GL_FLOAT,
                false, 0, textureBuffer);

        GLES20.glEnableVertexAttribArray(textureAttributeLocation);
        //Reset della posizione del buffer per quando verra' richiamato da OpenGL.
        textureBuffer.position(0);
    }


    /**
     * Metodo per il caricamento del buffer contenente le informazioni delle normali dell'attuale
     * oggetto 3D da disegnare.
     * @param normalAttributeLocation reference dell'attibuto per le normali.
     */
    public void directSetNormalAttributePointer(int normalAttributeLocation){
        normalBuffer.position(0);
        GLES20.glVertexAttribPointer(normalAttributeLocation, 3, GLES20.GL_FLOAT,
                false, 0, normalBuffer);
        GLES20.glEnableVertexAttribArray(normalAttributeLocation);
        normalBuffer.position(0);
    }

    /**
     * Restituisce il {@link FloatBuffer} associato ai dati delle texture.
     * @return buffer delle texture
     */
    public FloatBuffer getTextureBuffer() {
        return textureBuffer;
    }

    /**
     * Restituisce il {@link FloatBuffer} associato ai dati della normal map.
     * @return buffer
     */
    public FloatBuffer getNormalBuffer() {
        return normalBuffer;
    }

    /**
     * Restituisce lo {@link ShortBuffer} associato agli indici dei vertici.
     * @return buffer
     */
    public ShortBuffer getIndicesBuffer() {
        return indicesBuffer;
    }

    /**
     * Restituisce il numero totale degli indici.
     * @return numero totale di indici
     */
    public short getIndicesCount(){
        return indexCount;
    }

}