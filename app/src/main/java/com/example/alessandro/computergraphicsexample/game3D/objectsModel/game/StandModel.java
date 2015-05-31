package com.example.alessandro.computergraphicsexample.game3D.objectsModel.game;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.R;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

/**
 * {@link Model} impostato per il caricamento e setting del modello 3d dello Stand.
 * @author Jancarlos.
 */
public class StandModel extends Model {

    public static final String MODEL_ID = "StandModel";

    public StandModel(Context context) {
        super(context.getString(R.string.stand_model_path),
                context.getString(R.string.stand_texture_path));

        setShineDamper(10f);
        setReflectivity(1.0f);

    }

}
