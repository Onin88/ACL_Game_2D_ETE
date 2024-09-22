package ete.environnements;

import org.mapeditor.core.MapObject;

/**
 * Classe représentant un obstacle de lave (hérite de Obstacle)
 */
public class Lave extends Obstacle {
    public Lave(MapObject lave) {
        super(lave);
    }

    @Override
    public String getName() {
        return "Lave";
    }
}
