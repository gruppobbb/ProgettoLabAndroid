package com.example.alessandro.computergraphicsexample.game3D.graphics.shader;


import android.content.Context;

import com.example.alessandro.computergraphicsexample.game3D.entities.GameCamera;
import com.example.alessandro.computergraphicsexample.game3D.entities.Light;
import com.example.alessandro.computergraphicsexample.game3D.managers.JShaderManager;
import com.example.alessandro.computergraphicsexample.game3D.managers.JTextFileManager;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.obj.ModelData;

/**
 * Created by Jancarlos.
 */
public abstract class ShaderProgram {

    //Uniforms
    public static final String U_P_MATRIX = "u_PMatrix";
    public static final String U_V_MATRIX = "u_VMatrix";
    public static final String U_I_V_MATRIX = "u_InverseVMatrix";
    public static final String U_M_MATRIX = "u_MMatrix";
    public static final String U_TEXTURE_UNIT = "u_TextureUnit";
    public static final String U_LIGHT_POSITION = "u_LightPos";
    public static final String U_LIGHT_COLOR = "u_LightColor";
    public static final String U_SHINEDAMPER = "u_ShineDamper";
    public static final String U_REFLECTIVITY = "u_Reflectivity";

    //Attributes
    public static final String A_POSITION = "a_Position";
    public static final String A_NORMAL = "a_Normal";
    public static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    private int program;

    public ShaderProgram(Context context, int vertexShaderResourceID,
                         int fragmentShaderResourceID){

        program = JShaderManager.buildProgram(
                JTextFileManager.readFileFromResources(context, vertexShaderResourceID),
                JTextFileManager.readFileFromResources(context, fragmentShaderResourceID)
        );
    }

    public int getProgramID() {
        return program;
    }

    public abstract void bindData(ModelData data);

    public abstract void loadGlobalUniforms(float[] mProjectionMatrix, GameCamera camera, Light Sunlight);

    public abstract void loadPerModelUniform(Model model);

    public abstract void loadModelMatrix(float[] modelMatrix);

}
