package com.progettolab.game3D.graphics.shader;


import android.content.Context;

import com.progettolab.game3D.managers.ShaderManager;
import com.progettolab.game3D.managers.TextFileManager;
import com.progettolab.game3D.objectsModel.Model;
import com.progettolab.game3D.objectsModel.ModelData;

/**
 * Classe per la gestione degli shader.
 * Created by Jancarlos.
 */
public abstract class ShaderProgram {

    //Uniforms
    public static final String U_P_MATRIX = "u_PMatrix";
    public static final String U_V_MATRIX = "u_VMatrix";
    public static final String U_I_V_MATRIX = "u_InverseVMatrix";
    public static final String U_M_MATRIX = "u_MMatrix";
    public static final String U_TEXTURE_UNIT = "u_TextureUnit";
    public static final String U_SHINEDAMPER = "u_ShineDamper";
    public static final String U_REFLECTIVITY = "u_Reflectivity";

    //Attributes
    public static final String A_POSITION = "a_Position";
    public static final String A_NORMAL = "a_Normal";
    public static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    private int program;

    /**
     * @param context Contesto
     * @param vertexShaderResourceID ID della risorsa contenente il sorgente del vertex shader
     * @param fragmentShaderResourceID ID della risorsa contenente il sorgente del fragment shader
     */
    public ShaderProgram(Context context, int vertexShaderResourceID,
                         int fragmentShaderResourceID){

        program = ShaderManager.buildProgram(
                TextFileManager.readFileFromResources(context, vertexShaderResourceID),
                TextFileManager.readFileFromResources(context, fragmentShaderResourceID)
        );
    }

    /**
     * Restituisce l'ID univoco del programma.
     * @return ID univoco del programma
     */
    public int getProgramID() {
        return program;
    }

    /**
     * Associazione dei dati tramite i reference agli uniform dello shader.
     * @param data {@link ModelData}
     */
    public abstract void bindData(ModelData data);

    /**
     * Caricamento in memoria della ProjectionMatrix.
     * @param mProjectionMatrix Matrice di proiezione
     */
    public abstract void loadProjectionMatrix(float[] mProjectionMatrix);

    /**
     * Caricamento in memoria della ViewMatrix.
     * @param mViewMatrix View matrix
     */
    public abstract void loadViewMatrix(float[] mViewMatrix);

    /**
     * Caricamento in memoria degli uniform specifici del modello.
     * @param model Modello 3D
     */
    public abstract void loadPerModelUniform(Model model);

    /**
     * Caricamento in memoria della sola ModelMatrix per un modello precaricato in memoria.
     * @param modelMatrix Model matrix
     */
    public abstract void loadModelMatrix(float[] modelMatrix);

}
