package com.example.alessandro.computergraphicsexample.game3D.objectsModel.game;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.R;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

/**
 * Modello 3D della ship.
 * @author Jancarlos.
 */
public class ShipModel extends Model {

    public static final String MODEL_ID = "ShipModel";

    /**
     * Costruisce un {@link Model} per il disegno di una {@link model.ships.Ship}
     * @param context context context activity nel quale si sta istanziando il modello.
     */
    public ShipModel(Context context) {

        super(  context.getString(R.string.ship_model_path),
                context.getString(R.string.ship_texture_path));

        setShineDamper(15f);

        setReflectivity(1f);
    }

}
