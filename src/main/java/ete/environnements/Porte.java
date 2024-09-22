package ete.environnements;

import org.mapeditor.core.MapObject;

/**
 * Classe représentant un obstacle de porte (hérite de Obstacle)

 */
public class Porte extends Obstacle {
    public Porte(MapObject p ){
        super(p);
    }
    @Override
    public String getName() {
        return "Porte";
    }
}