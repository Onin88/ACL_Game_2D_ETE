package ete.environnements;

import org.mapeditor.core.MapObject;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;


/**
 * Classe représentant un obstacle (murs, eau, lave, porte, porte de sortie)
 */
public abstract class Obstacle {

    /**
     * Position de l'obstacle
     */
    protected int posX, posY;

    /**
     * Largeur et hauteur de l'obstacle
     */
    protected double width, height;

    /**
     * Identifiant de l'obstacle
     */
    protected int id;

    /**
     * Type de l'obstacle (rectangle ou polygone)
     */
    protected String type;

    /**
     * Tableau de points du polygone
     */
    protected double[] polygon;

    /**
     * Constructeur d'un obstacle à partir d'un objet de la carte (Pour avoir soit un rectangle, soit un polygone)
     * @param obj objet de la carte
     */
    public Obstacle(MapObject obj){

        // On récupère les informations de l'objet
        this.posX = (int) obj.getX();
        this.posY = (int) obj.getY();
        this.id = obj.getId();

        // On récupère l'objet si c'est un polygone avec un chemin
        if (obj.getShape() instanceof Path2D) {

            // On récupère la forme du chemin
            Path2D path = (Path2D) obj.getShape();

            // On initialise un itérateur de chemin
            PathIterator pathIterator = path.getPathIterator(null);

            // Tableau de coordonnées
            float[] coords = new float[6];

            // Initialisation de la liste de points
            ArrayList<Float> pointsList = new ArrayList<>();

            // On ajoute les points du chemin dans une liste
            while (!pathIterator.isDone()) {

                // On récupère le type de point et ses coordonnées
                int type = pathIterator.currentSegment(coords);

                // Si c'est un point de déplacement ou de mouvement, on l'ajoute à la liste
                if (type == PathIterator.SEG_LINETO || type == PathIterator.SEG_MOVETO) {
                    float x = coords[0];
                    float y = coords[1];
                    pointsList.add(x);
                    pointsList.add(y);
                }

                // On passe au point suivant
                pathIterator.next();
            }

            // On converti la liste en tableau de float
            this.polygon = new double[pointsList.size()];
            for (int i = 0; i < pointsList.size(); i++) {
                this.polygon[i] = pointsList.get(i);
            }

            // Maintenant, pointsArray contient les coordonnées des points du chemin en alternance x, y
            this.type = "Polygon";
            this.width = 0;
            this.height = 0;

        // Sinon c'est un rectangle, on récupère sa largeur et sa hauteur
        } else {
            this.type = "Rectangle";
            this.width = obj.getWidth();
            this.height = obj.getHeight();
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }

    public double[] getPolygon() {
        return polygon;
    }

    public abstract String getName();
}
