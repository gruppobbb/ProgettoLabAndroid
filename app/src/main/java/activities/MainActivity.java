package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Surface;

import control.ShipController;
import model.GameEngine;
import model.mobs.Mob;
import model.spawning.SpawnLogic;
import model3D.Ship3D;
import model.spawning.Spawner;
import model3D.Ship3D;
import model3D.SimpleSpawnLogic;
import view.GraphicsRenderer;
import view.GraphicsView;

import java.util.Timer;
import java.util.TimerTask;

import model.Coordinate;
import model.MobsManager;
import model3D.Mob3D;


public class MainActivity extends Activity {

    private GraphicsView graphicsView;
    private GraphicsRenderer renderer;
    private MobsManager mobsManager;

    private Ship3D ship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphicsView = new GraphicsView(this);

        mobsManager = new MobsManager();

        ship = new Ship3D(0.0f, 0.0f, -10.0f);

        ShipController controller = new ShipController(ship, 0.1f);
        graphicsView.setOnTouchListener(controller);

        renderer = new GraphicsRenderer(this, mobsManager, ship );
        renderer.addObserver(controller);


        //SPAWNING
        SpawnLogic spawnLogic = new SimpleSpawnLogic(20.0f, 20.0f, -95.0f);
        Spawner spawner = new Spawner(mobsManager, spawnLogic);
        spawner.setSleepTime(500);

        (new Thread(spawner)).start();


        //GAME ENGINE
        GameEngine gameEngine = new GameEngine(mobsManager, ship, new Coordinate(100, 100, -3));
        (new Thread((gameEngine))).start();


        graphicsView.setRenderer(renderer);
        setContentView(graphicsView);
    }


    @Override
    protected void onPause() {
        super.onPause();
        graphicsView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        graphicsView.onResume();
    }
}