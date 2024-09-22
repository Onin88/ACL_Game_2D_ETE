package ete.environnements;

import org.mapeditor.core.MapObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Classe représentant un gestionnaire d'obstacles (hérite de Iterable<Obstacle>)
 */
public class GestionnaireObstacles implements Iterable<Obstacle>{

    /**
     * Liste des obstacles
     */
    private ArrayList<Obstacle> obstacles;

    /**
     * Constructeur de GestionnaireObstacles
     */
    public GestionnaireObstacles(){
        this.obstacles = new ArrayList<>();
    }

    /**
     * Ajoute un obstacle à la liste des obstacles en fonction de son nom
     * @param m l'obstacle à ajouter (MapObject)
     */
    public void addObstacle(MapObject m){
        switch (m.getName()){
            case "Murs":
                this.obstacles.add(new Murs(m));
                break;
            case "Eau":
                this.obstacles.add(new Eau(m));
                break;
            case "Lave":
                this.obstacles.add(new Lave(m));
                break;
            case "Porte":
                this.obstacles.add(new Porte(m));
            case "PorteSortie":
                this.obstacles.add(new PorteSortie(m));
            case "Sanctuaire":
                this.obstacles.add(new Sanctuaire(m));
        }
    }

    @Override
    public Iterator<Obstacle> iterator() {
        return obstacles.iterator();
    }}
