package ete.environnements;

import org.mapeditor.core.MapObject;

/**
 * Classe représentant un obstacle de porte de sortie (hérite de Obstacle)
 */
public class PorteSortie extends Obstacle{
    public PorteSortie(MapObject obj) {
        super(obj);
    }

    @Override
    public String getName() {
        return "PorteSortie";
    }
}
