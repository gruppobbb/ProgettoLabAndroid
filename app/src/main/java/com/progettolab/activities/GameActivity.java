package com.progettolab.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewGroup;

import com.progettolab.game3D.audio.AudioManager;
import com.progettolab.game3D.audio.AudioPlayer;
import com.progettolab.game3D.control.FreeTouchController;
import com.progettolab.game3D.engine.GaussianSpawnLogic;
import com.progettolab.game3D.entities.GameCamera;
import com.progettolab.game3D.entities.Ship3D;
import com.progettolab.game3D.graphics.core.GameRenderer;
import com.progettolab.game3D.graphics.core.GameSurface;
import com.progettolab.game3D.graphics.hud.ScoreHud;
import com.progettolab.game3D.managers.ScreenManager;
import com.progettolab.R;

import java.util.Observable;
import java.util.Observer;

import model.Coordinate;
import model.GameEngine;
import model.MobsManager;
import model.scores.LocalScoreManager;
import model.scores.ManagerKeeper;
import model.scores.XMLLocalStatsManager;
import model.spawning.SpawnLogic;
import model.spawning.Spawner;

/**
 * Activity di gioco, che viene avviata all'inizio di ogni partita e ne permette lo svolgimento.
 * @author Max, Jancarlos
 */
public class GameActivity extends Activity implements Observer {

    private GameSurface surface;
    private GameRenderer renderer;
    private ScoreHud hud;
    private Spawner spawner;
    private GameEngine gameEngine;
    private Thread spawnerThread;
    private Thread gameEngineThread;
    private AudioPlayer explosionPlayer;
    private AudioPlayer backgroundPlayer;
    private Ship3D ship;
    private MobsManager mobsManager;
    private Coordinate shipCoordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGameElements();

        initGraphicsInterface();

        initAudio();

        //Attivazione FullScreen.
        ScreenManager.goFullScreen(this);

        setContentView(hud);
        addContentView(surface, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        startThreads();

    }

    private void startThreads(){
        spawnerThread.start();
        gameEngineThread.start();
    }

    private void initGraphicsInterface() {
        Coordinate cameraCoordinate = new Coordinate(
                shipCoordinate.getX(),
                shipCoordinate.getY()+1,
                shipCoordinate.getZ()
        );

        GameCamera camera = new GameCamera(new Coordinate(0.0f, 1.0f, 2.0f), cameraCoordinate);

        surface = new GameSurface(this);
        renderer = new GameRenderer(this, mobsManager, camera, ship );

        surface.setZOrderOnTop(true);
        surface.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        surface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surface.setRenderer(renderer);

        hud = new ScoreHud(this, gameEngine.getScoreCalculator() );
        hud.setBackgroundResource(R.drawable.black_space);


        Rect bound = new Rect(-2, -1, 2, 2);
        surface.setOnTouchListener(new FreeTouchController(this, ship, bound, 0.08f));
    }

    private void initGameElements() {
        ManagerKeeper.getInstance().setLocalStats(new XMLLocalStatsManager("localstats.xml"));
        ManagerKeeper.getInstance().setScoreManager(new LocalScoreManager("scorelist.xml"));

        shipCoordinate = new Coordinate(0.0f, 0.0f, 0.0f);
        ship = new Ship3D(shipCoordinate, 0.75);

        mobsManager = new MobsManager();

        //SPAWNER
        SpawnLogic spawnLogic = new GaussianSpawnLogic(ship, 5.0f, 4, -60f);
        spawner = new Spawner(mobsManager, spawnLogic);
        spawner.setSleepTime(500);
        spawnerThread = new Thread(spawner);

        //GAME ENGINE
        gameEngine = new GameEngine(mobsManager, ship, new Coordinate(100, 100, -1));
        //gameEngine.setDebugMode(true);
        gameEngine.addObserver(this);
        gameEngineThread = new Thread(gameEngine);
    }

    private void initAudio() {
        AudioManager.getInstance().addResource("Explosion_sound", R.raw.ship_explosion);
        AudioManager.getInstance().addResource("Background_sound", R.raw.singleplayer);

        explosionPlayer = new AudioPlayer(this, "Explosion_sound", false);
        backgroundPlayer = new AudioPlayer(this, "Background_sound", true);
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
        //Intent intent = new Intent(this, ScoreActivity.class);
    }

    @Override
    public void update(Observable observable, Object data) {

        explosionPlayer.play();
        surface.setFocusable(false);
        backgroundPlayer.stop();

        gameEngine.getScoreCalculator().convalidateScore();

        finish();

        //Questa chiamata viene fatta fuori dal main thread, quindi per tornare indietro
        // serve "ritornare " nel main thread.
        runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        }));
        /*
        Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
        */
    }
}
