package ete.environnements;

import org.mapeditor.core.MapObject;

/**
 * Classe représentant un obstacle de murs (hérite de Obstacle)
 */
public class Murs extends Obstacle {
    public Murs(MapObject obj) {
        super(obj);
    }

    @Override
    public String getName() {
        return "Murs";
    }
}
