package com.progettolab.game3D.objectsModel;

/**
 * Interfaccia che astrae sul modo in cui sono assemblati i modelli 3D.
 * @author Jancarlos.
 */
public interface ModelBuilder {

    /**
     * Metodo per il caricamento e assegnazione di modello 3D e texture per un {@link Model}.
     * @param model Modello 3D
     */
    void buildModel(Model model);

}
