package ete.outils;

/**
 * Singleton qui permet de générer un numéro de salle unique
 */
public class FabriqueNumSalle {

    /**
     * L'instance de la fabrique
     */
    private static FabriqueNumSalle instance = null;

    /**
     * Le numéro unique de salle
     */
    private int numSalle = 0;

    /**
     * Constructeur de la fabrique
     */
    private FabriqueNumSalle(){}

    /**
     * Fonction pour récupérer l'instance de la fabrique
     * @return L'instance de la fabrique
     */
    public static FabriqueNumSalle getInstance(){
        if(instance == null){
            instance = new FabriqueNumSalle();
        }
        return instance;
    }

    /**
     * Fonction pour récupérer un numéro de salle
     * @return Un numéro de salle
     */
    public int getNumSalle(){
        return this.numSalle++;
    }
}
