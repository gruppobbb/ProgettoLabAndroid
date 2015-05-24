package com.example.alessandro.computergraphicsexample.alternative.managers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jancarlos.
 */
public class JImageManager {
    private static final String TAG = "TextureManager";

    public static Bitmap loadBitmap(Context context, String texturePath){
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        Bitmap bitmap = null;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        //Non vogliamo che l'immagine venga caricata e scalata. La scalatura la lasciamo ad OpenGL.
        options.inScaled = false;

        try {
            inputStream = assetManager.open(texturePath);
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    /**
     * Metodo per il caricamento di una texture dalle Resources per OpenGL.
     * @param context context della activity
     * @param textureID identificativo all'intersno delle resources dell'app.
     * @return ID della texture caricata in OpenGL.
     */
    public static int loadTextureFromResurces(Context context, int textureID){

        //OpenlGL non può leggere dati direttamente da PNG o JPG in quanto compressi, bisona prima
        // decodificare l'inmagine tramite Bitmap

        final BitmapFactory.Options options = new BitmapFactory.Options();
        //Non vogliamo che l'immagine venga caricata e scalata. La scalatura la lasciamo ad OpenGL.
        options.inScaled = false;

        final Bitmap textureBitmap = BitmapFactory.decodeResource(context.getResources(), textureID, options);

        return createTexture(textureBitmap);
    }

    /**
     * Metodo per il caricamento di una texture dalle Resources per OpenGL.
     * @param context context della activity
     * @param texturePath path della texture, interno, tra gli Assets.
     * @return ID della texture caricata in OpenGL.
     */
    public static int loadTextureFromAssets(Context context, String texturePath){

        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        Bitmap textureBitmap = null;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        //Non vogliamo che l'immagine venga caricata e scalata. La scalatura la lasciamo ad OpenGL.
        options.inScaled = false;

        try {
            inputStream = assetManager.open(texturePath);
            textureBitmap = BitmapFactory.decodeStream(inputStream, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return createTexture(textureBitmap);

    }

    private static  int createTexture(Bitmap textureBitmap){

        final int[] textureObjectID = new int[1];

        //Carica una texture in textureObjectID, nella posizione 0.
        GLES20.glGenTextures(1, textureObjectID, 0);

        if( textureObjectID[0] == 0){
            if(JLogStatusManager.JTEXTURE_MANAGER_LOG_ON){
                Log.w(TAG, "Could not generate a new OpenGL texture object");
            }
            return 0;
        }

        if(textureBitmap == null){
            if(JLogStatusManager.JTEXTURE_MANAGER_LOG_ON){
                Log.w(TAG, "Could not decode image into bitmap.");
            }
            GLES20.glDeleteTextures(1, textureObjectID, 0);
            return 0;
        }

        //**Viene detto ad OpenGL che la texture 2D da utilizzare, per le chiamate qui sotto, e' questa.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectID[0]);

        //Diciamo ad OpenGL che filti utilizzare quando effettua una miniaturizzzazione della texture.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR_MIPMAP_LINEAR);

        //Diciamo ad OpenGL che filti utilizzare quando effettua la magnification della texture.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        //Caricamento vero e proprio della textur in OpenGL.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, textureBitmap, 0);

        //Liberiamo la memoria nella VM Android.
        textureBitmap.recycle();

        //Siccome la generazione delle mipbap puo' essere "pesante", chiedo ora che generi
        // tutto il set di miniature.
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

        //**Rilascio dell'associazione inizziata sopra.
        //Passando 0 si effettua la disassociamento dalla texture attuale.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return textureObjectID[0];
    }

}
