package com.example.alessandro.computergraphicsexample.game3D.graphics.hud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Observable;
import java.util.Observer;

import model.scores.ScoreCalculator;

/**
 * @author Jancarlos.
 */
public class GameHud extends SurfaceView  implements SurfaceHolder.Callback, Observer{

    private SurfaceHolder holder;
    private ScoreCalculator scoreCalculator;

    public GameHud(Context context, ScoreCalculator scoreCalculator) {
        super(context);

        scoreCalculator.addObserver(this);
        this.scoreCalculator = scoreCalculator;

        holder = getHolder();
        holder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);

        holder.addCallback(this);


        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/origami.ttf");
        pointsPaint = new Paint();
        pointsPaint.setTextSize(32);
        pointsPaint.setTypeface(font);
        pointsPaint.setColor(Color.YELLOW);

    }

    private Paint pointsPaint;
    private int width;
    private int height;


    public void drawHud(){

        Canvas canvas = holder.lockCanvas();

        if(canvas != null){
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);

                canvas.drawText("Punteggio: " + scoreCalculator.getScore(), 25, 25, pointsPaint);

                //Richiede API 21 ...
                //canvas.drawOval(x0 + xShift, y0+xShift, w0 + xShift, h0+xShift, pointsPaint);
                holder.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {


    }

    @Override
    public void update(Observable observable, Object o) {
        drawHud();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}
