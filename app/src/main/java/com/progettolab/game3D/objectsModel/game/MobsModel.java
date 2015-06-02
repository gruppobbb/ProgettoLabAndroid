package com.progettolab.game3D.objectsModel.game;

import android.content.Context;

import com.progettolab.R;
import com.progettolab.game3D.objectsModel.Model;

import model.mobs.Mob;

/**
 * {@link Model} impostato per il caricamento e setting del modello 3d dei Mob.
 * @author Jancarlos.
 */
public class MobsModel extends Model {

    public static final String MODEL_ID = "MobsModel";

    /**
     * Costruisce un {@link Model} per il disegno dei {@link Mob}
     * @param context context activity nel quale si sta istanziando il modello.
     */
    public MobsModel(Context context) {

        super(context.getString(R.string.mob_model_path),
                context.getString(R.string.mob_texture_path));

        setShineDamper(20.0f);

        setReflectivity(1.0f);

    }

}
