package com.progettolab.game3D.managers;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jancarlos.
 */
public class TextFileManager {

    /**
     * Lettura di un file di testo per il salvataggio del contenuto in un unica stringa di caratteri.
     * @param context
     * @param resourceID identificativo nelle resurces del file da leggere.
     * @return stringa di caratteri contenente tutto il contenuto dei file.
     */
    public static String readFileFromResources(Context context, int resourceID){
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceID);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while( ( nextLine = bufferedReader.readLine() ) != null ){
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw  new RuntimeException( "Could not find resurce: "+ resourceID,e );
        } catch (Resources.NotFoundException nfe){
            throw  new RuntimeException("Resource not fount: "+ resourceID, nfe);
        }

        return body.toString();
    }

}
