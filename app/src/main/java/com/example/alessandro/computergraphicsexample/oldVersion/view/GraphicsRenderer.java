package com.example.alessandro.computergraphicsexample.oldVersion.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.support.annotation.NonNull;

import com.example.alessandro.computergraphicsexample.R;

import java.util.ArrayList;
import java.util.Observable;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import model.Coordinate;
import model.MobsManager;
import model.mobs.Mob;
import com.example.alessandro.computergraphicsexample.model3D.Mob3D;
import com.example.alessandro.computergraphicsexample.oldVersion.library.objLoader.ObjLoader;
import com.example.alessandro.computergraphicsexample.oldVersion.library.integration.ArrayObject;
import com.example.alessandro.computergraphicsexample.oldVersion.library.integration.BitmapTexture;
import com.example.alessandro.computergraphicsexample.oldVersion.library.integration.Material;
import com.example.alessandro.computergraphicsexample.oldVersion.library.integration.Mesh;
import com.example.alessandro.computergraphicsexample.oldVersion.library.integration.Model;
import com.example.alessandro.computergraphicsexample.oldVersion.library.integration.Node;
import com.example.alessandro.computergraphicsexample.oldVersion.library.integration.ShadingProgram;
import com.example.alessandro.computergraphicsexample.oldVersion.library.sfogl2.SFOGLSystemState;
import com.example.alessandro.computergraphicsexample.oldVersion.library.sfogl2.SFOGLTextureModel;
import com.example.alessandro.computergraphicsexample.oldVersion.library.shadow.graphics.SFImageFormat;
import com.example.alessandro.computergraphicsexample.oldVersion.library.shadow.math.SFTransform3f;
import com.example.alessandro.computergraphicsexample.model3D.Ship3D;

import static android.opengl.GLSurfaceView.Renderer;

/**
 * Renderer che si occupa delle chiamate draw verso OpenGLES per ogni Mob e per la ship.
 *
 * @author Max
 * @author Jan
 */
public class GraphicsRenderer extends Observable implements Renderer {

    private ShadingProgram program;
    private Context context;
    private MobsManager mobsManager;
    private Ship3D ship;

    private float[] projection = new float[16];
    private float[] viewMatrix = new float[16];
    private int width, height;

    private Node shipNode;
    private Node mobsNode;

    public GraphicsRenderer(Context context, @NonNull MobsManager mobsManager, Ship3D ship) {
        this.context = context;
        this.mobsManager = mobsManager;
        this.ship = ship;

    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        Matrix.setIdentityM(viewMatrix, 0);

        float eyeX = 0;
        float eyeY = 0;
        float eyeZ = 5;
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


        Model shipModel = loadModel("tempShip.obj", R.drawable.suzanne_manual_texture);
        //Step 6: create a Node, that is a reference system where you can place your Model
        shipNode = new Node();
        shipNode.setModel(shipModel);

        Model mobsModel = loadModel("SuzanneTextured.obj", R.drawable.suzanne_manual_texture);
        //Model mobsModel = loadModel("tempShip.obj", R.drawable.suzanne_manual_texture);
        mobsNode = new Node();
        mobsNode.setModel(mobsModel);

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
        Matrix.frustumM(projection, 0, -ratio, ratio, -1f, 1f, 3f, 100f);

        this.width = width;
        this.height = height;
        setChanged();

        notifyObservers();
    }


    private SFTransform3f sfTransform3f = new SFTransform3f();

    @Override
    public void onDrawFrame(GL10 gl) {

        SFOGLSystemState.cleanupColorAndDepth(0, 0, 0, 1);

        //setup the Projection e View matrix
        program.setupProjection(projection);
        program.setViewMatrix(viewMatrix);


        Coordinate coord = ship.getCoordinate();
        shipNode.getRelativeTransform().setPosition(coord.getX(), coord.getY(), coord.getZ());

        shipNode.getRelativeTransform().setMatrix(ship.getModelMatrix());

        shipNode.updateTree(new SFTransform3f());

        shipNode.draw();

        //caricamento dei mobs attivi
        ArrayList<Mob> mobs = mobsManager.getMobsList();

        for(Mob mob : mobs ){

            //Posizionamento del mob
            coord = mob.getCoordinate();
            mobsNode.getRelativeTransform().setPosition(coord.getX(), coord.getY(), coord.getZ());

            //Trasformazione del mob
            mobsNode.getRelativeTransform().setMatrix(((Mob3D) mob).getModelMatrix());

            //TODO: Aggiornamento dei nodi figli ?, serve?...
            mobsNode.updateTree(new SFTransform3f());

            //Disegno del nodo, nella posizione e con la trasformazione indicta dal Mob attuale
            mobsNode.draw();

        }






    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}