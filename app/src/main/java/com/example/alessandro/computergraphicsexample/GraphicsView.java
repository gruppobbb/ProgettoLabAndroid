package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import objLoader.ObjLoader;
import sfogl.integration.ArrayObject;
import sfogl.integration.BitmapTexture;
import sfogl.integration.Material;
import sfogl.integration.Mesh;
import sfogl.integration.Model;
import sfogl.integration.Node;
import sfogl.integration.ShadingProgram;
import sfogl2.SFOGLSystemState;
import sfogl2.SFOGLTextureModel;
import shadow.graphics.SFImageFormat;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;

/**
 * Created by Alessandro on 13/03/15.
 */
public class GraphicsView extends GLSurfaceView{

    private Context context;

    public GraphicsView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        this.context=context;
        setRenderer(new GraphicsRenderer(context));
    }


}
