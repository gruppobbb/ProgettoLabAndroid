package com.example.alessandro.computergraphicsexample.game3D.engine;

import com.example.alessandro.computergraphicsexample.game3D.entities.Mob3D;

import java.util.Random;

import model.Coordinate;
import model.mobs.Mob;
import model.ships.Ship;
import model.spawning.SpawnLogic;

/**
 * @author Max
 */
public class MoreDenseSpawnLogic implements SpawnLogic  {

    private static final float MAX_MOB_SPEED = 0.6f;
    private static final float STANDARD_DEVIATION = 5.5f;
    private float initialZ;
    private Random r = new Random();
    private float mobSpeed;
    private Ship ship;

    public MoreDenseSpawnLogic(Ship ship, float initialZ) {
        this.initialZ = initialZ;
        this.ship = ship;

        mobSpeed = 0.05f;
    }

    @Override
    public Mob[] spawnMob() {
        Coordinate coord = ship.getCoordinate();
        float x = STANDARD_DEVIATION * (float)r.nextGaussian() + coord.getX();
        float y = STANDARD_DEVIATION * (float)r.nextGaussian() + coord.getY();

        Mob[] mob = new Mob[1];
        Coordinate newMobCoord = new Coordinate( x, y, initialZ);
        mob[0] = new Mob3D(newMobCoord, mobSpeed);
        mob[0].setCollisionRay(0.5);

        if(mobSpeed < MAX_MOB_SPEED) {
            mobSpeed += 0.0001f;
        }

        return mob;
    }

}
