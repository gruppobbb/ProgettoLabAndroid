package com.progettolab.game3D.engine;

import com.progettolab.game3D.entities.Mob3D;

import java.util.Random;

import model.Coordinate;
import model.mobs.Mob;
import model.ships.Ship;
import model.spawning.SpawnLogic;

/**
 * @author Max
 * Logica di spawning che si basa sull'utilizzo di variabili casuali gaussiane per generare le coordinate dei nuovi mob.
 * Tali variabili sono centrate nei valori x e y della ship ed hanno una deviazione standard specificata.
 */
public class GaussianSpawnLogic implements SpawnLogic  {

    public static final float MOB_MAX_SPEED = 0.8f;
    public static final float MOB_INITIAL_SPEED = 0.12f;
    public static final float MOB_SPEED_INCREMENT = 0.0001f;
    public static final double MOB_COLLISION_RAY = 0.5;
    public int simultaneousMobs;
    private float stdev;
    private float initialZ;
    private Random r;
    private float mobSpeed;
    private Ship ship;

    /**
     * @param ship Ship comandata dall'utente
     * @param stdev Deviazione standard della gaussiana utilizzata per lo spawning
     * @param simultaneousMobs Numero di mob che spawnano contemporaneamente a seguito di una chiamata a spawnMob()
     * @param initialZ Coordinata Z iniziale dei nuovi mob
     */
    public GaussianSpawnLogic(Ship ship, float stdev, int simultaneousMobs, float initialZ) {
        this.ship = ship;
        this.stdev = stdev;
        this.simultaneousMobs = simultaneousMobs;
        this.initialZ = initialZ;

        r = new Random();
        mobSpeed = MOB_INITIAL_SPEED;
    }

    /**
     * Spawna un insieme di mob e restituisce un vettore di reference ad essi.
     * @return un vettore di reference ai mob istanziati
     */
    @Override
    public Mob[] spawnMob() {
        Coordinate coord = ship.getCoordinate();

        Mob[] mobs = new Mob[simultaneousMobs];
        for (int i = 0; i < mobs.length ; i++) {

            float x = stdev * (float)r.nextGaussian() + coord.getX();
            float y = stdev * (float)r.nextGaussian() + coord.getY();
            Coordinate newMobCoord = new Coordinate( x, y, initialZ);
            mobs[i] = new Mob3D(newMobCoord, mobSpeed);
            mobs[i].setCollisionRay(MOB_COLLISION_RAY);
        }

        if(mobSpeed < MOB_MAX_SPEED) {
            mobSpeed += MOB_SPEED_INCREMENT;
        }

        return mobs;
    }

}
