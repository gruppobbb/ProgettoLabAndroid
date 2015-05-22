package model3D;

import java.util.Observable;
import java.util.Observer;

import model.mobs.Mob;
import model.spawning.SpawnLogic;

/**
 * Created by Max on 22/05/2015.
 */
public class SimpleSpawnLogic implements SpawnLogic, Observer  {

    private float upperBound;
    private float lowerBound;
    private float rightBound;
    private float leftBound;


    @Override
    public Mob[] spawnMob() {
        return new Mob[0];
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
