package com.example.alessandro.computergraphicsexample.alternative.objectsModel.obj;

import android.content.Context;

import com.example.alessandro.computergraphicsexample.alternative.managers.TextureLoader;
import com.example.alessandro.computergraphicsexample.alternative.objectsModel.Model;
import com.example.alessandro.computergraphicsexample.alternative.objectsModel.ModelBuilder;

/**
 * Created by Jancarlos.
 */
public class ObjModelBuilder implements ModelBuilder {

    private ObjLoader loader;
    private TextureLoader textureLoader;

    public ObjModelBuilder(Context context){
        loader = new ObjLoader(context);
        textureLoader = new TextureLoader(context);
    }

    @Override
    public void buildModel(Model model) {
        ModelData data = loader.loadOBJFromAssets(model.getModelPath());
        model.setData(data);

        int textureID = textureLoader.loadTextureFromAssets(model.getModelTexturePath());
        model.setTexture(textureID);
    }
}
