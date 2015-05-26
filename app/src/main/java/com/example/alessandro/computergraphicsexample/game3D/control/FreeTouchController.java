package com.example.alessandro.computergraphicsexample.game3D.control;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.alessandro.computergraphicsexample.R;
import com.example.alessandro.computergraphicsexample.game3D.entities.Ship3D;

import java.util.Timer;
import java.util.TimerTask;

import model.Coordinate;

/**
 * @author Jancarlos.
 */
public class FreeTouchController implements View.OnTouchListener {

    private static final String TAG = "FreeTouchController";

    private Ship3D ship3D;

    private float mDensity;
    private float maxModulo;
    private float maxX, maxY;
    private float minX, minY;
    private float startX, startY;
    private float deltaX, deltaY;

    private boolean pressing;
    private Timer cTimer;
    private long cDelay;
    private float shiftScale;

    /**
     * Crea un controllo touch per una ship con una velocita' massima di maxModulo ( deve essere minore di 0.08 ).
     * @param activity activity in cui viene utilizzato il controllo
     * @param ship3D ship da controllare
     * @param bound limiti del controllo
     * @param maxModulo velocita' massima
     */
    public FreeTouchController(Activity activity, Ship3D ship3D, Rect bound, float maxModulo) {
        this.ship3D = ship3D;
        minX = bound.left;
        maxX = bound.right;

        minY = bound.top;
        maxY = bound.bottom;

        if(maxModulo > 0){
            this.maxModulo = maxModulo;
        }else{
            this.maxModulo = 0.08f;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mDensity = displayMetrics.density;



        cDelay = 17;
        shiftScale = 0.015f;

        cTimer = new Timer();
        cTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(pressing){
                    moveShip();
                }
            }
        }, cDelay, cDelay);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN: {
                startX = motionEvent.getX();
                startY = motionEvent.getY();

                pressing = true;

                break;
            }

            case MotionEvent.ACTION_MOVE: {

                deltaX =  chackDelta((-(startX - motionEvent.getX()) / mDensity / 2f ) * shiftScale );

                deltaY = chackDelta(((startY - motionEvent.getY()) / mDensity / 2f) * shiftScale);

                break;

            }

            case MotionEvent.ACTION_UP: {
                pressing = false;
                deltaX = 0.0f;
                deltaY = 0.0f;
                break;
            }
        }
        return true;
    }

    private void moveShip(){
        Coordinate coord = ship3D.getCoordinate();
        float scaledDeltaX = deltaX;
        float scaledDetlaY = deltaY;
        if(coord.getX() + scaledDeltaX < minX || coord.getX() + scaledDeltaX > maxX ){
            scaledDeltaX = 0;
        }

        if(coord.getY() + scaledDetlaY < minY || coord.getY() + scaledDetlaY > maxY ){
            scaledDetlaY = 0;
        }

        ship3D.shiftTraslation(scaledDeltaX, scaledDetlaY, 0.0f);
    }

    private float chackDelta(float delta){
        if( Math.abs( delta ) > maxModulo){
            float sign = Math.signum(delta);
            delta = sign * maxModulo;
        }


        Log.d(TAG, "Data:\n"+ delta);
        return delta;
    }
}
