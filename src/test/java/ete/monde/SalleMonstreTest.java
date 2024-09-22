package ete.monde;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalleMonstreTest {

    private static SalleMonstre salleMonstre, salleMonstre2, salleMonstre3;

    @BeforeAll
    static void setUp() {
        salleMonstre = new SalleMonstre();
        salleMonstre2 = new SalleMonstre();
        salleMonstre3 = new SalleMonstre();
    }

    @Test
    void testReinitialiserSalle() {
       
        salleMonstre.reinitialiserSalle(2);
        // On suppose que le nombre de monstres dans la salle est 20 après réinitialisation avec une difficulté de 2
        assertEquals(0, salleMonstre.getMonstres().size());

        
        salleMonstre2.reinitialiserSalle(3);
        // On suppose que le nombre de monstres dans la salle est 30 après réinitialisation avec une difficulté de 3
        assertEquals(10, salleMonstre2.getMonstres().size());

        
        salleMonstre3.reinitialiserSalle(4);
        // On suppose que le nombre de monstres dans la salle est 40 après réinitialisation avec une difficulté de 4
        assertEquals(6, salleMonstre3.getMonstres().size());
    }

    @Test
    void testClearMonstres() {
       
        salleMonstre.clearMonstres();
        // On suppose que le nombre de monstres dans la salle est 0 après suppression
        assertEquals(0, salleMonstre.getMonstres().size());

        
        salleMonstre2.clearMonstres();
        // On suppose que le nombre de monstres dans la salle est 0 après suppression
        assertEquals(0, salleMonstre2.getMonstres().size());

        
        salleMonstre3.clearMonstres();
        // On suppose que le nombre de monstres dans la salle est 0 après suppression
        assertEquals(0, salleMonstre3.getMonstres().size());
    }

    @Test
    void testAddMonstre() {
       
        salleMonstre.addMonstre(5, 3);
        // On suppose que le nombre de monstres dans la salle est 18 après ajout de 5 monstres basiques et 3 monstres avancés
        assertEquals(8, salleMonstre.getMonstres().size());

        
        salleMonstre2.addMonstre(2, 4);
        // On suppose que le nombre de monstres dans la salle est 12 après ajout de 2 monstres basiques et 4 monstres avancés
        assertEquals(16, salleMonstre2.getMonstres().size());

        
        salleMonstre3.addMonstre(3, 2);
        // On suppose que le nombre de monstres dans la salle est 10 après ajout de 3 monstres basiques et 2 monstres avancés
        assertEquals(11, salleMonstre3.getMonstres().size());
    }

    @Test
    void testGetPositionAleatoireMonstre() {
       
        int[] position = salleMonstre.getPositionAleatoireMonstre();
        // On suppose que la position du monstre est comprise entre 0 et 1600 pour l'axe des abscisses et entre 0 et 1376 pour l'axe des ordonnées
        assertTrue(position[0] >= 0 && position[0] <= 1600);
        assertTrue(position[1] >= 0 && position[1] <= 1376);

        
        int[] position2 = salleMonstre2.getPositionAleatoireMonstre();
        // On suppose que la position du monstre est comprise entre 0 et 1600 pour l'axe des abscisses et entre 0 et 1376 pour l'axe des ordonnées
        assertTrue(position2[0] >= 0 && position2[0] <= 1600);
        assertTrue(position2[1] >= 0 && position2[1] <= 1376);

        
        int[] position3 = salleMonstre3.getPositionAleatoireMonstre();
        // On suppose que la position du monstre est comprise entre 0 et 1600 pour l'axe des abscisses et entre 0 et 1376 pour l'axe des ordonnées
        assertTrue(position3[0] >= 0 && position3[0] <= 1600);
        assertTrue(position3[1] >= 0 && position3[1] <= 1376);
    }
}