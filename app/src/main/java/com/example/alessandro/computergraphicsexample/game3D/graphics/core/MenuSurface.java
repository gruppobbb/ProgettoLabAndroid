package com.example.alessandro.computergraphicsexample.game3D.graphics.core;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

/**
 * @author Jancarlos.
 */
public class MenuSurface extends GLSurfaceView {

    public MenuSurface(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setZOrderOnTop(true);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }
}
