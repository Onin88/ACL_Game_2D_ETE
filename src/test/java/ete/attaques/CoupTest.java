package ete.attaques;

import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ete.outils.Constantes.*;
import static org.junit.jupiter.api.Assertions.*;

class CoupTest {

    private Coup coup;
    private String direction;
    private Rectangle2D coordonnees;
    private double xPersonnage;
    private double yPersonnage;

    @BeforeEach
    void setUp() {
        direction = DIRECTION_DROITE;
        coordonnees = new Rectangle2D(200, 200, 50, 100);
        xPersonnage = 150;
        yPersonnage = 150;
        coup = new Coup(direction, coordonnees, xPersonnage, yPersonnage);
    }

    @Test
    void updateCoordonneesOnly() {
        assertEquals(150, coup.getCoordonnees().getMinX());
        assertEquals(150, coup.getCoordonnees().getMinY());

        coup.updateCoordonneesPersonnage(200, 200);

        assertEquals(200, coup.getCoordonnees().getMinX());
        assertEquals(200, coup.getCoordonnees().getMinY());
    }

    @Test
    void updateCoordonneesDirections() {
        assertEquals(150, coup.getCoordonnees().getMinX());
        assertEquals(150, coup.getCoordonnees().getMinY());

        coup.updateCoordonneesDirections(DIRECTION_HAUT,200, 200);

        assertEquals(200, coup.getCoordonnees().getMinX());
        assertEquals(150, coup.getCoordonnees().getMinY());
        assertEquals(100, coup.getCoordonnees().getWidth());
        assertEquals(50, coup.getCoordonnees().getHeight());

        coup.updateCoordonneesDirections(DIRECTION_DROITE,200, 200);

        assertEquals(200, coup.getCoordonnees().getMinX());
        assertEquals(200, coup.getCoordonnees().getMinY());
        assertEquals(50, coup.getCoordonnees().getWidth());
        assertEquals(100, coup.getCoordonnees().getHeight());

        coup.updateCoordonneesDirections(DIRECTION_BAS,200, 200);

        assertEquals(200, coup.getCoordonnees().getMinX());
        assertEquals(200, coup.getCoordonnees().getMinY());
        assertEquals(100, coup.getCoordonnees().getWidth());
        assertEquals(50, coup.getCoordonnees().getHeight());

        coup.updateCoordonneesDirections(DIRECTION_GAUCHE,200, 200);

        assertEquals(150, coup.getCoordonnees().getMinX());
        assertEquals(200, coup.getCoordonnees().getMinY());
        assertEquals(50, coup.getCoordonnees().getWidth());
        assertEquals(100, coup.getCoordonnees().getHeight());
    }
}