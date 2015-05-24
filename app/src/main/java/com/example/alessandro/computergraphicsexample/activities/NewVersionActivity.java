package com.example.alessandro.computergraphicsexample.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.alessandro.computergraphicsexample.alternative.engine.AlternativeSimpleSpawnLogic;
import com.example.alessandro.computergraphicsexample.alternative.entities.AlternativeShip3D;
import com.example.alessandro.computergraphicsexample.alternative.entities.GameCamera;
import com.example.alessandro.computergraphicsexample.alternative.entities.Light;
import com.example.alessandro.computergraphicsexample.alternative.graphics.core.GameRenderer;
import com.example.alessandro.computergraphicsexample.alternative.graphics.core.GameSurface;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import model.Coordinate;
import model.GameEngine;
import model.MobsManager;
import model.mobs.Mob;
import model.movement.Moveable;
import model.spawning.SpawnLogic;
import model.spawning.Spawner;

public class NewVersionActivity extends Activity {

    private GameSurface surface;
    private GameRenderer renderer;

    private Spawner spawner;
    private GameEngine gameEngine;
    private Thread spawnerThread;
    private Thread gameEngineThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Coordinate shipCoordinate = new Coordinate(0.0f, 0.0f, -5.0f);
        final AlternativeShip3D ship = new AlternativeShip3D(shipCoordinate);
        //ship.setAngle(0.0f, 180.0f, 0.0f);

        Coordinate cameraCoordinate = new Coordinate(
                shipCoordinate.getX(),
                shipCoordinate.getY()+5,
                shipCoordinate.getZ()
                );

        //GameCamera camera = new GameCamera(new Coordinate(0.0f, 5.0f, 5.0f), shipCoordinate );
        //GameCamera camera = new GameCamera(new Coordinate(0.0f, 5.0f, 5.0f), new Coordinate(0,0,0) );
        GameCamera camera = new GameCamera(new Coordinate(0.0f, 10.0f, 10.0f), cameraCoordinate );


        final MobsManager mobsManager = new MobsManager();

        SpawnLogic spawnLogic = new AlternativeSimpleSpawnLogic(45.0f, 45.0f, -95.0f);
        spawner = new Spawner(mobsManager, spawnLogic);
        spawner.setSleepTime(50);
        spawnerThread = new Thread(spawner);

        //GAME ENGINE
        gameEngine = new GameEngine(mobsManager, ship, new Coordinate(100, 100, -3));
        gameEngineThread = new Thread(gameEngine);


        Light sunLight = new Light(new Coordinate(0.0f, 20.0f, 20.0f));

        surface = new GameSurface(this);
        renderer = new GameRenderer(this, mobsManager, camera,sunLight, ship );
        surface.setRenderer(renderer);


        spawnerThread.start();
        gameEngineThread.start();

        /*
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //ship.shiftAngle(0.0f, 1.0f, 0.0f);
                ArrayList<Mob> mobs = mobsManager.getMobsList();

                for(Mob mob: mobs){
                    ((Moveable)mob).move();
                }

            }
        }, 17 ,17);

        */


        setContentView(surface);
    }

    @Override
    protected void onResume() {
        super.onResume();

        gameEngine.onResume();
        spawner.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

        spawner.onPause();
        gameEngine.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        spawner.setToKill(true);
        gameEngine.setToKill(true);
    }
}
