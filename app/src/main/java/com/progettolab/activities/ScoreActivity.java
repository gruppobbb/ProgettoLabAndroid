package com.progettolab.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.progettolab.R;
import com.progettolab.game3D.managers.ScreenManager;

import model.scores.ScoreEntry;
import model.scores.ScoreKeeper;

public class ScoreActivity extends ActionBarActivity {

    private ScoreKeeper keeper = ScoreKeeper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Attivazione FullScreen.
        ScreenManager.goFullScreen(this);

        setContentView(R.layout.activity_score);

        onResume();

    }

    private String getPersonalStats(){
        String stats = "Good job, " + keeper.getPlayerName() + "!\n" +
                "Your personal best is " + keeper.getPersonalBest();
        return stats;
    }

    private String getScores(){
        String scores = "Highscores\n";

        Log.d("ScoreActivity", ""+keeper.getHighScores().size());

        /*for (int i = 0; i < ScoreKeeper.MAX_SCORES; i++){
            ScoreEntry score = keeper.getHighScores().get(i);
            scores = scores + i + ") " + score.getPlayerName() + ": " + score.getScore() + "\n";
        }
        */
        return scores;
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView statsView = (TextView)findViewById(R.id.statsView);
        statsView.setText(getPersonalStats());

        TextView scoresView = (TextView)findViewById(R.id.scoreView);
        scoresView.setText(getScores());
    }

    public void goBack(View view){
        finish();
    }
}
