package ete.environnements;

import org.mapeditor.core.MapObject;

/**
 * Classe représentant un obstacle d'eau (hérite de Obstacle)
 */
public class Eau extends Obstacle {
    public Eau(MapObject obj) {
        super(obj);
    }

    @Override
    public String getName() {
        return "Eau";
    }
}
