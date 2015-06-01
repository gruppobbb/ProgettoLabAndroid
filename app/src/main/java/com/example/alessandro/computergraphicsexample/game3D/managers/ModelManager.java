package com.example.alessandro.computergraphicsexample.game3D.managers;

import android.content.res.Resources;

import com.example.alessandro.computergraphicsexample.game3D.objectsModel.Model;

import java.util.HashMap;

/**
 * Gestore dei modelli caricati in moria, in grado di conservare i modelli precedentemente
 * caricati in diverse activity.
 * @author Jancarlos.
 */
public class ModelManager {

    private static final ModelManager manager = new ModelManager();

    private ModelManager(){}

    /**
     * Restituisce l'istanza corrente di {@link ModelManager}
     * @return
     */
    public static ModelManager getInstance(){
        return manager;
    }

    private static final HashMap<String, Model> models = new HashMap<>();

    /**
     * Inserisce il modello specificato, mappandolo con l'identificatore modelID.
     * @param modelID identificatore del modello
     * @param model modello
     */
    public void storeModel(String modelID, Model model){
        models.put(modelID, model);
    }

    /**
     * Controlla se il modello associato all'identificatore modelID è già presente.
     * @param modelID identificatore del modello
     * @return true o false, rispettivamente se il modello è presente o no
     */
    public boolean containsModel(String modelID){
        return models.containsKey(modelID);
    }

    /**
     * Restituisce il modello associato a modelID.
     * @param modelID identificatore del modello
     * @return modello
     */
    public Model getModel(String modelID){
        return models.get(modelID);
    }

    /**
     * Rimuove il modello associato all'identificatore modelID.
     * @param modelID identificatore del modello
     */
    public void deleteModel(String modelID){
        models.remove(modelID);
    }

    /**
     * Elimina tutti i modelli presenti.
     */
    public void reset(){
        models.clear();
    }

}
