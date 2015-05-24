package com.example.alessandro.computergraphicsexample.alternative.objectsModel.obj;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Classe che contiene la descrizione diun oggetto per OpenGL.
 * Created by Jancarlos.
 */
public class ModelData {

    public static final int BYTES_PER_FLOAT = 4;
    public static final int BYTES_PER_SHORT = 2;

    private String objectName;
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

        this.objectName = objectName;


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
        Log.e("ModelData", objectName + " indexCount " + indexCount);
    }


    //TODO: vedere se cosi' gli piace o se se bisogna usare return...


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
     * Metodo per il caricamento di un vettore short java in un ShortBuffer, odinato,
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
     * Variante del metodo per l'associazione in cui si considerano nulli offset e stride, mentre
     * si considera costante il numero di componenti per vertice, 3. Da usare per buffer separati standard.
     *
     * @param attributeLocation puntatore all'attributo nello shader.
     */
    public void directSetVertexAttributePointer(int attributeLocation) {

        //TODO: fare diventare il meodo riutilizzbile mediante l'uso di switch e costanti.
        //Set nel punto iniziale, comprensivo di offset, del buffer.
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(attributeLocation, 3, GLES20.GL_FLOAT,
                false, 0, vertexBuffer);

        GLES20.glEnableVertexAttribArray(attributeLocation);
        //Reset della posizione del buffer per quando verra' richiamato da OpenGL.
        vertexBuffer.position(0);


    }


    /**
     * Variante del metodo per l'associazione in cui si considerano nulli offset e stride, mentre
     * si considera costante il numero di componenti per vertice, 3. Da usare per buffer separati standard.
     *
     * @param attributeLocation puntatore all'attributo nello shader.
     */
    public void directSetTextureAttributePointer(int attributeLocation) {

        //TODO: fare diventare il meodo riutilizzbile mediante l'uso di switch e costanti.
        //Set nel punto iniziale, comprensivo di offset, del buffer.
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(attributeLocation, 2, GLES20.GL_FLOAT,
                false, 0, textureBuffer);

        GLES20.glEnableVertexAttribArray(attributeLocation);
        //Reset della posizione del buffer per quando verra' richiamato da OpenGL.
        textureBuffer.position(0);
    }


    public void directSetNormalAttributePointer(int normalLocation){
        normalBuffer.position(0);
        GLES20.glVertexAttribPointer(normalLocation, 3, GLES20.GL_FLOAT,
                false, 0, normalBuffer);
        GLES20.glEnableVertexAttribArray(normalLocation);
        normalBuffer.position(0);

    }


    public String getObjectName() {
        return objectName;
    }

    public FloatBuffer getTextureBuffer() {
        return textureBuffer;
    }

    public FloatBuffer getNormalBuffer() {
        return normalBuffer;
    }

    public ShortBuffer getIndicesBuffer() {
        return indicesBuffer;
    }

    public short getIndicesCount(){
        return indexCount;
    }

}