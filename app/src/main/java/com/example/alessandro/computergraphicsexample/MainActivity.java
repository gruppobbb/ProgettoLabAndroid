package com.example.alessandro.computergraphicsexample;

import android.app.Activity;
import android.os.Bundle;

import model.Coordinate;
import model.MobsManager;
import model3D.Mob3D;


public class MainActivity extends Activity {

    private  GraphicsView graphicsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobsManager mobsManager = new MobsManager();

        mobsManager.addMob(new Mob3D(new Coordinate(0.0f, 0.0f, -5.0f), 0.0f));


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
