package ete.monde;

import java.util.HashMap;
import java.util.Iterator;

import static ete.outils.Constantes.*;

/**
 * Classe représentant une salle de monstre (Hérite de Salle)
 */
public class SalleMonstre extends Salle implements Iterable<Monstre>{
    /**
     * Liste des monstres de la salle
     */
    private HashMap<Integer, Monstre> monstres;

    /**
     * Difficulté de la salle
     */
    private int difficulte;

    /**
     * Constructeur de SalleMonstre qui initialise la liste des monstres et ajoute des monstres dans la salle
     */
    public SalleMonstre() {
        super();

        //Initialisation de la liste des monstres
        this.monstres = new HashMap<>();

        //Initialisation du type de salle
        typeSalle = "SalleMonstre";

        //Initialisation de la difficulté
        difficulte = 1;

        //Initialisation d'un monstre basique dans la salle
        initialiserMonstreSalle();
    }

    /**
     * Ajoute un certain nombre de monstres basiques et avancés dans la salle en fonction du numéro de la salle
     */
    public void initialiserMonstreSalle() {
        switch (numeroSalle) {
            case 1:
                addMonstre(10,0);
                break;
            case 2:
                addMonstre(4,2);
                break;
            case 3:
                addMonstre(2,4);
                break;
        }
    }

    /**
     * Réinitialise la salle en supprimant tous les monstres et en en recréant
     */
    @Override
    public void reinitialiserSalle(int difficulte){
        this.difficulte = difficulte;
        this.clearMonstres();
        this.initialiserMonstreSalle();
    }

    /**
     * Retourne la liste des monstres de la salle
     * @return la liste des monstres de la salle
     */
    public HashMap<Integer, Monstre> getMonstres() {
        return monstres;
    }

    /**
     * Supprime tous les monstres de la salle
     */
    public void clearMonstres(){
        this.monstres.clear();
    }

    /**
     * Ajoute des monstres dans la salle en fonction du nombre de monstres basiques et avancés
     */
    public void addMonstre(int nbMonstreBasique, int nbMonstreAvancee){

        // On ajoute les monstres basiques pour un nombre donné
        for (int i = 0; i < nbMonstreBasique; i++) {

            // On récupère une position aléatoire pour le monstre
            int[] position = getPositionAleatoireMonstre();

            // On crée un monstre basique
            Monstre monstre = new MonstreBasique();

            // On lui affecte les coordonnées
            monstre.setCoordonnees(position[0], position[1]);

            // On multiplie ses PV par la difficulté
            monstre.setPv(monstre.getPv() * difficulte);

            // On ajoute le monstre à la liste des monstres
            this.monstres.put(monstre.getNumPersonnage(), monstre);
        }

        // On ajoute les monstres avancés pour un nombre donné
        for (int i = 0; i < nbMonstreAvancee; i++) {

            // On récupère une position aléatoire pour le monstre
            int[] position = getPositionAleatoireMonstre();

            // On crée un monstre avancé
            Monstre monstreAvancee = new MonstreAvancee();

            // On lui affecte les coordonnées
            monstreAvancee.setCoordonnees(position[0], position[1]);

            // On multiplie ses PV par la difficulté
            monstreAvancee.setPv(monstreAvancee.getPv() * difficulte);

            // On ajoute le monstre à la liste des monstres
            this.monstres.put(monstreAvancee.getNumPersonnage(), monstreAvancee);
        }
    }

    /**
     * Génère une position aléatoire pour un monstre
     * @return un tableau contenant les coordonnées x et y du monstre
     */
    public int[] getPositionAleatoireMonstre(){
        int x = (int) (Math.random() * SCREEN_WIDTH/TILE_SIZE) +1;
        int y = (int) (Math.random() * SCREEN_HEIGHT/TILE_SIZE) +1;

        // Si la position est déjà occupée par un obstacle ou un monstre, on en génère une autre (en se basant sur la taille de la salle et la matrice de la salle)
        while (mapArray[x][y] != 0 && mapArray[x-1][y] != 0 && mapArray[x][y-1] != 0 && mapArray[x+1][y] != 0 && mapArray[x][y+1] != 0 && mapArray[x-1][y-1] != 0 && mapArray[x+1][y+1] != 0 && mapArray[x-1][y+1] != 0 && mapArray[x+1][y-1] != 0) {

            // On génère des coordonnées aléatoires
            int xTmp = (int) (Math.random() * (SCREEN_WIDTH /TILE_SIZE));
            int yTmp = (int) (Math.random() * (SCREEN_HEIGHT /TILE_SIZE));

            // On vérifie que les coordonnées ne sont pas en dehors de la salle
            if (xTmp-1 > 0 && xTmp+1 < SCREEN_WIDTH/TILE_SIZE && yTmp-1 > 0 && yTmp+1 < SCREEN_HEIGHT/TILE_SIZE) {
                x = xTmp;
                y = yTmp;
            }
        }

        // On retourne les coordonnées du monstre
        return new int[]{x*TILE_SIZE, y*TILE_SIZE};
    }

    @Override
    public Iterator<Monstre> iterator() {
        return monstres.values().iterator();
    }
}
