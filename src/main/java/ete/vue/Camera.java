package ete.vue;

import ete.monde.Monde;
import javafx.geometry.Point2D;


/**
 * Classe représentant la caméra du jeu
 */
public class Camera {

    /**
     * Monde du jeu
     */
    private Monde monde;

    /**
     * Largeur et hauteur de la caméra
     */
    private int largeur, hauteur;

    /**
     * Largeur et hauteur du héros
     */
    private int largeurHeros, hauteurHeros;

    /**
     * Position du héros
     */
    private Point2D positionHeros;

    /**
     * Position de la caméra
     */
    private Point2D positionCamera;

    /**
     * Constante de la caméra
     */
    private Point2D constanteCamera;

    /**
     * Constructeur de la caméra (initialise la largeur, la hauteur, le monde, la largeur et la hauteur du héros, la position du héros, la constante de la caméra et la position de la caméra)
     * @param l Largeur de la caméra
     * @param h Hauteur de la caméra
     * @param m Monde du jeu
     */
    public Camera(int l, int h,Monde m){

        // On déclare la largeur, la hauteur et le monde
        this.largeur = l;
        this.hauteur = h;
        this.monde = m;

        // On ajoute la caméra au monde
        this.monde.ajouterCamera(this);

        // On déclare la largeur et la hauteur du héros
        this.largeurHeros = (int)this.monde.getHeros().getLargeur();
        this.hauteurHeros = (int)this.monde.getHeros().getHauteur();

        // On déclare la position du héros
        this.positionHeros = new Point2D(this.monde.getHeros().getPosX(),this.monde.getHeros().getPosY());

        // On déclare la constante de la caméra (au centre du héros)
        this.constanteCamera = new Point2D((largeur -largeurHeros)/2,(hauteur - hauteurHeros)/2);

        // On déclare la position de la caméra (au centre du héros)
        this.positionCamera = new Point2D(constanteCamera.getX() - positionHeros.getX(),constanteCamera.getY() - positionHeros.getY());
    }

    /**
     * Met à jour la position du héros et de la caméra
     */
    public void updateHeros(){
        this.positionHeros = new Point2D(this.monde.getHeros().getPosX(),this.monde.getHeros().getPosY());
        this.positionCamera = new Point2D(constanteCamera.getX() - positionHeros.getX(),constanteCamera.getY() - positionHeros.getY());
    }

/**
     * Renvoie la position relative en fonction de la position du héros
     * @return la position relative de l'objet/personnage
     */
    public Point2D relatifHeros(double x, double y){
        return new Point2D(this.positionCamera.getX() + x,this.positionCamera.getY() + y);
    }
}
