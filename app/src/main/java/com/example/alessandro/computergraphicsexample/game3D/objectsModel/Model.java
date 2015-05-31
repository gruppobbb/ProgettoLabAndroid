package com.example.alessandro.computergraphicsexample.game3D.objectsModel;

import com.example.alessandro.computergraphicsexample.game3D.graphics.shader.ShaderProgram;

/**
 * Created by Jancarlos.
 */
public class Model {


    private String modelPath;
    private String modelTexturePath;

    private float shineDamper = 1f;
    private float reflectivity = 0.5f;

    private ModelData data;
    private int texture;

    private ShaderProgram program;

    public Model(String modelPath, String modelTexturePath){
        this.modelPath = modelPath;
        this.modelTexturePath = modelTexturePath;
    }

    public String getModelPath() {
        return modelPath;
    }

    public String getModelTexturePath() {
        return modelTexturePath;
    }

    public void setData(ModelData data) {
        this.data = data;
    }

    public ModelData getData() {
        return data;
    }

    public void setProgram(ShaderProgram program) {
        this.program = program;
    }

    public ShaderProgram getProgram(){
        return program;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public int getTexture() {
        return texture;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
