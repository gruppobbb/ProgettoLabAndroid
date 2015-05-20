package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import model.Coordinate;
import model.MobsManager;
import model.mobs.Mob;
import model3D.Mob3D;
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
 * Renderer che si occupa delle chiamate draw verso OpenGLES per ogni Mob e per la ship.
 *
 * @author Max
 * @author Jan
 */
public class GraphicsRenderer implements Renderer {

    private ShadingProgram program;
    private Context context;
    private MobsManager mobsManager;
    private float[] projection = new float[16];
    private float[] viewMatrix = new float[16];

    private Node shipNode;
    private Node mobsNode;

    public GraphicsRenderer(Context context, MobsManager mobsManager) {
        this.context = context;
        this.mobsManager = mobsManager;
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


        Model shipModel = loadModel("MonkeyTxN.obj", R.drawable.paddedroomtexture01);

        //Step 6: create a Node, that is a reference system where you can place your Model
        shipNode = new Node();
        shipNode.setModel(shipModel);
        //TODO: Posizionamento della ship alla partenza fatto da costruttore in Ship3D.
        //TODO: Fare Ship3D.
        // shipNode.getRelativeTransform().setPosition(0, 0, -5);



        Model mobsModel = loadModel("MonkeyTxN.obj", R.drawable.paddedroomtexture01);
        mobsNode = new Node();
        mobsNode.setModel(mobsModel);

        //TODO: queste le fa lo spawner quando genera un Mob3D
        //mobsNode.getRelativeTransform().setPosition(1, 1, -5);
        //mobsNode.getRelativeTransform().setMatrix(SFMatrix3f.getScale(0.3f, 0.2f, 0.1f));

    }

    private Model loadModel(String modelPath, int textureID) {
        //Step 1 : load Shading effects
        ShadersKeeper.loadPipelineShaders(context);
        program = ShadersKeeper.getProgram(ShadersKeeper.STANDARD_TEXTURE_SHADER);

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

        //Generazione della ProjectionMAtrix per la correzzione delle coordinate in proporzione
        // alle dimensioni della surface.

        float ratio = (float) width / height;
        Matrix.setIdentityM(projection, 0);
        Matrix.frustumM(projection, 0, -ratio, ratio, -1f, 1f, 1f, 100f);

    }


    @Override
    public void onDrawFrame(GL10 gl) {

        SFOGLSystemState.cleanupColorAndDepth(0, 0, 0, 1);

        //setup the Projection e View matrix
        program.setupProjection(projection);
        program.setViewMatrix(viewMatrix);

        //caricamento dei mobs attivi
        ArrayList<Mob> mobs = mobsManager.getMobsList();

        for(Mob mob : mobs ){

            //Posizionamento del mob
            Coordinate coord = mob.getCoordinate();
            mobsNode.getRelativeTransform().setPosition(coord.getX(), coord.getY(), coord.getZ());

            //Trasformazione del mob
            mobsNode.getRelativeTransform().setMatrix(((Mob3D) mob).getModelMatrix());

            //TODO: Aggiornamento dei nodi figli ?, serve?...
            mobsNode.updateTree(new SFTransform3f());

            //Disegno del nodo, nella posizione e con la trasformazione indicta dal Mob attuale
            mobsNode.draw();

        }

        //int[] viewport=new int[4];
        //GLES20.glGetIntegerv(GLES20.GL_VIEWPORT,viewport,0);
        //Log.e("Graphics View Size", Arrays.toString(viewport));
    }
}
