package ete.monde;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ete.outils.Constantes.*;
import static org.junit.jupiter.api.Assertions.*;

class PersonnageTest {

    private Heros heros;
    private MonstreBasique monstreBasique;
    private MonstreAvancee monstreAvancee;

    @BeforeEach
    void setUp() {
        heros = new Heros();
        monstreBasique = new MonstreBasique();
        monstreAvancee = new MonstreAvancee();
    }

    @Test
    void testGetNumPersonnage() {
        // On suppose que le numéro unique du héros est 0
        assertEquals(0, heros.getNumPersonnage());

        // On suppose que le numéro unique du monstre basique est 1
        assertEquals(1, monstreBasique.getNumPersonnage());

        // On suppose que le numéro unique du monstre avancée est 2
        assertEquals(2, monstreAvancee.getNumPersonnage());
    }

    @Test
    void testGetNomPersonnage() {
        // On suppose que le nom du héros est Heros
        assertEquals("Heros", heros.getNomPersonnage());

        // On suppose que le nom du monstre basique est MonstreBasique
        assertEquals("MonstreBasique", monstreBasique.getNomPersonnage());

        // On suppose que le nom du monstre avancée est MonstreAvancee
        assertEquals("MonstreAvancee", monstreAvancee.getNomPersonnage());
    }

    @Test
    void testGetPv() {
        // On suppose que les PV initiaux sont 10
        assertEquals(10, heros.getPv());

        // On suppose que les PV initiaux sont 1
        assertEquals(1, monstreBasique.getPv());

        // On suppose que les PV initiaux sont 2
        assertEquals(3, monstreAvancee.getPv());
    }

    @Test
    void testPerdPv() {
        // On suppose que les PV initiaux sont 10
        heros.perdPv(5);
        assertEquals(5, heros.getPv());

        // On suppose que les PV initiaux sont 1
        monstreBasique.perdPv(1);
        assertEquals(0, monstreBasique.getPv());

        // On suppose que les PV initiaux sont 3
        monstreAvancee.perdPv(1);
        assertEquals(2, monstreAvancee.getPv());
    }

    @Test
    void testAjouterCoup() {
        // On suppose que le nombre de coups initiaux est 0
        heros.ajouterCoup();
        assertTrue(heros.haveCoups());

        // On suppose que le nombre de coups initiaux est 0
        monstreBasique.ajouterCoup();
        assertTrue(monstreBasique.haveCoups());

        // On suppose que le nombre de coups initiaux est 0
        monstreAvancee.ajouterCoup();
        assertTrue(monstreAvancee.haveCoups());
    }

    @Test
    void testHaveCoups() {
        // On suppose que le nombre de coups initiaux est 0
        assertFalse(heros.haveCoups());

        // On suppose que le nombre de coups initiaux est 0
        assertFalse(monstreBasique.haveCoups());

        // On suppose que le nombre de coups initiaux est 0
        assertFalse(monstreAvancee.haveCoups());
    }

    @Test
    void testGetVitesse() {
        // On suppose que la vitesse initiale est BASIC_SPEED_PLAYER
        assertEquals(BASIC_SPEED_PLAYER, heros.getVitesse());

        // On suppose que la vitesse initiale est BASIC_SPEED_MONSTRE
        assertEquals(BASIC_SPEED_MONSTRE, monstreBasique.getVitesse());

        // On suppose que la vitesse initiale est BASIC_SPEED_MONSTRE_AVANCEE
        assertEquals(BASIC_SPEED_MONSTRE_AVANCEE, monstreAvancee.getVitesse());
    }

    @Test
    void testGetPosX() {
        // On suppose que la position initiale est 0
        assertEquals(0, heros.getPosX());

        // On suppose que la position initiale est 0
        assertEquals(0, monstreBasique.getPosX());

        // On suppose que la position initiale est 0
        assertEquals(0, monstreAvancee.getPosX());
    }

    @Test
    void testGetPosY() {
        // On suppose que la position initiale est 0
        assertEquals(0, heros.getPosY());

        // On suppose que la position initiale est 0
        assertEquals(0, monstreBasique.getPosY());

        // On suppose que la position initiale est 0
        assertEquals(0, monstreAvancee.getPosY());
    }

    @Test
    void testGetAttaque() {
        // On suppose que l'attaque initiale est une épée courte
        assertEquals("EpeeCourte", heros.getAttaque().getNom());

        // On suppose que l'attaque initiale est une attaque de corps à corps basique
        assertEquals("CaCBasique", monstreBasique.getAttaque().getNom());

        // On suppose que l'attaque initiale est une attaque de corps à corps avancée
        assertEquals("CaCAvancee", monstreAvancee.getAttaque().getNom());
    }

    @Test
    void testReinitialiser() {
        // On suppose que les PV initiaux sont 10
        heros.perdPv(5);
        heros.reinitialiser();
        assertEquals(10, heros.getPv());
    }

    @Test
    void testCanAttack() {
        // On suppose que le nombre de coups initiaux est 0
        assertTrue(heros.canAttack());
        heros.ajouterCoup();
        assertFalse(heros.canAttack());
    }

    @Test
    void testCanDeplacer() {
        // On suppose que le monstre ne s'est pas déplacé depuis 0ms
        assertTrue(monstreBasique.canDeplacer());
        monstreBasique.deplacer(1, 1);
        assertFalse(monstreBasique.canDeplacer());

        // On suppose que le monstre ne s'est pas déplacé depuis 0ms
        assertFalse(monstreAvancee.canDeplacer());
        monstreAvancee.deplacer(1, 1);
        assertFalse(monstreAvancee.canDeplacer());
    }

    @Test
    void testDeplacer() {
        // On suppose que la position initiale est 0
        heros.deplacer(1, 1);
        assertEquals(1, heros.getPosX());
        assertEquals(1, heros.getPosY());

        // On suppose que la position initiale est 0
        monstreBasique.deplacer(1, 1);
        assertEquals(1, monstreBasique.getPosX());
        assertEquals(1, monstreBasique.getPosY());

        // On suppose que la position initiale est 0
        monstreAvancee.deplacer(1, 1);
        assertEquals(1, monstreAvancee.getPosX());
        assertEquals(1, monstreAvancee.getPosY());
    }

    @Test
    public void setCoordonnees() {
        // On suppose que la position initiale est 0
        heros.setCoordonnees(1, 1);
        assertEquals(1, heros.getPosX());
        assertEquals(1, heros.getPosY());

        // On suppose que la position initiale est 0
        monstreBasique.setCoordonnees(1, 1);
        assertEquals(1, monstreBasique.getPosX());
        assertEquals(1, monstreBasique.getPosY());

        // On suppose que la position initiale est 0
        monstreAvancee.setCoordonnees(1, 1);
        assertEquals(1, monstreAvancee.getPosX());
        assertEquals(1, monstreAvancee.getPosY());
    }

    @Test
    public void gestionDeplacement() {
        // On suppose que la position initiale est 0
        double[] deplacement = monstreBasique.gestionDeplacement(1, 1, new int[0][0]);
        assertEquals(0.5, deplacement[0]);
        assertEquals(0.5, deplacement[1]);

        // On suppose que la position initiale est 0
        deplacement = monstreAvancee.gestionDeplacement(10, 10, new int[][]{{1, 3}, {1, 3}});
        assertEquals(0, deplacement[0]);
        assertEquals(0, deplacement[1]);
    }
}