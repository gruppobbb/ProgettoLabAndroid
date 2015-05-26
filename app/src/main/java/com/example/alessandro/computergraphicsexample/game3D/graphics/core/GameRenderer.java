package com.example.alessandro.computergraphicsexample.game3D.graphics.core;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.alessandro.computergraphicsexample.game3D.entities.Mob3D;
import com.example.alessandro.computergraphicsexample.game3D.entities.Ship3D;
import com.example.alessandro.computergraphicsexample.game3D.entities.GameCamera;
import com.example.alessandro.computergraphicsexample.game3D.entities.Light;
import com.example.alessandro.computergraphicsexample.game3D.graphics.shader.ShaderProgram;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.ModelBuilder;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.gameModels.MobsModel;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.gameModels.ShipModel;
import com.example.alessandro.computergraphicsexample.game3D.objectsModel.obj.ObjModelBuilder;
import model.MobsManager;
import model.mobs.Mob;

/**
 * @author Jancarlos.
 */
public class GameRenderer implements GLSurfaceView.Renderer{

    private Context context;
    private MobsManager mobsManager;
    private Model mobsModel;
    private Model shipModel;
    private Ship3D ship;
    private ShaderProgram program;
    private GameCamera camera;
    private Light sunLight;

    private float[] mProjectionMatrix = new float[16];

    /**
     * Renderer ottimizzato per il disegno di una modalità singleplayer con N {@link Mob3D}
     * recuperati dal {@link MobsManager}, con una inquadratura definita dalla {@link GameCamera},
     * con una {@link Ship3D} controllata dal giocatore.
     * @param context
     * @param mobsManager componente contente tutte le istanze dei mob da disegnare
     * @param camera componente che deifinisce la posizione i dati della telecamera
     * @param sunLight componente che definisce la posizione della luce principale
     * @param ship ship controllata dal giocatore
     */
    public GameRenderer(Context context, MobsManager mobsManager, GameCamera camera, Light sunLight, Ship3D ship) {
        this.context = context;
        this.mobsManager = mobsManager;
        this.camera = camera;
        this.sunLight = sunLight;
        this.ship = ship;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //Colore di default dopo ogni cancellazione.
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        //Taglio delle face/vertici che non sono inquadrati.
        GLES20.glEnable(GLES20.GL_SCISSOR_TEST);

        //Controllo delle face oscurate/nascoste ( quelle che non si possono vedere non vengono renderizzate ).
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);

        //Controllo della profondità per il rendering dei vertici.
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        //GLES20.glDisable(GLES20.GL_BLEND);

        //TODO: fare qui l'iniializzaione di modelli.

        initModels();
    }

    public void initModels(){
        //caricamento e assemblaggio dei modelli

        shipModel = new ShipModel(context);

        mobsModel = new MobsModel(context);

        ModelBuilder builder = new ObjModelBuilder(context);

        builder.buildModel(shipModel);

        builder.buildModel(mobsModel);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

        //set dell'area della surface.
        GLES20.glViewport(0, 0, width, height);

        //Set della matrice di proiezione per la prospettiva e correzzione delle proporzioni in base
        // alla dimensione dello schermo.
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);

    }


    @Override
    public void onDrawFrame(GL10 gl10) {

        //Clean della scena per un nuovo render.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        drawMobs();

        drawShip();
    }

    /**
     * Disegno dei Mobs, recuperati al {@link MobsManager}
     */
    private void drawMobs(){

        ArrayList<Mob> mobs = mobsManager.getMobsList();

        if(mobs.size() > 0 ){

            program = mobsModel.getProgram();
            GLES20.glUseProgram(program.getProgramID());
            program.loadGlobalUniforms(mProjectionMatrix, camera, sunLight);
            program.loadPerModelUniform(mobsModel);

            for(Mob mob : mobs){

                if( mob != null ){
                    program.loadModelMatrix(((Mob3D)mob).getModelMatrix());

                    GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                            mobsModel.getData().getIndicesCount(),
                            GLES20.GL_UNSIGNED_SHORT,
                            mobsModel.getData().getIndicesBuffer());
                }

            }

        }
    }


    private void drawShip(){
        program = shipModel.getProgram();
        GLES20.glUseProgram(program.getProgramID());
        program.loadGlobalUniforms(mProjectionMatrix, camera, sunLight);
        program.loadPerModelUniform(shipModel);

        program.loadModelMatrix(ship.getModelMatrix());

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                shipModel.getData().getIndicesCount(),
                GLES20.GL_UNSIGNED_SHORT,
                shipModel.getData().getIndicesBuffer());
    }

}
