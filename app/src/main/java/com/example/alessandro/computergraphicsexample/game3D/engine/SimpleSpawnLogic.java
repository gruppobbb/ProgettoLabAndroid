package com.example.alessandro.computergraphicsexample.game3D.engine;

import com.example.alessandro.computergraphicsexample.game3D.entities.Mob3D;

import java.util.Random;

import model.Coordinate;
import model.mobs.Mob;
import model.spawning.SpawnLogic;

/**
 * @author Max
 * Una semplice logica di spawning, che utilizza variabili casuali uniformi per selezionare le coordinate di spawn.
 * Non permette lo spawn simultaneo di piu' mob.
 */
public class SimpleSpawnLogic implements SpawnLogic  {

    public static final float MOB_MAX_SPEED = 0.6f;
    public static final float MOB_INITIAL_SPEED = 0.12f;
    public static final float MOB_SPEED_INCREMENT = 0.0001f;
    public static final double MOB_COLLISION_RAY = 0.5;
    private float widthBound;
    private float heightBound;
    private float initialZ;
    private Random r;
    private float mobSpeed;

    /**
     * @param widthBound Coordinata massima ammessa, lungo l'asse x
     * @param heightBound Coordinata massima ammessa, lungo l'asse y
     * @param initialZ Coordinata Z iniziale dei nuovi mob
     */
    public SimpleSpawnLogic(float widthBound, float heightBound, float initialZ) {
        this.widthBound = widthBound;
        this.heightBound = heightBound;
        this.initialZ = initialZ;

        r = new Random();
        mobSpeed = MOB_INITIAL_SPEED;
    }

    /**
     * Spawna un mob e restituisce un vettore unitario, contenente il reference al mob istanziato.
     * @return un vettore unitario contenente il reference al mob istanziato
     */
    @Override
    public Mob[] spawnMob() {
        Mob[] mob = new Mob[1];
        Coordinate newMobCoord = new Coordinate( r.nextFloat() * widthBound * 2 - widthBound, r.nextFloat() * heightBound * 2 - heightBound, initialZ);
        mob[0] = new Mob3D(newMobCoord, mobSpeed);
        mob[0].setCollisionRay(MOB_COLLISION_RAY);

        if(mobSpeed < MOB_MAX_SPEED) {
            mobSpeed += MOB_SPEED_INCREMENT;
        }

        return mob;
    }

}
