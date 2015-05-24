package com.example.alessandro.computergraphicsexample.oldVersion.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Alessandro on 13/03/15.
 */
public class GraphicsView extends GLSurfaceView{

    public GraphicsView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
    }


}
