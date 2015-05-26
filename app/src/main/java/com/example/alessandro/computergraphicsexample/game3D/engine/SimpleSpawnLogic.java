package com.example.alessandro.computergraphicsexample.game3D.engine;

import com.example.alessandro.computergraphicsexample.game3D.entities.Mob3D;

import java.util.Random;

import model.Coordinate;
import model.mobs.Mob;
import model.spawning.SpawnLogic;

/**
 * @author Max
 */
public class SimpleSpawnLogic implements SpawnLogic  {

    private static final float MAX_MOB_SPEED = 0.6f;
    private float widthBound;
    private float heightBound;
    private float initialZ;
    private Random r = new Random();
    private float mobSpeed;

    public SimpleSpawnLogic(float widthBound, float heightBound, float initialZ) {
        this.widthBound = widthBound;
        this.heightBound = heightBound;
        this.initialZ = initialZ;

        mobSpeed = 0.05f;
    }

    @Override
    public Mob[] spawnMob() {
        Mob[] mob = new Mob[1];
        Coordinate newMobCoord = new Coordinate( r.nextFloat() * widthBound * 2 - widthBound, r.nextFloat() * heightBound * 2 - heightBound, initialZ);
        mob[0] = new Mob3D(newMobCoord, mobSpeed);
        mob[0].setCollisionRay(0.5);

        if(mobSpeed < MAX_MOB_SPEED) {
            mobSpeed += 0.0001f;
        }

        return mob;
    }

}
