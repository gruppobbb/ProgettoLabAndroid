package com.example.alessandro.computergraphicsexample.game3D.objectsModel.game;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

/**
 * @author Jancarlos.
 */
public class MobsModel extends Model {

    public static final String MODEL_ID = "MobsModel";

    public MobsModel(Context context) {

        super("realMob.obj", "blue.jpg");

        setShineDamper(10.0f);

        setReflectivity(1.0f);

    }

}
