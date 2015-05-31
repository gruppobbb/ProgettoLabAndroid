package com.example.alessandro.computergraphicsexample.game3D.objectsModel.game;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.R;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

/**
 * @author Jancarlos.
 */
public class ShipModel extends Model {

    public static final String MODEL_ID = "ShipModel";

    public ShipModel(Context context) {

        super(  context.getString(R.string.ship_model_path),
                context.getString(R.string.ship_texture_path));

        setShineDamper(15f);

        setReflectivity(1f);

    }

}
