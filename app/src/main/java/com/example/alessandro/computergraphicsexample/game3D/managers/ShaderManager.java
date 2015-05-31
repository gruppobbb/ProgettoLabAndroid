package com.example.alessandro.computergraphicsexample.game3D.managers;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Jancarlos.
 */
public class ShaderManager {

    private static final String TAG = "ShaderManager";

    /**
     * Effettua la compilazione di una VertexShader.
     * @param vertexShaderCode codice sorgente del VertexShader
     * @return 0 se non compilato, altrimenti l'ID del file oggetto creato.
     * NOTA: 0 e non null, perche' e' una informazione che usa OpenGL a basso livello.
     */
    public static int compileVertexSHader(String vertexShaderCode){
        return compliShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    }

    /**
     * Effettua la compilazione di un FragmentShader.
     * @param fragmentShaderCode Codice sorgente del FragmentShader.
     * @return 0 se non compilato, altrimenti l'ID del file oggetto creato.
     * NOTA: 0 e non null, perche' e' una informazione che usa OpenGL a basso livello.
     */
    public static int compileFragmentSHader(String fragmentShaderCode){
        return compliShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
    }

    /**
     * Effettua la compilazione del sorgente richiesto tramite compileFragmentSHader() o
     * compileVertexSHader(), efettuando una verifica sulla buona riuscita della compilazione.
     * NOTA:    NON viene lancata un eccezione in quanto OpenGL non usa le eccezioni.
     *          E' comunque possibile recuperare l'errore tramite glGetError().
     * @param type tipo di sorgente da complilare, GL_FRAGMENT_SHADER o GL_VERTEX_SHADER
     * @param shaderCode sorgente dello shader di cui e' richiesta la compilazione
     * @return 0 se non compilato, altrimenti l'ID del file oggetto creato.
     * NOTA: 0 e non null o un eccezzione  perche' e' una informazione che usa OpenGL a basso livello.
     */
    private static int compliShader(int type, String shaderCode){
        final int shaderObjectID = GLES20.glCreateShader(type);

        if(shaderObjectID == 0){
            if(LogStatusManager.SHADER_MANAGER_LOG_ON){
                Log.w(TAG, "\nCould not create new shader.\n Type: " + type + "\n" + shaderCode);
            }

            return 0;
        }

        //Upload del codice sorgente all'interno del file oggetto creato.
        //Serve ad OpenGL per associare il sorgente al refernce dell'oggetto creato.
        GLES20.glShaderSource(shaderObjectID, shaderCode);

        //Compilazione vera e propria.
        //OpenGL effettua la complizaione grazie all'associazione fatta sopra.
        GLES20.glCompileShader(shaderObjectID);

        //Recupero le informazioni sullo stato della compilazione.
        //Il metodo chiede ad OpenGL di leggere lo stato, e di inseirlo nella posizione 0.
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectID, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if(LogStatusManager.SHADER_MANAGER_LOG_ON){
            Log.d(TAG, "\nResult of compiling source: \n" + shaderCode + "\n" +
                    GLES20.glGetShaderInfoLog(shaderObjectID));
        }

        if(compileStatus[0] == 0){
            GLES20.glDeleteShader(shaderObjectID);
            Log.w(TAG, "\nCompilation of shader failed");
            return 0;
        }

        // Se non è riuscita la compilazione, si eliminare il file oggetto.
        // Questo fa si che OpenGL restituisce 0 alla chiamata di quell'oggetto,
        // invece di eseguire qualcosa che non ha.

        return shaderObjectID;
    }

    /**
     * Metodo per il linking di un VertexShader e un FragmentShader gia' compilati.
     * @param vertexShaderID reference al file oggetto del VertexShader.
     * @param fragmentShaderID reference al file oggetto del FRamgmenShader.
     * @return reference al file oggetto del programma con i due shader linkati.
     * NOOTA: Devono lavorare sempre in coppia:
     * - Senza FragmenteShader, una volta calcolata la posizone finale
     *   dei vertici OpenGL non sapra' come coloarli
     * - Senza VertexShader, OpenGL non saprà dove posizione i fragment* .
     *
     * *Brevemente: Un fragment non è esattamente "un pixel", ma per rendere l'idea si può fare il paragone.
     *  In realta' un fragmente non è altro che una cella su cui viene disegnato UN colore ( come i pixel... )
     *  con la differenza che questa cella e' di dimensioni variabili, e segue la geometria del poligono,
     *  mentre il pixel lo si ottiene tramite proiezione da 3D a 2D sullo Schermo.
     */
    public static int linkProgram( int vertexShaderID, int fragmentShaderID){

        //Richiesta di una file oggetto.
        final int programObjectID = GLES20.glCreateProgram();

        if(programObjectID == 0){
            if(LogStatusManager.SHADER_MANAGER_LOG_ON){
                Log.w(TAG, "Could not create program.");
            }

            return 0;
        }

        //Accodati i due shader all'interno di unico file oggetto.
        GLES20.glAttachShader(programObjectID, vertexShaderID);
        GLES20.glAttachShader(programObjectID, fragmentShaderID);

        //Unione dei due shader all'interno di unico file oggetto.
        GLES20.glLinkProgram(programObjectID);

        final int[] linkStatus = new int[1];

        //Recupero le informazioni sullo stato del linking.
        //Il metodo chiede ad OpenGL di leggere lo stato, e di inseirlo nella posizione 0.
        GLES20.glGetProgramiv(programObjectID, GLES20.GL_LINK_STATUS, linkStatus, 0);

        if(linkStatus[0] == 0){
            GLES20.glDeleteProgram(programObjectID);
            if(LogStatusManager.SHADER_MANAGER_LOG_ON){
                Log.v(TAG, "Linking of program failed");
            }
            return 0;
        }
        return programObjectID;
    }

    /**
     * Metodo per la verifica del file oggetto del programma contenente vertex e
     * fragment shader gia' linkati.
     * @param programObjectID
     * @return
     */
    public static boolean validateProgram(int programObjectID){
        GLES20.glValidateProgram(programObjectID);
        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectID, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);

        Log.v(TAG, "\nResult of validating program: " + validateStatus[0] +
                "\nLog:" + GLES20.glGetProgramInfoLog(programObjectID));
        return validateStatus[0] != 0;
    }

    /**
     * Metodo che effettua in automatico tutte le procedure necessario per ottenere
     * un programma utilizzabile per le draw in OpenGL.
     * @param vertexShaderCode sorcente del vertex shader
     * @param fragmentShaderCode sorgente del fragment shader
     * @return reference del fle oggetto del program che ne risulta
     */
    public static int buildProgram(String vertexShaderCode, String fragmentShaderCode){

        int program;

        //Compilazione degli Shaders.
        int vertexShader = compileVertexSHader(vertexShaderCode);
        int fragmentShader = compileFragmentSHader(fragmentShaderCode);

        program = linkProgram(vertexShader, fragmentShader);

        if(LogStatusManager.SHADER_MANAGER_LOG_ON){
            validateProgram(program);
        }
        return program;
    }
}
