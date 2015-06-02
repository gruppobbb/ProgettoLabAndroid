package com.progettolab.game3D.objectsModel.game;

import android.content.Context;

import com.progettolab.R;
import com.progettolab.game3D.objectsModel.Model;

/**
 * {@link Model} impostato per il caricamento e setting del modello 3d della ship.
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
