package com.progettolab.game3D.graphics.core;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.progettolab.game3D.entities.GameCamera;
import com.progettolab.game3D.entities.Ship3D;
import com.progettolab.game3D.entities.Stand3D;
import com.progettolab.game3D.graphics.shader.CompleteShaderProgram;
import com.progettolab.game3D.graphics.shader.ShaderProgram;
import com.progettolab.game3D.managers.ModelManager;
import com.progettolab.game3D.objectsModel.Model;
import com.progettolab.game3D.objectsModel.ModelBuilder;
import com.progettolab.game3D.objectsModel.game.MobsModel;
import com.progettolab.game3D.objectsModel.game.ShipModel;
import com.progettolab.game3D.objectsModel.game.StandModel;
import com.progettolab.game3D.objectsModel.obj.ObjModelBuilder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import model.Coordinate;

/**
 * Renderer dedicato al solo render di una scena per il menu.
 * @author Jancarlos.
 */
public class MenuRenderer implements GLSurfaceView.Renderer {

    private Context context;

    private float[] mProjectionMatrix = new float[16];
    private ModelManager modelManager;
    private Model standModel;
    private Model shipModel;

    private GameCamera camera;

    private Ship3D ship;
    private Stand3D stand;

    private ShaderProgram program;
    private ModelBuilder builder;

    /**
     * @param context
     */
    public MenuRenderer(Context context) {
        this.context = context;
        Coordinate shipCoordinate = new Coordinate(0.0f, 0.0f, 0.0f);

        Coordinate cameraCoordinate = new Coordinate(
                shipCoordinate.getX(),
                shipCoordinate.getY(),
                shipCoordinate.getZ()-2.0f);

        Coordinate standCoordinate = new Coordinate(
                shipCoordinate.getX(),
                shipCoordinate.getY()-0.5f,
                shipCoordinate.getZ()
        );

        ship = new Ship3D(shipCoordinate);
        stand = new Stand3D(standCoordinate);

        camera = new GameCamera(cameraCoordinate, standCoordinate);

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0, 0, 0, 0);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        init();

        program = new CompleteShaderProgram(context);
        GLES20.glUseProgram(program.getProgramID());

        preloadMobs();
    }

    /**
     * Carica e inizializza i modelli da renderizzare.
     */
    public void init(){

        modelManager = ModelManager.getInstance();
        builder = new ObjModelBuilder(context);

        shipModel = modelManager.getModel(ShipModel.MODEL_ID);

        if(shipModel == null) {
            shipModel = new ShipModel(context);
            modelManager.storeModel(ShipModel.MODEL_ID, shipModel);
        }

        builder.buildModel(shipModel);

        standModel = modelManager.getModel(StandModel.MODEL_ID);
        if(standModel == null ){
            standModel = new StandModel(context);
            modelManager.storeModel(StandModel.MODEL_ID, standModel);
        }

        builder.buildModel(standModel);
    }

    /*
    Precaricamento di modelli aggiuntivi, utili per la modalità sucessiva.
     */
    private void preloadMobs(){
        if(!modelManager.containsModel(MobsModel.MODEL_ID)){
            MobsModel mobsModel = new MobsModel(context);
            builder.buildModel(mobsModel);
            modelManager.storeModel(MobsModel.MODEL_ID, mobsModel);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);

        program.loadProjectionMatrix(mProjectionMatrix);

        program.loadViewMatrix(camera.getViewMatrix());
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        program.loadPerModelUniform(shipModel);
        program.loadModelMatrix(ship.getModelMatrix());
        drawModel(shipModel);

        program.loadPerModelUniform(standModel);
        program.loadModelMatrix(stand.getModelMatrix());
        drawModel(standModel);

        updateModelMatrices();

    }

    private void drawModel(Model sourceModel){
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                sourceModel.getData().getIndicesCount(),
                GLES20.GL_UNSIGNED_SHORT,
                sourceModel.getData().getIndicesBuffer());
    }


    private static final double RAD_STEP = Math.PI/180;
    private static double rad = 0;

    private void updateModelMatrices(){
        rad = (rad + RAD_STEP)%6.18;
        float newY = (float)( Math.sin(rad)/8 );
        ship.setTraslation(0.0f, newY, 0.0f);
        ship.shiftAngle(0.0f, 0.5f, 0.0f);
        stand.shiftAngle(0.0f, 0.5f, 0.0f);
    }

}
