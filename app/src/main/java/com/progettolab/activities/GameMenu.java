package com.progettolab.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.progettolab.R;
import com.progettolab.game3D.audio.AudioManager;
import com.progettolab.game3D.audio.AudioPlayer;
import com.progettolab.game3D.graphics.core.MenuRenderer;
import com.progettolab.game3D.graphics.core.MenuSurface;
import com.progettolab.game3D.managers.ScreenManager;

import model.scores.LocalScoreManager;
import model.scores.ManagerKeeper;
import model.scores.ScoreKeeper;
import model.scores.XMLLocalStatsManager;


/**
 * Activity che contiene il menu' principale.
 * @author Jancarlos.
 */
public class GameMenu extends ActionBarActivity {

    private MenuSurface menuSurface;
    private AudioPlayer confirmPlayer;
    private AudioPlayer titlePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Attivazione FullScreen.
        ScreenManager.goFullScreen(this);

        setContentView(R.layout.activity_game_menu);


        //Setting di una GLSurfaceView in overlay
        menuSurface = new MenuSurface(this);
        menuSurface.setRenderer(new MenuRenderer(this));
        addContentView(menuSurface, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        //Setting del font nel/nei Button
        Button button = (Button)findViewById(R.id.new_game_button);
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.button_font));
        button.setTypeface(font);

        //ManagerKeeper.getInstance().setLocalStats(new XMLLocalStatsManager(getString(R.raw.)));
        //ManagerKeeper.getInstance().setScoreManager(new LocalScoreManager(""));
    }

    /**
     * Gestisce la pressione dei bottoni.
     * @param view View nella quale deve essere gestito il controllo
     */
    public void handleButtonPress(View view){

        Intent intent;
        switch (view.getId()) {
            case R.id.new_game_button:
                titlePlayer.stop();
                confirmPlayer.play();
                intent = new Intent(this, GameActivity.class);
                break;
            default: return;
        }

        this.startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initAudio();
        titlePlayer.play();
        menuSurface.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        titlePlayer.pause();
        menuSurface.onPause();
    }

    private void initAudio() {
        AudioManager.getInstance().addResource("Confirm_sound", R.raw.confirm);
        AudioManager.getInstance().addResource("Title_sound", R.raw.title);

        confirmPlayer = new AudioPlayer(this, "Confirm_sound", false);
        titlePlayer = new AudioPlayer(this, "Title_sound", true);
    }

}
