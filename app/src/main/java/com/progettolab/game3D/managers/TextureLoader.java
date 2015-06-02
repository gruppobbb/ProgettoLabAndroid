package com.progettolab.game3D.managers;

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
 * Classe per il caricamento e decodifica delle texture.
 * Created by Jancarlos.
 */
public class TextureLoader {

    private static final String TAG = "TextureManager";

    private Context context;

    public TextureLoader(Context context){
        this.context = context;
    }

    /**
     * Metodo per il caricamento di una texture dagli Assets per OpenGL.
     * @param texturePath path della texture, interno, tra gli Assets.
     * @return ID della texture caricata in OpenGL.
     */
    public int loadTextureFromAssets( String texturePath){

        //TODO usare il metodo del BitmapLoader
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

        int textureID = createTexture(textureBitmap);

        textureBitmap.recycle();

        return textureID;

    }

    /*
    Metodo per la creazione vera e propria della texture.
     */
    private  int createTexture(Bitmap textureBitmap){

        final int[] textureObjectID = new int[1];

        //Carica una texture in textureObjectID, nella posizione 0.
        GLES20.glGenTextures(1, textureObjectID, 0);

        if( textureObjectID[0] == 0){
            if(LogStatusManager.TEXTURE_MANAGER_LOG_ON){
                Log.w(TAG, "Could not generate a new OpenGL texture object");
            }
            return 0;
        }

        if(textureBitmap == null){
            if(LogStatusManager.TEXTURE_MANAGER_LOG_ON){
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
