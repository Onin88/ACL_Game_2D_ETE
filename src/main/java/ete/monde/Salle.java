package ete.monde;

import ete.environnements.GestionnaireObstacles;
import ete.outils.FabriqueNumSalle;
import ete.outils.TiledMap;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static ete.outils.Constantes.SCREEN_HEIGHT;
import static ete.outils.Constantes.SCREEN_WIDTH;

/**
 * Classe abstraite représentant une salle du jeu (Largeur, hauteur, numéro de salle, type de salle, map, gestionnaire d'obstacles)
 */
public abstract class Salle {

    /**
     * Largeur et hauteur de la salle
     */
    protected int width, height;

    /**
     * Numéro de la salle
     */
    protected int numeroSalle;

    /**
     * Map de la salle (contient les images de la salle)
     */
    private ArrayList<ImageView> map;

    /**
     * Gestionnaire d'obstacles de la salle
     */
    private GestionnaireObstacles gestionnaireObstacles;

    /**
     * Type de la salle (Menu, monstre, boss, etc...)
     */
    protected String typeSalle;

    /**
     * Tableau de 2D de la map (0 = vide, 1 = obstacle)
     */
    protected int[][] mapArray;

    /**
     * Constructeur de la salle (initialise la map, le gestionnaire d'obstacles, le numéro de salle, la largeur et la hauteur)
     */
    public Salle() {
        // On initialise la largeur et la hauteur
        this.width = SCREEN_WIDTH;
        this.height = SCREEN_HEIGHT;

        // On initialise le numéro de salle
        this.numeroSalle = FabriqueNumSalle.getInstance().getNumSalle();

        // On initialise le type de salle
        this.typeSalle = "";

        try {
            // On initialise la map
            TiledMap tiledMap = new TiledMap();

            // On charge la map
            this.map = tiledMap.loadMap(this.numeroSalle);

            // On affecte la largeur et la hauteur
            this.width = tiledMap.getWidht();
            this.height = tiledMap.getHeight();

            // On initialise le gestionnaire d'obstacles
            this.gestionnaireObstacles = tiledMap.getObstacles();
            this.mapArray = tiledMap.getMapArray();

            //affichageConsole();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int[][] getMapArray() {
        return mapArray;
    }

    /**
     * Reinitialise la salle (pour la réutiliser)
     */
    public abstract void reinitialiserSalle(int difficulte);

    public int getNumeroSalle() {
        return numeroSalle;
    }

    public ArrayList<ImageView> getMap(){
        return map;
    }

    public GestionnaireObstacles getObstacles() {
        return gestionnaireObstacles;
    }

    public String getTypeSalle() {
        return typeSalle;
    }

    /**
     * Affiche la map en 2D dans la console
     */
    private void affichageConsole(){

        // Afficher mapArray dans la console
        char lettre = 'A';
        System.out.print("    ");
        for (int i = 0; i < mapArray[0].length; i++) {
            System.out.print(lettre + " ");
            if(lettre+1 > 'Z'){
                lettre = 'A';
            } else
                lettre += 1;
        }
        System.out.println();
        for (int i = 0; i < mapArray.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < mapArray[i].length; j++) {
                System.out.print(mapArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}
