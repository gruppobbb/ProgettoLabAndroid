package com.example.alessandro.computergraphicsexample.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.alessandro.computergraphicsexample.R;
import com.example.alessandro.computergraphicsexample.game3D.graphics.core.MenuRenderer;
import com.example.alessandro.computergraphicsexample.game3D.graphics.core.MenuSurface;

/**
 * Activity che contiene il menù principale.
 * @author Jancarlos.
 */
public class GameMenu extends ActionBarActivity {

    private MenuSurface menuSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_menu);

        //Setting di una GLSurfaceView in overlay
        menuSurface = new MenuSurface(this);
        menuSurface.setRenderer(new MenuRenderer(this));
        addContentView(menuSurface, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        //Abilitazione del FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Setting del font nel/nei Button
        Button button = (Button)findViewById(R.id.new_game_button);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/origami.ttf");
        button.setTypeface(font);
    }

    private void handleButtonPress(View view){

        Intent intent;
        switch (view.getId()) {
            case R.id.new_game_button:
                intent = new Intent(this, GameActivity.class);
                break;
            default: return;
        }

        if(intent != null ){
            this.startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        menuSurface.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        menuSurface.onPause();
    }
}
