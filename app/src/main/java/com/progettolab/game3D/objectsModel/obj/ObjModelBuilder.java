package com.progettolab.game3D.objectsModel.obj;

import android.content.Context;

import com.progettolab.game3D.managers.TextureLoader;
import com.progettolab.game3D.objectsModel.Model;
import com.progettolab.game3D.objectsModel.ModelBuilder;
import com.progettolab.game3D.objectsModel.ModelData;

/**
 * Builder che crea un modello partendo da un file obj.
 * Created by Jancarlos.
 */
public class ObjModelBuilder implements ModelBuilder {

    private ObjLoader loader;
    private TextureLoader textureLoader;

    /**
     * @param context Contesto
     */
    public ObjModelBuilder(Context context){
        loader = new ObjLoader(context);
        textureLoader = new TextureLoader(context);
    }

    /**
     * @see ModelBuilder
     */
    @Override
    public void buildModel(Model model) {

        if(model.getData() == null ){
            ModelData data = loader.loadOBJFromAssets(model.getModelPath());
            model.setData(data);
        }

        int textureID = textureLoader.loadTextureFromAssets(model.getModelTexturePath());
        model.setTexture(textureID);
    }
}
