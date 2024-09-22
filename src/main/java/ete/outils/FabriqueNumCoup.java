package ete.outils;

/**
 * Singleton permettant de générer un numéro de coup unique
 */
public class FabriqueNumCoup {

    /**
     * L'instance de la fabrique de numéro de coup
     */
    private static FabriqueNumCoup instance = null;

    /**
     * Le numéro unique de coup
     */
    private int numCoup;

    /**
     * Constructeur privé
     */
    private FabriqueNumCoup() {
        this.numCoup = 0;
    }

    /**
     * Retourne l'instance de la fabrique de numéro de coup
     * @return L'instance de la fabrique de numéro de coup
     */
    public static FabriqueNumCoup getInstance() {
        if (instance == null) {
            instance = new FabriqueNumCoup();
        }
        return instance;
    }

    /**
     * Retourne un numéro de coup unique
     * @return Un numéro de coup unique
     */
    public int getNumCoup() {
        return this.numCoup++;
    }
}
