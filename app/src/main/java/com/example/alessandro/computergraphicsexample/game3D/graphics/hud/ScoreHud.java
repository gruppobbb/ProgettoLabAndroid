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
 * Surface per la visualizzazione del punteggio.
 * @author Jancarlos.
 */
public class ScoreHud extends SurfaceView  implements Observer{

    private SurfaceHolder holder;
    private ScoreCalculator scoreCalculator;
    private long nextValidScore;

    private Paint pointsPaint;
    private int xScoreDraw, yScoreDraw;

    /**
     * Costruisce una {@link SurfaceView} per la visualizzazione del punteggio, recuperato da {@link ScoreCalculator}.
     * @param context context dell'activity in cui viene utilizata la surface
     * @param scoreCalculator componente che calcola e fonisce il punteggio
     */
    public ScoreHud(Context context, ScoreCalculator scoreCalculator) {
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

        float[] widths = new float[1];
        xScoreDraw = pointsPaint.getTextWidths("A", widths);
        yScoreDraw = (int)(pointsPaint.getFontMetrics().descent-pointsPaint.getFontMetrics().ascent);

    }

    /*
    Disegno dello score.
     */
    private void drawScore(){

        long score = scoreCalculator.getScore();

        if(score >= nextValidScore){
            Canvas canvas = holder.lockCanvas();
            if(canvas != null ){

                canvas.drawColor(0, PorterDuff.Mode.CLEAR);

                canvas.drawText("Punteggio: " + score,
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
        drawScore();
    }

}
