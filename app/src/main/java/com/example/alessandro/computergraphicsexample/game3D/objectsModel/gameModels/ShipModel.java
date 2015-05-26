package com.example.alessandro.computergraphicsexample.game3D.objectsModel.gameModels;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.game3D.graphics.shader.CompleteShaderProgram;
import com.example.alessandro.computergraphicsexample.game3D.graphics.shader.ShaderProgram;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

/**
 * @author Jancarlos.
 */
public class ShipModel extends Model {

    private Context context;

    public ShipModel(Context context) {

        super("MyShip_H.obj", "debug.jpg");

        this.context = context;

        setShineDamper(2.0f);

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
