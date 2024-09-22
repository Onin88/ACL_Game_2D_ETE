package ete.attaques;

import ete.outils.FabriqueNumCoup;
import javafx.geometry.Rectangle2D;

import static ete.outils.Constantes.*;

/**
 * Classe pour les coups qui hérite d'Attaque
 */
public class Coup {
    /**
     * Numéro unique du coup
     */
    private int numCoup;

    /**
     * Direction du coup (en fonction des touches directionnelles)
     */
    private String direction;

    /**
     * La dernière direction du coup (pour changer la direction de l'attaque)
     */
    private String lastDirection;

    /**
     * Coordonnées du coup (X, Y, Largeur, Hauteur)
     */
    private Rectangle2D coordonnees;

    /**
     * Constructeur d'un coup pour le héro (en fonction de la direction)
     */
    public Coup(String direction, Rectangle2D coordonnees, double xPersonnage, double yPersonnage) {
        // On affecte un numéro unique au coup
        this.numCoup = FabriqueNumCoup.getInstance().getNumCoup();

        // On affecte la direction
        this.direction = direction;

        // On vérifie si la direction est vide, si c'est le cas alors on affecte la direction droite
        if(direction.equals("")) this.lastDirection = DIRECTION_DROITE;

        // Sinon on affecte la direction
        else this.lastDirection = direction;

        // On affecte les coordonnées du coup (X, Y, Largeur, Hauteur)
        this.coordonnees = coordonnees;

        // On met à jour les coordonnées du coup en fonction des coordonnées du personnage
        updateCoordonneesRectangle(xPersonnage, yPersonnage);
    }

    /**
     * Met à jour les coordonnées du coup en fonction de la direction
     * @param xPersonnage Position X du personnage
     * @param yPersonnage Position Y du personnage
     */
    private void updateCoordonneesRectangle(double xPersonnage, double yPersonnage) {
        switch (lastDirection) {
            case DIRECTION_HAUT:

                // Si la direction précédente est la direction bas alors on change juste les coordonnées du rectangle
                if(direction.equals(DIRECTION_BAS)) this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage - coordonnees.getHeight(), coordonnees.getHeight(), coordonnees.getWidth());

                // Si la direction précédente est la direction gauche ou droite alors on change les coordonnées du rectangle et on le fait pivoter
                if(direction.equals(DIRECTION_GAUCHE) || direction.equals(DIRECTION_DROITE)) this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage - coordonnees.getWidth(), coordonnees.getWidth(), coordonnees.getHeight());

                // Sinon on change juste les coordonnées du rectangle
                this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage - coordonnees.getWidth(), coordonnees.getHeight(), coordonnees.getWidth());
                break;
            case DIRECTION_BAS:

                // Si la direction précédente est la direction haut alors on change juste les coordonnées du rectangle
                if(direction.equals(DIRECTION_HAUT)) this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage, coordonnees.getHeight(), coordonnees.getWidth());

                // Si la direction précédente est la direction gauche ou droite alors on change les coordonnées du rectangle et on le fait pivoter
                if(direction.equals(DIRECTION_GAUCHE) || direction.equals(DIRECTION_DROITE)) this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage, coordonnees.getWidth(), coordonnees.getHeight());

                // Sinon on change juste les coordonnées du rectangle
                this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage, coordonnees.getHeight(), coordonnees.getWidth());
                break;
            case DIRECTION_DROITE:

                // Si la direction précédente est la direction haut ou bas alors on change les coordonnées du rectangle et on le fait pivoter
                if(direction.equals(DIRECTION_HAUT) || direction.equals(DIRECTION_BAS)) this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage, coordonnees.getHeight(), coordonnees.getWidth());

                // Sinon on change juste les coordonnées du rectangle
                this.coordonnees = new Rectangle2D(xPersonnage, yPersonnage, coordonnees.getWidth(), coordonnees.getHeight());
                break;
            case DIRECTION_GAUCHE:

                // Si la direction précédente est la direction haut ou bas alors on change les coordonnées du rectangle et on le fait pivoter
                if(direction.equals(DIRECTION_HAUT) || direction.equals(DIRECTION_BAS)) this.coordonnees = new Rectangle2D(xPersonnage - coordonnees.getWidth(), yPersonnage, coordonnees.getHeight(), coordonnees.getWidth());

                // Sinon on change juste les coordonnées du rectangle
                this.coordonnees = new Rectangle2D(xPersonnage - coordonnees.getWidth(), yPersonnage, coordonnees.getWidth(), coordonnees.getHeight());
                break;
        }

        // On met à jour la direction
        direction = lastDirection;
    }

    /**
     * Retourne le numéro unique du coup
     * @return Numéro unique du coup
     */
    public int getNumCoup() {
        return numCoup;
    }

    /**
     * Retourne les coordonnées du coup
     * @return Rectangle des coordonnées du coup (X, Y, Largeur, Hauteur)
     */
    public Rectangle2D getCoordonnees() {
        return coordonnees;
    }

    /**
     * Met à jour les coordonnées du coup en fonction des nouvelles coordonnées du personnage
     * @param posX Position X du personnage
     * @param posY Position Y du personnage
     */
    public void updateCoordonneesPersonnage(double posX, double posY) {
        // Si la direction est la même que la dernière direction alors on met à jour juste les coordonnées du coup
        if(lastDirection.equals(direction)) {
            updateCoordonneesOnly(posX, posY);

            // Sinon on met à jour les coordonnées du coup en fonction de la direction
        } else {
            updateCoordonneesRectangle(posX, posY);
        }
    }

    /**
     * Met à jour les coordonnées du coup en fonction de la dernière direction du personnage
     */
    public void updateCoordonneesDirections(String lastDirection, double posX, double posY) {

        // On met à jour la dernière direction
        this.lastDirection = lastDirection;

        // Si la direction est la même que la dernière direction alors on met à jour juste les coordonnées du coup
        if (this.lastDirection.equals(direction)) {
            updateCoordonneesOnly(posX, posY);

            // Sinon on met à jour les coordonnées du coup en fonction de la direction
        } else {
            updateCoordonneesRectangle(posX, posY);
        }
    }


    /**
     * Met à jour les coordonnées du coup en fonction de la direction (sans pivoter le rectangle)
     * @param posX Position X du personnage
     * @param posY Position Y du personnage
     */
    private void updateCoordonneesOnly(double posX, double posY) {
        switch (direction) {

            // Si la direction est la direction haut alors on change la position X et Y - la hauteur du rectangle
            case DIRECTION_HAUT:
                this.coordonnees = new Rectangle2D(posX, posY - coordonnees.getHeight(), coordonnees.getWidth(), coordonnees.getHeight());
                break;

            // Si la direction est la direction bas ou droite alors on change la position X et Y
            case DIRECTION_BAS, DIRECTION_DROITE:
                this.coordonnees = new Rectangle2D(posX, posY, coordonnees.getWidth(), coordonnees.getHeight());
                break;

            // Si la direction est la direction gauche alors on change la position X - la largeur du rectangle et Y
            case DIRECTION_GAUCHE:
                this.coordonnees = new Rectangle2D(posX - coordonnees.getWidth(), posY, coordonnees.getWidth(), coordonnees.getHeight());
                break;
        }
    }
}
