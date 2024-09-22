package ete.environnements;

import org.mapeditor.core.MapObject;

/**
 * Classe représentant un obstacle de sanctuaire (hérite de Obstacle)
 */
public class Sanctuaire extends Obstacle{

    /**
     * Constructeur d'un obstacle à partir d'un objet de la carte (Pour avoir soit un rectangle, soit un polygone)
     * @param obj objet de la carte
     */
    public Sanctuaire(MapObject obj) {
        super(obj);
    }

    @Override
    public String getName() {
        return "Sanctuaire";
    }
}
