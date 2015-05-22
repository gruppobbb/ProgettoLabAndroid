package control;

import android.view.MotionEvent;
import android.view.View;

import model3D.Ship3D;

/**
 * Created by jan on 22/05/15.
 */
public class ShipController  implements View.OnTouchListener{

    private Ship3D ship;


    public ShipController(Ship3D ship) {
        this.ship = ship;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
