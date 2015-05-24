package com.example.alessandro.computergraphicsexample.alternative.objectsModel.gameModels;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.alternative.graphics.shader.CompleteShaderProgram;
import com.example.alessandro.computergraphicsexample.alternative.graphics.shader.ShaderProgram;
import com.example.alessandro.computergraphicsexample.alternative.objectsModel.Model;

/**
 * @author Jancarlos.
 */
public class MobsModel extends Model {

    private Context context;

    public MobsModel(Context context) {

        super("SuzanneTextured.obj", "suzanne_manual_texture.png");

        this.context = context;

        setShineDamper(10.0f);

        setReflectivity(1.0f);
    }

    @Override
    public ShaderProgram getProgram() {

        ShaderProgram program = super.getProgram();

        if(program == null){
            program = new CompleteShaderProgram(context);
            setProgram(program);
        }

        return program;
    }
}
