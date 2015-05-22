package activities;

import android.app.Activity;
import android.os.Bundle;

import model.mobs.Mob;
import model.spawning.SpawnLogic;
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