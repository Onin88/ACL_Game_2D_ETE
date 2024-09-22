package ete.outils;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

/**
 * Classe contenant les constantes du jeu
 */
public class Constantes {

    /**
     * Nom du jeu
     */
    public static final String TITLE = "Epopée des Terres Ethérées";





    /****************************************** DIMENSIONS ******************************************/

    /**
     * Largeur de la fenêtre
     */
    public static int SCREEN_WIDTH = 1366;

    /**
     * Hauteur de la fenêtre
     */
    public static int SCREEN_HEIGHT = 768;

    /**
     * Largeur de la map
     */
    public static int MAP_WIDTH = 1600;

    /**
     * Hauteur de la map
     */
    public static int MAP_HEIGHT = 1376;

    /**
     * Taille d'une case de la map
     */
    public static int TILE_SIZE = 32;

    /***********************************************************************************************/





    /************************************ VITESSES DES ENTITES ***********************************/

    /**
     * Vitesse de base du joueur
     */
    public static final float BASIC_SPEED_PLAYER = 2F;

    /**
     * Vitesse de base du monstre
     */
    public static final float BASIC_SPEED_MONSTRE = 1F;

    /**
     * Vitesse de base des personnages
     */
    public static final float BASIC_SPEED = 1F;

    /**
     * Vitesse de base du monstre avancée
     */
    public static final float BASIC_SPEED_MONSTRE_AVANCEE = 2F;

    /*********************************************************************************************/







    /*********************************** COORDONNEES DES ENTITES ***********************************/

    /**
     * Coordonnée X du héros au début du jeu
     */
    public static final ArrayList<Integer> COORD_HEROS_X = new ArrayList<Integer>() {{
        add(MAP_WIDTH / 2);
        add(MAP_WIDTH /2 - 200);
        add(MAP_WIDTH / 2);
        add(MAP_WIDTH / 2);
    }};

    /**
     * Coordonnée Y du héros
     */
    public static final ArrayList<Integer> COORD_HEROS_Y = new ArrayList<Integer>() {{
        add(MAP_HEIGHT / 2);
        add(MAP_HEIGHT - 170);
        add(MAP_HEIGHT - 75);
        add(MAP_HEIGHT - 75);
    }};

    /***********************************************************************************************/







    /********************************** URL DES IMAGES **********************************/

    /**
     * URL du sprite du héros
     */
    public static final String URL_SPRITE_HEROS = "/sprites/heros_statique/droite/1.png";

    /**
     * URL du sprite du monstre basique
     */
    public static final String URL_SPRITE_MONSTRE_BASIQUE = "/sprites/monstreBasique_statique/droite/1.png";

    /**
     * URL du sprite du monstre avancee (<a href="https://www.chronocompendium.com/Term/File:Cyrus_Monster_Sprites.png.html">...</a>)
     */
    public static final String URL_SPRITE_MONSTRE_AVANCEE = "/sprites/monstreAvancee_statique/droite/1.png";

    /**
     * URL du coeur (<a href="https://www.vecteezy.com/png/34890045-heart-pixel-clipart-flat-design-icon-isolated-on-transparent-background-3d-render-valentine-concept">...</a>)
     */
    public static final String URL_COEUR = "/autres/heart.png";

    /*************************************************************************************/







    /*********************************** TEMPS DES ANIMATIONS ***********************************/

    /**
     * Nombre d'images de l'animation du héros
     */
    public static final int NB_IMAGES_ANIMATION = 7;

    /**
     * Temps d'animation
     */
    public static final int TIME_ANIMATION = 350;

    /**
     * Temps entre chaque image de l'animation du héros
     */
    public static final int TIME_BETWEEN_ANIMATION = 50;

    /**
     * Temps entre chaque image de l'animation du monstre
     */
    public static final int TIME_BETWEEN_ANIMATION_MONSTRE = 100;

    /*********************************************************************************************/










    /*********************************** ANIMATIONS ***********************************/

    /**
     * Etat d'animation du héros
     */
    public static final String ETAT_ANIMATION_STATIQUE = "Statique", ETAT_ANIMATION_DEGAT = "Degat", ETAT_ANIMATION_MORT = "Mort", ETAT_ANIMATION_ATTAQUE = "Attaque";







    /**
     * Les différentes directions
     */
    public static final String DIRECTION_HAUT = "haut", DIRECTION_BAS = "bas", DIRECTION_GAUCHE = "gauche", DIRECTION_DROITE = "droite";






    /************************** CONTROLES (PEUVENT ETRE MODIFIES) **************************/

    /**
     * Contrôle de la touche haut
     */
    public static KeyCode CONTROLE_HAUT = KeyCode.UP, CONTROLE_HAUT2 = KeyCode.Z;

    /**
     * Contrôle de la touche bas
     */
    public static KeyCode CONTROLE_BAS = KeyCode.DOWN, CONTROLE_BAS2 = KeyCode.S;

    /**
     * Contrôle de la touche gauche
     */
    public static KeyCode CONTROLE_GAUCHE = KeyCode.LEFT, CONTROLE_GAUCHE2 = KeyCode.Q;

    /**
     * Contrôle de la touche droite
     */
    public static KeyCode CONTROLE_DROITE = KeyCode.RIGHT, CONTROLE_DROITE2 = KeyCode.D;

    /**
     * Contrôle de la touche attaque
     */
    public static KeyCode CONTROLE_TIR = KeyCode.SPACE;

    /**
     * Contrôle de la touche sprint
     */
    public static KeyCode CONTROLE_SPRINT = KeyCode.SHIFT;

    /***************************************************************************************/
}
