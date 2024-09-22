package ete.attaques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GestionnaireCoups implements Iterable<Coup> {
    private HashMap<Integer,Coup> coups;
    private HashMap<Integer,Long> tempsAjout;

    public GestionnaireCoups() {
        this.coups = new HashMap<>();
        this.tempsAjout = new HashMap<>();
    }

    /**
     * Ajoute un coup
     * @param coup Le coup à ajouter
     */
    public void ajouterCoup(Coup coup) {
        this.coups.put(coup.getNumCoup(), coup);
        this.tempsAjout.put(coup.getNumCoup(), System.currentTimeMillis());
    }

    /**
     * Supprime un coup
     * @param numCoup Le numéro unique du coup à supprimer
     */
    public void supprimerCoup(int numCoup) {
        this.tempsAjout.remove(numCoup);
        this.coups.remove(numCoup);
    }

    public int getNbCoups() {
        return this.coups.size();
    }

    /**
     * Retourne le temps d'ajout d'un coup
     * @param numCoup Le numéro unique du coup
     * @return
     */
    public long getTempsAjoutCoup(int numCoup) {
        return this.tempsAjout.get(numCoup);
    }

    /**
     * Retourne le dernier coup
     * @return Le dernier coup
     */
    public Coup getDernierCoup() {
        ArrayList<Integer> keys = new ArrayList<>(this.coups.keySet());
        return this.coups.get(keys.get(keys.size() - 1));
    }

    @Override
    public Iterator<Coup> iterator() {
        return this.coups.values().iterator();
    }
}
