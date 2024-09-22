package ete.outils;

/**
 * Singleton pour la génération de numéros de personnages
 */
public class FabriqueNumPerso {

    /**
     * L'instance de la fabrique
     */
    private static FabriqueNumPerso instance = null;

    /**
     * Le numéro unique de personnage
     */
    private int numPerso = 0;

    /**
     * Constructeur de la fabrique
     */
    private FabriqueNumPerso(){}

    /**
     * Fonction pour récupérer l'instance de la fabrique
     * @return L'instance de la fabrique
     */
    public static FabriqueNumPerso getInstance(){
        if(instance == null){
            instance = new FabriqueNumPerso();
        }
        return instance;
    }

    /**
     * Fonction pour récupérer un numéro de personnage
     * @return Un numéro de personnage
     */
    public int getNumPerso(){
        return this.numPerso++;
    }
}
