package control;

import android.view.MotionEvent;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

import model.ships.Ship;
import model3D.Ship3D;
import view.GraphicsRenderer;

/**
 * Author Jan.
 */
public class ShipController  implements View.OnTouchListener, Observer{

    private Ship3D ship;
    private float lastX;
    private float lastY;
    private float maxX;
    private float maxY;

    private float shiftAmount;



    public ShipController(Ship3D ship, float shitAmount) {
        this.ship = ship;
        this.shiftAmount = shiftAmount;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            ship.shiftShip(shiftAmount, shiftAmount);
        }


        return false;
    }

    @Override
    public void update(Observable observable, Object o) {
        maxX = ((GraphicsRenderer)observable).getWidth();
        maxY = ((GraphicsRenderer)observable).getHeight();
    }
}
