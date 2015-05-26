package com.example.alessandro.computergraphicsexample.alternative.engine;

import com.example.alessandro.computergraphicsexample.alternative.entities.AlternativeMob3D;
import com.example.alessandro.computergraphicsexample.model3D.Mob3D;

import java.util.Random;

import model.Coordinate;
import model.mobs.Mob;
import model.spawning.SpawnLogic;

/**
 * @author Max
 */
public class AlternativeSimpleSpawnLogic implements SpawnLogic  {

    private float widthBound;
    private float heightBound;
    private float initialZ;
    private Random r = new Random();

    public AlternativeSimpleSpawnLogic(float widthBound, float heightBound, float initialZ) {
        this.widthBound = widthBound;
        this.heightBound = heightBound;
        this.initialZ = initialZ;
    }

    @Override
    public Mob[] spawnMob() {
        Mob[] mob = new Mob[1];
        Coordinate newMobCoord = new Coordinate(r.nextFloat() * heightBound * 2 - heightBound, r.nextFloat() * widthBound * 2 - widthBound, initialZ);
        mob[0] = new AlternativeMob3D(newMobCoord, 0.5f);
        mob[0].setCollisionRay(5.0);

        return mob;
    }


}
