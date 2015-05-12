package com.example.alessandro.computergraphicsexample;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    private  GraphicsView graphicsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphicsView = new GraphicsView(this);
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
