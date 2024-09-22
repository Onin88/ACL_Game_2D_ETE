package ete.monde;

/**
 * Classe représentant une salle de menu (Hérite de Salle)
 */
public class SalleMenu extends Salle{
    public SalleMenu() {
        super();
        typeSalle = "SalleMenu";
    }

    @Override
    public void reinitialiserSalle(int difficulte) {}

}
