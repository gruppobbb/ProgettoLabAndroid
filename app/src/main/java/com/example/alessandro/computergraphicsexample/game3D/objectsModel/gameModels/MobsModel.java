package com.example.alessandro.computergraphicsexample.game3D.objectsModel.gameModels;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.game3D.graphics.shader.CompleteShaderProgram;
import com.example.alessandro.computergraphicsexample.game3D.graphics.shader.ShaderProgram;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

/**
 * @author Jancarlos.
 */
public class MobsModel extends Model {

    private Context context;

    public MobsModel(Context context) {

        super("mobship.obj", "gold.jpg");

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
