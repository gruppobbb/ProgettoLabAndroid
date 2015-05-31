package com.example.alessandro.computergraphicsexample.game3D.graphics.hud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Observable;
import java.util.Observer;

import model.scores.ScoreCalculator;

/**
 * @author Jancarlos.
 */
public class GameHud extends SurfaceView  implements Observer{

    private SurfaceHolder holder;
    private ScoreCalculator scoreCalculator;
    private long nextValidScore;

    public GameHud(Context context, ScoreCalculator scoreCalculator) {
        super(context);

        scoreCalculator.addObserver(this);
        this.scoreCalculator = scoreCalculator;

        holder = getHolder();
        holder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/origami.ttf");
        pointsPaint = new Paint();
        pointsPaint.setTextSize(48);
        pointsPaint.setTypeface(font);
        pointsPaint.setColor(Color.YELLOW);

        scoreAreaPaint = new Paint();
        scoreAreaPaint.setStyle(Paint.Style.STROKE);
        scoreAreaPaint.setColor(Color.WHITE);
    }

    private Paint pointsPaint;
    private Paint scoreAreaPaint;

    private int xScoreDraw, yScoreDraw = 48;

    private String formattedScore;

    public void drawHud(){


        long score = scoreCalculator.getScore();

        if(score >= nextValidScore){
            Canvas canvas = holder.lockCanvas();
            if(canvas != null ){

                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                formattedScore = "Punteggio: " + score;

                canvas.drawText(formattedScore,
                        xScoreDraw,
                        yScoreDraw,
                        pointsPaint);

                nextValidScore = score+1000;
                holder.unlockCanvasAndPost(canvas);
            }


        }

    }


    @Override
    public void update(Observable observable, Object o) {
        drawHud();
    }

}
