package com.example.alessandro.computergraphicsexample.game3D.objectsModel.game;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.R;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

/**
 * Modello 3D dei mob.
 * @author Jancarlos.
 */
public class MobsModel extends Model {

    public static final String MODEL_ID = "MobsModel";

    /**
     * @param context
     */
    public MobsModel(Context context) {

        super(context.getString(R.string.mob_model_path),
                context.getString(R.string.mob_texture_path));

        setShineDamper(10.0f);

        setReflectivity(1.0f);

    }

}
