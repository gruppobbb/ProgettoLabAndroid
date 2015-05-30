package com.example.alessandro.computergraphicsexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.example.alessandro.computergraphicsexample.R;

/**
 * @author Jancarlos.
 */
public class GameMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
    }

    public void openVersion( View view) {

        Intent intent;

        switch (view.getId()) {

            case R.id.new_version_button:
                intent = new Intent(this, GameActivity.class);
                break;

            default:
                return;

        }

        this.startActivity(intent);
    }
}
