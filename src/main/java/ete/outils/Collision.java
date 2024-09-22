package ete.outils;

import ete.attaques.Coup;
import ete.environnements.Obstacle;
import ete.monde.*;
import java.awt.*;
import java.awt.geom.Area;

/**
 * Classe permettant de gérer les collisions entre les personnages et les obstacles, ainsi que les coups et les personnages, et les personnages entre eux
 */
public class Collision {

    /**
     * Le monde dans lequel on vérifie les collisions
     */
    private Monde monde;

    /**
     * Constructeur de Collision
     * @param m le monde dans lequel on vérifie les collisions
     */
    public Collision(Monde m) {
        this.monde = m;
    }

    /**
     * Vérifie si un personnage est en collision avec un autre personnage ou un obstacle
     * @param personnage le personnage dont on vérifie la collision
     * @param x la position en x de l'obstacle ou du personnage
     * @param y la position en y de l'obstacle ou du personnage
     * @return true si il y a collision, false sinon
     */
    public boolean checkCollision(Personnage personnage, double x, double y){

        //On dessine la hitbox du personnage
        Rectangle personnageR = new Rectangle((int) (personnage.getPosX()+ x),
                (int) (personnage.getPosY() + y),
                (int) personnage.getLargeur(),
                (int) personnage.getHauteur());

        //On parcourt les obstacles de la map
        for(Obstacle o : this.monde.getCurrentSalle().getObstacles()){

            //Si l'obstacle est un rectangle
            if(o.getType().equals("Rectangle")){

                //On dessine la hitbox de l'obstacle
                Rectangle obstacle = new Rectangle(o.getPosX(),o.getPosY(),(int)o.getWidth(),(int)o.getHeight());

                //S'il y a collision entre l'obstacle et le joueur
                if(personnageR.intersects(obstacle)){
                    // Enregistre le type de l'obstacle
                    personnage.setTypeObstacleCollision(o.getName());

                    // On renvoi true
                    return true;
                }

            //Sinon si l'obstacle est un polygone
            }else{

                //On vérifie la collision entre le polygone et le personnage
                if(checkCollisionPolygon(o.getPolygon(), personnageR)){

                    // On enregistre le type de l'obstacle
                    personnage.setTypeObstacleCollision(o.getName());

                    // On renvoi true
                    return true;
                }
            }
        }

        //On renvoie false si il n'y a pas de collision
        return false;
    }

    /**
     * Vérifie si un coup est en collision avec un personnage
     * @param coup le coup dont on vérifie la collision
     * @param personnage le personnage dont on vérifie la collision
     * @return true si il y a collision, false sinon
     */
    public boolean checkHitPersonnageCollision(Coup coup, Personnage personnage) {
        // On crée un rectangle avec les coordonnées du coup
        Rectangle attaque = new Rectangle((int)coup.getCoordonnees().getMinX(),(int)coup.getCoordonnees().getMinY(),(int)coup.getCoordonnees().getWidth(),(int)coup.getCoordonnees().getHeight());

        // On crée un rectangle avec les coordonnées du personnage
        Rectangle personnageR = new Rectangle((int)personnage.getPosX(),(int)personnage.getPosY(),(int)personnage.getLargeur(),(int)personnage.getHauteur());

        // On vérifie si les deux rectangles s'intersectent et on renvoie le résultat
        return personnageR.intersects(attaque);
    }

    /**
     * Vérifie si un personnage est en collision avec un polygone
     * @param points les points du polygone
     * @param r le rectangle du personnage
     * @return
     */
    private boolean checkCollisionPolygon(double[] points, Rectangle r) {
        // Nombre de points dans le polygone
        int n = points.length / 2;

        // Tableaux pour les coordonnées x et y des points du polygone
        int[] xCoords = new int[n];
        int[] yCoords = new int[n];

        // Séparation des coordonnées x et y dans les tableaux respectifs
        for (int i = 0; i < n; i++) {
            xCoords[i] = (int) points[i * 2];
            yCoords[i] = (int) points[i * 2 + 1];
        }

        // On crée un polygone avec les coordonnées avec les points du polygone de l'obstacle
        Polygon polygon = new Polygon(xCoords, yCoords, n);

        // On crée une zone avec le polygone
        Area area = new Area(polygon);

        // On crée un rectangle avec les coordonnées du rectangle
        int[] xPoints = {r.x, r.x + r.width, r.x + r.width, r.x};
        int[] yPoints = {r.y, r.y, r.y + r.height, r.y + r.height};

        // On crée un polygone avec les coordonnées du rectangle
        Polygon rPoly = new Polygon(xPoints, yPoints, xPoints.length);

        // On crée une zone avec le rectangle et on l'intersecte avec la zone du polygone
        area.intersect(new Area(rPoly));

        // Si la zone n'est pas vide, il y a collision
        if (!area.isEmpty()) {

            // Alors on renvoie true
            return true;
        }

        // Sinon il n'y a pas collision
        return false;
    }
}
