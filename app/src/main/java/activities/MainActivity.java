package activities;

import android.app.Activity;
import android.os.Bundle;

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

        final Mob3D test = new Mob3D(new Coordinate(0.0f, 5.0f, -1.0f), 0.0f);
        mobsManager.addMob(test);

        final Mob3D test2 = new Mob3D(new Coordinate(0.0f, -5.0f, -5.0f), 0.0f);
        mobsManager.addMob(test2);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                test.shiftAngle(0.0f, (float)Math.PI/180f, 0.0f);
            }
        }, 17, 17);


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