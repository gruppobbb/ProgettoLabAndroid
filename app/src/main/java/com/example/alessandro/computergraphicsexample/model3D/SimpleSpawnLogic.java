package com.example.alessandro.computergraphicsexample.model3D;

import java.util.Random;

import model.Coordinate;
import model.mobs.Mob;
import model.spawning.SpawnLogic;

/**
 * @author Max
 */
public class SimpleSpawnLogic implements SpawnLogic  {

    private float widthBound;
    private float heightBound;
    private float initialZ;
    private Random r = new Random();

    public SimpleSpawnLogic(float widthBound, float heightBound, float initialZ) {
        this.widthBound = widthBound;
        this.heightBound = heightBound;
        this.initialZ = initialZ;
    }

    @Override
    public Mob[] spawnMob() {
        Mob[] mob = new Mob[1];
        Coordinate newMobCoord = new Coordinate(r.nextFloat() * heightBound * 2 - heightBound, r.nextFloat() * widthBound * 2 - widthBound, initialZ);
        mob[0] = new Mob3D(newMobCoord, 0.1f);

        return mob;
    }


}
