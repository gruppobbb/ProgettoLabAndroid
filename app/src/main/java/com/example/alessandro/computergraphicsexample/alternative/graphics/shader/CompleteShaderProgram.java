package com.example.alessandro.computergraphicsexample.alternative.graphics.shader;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.alessandro.computergraphicsexample.R;

import com.example.alessandro.computergraphicsexample.alternative.entities.GameCamera;
import com.example.alessandro.computergraphicsexample.alternative.entities.Light;
import com.example.alessandro.computergraphicsexample.alternative.objectsModel.Model;
import com.example.alessandro.computergraphicsexample.alternative.objectsModel.obj.ModelData;


/**
 * Created by Jancarlos.
 */
public class CompleteShaderProgram extends ShaderProgram {

    //referece per gli uniform
    private int u_ProjectionMatrixLocation;
    private int u_ViewMatrixLocation;
    private int u_InverseViewMatrixLocation;
    private int u_ModelMatrixLocation;

    private int u_TextureUnitLocation;
    private int u_LightColorPosition;
    private int u_LightPosLocation;
    private int u_ShineDumperLocation;
    private int u_ReflectivityLocation;

    //reference per gli attributi
    private int aPositionLocation;
    private int aTextureCoordinatesLocation;
    private int aNormalLocation;

    /**
     * Costruttore di un program per l'utilizzo di textures.
     */
    public CompleteShaderProgram(Context context){
        super(context, R.raw.diffuse_specular_vertex_shader, R.raw.diffuse_specular_fragment_shader);

        //Recupero dei reference verso gli uniform definiti nel program.
        u_ProjectionMatrixLocation = GLES20.glGetUniformLocation(getProgramID(), U_P_MATRIX);
        u_ViewMatrixLocation = GLES20.glGetUniformLocation(getProgramID(), U_V_MATRIX);
        u_InverseViewMatrixLocation = GLES20.glGetUniformLocation(getProgramID(), U_I_V_MATRIX);
        u_ModelMatrixLocation = GLES20.glGetUniformLocation(getProgramID(), U_M_MATRIX);

        u_TextureUnitLocation = GLES20.glGetUniformLocation(getProgramID(), U_TEXTURE_UNIT);
        u_LightPosLocation = GLES20.glGetUniformLocation(getProgramID(), U_LIGHT_POSITION);
        u_LightColorPosition = GLES20.glGetUniformLocation(getProgramID(), U_LIGHT_COLOR);

        u_ShineDumperLocation = GLES20.glGetUniformLocation(getProgramID(), U_SHINEDAMPER);
        u_ReflectivityLocation = GLES20.glGetUniformLocation(getProgramID(), U_REFLECTIVITY);

        //Recupero dei reference verso gli attributi definiti nel program.
        aPositionLocation = GLES20.glGetAttribLocation(getProgramID(), A_POSITION);
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(getProgramID(), A_TEXTURE_COORDINATES);
        aNormalLocation = GLES20.glGetAttribLocation(getProgramID(), A_NORMAL);

    }

    @Override
    public void bindData(ModelData data) {
        data.directSetVertexAttributePointer(aPositionLocation);

        if(data.getTextureBuffer() != null){
            data.directSetTextureAttributePointer(aTextureCoordinatesLocation);
        }

        if(data.getNormalBuffer() != null){
            data.directSetNormalAttributePointer(aNormalLocation);
        }

    }

    @Override
    public void loadGlobalUniforms(float[] mProjectionMatrix, GameCamera camera, Light light){

        //Passaggio della matrice di Proiezione
        GLES20.glUniformMatrix4fv(u_ProjectionMatrixLocation, 1, false, mProjectionMatrix, 0);

        //Passaggio della view matrix
        GLES20.glUniformMatrix4fv(u_ViewMatrixLocation, 1, false, camera.getViewMatrix(), 0);

        //Passaggio della view matrix invertita*
        float[] mInverseViewMatrix = new float[16];
        Matrix.invertM(mInverseViewMatrix, 0, camera.getViewMatrix(), 0);
        GLES20.glUniformMatrix4fv(u_InverseViewMatrixLocation, 1, false, mInverseViewMatrix, 0);

        //Passaggio della posizione della luce principale.
        GLES20.glUniform3fv(u_LightPosLocation, 0, light.getPositionVector(), 0);

        //Passaggio del colore della luce principale.
        GLES20.glUniform4fv(u_LightColorPosition, 0, light.getColor(), 0);

        /*

        * E' possibile calcolarla dirattamente nel vertex shader, ma oltre al fatto
             che è computazionalmente pesante da calcolare, la funzione gls che la colcola
             e' stata introdotta da OpenGL3.

         */

    }

    @Override
    public void loadPerModelUniform(Model model){

        bindData( model.getData() );
        //Attiva la texture unit sulla texture init 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        //Associa la texture alla unit 0.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, model.getTexture());

        //Viene detto al TextureSampler di utilizzare la texture associata,
        // e di leggerla dalla texture unit 0.
        GLES20.glUniform1i(u_TextureUnitLocation, 0);

        //Passaggio del valore ShineDamper
        GLES20.glUniform1f(u_ShineDumperLocation, model.getShineDamper());

        //Passaggio del valore Reflectivity
        GLES20.glUniform1f(u_ReflectivityLocation, model.getReflectivity());
    }

    @Override
    public void loadModelMatrix(float[] modelMatrix){
        //Passaggio della model matrix
        GLES20.glUniformMatrix4fv(u_ModelMatrixLocation, 1, false, modelMatrix, 0);
    }
}