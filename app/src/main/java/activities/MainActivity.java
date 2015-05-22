package activities;

import android.app.Activity;
import android.os.Bundle;

import model.GameEngine;
import model.mobs.Mob;
import model.spawning.SpawnLogic;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobsManager mobsManager = new MobsManager();


        graphicsView = new GraphicsView(this);
        graphicsView.setRenderer(new GraphicsRenderer(this, mobsManager));
        setContentView(graphicsView);


        SpawnLogic spawnLogic = new SimpleSpawnLogic(45.0f, 45.0f, -95.0f);
        Spawner spawner = new Spawner(mobsManager, spawnLogic);
        spawner.setSleepTime(500);

        (new Thread(spawner)).start();



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