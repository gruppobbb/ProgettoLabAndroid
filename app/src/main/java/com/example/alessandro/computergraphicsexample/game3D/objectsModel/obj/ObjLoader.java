package com.example.alessandro.computergraphicsexample.game3D.objectsModel.obj;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.alessandro.computergraphicsexample.game3D.objectsModel.ModelData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Classe per il caricamento dei file con estensione .obj.
 * Created by Jancarlos.
 */
public class ObjLoader {

    private Context context;

    private String objectName;
    private float[] vertexData;
    private float[] textureData;
    private float[] normalData;
    private short[] indicesData;

    private ArrayList<Vector3f> vertices;
    private ArrayList<Vector2f> textures;
    private ArrayList<Vector3f> normals;
    private ArrayList<Short> indices;

    private ArrayList<String> faceLines;

    private AssetManager am;
    /**
     * Classe per il caricamento da file .obj dei dati per la costruzione di un modello compesso.
     * @param context context andoird in cui viene creata l'istanza.
     */
    public ObjLoader(Context context) {
        this.context = context;
        this.am = context.getAssets();
    }

    /**
     * Metodo per effettuare il caricamento dei dati da un file .obj presente negli assets interni dell'App.
     * @param objPath path interno agli assets del file .obj
     * @return JOBJData che descive un modello complesso
     */
    public ModelData loadOBJFromAssets(String objPath){

        vertices = new ArrayList<>();
        textures = new ArrayList<>();
        normals = new ArrayList<>();
        indices = new ArrayList<>();
        faceLines = new ArrayList<>();

        InputStream inputStream;
        BufferedReader reader;
        String line;
        String[] currentLine;

        try {
            inputStream = am.open(objPath);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while((line = reader.readLine()) != null ){

                currentLine = line.split(" ");

                if(line.startsWith("o ")){

                    objectName = line.split(" ")[1];

                }else if(line.startsWith("v ")){

                    vertices.add(new Vector3f(
                            Float.parseFloat(currentLine[1]),     // X
                            Float.parseFloat(currentLine[2]),     // Y
                            Float.parseFloat(currentLine[3])      // Z
                    ));

                } else if(line.startsWith("vt ")){

                    textures.add(new Vector2f(
                            Float.parseFloat(currentLine[1]),     // S
                            Float.parseFloat(currentLine[2])      // T
                    ));

                } else if(line.startsWith("vn ")){

                    normals.add(new Vector3f(
                            Float.parseFloat(currentLine[1]),     // nX
                            Float.parseFloat(currentLine[2]),     // nY
                            Float.parseFloat(currentLine[3])      // nZ
                    ));

                } else if(line.startsWith("f ")){

                    faceLines.add(line);

                }

            }

            reader.close();

            if(textures.size() > 0 ){
                //perche' ogni punto della texture richiede 2 componenti
                textureData = new float[ vertices.size()*2 ];
            }

            if(normals.size() > 0){
                //perche' consideriamo vertici con noormali a 3 componenti.
                normalData = new float[ vertices.size()*3 ];
            }

            handleFaceLines();

        } catch (IOException e) {
            e.printStackTrace();
        }


        //perche' consideriamo vertici con coordinate a 3 componenti.
        vertexData = new float[vertices.size()*3];
        indicesData = new short[indices.size()];

        int vertexPointer = 0;
        for(Vector3f vertex : vertices){
            vertexData[vertexPointer++] = vertex.x;
            vertexData[vertexPointer++] = vertex.y;
            vertexData[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i<indices.size(); i++){
            indicesData[i] = indices.get(i);
        }

        return new ModelData( objectName, vertexData, textureData, normalData, indicesData);
    }


    /*
    Metodo per l'elaborazione delle linee "f ".
     */
    private void handleFaceLines(){

        String[] currentFaceLineData;
        String[] vertex1;
        String[] vertex2;
        String[] vertex3;

        for (int i = 0; i < faceLines.size() ; i++) {

            currentFaceLineData = faceLines.get(i).split(" ");

            vertex1 = currentFaceLineData[1].split("/");
            vertex2 = currentFaceLineData[2].split("/");
            vertex3 = currentFaceLineData[3].split("/");

            processVertex( vertex1 );

            processVertex( vertex2 );

            processVertex( vertex3 );
        }
    }


    private short currentVertexPointer;
    private Vector2f currentTex;
    private Vector3f currentNorm;

    /**
     * Metodo che effettua il parsing delle colonne nelle righe riguardanti le face,
     * mettendo in ordine gli indici.
     * @param vertexData
     */
    private void processVertex(String[] vertexData){

        //Il formato .obj parte da 1, non da 0;
        currentVertexPointer= (short)(  Short.parseShort(vertexData[0]) - 1 ) ;

        indices.add(currentVertexPointer);

        if(textures.size() > 0){
            //il "-1" e' per lo stesso motivo di sopra
            currentTex = textures.get((short)(Short.parseShort(vertexData[1]) -1));

            //"*2" perche' il vettore ha 2 componenti.
            //"-1" perche' OpenGL parte dalla parte in alto a sinista...
            //
            //  U = S = x dimension
            //  V = T = y dimension
            //
            //  0|
            // --|-----> S
            //   |
            // T\/
            //
            textureData[currentVertexPointer*2] = currentTex.x;
            textureData[currentVertexPointer*2+1] = 1-currentTex.y;
        }

        if(normals.size() > 0){
            currentNorm = normals.get(Short.parseShort(vertexData[2]) -1 );

            //Perche il vettore ha 3 componenti
            normalData[currentVertexPointer*3] = currentNorm.x;
            normalData[currentVertexPointer*3+1] = currentNorm.y;
            normalData[currentVertexPointer*3+2] = currentNorm.z;
        }
    }

    private class Vector2f{
        float x;
        float y;

        public Vector2f(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Vector3f{
        float x;
        float y;
        float z;

        public Vector3f(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}