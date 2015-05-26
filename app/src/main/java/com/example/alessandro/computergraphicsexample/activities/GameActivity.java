package com.example.alessandro.computergraphicsexample.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.example.alessandro.computergraphicsexample.R;
import com.example.alessandro.computergraphicsexample.game3D.audio.AudioManager;
import com.example.alessandro.computergraphicsexample.game3D.audio.AudioPlayer;
import com.example.alessandro.computergraphicsexample.game3D.control.FreeTouchController;
import com.example.alessandro.computergraphicsexample.game3D.engine.MoreDenseSpawnLogic;
import com.example.alessandro.computergraphicsexample.game3D.engine.SimpleSpawnLogic;
import com.example.alessandro.computergraphicsexample.game3D.entities.Ship3D;
import com.example.alessandro.computergraphicsexample.game3D.entities.GameCamera;
import com.example.alessandro.computergraphicsexample.game3D.entities.Light;
import com.example.alessandro.computergraphicsexample.game3D.graphics.core.GameRenderer;
import com.example.alessandro.computergraphicsexample.game3D.graphics.core.GameSurface;

import java.util.Observable;
import java.util.Observer;

import model.Coordinate;
import model.GameEngine;
import model.MobsManager;
import model.spawning.SpawnLogic;
import model.spawning.Spawner;

public class GameActivity extends Activity implements Observer {

    private GameSurface surface;
    private GameRenderer renderer;

    private Spawner spawner;
    private GameEngine gameEngine;
    private Thread spawnerThread;
    private Thread gameEngineThread;

    private AudioPlayer explosionPlayer;
    private AudioPlayer backgroundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AudioManager.getInstance().addResource("Explosion_sound", R.raw.ship_explosion);
        AudioManager.getInstance().addResource("Background_sound", R.raw.singleplayer);

        explosionPlayer = new AudioPlayer(this, "Explosion_sound", false);
        backgroundPlayer = new AudioPlayer(this, "Background_sound", true);

        Coordinate shipCoordinate = new Coordinate(0.0f, 0.0f, -1.0f);
        final Ship3D ship = new Ship3D(shipCoordinate);
        ship.setCollisionRay(0.75f);

        Coordinate cameraCoordinate = new Coordinate(
                shipCoordinate.getX(),
                shipCoordinate.getY()+1,
                shipCoordinate.getZ()
                );

        //GameCamera camera = new GameCamera(new Coordinate(0.0f, 5.0f, 5.0f), shipCoordinate );
        //GameCamera camera = new GameCamera(new Coordinate(0.0f, 5.0f, 5.0f), new Coordinate(0,0,0) );
        GameCamera camera = new GameCamera(new Coordinate(0.0f, 1.0f, 1.0f), cameraCoordinate );


        final MobsManager mobsManager = new MobsManager();

        //SpawnLogic spawnLogic = new SimpleSpawnLogic(20.0f, 10.0f, -50f);
        SpawnLogic spawnLogic = new MoreDenseSpawnLogic(ship, -60f);
        spawner = new Spawner(mobsManager, spawnLogic);
        spawner.setSleepTime(200);
        spawnerThread = new Thread(spawner);

        //GAME ENGINE
        gameEngine = new GameEngine(mobsManager, ship, new Coordinate(100, 100, -1));
        //gameEngine.setDebugMode(true);
        gameEngine.addObserver(this);
        gameEngineThread = new Thread(gameEngine);


        Light sunLight = new Light(new Coordinate(0.0f, 20.0f, 20.0f));

        surface = new GameSurface(this);
        renderer = new GameRenderer(this, mobsManager, camera,sunLight, ship );
        surface.setRenderer(renderer);

        Rect bound = new Rect(-4, 0, 4, 4);
        surface.setOnTouchListener(new FreeTouchController(this, ship, bound, 0.08f));

        spawnerThread.start();
        gameEngineThread.start();

        setContentView(surface);
    }

    @Override
    protected void onResume() {
        super.onResume();

        gameEngine.onResume();
        spawner.onResume();
        backgroundPlayer.play();

    }

    @Override
    protected void onPause() {
        super.onPause();

        spawner.onPause();
        gameEngine.onPause();
        backgroundPlayer.pause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        spawner.setToKill(true);
        gameEngine.setToKill(true);
        backgroundPlayer.stop();
    }

    @Override
    public void update(Observable observable, Object data) {

        explosionPlayer.play();
        backgroundPlayer.stop();

        finish();

        Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }
}
