package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import model.mobs.Mob;
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

import static android.opengl.GLSurfaceView.Renderer;

/**
 * Created by Max on 12/05/2015.
 */
public class GraphicsRenderer implements Renderer {

    private ShadingProgram program;
    private Context context;
    private ArrayList<Node> nodes = new ArrayList<>();
    private float[] projection = new float[16];
    private float[] viewMatrix = new float[16];


    public GraphicsRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        Matrix.setIdentityM(viewMatrix, 0);

        float eyeX = 0;
        float eyeY = 0;
        float eyeZ = 1;
        float centerX = 0;
        float centerY = 0;
        float centerZ = 0;
        float upX = 0;
        float upY = 1;
        float upZ = 0;

        Matrix.setLookAtM(
                viewMatrix, 0,
                eyeX, eyeY, eyeZ,
                centerX, centerY, centerZ,
                upX, upY, upZ
        );


        Model model1 = loadModel("MonkeyTxN.obj", R.drawable.paddedroomtexture01);

        //Step 6: create a Node, that is a reference system where you can place your Model
        Node node=new Node();
        node.setModel(model1);
        node.getRelativeTransform().setPosition(0, 0, -5);

        nodes.add(node);

        Model model2 = loadModel("MonkeyTxN.obj", R.drawable.paddedroomtexture01);

        Node anotherNode=new Node();
        anotherNode.setModel(model2);
        anotherNode.getRelativeTransform().setPosition(1, 1, -5);
        anotherNode.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.3f, 0.2f, 0.1f));
        node.getSonNodes().add(anotherNode);

        nodes.add(anotherNode);
    }

    private Model loadModel(String modelPath, int textureID) {
        //Step 1 : load Shading effects
        ShadersKeeper.loadPipelineShaders(context);
        program= ShadersKeeper.getProgram(ShadersKeeper.STANDARD_TEXTURE_SHADER);

        //Step 2 : load Textures
        int textureModel= SFOGLTextureModel.generateTextureObjectModel(SFImageFormat.RGB,
                GLES20.GL_REPEAT, GLES20.GL_REPEAT, GLES20.GL_LINEAR, GLES20.GL_LINEAR);
        BitmapTexture texture= BitmapTexture.loadBitmapTexture(BitmapFactory.decodeResource(context.getResources(),
                textureID), textureModel);
        texture.init();

        //Step 3 : create a Material (materials combine shaders+textures+shading parameters)
        Material material=new Material(program);
        material.getTextures().add(texture);

        //Step 4: load a Geometry
        ArrayObject[] objects = ObjLoader.arrayObjectFromFile(context, modelPath);

        Mesh mesh=new Mesh(objects[0]);
        mesh.init();

        //Step 5: create a Model combining material+geometry
        Model model1=new Model();
        model1.setRootGeometry(mesh);
        model1.setMaterialComponent(material);
        return model1;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        float ratio = (float) width / height;

        Matrix.setIdentityM(projection, 0);
        Matrix.frustumM(projection, 0, -ratio, ratio, -1f, 1f, 1f, 100f);
    }


    @Override
    public void onDrawFrame(GL10 gl) {

        SFOGLSystemState.cleanupColorAndDepth(0, 0, 0, 1);

        program.setupProjection(projection);
        program.setViewMatrix(viewMatrix);


        for (int i=0; i<nodes.size(); i++) {
            //setup the View Projection

            SFMatrix3f matrix3f=SFMatrix3f.getScale(scaling,scaling,scaling);
            matrix3f=matrix3f.MultMatrix(SFMatrix3f.getRotationX(rotation));
            nodes.get(i).getRelativeTransform().setMatrix(matrix3f);
            nodes.get(i).updateTree(new SFTransform3f());

            //Draw the node
            nodes.get(i).draw();
        }

        //int[] viewport=new int[4];
        //GLES20.glGetIntegerv(GLES20.GL_VIEWPORT,viewport,0);
        //Log.e("Graphics View Size", Arrays.toString(viewport));
    }
}
