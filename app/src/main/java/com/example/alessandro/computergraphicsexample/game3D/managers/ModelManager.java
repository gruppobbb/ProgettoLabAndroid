package com.example.alessandro.computergraphicsexample.game3D.managers;

import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

import java.util.HashMap;

/**
 * @author Jancarlos.
 */
public class ModelManager {

    private static final ModelManager manager = new ModelManager();

    private ModelManager(){}

    public static ModelManager getInstance(){
        return manager;
    }

    private static final HashMap<String, Model> models = new HashMap<>();

    public void storeModel(String modelID, Model model){
        models.put(modelID, model);
    }

    public boolean containsModel(String modelID){
        return models.containsKey(modelID);
    }

    public Model getModel(String modelID){
        return models.get(modelID);
    }

    public void deleteModel(String modelID){
        models.remove(modelID);
    }

    public void reset(){
        models.clear();
    }

}
