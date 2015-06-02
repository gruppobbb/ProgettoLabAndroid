package com.progettolab.game3D.objectsModel;

/**
 * Classe contente le informazioni per la rappresentazione di un modello 3D.
 * Created by Jancarlos.
 */
public class Model {


    private String modelPath;
    private String modelTexturePath;

    private float shineDamper = 1f;
    private float reflectivity = 0.5f;

    private ModelData data;
    private int texture;

    /**
     * @param modelPath path del modello
     * @param modelTexturePath path delle texture associate al modello
     */
    public Model(String modelPath, String modelTexturePath){
        this.modelPath = modelPath;
        this.modelTexturePath = modelTexturePath;
    }

    /**
     * Restituisce il path del modello.
     * @return path del modello
     */
    public String getModelPath() {
        return modelPath;
    }

    /**
     * Restituisce il path delle texture.
     * @return path delle texture
     */
    public String getModelTexturePath() {
        return modelTexturePath;
    }

    /**
     * Imposta i dati associati al modello.
     * @param data {@link ModelData} associato al modello
     */
    public void setData(ModelData data) {
        this.data = data;
    }

    /**
     * Restituisce il {@link ModelData} associato al modello.
     * @return dati associati al modello
     */
    public ModelData getData() {
        return data;
    }

    /**
     * Imposta la texture.
     * @param texture ID della risorsa contenente la texture
     */
    public void setTexture(int texture) {
        this.texture = texture;
    }

    /**
     * Restituisce l'id delle texture.
     * @return ID delle texture
     */
    public int getTexture() {
        return texture;
    }

    /**
     * @return reflectivity
     */
    public float getReflectivity() {
        return reflectivity;
    }

    /**
     * @return shine damper
     */
    public float getShineDamper() {
        return shineDamper;
    }

    /**
     * @param shineDamper shine damper
     */
    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    /**
     * @param reflectivity reflectivity
     */
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
