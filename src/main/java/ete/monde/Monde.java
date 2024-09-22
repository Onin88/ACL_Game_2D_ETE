package ete.monde;

import ete.attaques.Coup;
import ete.outils.Collision;
import ete.vue.Camera;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import static ete.outils.Constantes.*;

/**
 * Classe permettant de gérer le monde (héros, monstres, salles, ...)
 */
public class Monde{

    /**
     * La caméra pour gérer le déplacement du héros dans la map
     */
    private Camera camera;

    /**
     * Gestionnaire de collision
     */
    private Collision collision;

    /**
     * Indique si le héros est dans un menu ou pas
     */
    private boolean dansUnMenu;

    /**
     * Initialisation du monde (salles dont les monstres, héros, initialisation des collisions)
     */
    public Monde(){

        // Initialisation des salles
        initialisationSalles();

        // On instancie le héros et on le place au début de la première map
        this.heros = new Heros();
        this.heros.setCoordonnees(COORD_HEROS_X.get(currentSalle.getNumeroSalle()),COORD_HEROS_Y.get(currentSalle.getNumeroSalle()));

        this.collision = new Collision(this);

        // On initialise la difficulté
        this.difficulte = 1;

        // On initialise le temps de collision
        this.collisionStartTime = 0;
    }

    /**
     * Met à jour le monde (Héros, monstres et caméra)
     */
    public void update(){
        this.gestionHeros();
        if(this.currentSalle.getTypeSalle().equals("SalleMonstre")) this.gestionMonstre();
        this.camera.updateHeros();
    }

    /**
     * Réinitialise le monde (héros, salles)
     */
    public void reinitialiserMonde() {
        //On réinitialise les salles
        for (Salle s : this.salles){
            if(s.getTypeSalle().equals("SalleMonstre")){
                s.reinitialiserSalle(difficulte);
            }
        }

        //On réinitialise à la salle actuelle
        this.currentSalle = this.salles.get(0);

        //On réinitialise les coordonnées du héros
        this.heros.setCoordonnees(COORD_HEROS_X.get(currentSalle.getNumeroSalle()),COORD_HEROS_Y.get(currentSalle.getNumeroSalle()));

        // On réinitialise le héros
        this.heros.reinitialiser();
    }

    /**
     * Gère la fermeture du jeu (affiche une alerte pour confirmer la fermeture ou non)
     */
    private void quitterJeu() {
        dansUnMenu = true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter le jeu");
        alert.setHeaderText("Voulez-vous vraiment quitter le jeu ?");
        alert.setContentText("Toutes les données non sauvegardées seront perdues");

        Platform.runLater(() -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Platform.exit();
            } else {
                alert.close();
                heros.deplacer(0,-100);
                dansUnMenu = false;
            }
        });
    }

    /**
     * Gère quand le joueur termine le jeu (affiche une alerte pour recommencer ou quitter le jeu)
     */
    private void terminerJeu(){
        //On indique que le héros est dans un menu
        dansUnMenu = true;

        //On crée une alerte pour le menu de fin
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Félicitations");
        alert.setHeaderText("Vous avez terminé le jeu !");
        alert.setContentText("Voulez-vous recommencer ou quitter le jeu ?\n" +
                "Le jeu sera réinitialisé, plus difficile.\n" +
                "Vous aurez la capacité de nager dans l'eau et la lave.");
        alert.getButtonTypes().set(0, new ButtonType("Recommencer"));
        alert.getButtonTypes().set(1, new ButtonType("Quitter"));

        // On lance la tâche dans le thread JavaFX
        Platform.runLater(() -> {

            //On affiche et récupère le résultat de l'alerte
            Optional<ButtonType> result = alert.showAndWait();

            //On vérifie si le résultat est présent
            if(result.isPresent()) {

                //On vérifie si le joueur a cliqué sur recommencer
                if (result.get() == alert.getButtonTypes().get(0)) {

                    //On ferme l'alerte
                    alert.close();

                    //On augmente la difficulté
                    this.difficulte++;

                    //On réinitialise le monde
                    reinitialiserMonde();

                    // Le héros gagne la capacité de nager
                    getHeros().setSaitNager(true);

                    //On indique que le héros n'est plus
                    dansUnMenu = false;

                    // Sinon on ferme le jeu
                } else {
                    Platform.exit();
                }
            }
        });
    }

    /**
     * Retourne si le héros est dans un menu ou pas
     * @return vrai ou faux
     */
    public boolean estDansUnMenu() {
        return dansUnMenu;
    }

    /**
     * Ajoute une caméra au monde
     * @param c la caméra
     */
    public void ajouterCamera(Camera c){
        this.camera = c;
    }

    /**********************************************************************************************/











    /******************************************** Gestion des salles ************************************/

    /**
     * La salle actuelle
     */
    private Salle currentSalle;

    /**
     * La liste des salles
     */
    private ArrayList<Salle> salles;

    /**
     * Difficulté du jeu
     */
    private int difficulte;

    /**
     * Initialise les salles
     */
    private void initialisationSalles() {
        this.salles = new ArrayList<>();


        // On indique que le héros n'est pas dans un menu
        dansUnMenu = false;

        /*--- Création de la salle menu ---*/
        this.salles.add(new SalleMenu());

        /*--- Création des salles monstres ---*/
        for (int i = 0; i <3; i++) {
            Salle s = new SalleMonstre();
            this.salles.add(s);
        }

        /*--- Salle actuelle est le menu ---*/
        this.currentSalle = salles.get(0);
    }

    /**
     * Gère ce qu'il se passe quand le héros change de salle
     */
    public void changerSalle(){
        if(canChangerSalle()) {

            //On vérifie si le héros est en collision avec une porte
            if (this.heros.getTypeObstacleCollision().equals("Porte")) {

                if(currentSalle.getNumeroSalle() + 1 == salles.size()) {
                    terminerJeu();
                } else {

                    //On change la salle actuelle
                    this.currentSalle = salles.get(this.currentSalle.getNumeroSalle() + 1);

                    //On change les coordonnées du héros
                    this.heros.setCoordonnees(COORD_HEROS_X.get(this.currentSalle.getNumeroSalle()), COORD_HEROS_Y.get(this.currentSalle.getNumeroSalle()));
                }
            }
        }
    }

    /**
     * Vérifie si le héros peut changer de salle
     * @return vrai s'il peut changer de salle, faux sinon
     */
    public boolean canChangerSalle(){

        //On vérifie si le héros est dans une salle monstre et retourne vrai si la salle est vide
        if(currentSalle.getTypeSalle().equals("SalleMonstre")) return ((SalleMonstre)currentSalle).getMonstres().isEmpty();

        // Sinon le héros est forcément dans une salle sans monstre donc on retourne vrai
        return true;
    }

    /**
     * Retourne la salle actuelle
     * @return la salle actuelle
     */
    public Salle getCurrentSalle() {
        return currentSalle;
    }

    /**
     * Retourne la salle actuelle (sous forme de salle monstre) (si la salle actuelle n'est pas une salle monstre, on retourne null)
     * @return la salle actuelle
     */
    public SalleMonstre getCurrentSalleMonstre() {
        if (currentSalle.getTypeSalle().equals("SalleMonstre")) return (SalleMonstre) currentSalle;
        return null;
    }

    public int getDifficulte() {
        return difficulte;
    }

    /**********************************************************************************************/












    /************************************ Gestions du héros ************************************/

    /**
     * Le héros du jeu
     */
    private Heros heros;

    /**
     * Debut du timer de collision
     */
    private long collisionStartTime;

    public Heros getHeros(){
        return this.heros;
    }

    /**
     * Déplace le héros dans le monde et gère les collisions (Si il y a collision, on gère la collision sinon on déplace le héros)
     * @param x les coordonnées X du héros à ajouter
     * @param y les coordonnées Y du héros à ajouter
     */
    public void deplacerHeros(double x,double y){
        // Si il y a collision on gère la collision
        if(this.collision.checkCollision(this.heros,x,y)) {
            gestionCollisionHeros(x,y);

        // Sinon on déplace le héros
        }else{
            this.heros.deplacer(x,y);
        }
    }

    /**
     * Gère les coups du héros (mise à jour des coordonnées, collisions avec les monstres, suppression au bout d'un certain temps) et sa mort
     */
    public void gestionHeros(){

        // Gestions du héros mort
        gestionHerosMort();

        //On vérifie si le héros a des coups d'actifs
        if(this.heros.haveCoups()){

            //On vérifie si le dernier coup est en collision avec un monstre
            this.verifierCoupCollisionAvecMonstres();

            //On vérifie si le dernier coup doit être supprimé
            this.verifierSuppressionCoup(this.heros);

            //On met à jour les coordonnées des coups
            this.heros.updateCoup();
        }
    }

    /**
     * Gère la mort du héros (affichage de l'animation de mort et du menu de mort)
     */
    private void gestionHerosMort() {
        //On vérifie si le héros est mort
        if(this.getHeros().estMort() && !dansUnMenu){

            //On affiche l'animation de mort
            this.heros.startAnimation(ETAT_ANIMATION_MORT);

            //On indique que le héros est dans un menu afin de ne pas relancer l'animation
            dansUnMenu = true;

            //On attend le temps de l'animation sans bloquer le thread
            Platform.runLater(() -> {
                try {
                    Thread.sleep(TIME_ANIMATION);

                    //On affiche le menu de mort
                    afficherMenuMort();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Affiche le menu de mort (permet de recommencer ou de quitter le jeu)
     */
    private void afficherMenuMort() {
        // On lance la tâche dans le thread JavaFX

        // Crée une alerte pour le menu de mort
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // On change le titre, le message et les boutons
        alert.setTitle("Mort");
        alert.setHeaderText("Vous êtes mort");
        alert.setContentText("Que souhaitez-vous faire ?");
        alert.getButtonTypes().set(0, new ButtonType("Recommencer"));
        alert.getButtonTypes().set(1, new ButtonType("Quitter"));

        // On affiche et récupère le résultat de l'alerte
        Optional<ButtonType> result = alert.showAndWait();

        // On vérifie si le résultat est présent
        if(result.isPresent()) {

            // On vérifie si le joueur a cliqué sur recommencer
            if (result.get().getText().equals("Recommencer")) {

                //On réinitialise le monde
                reinitialiserMonde();

                //On relance l'animation du héros
                getHeros().setEtatAnimation(ETAT_ANIMATION_STATIQUE);

                //On indique que le héros n'est plus dans un menu
                dansUnMenu = false;

            } else {
                // Sinon on ferme le jeu
                Platform.exit();
            }
        }
    }

    /**
     * Vérifie si un coup est en collision avec tous les monstres et gère les dégâts.
     * Si le coup est en collision avec un monstre, on supprime le coup.
     * On fait perdre des pv au monstre en fonction des dégâts du héros.
     * On définit également le temps de la dernière attaque
     */
    private void verifierCoupCollisionAvecMonstres() {
        if(currentSalle.getTypeSalle().equals("SalleMonstre")) {
            //On récupère le dernier coup
            Coup coup = this.heros.getAttaque().getGestionnaireCoups().getDernierCoup();

            //Pour chaque monstre présent dans la pièce
            Iterator<Monstre> iteratorMonstres = getCurrentSalleMonstre().iterator();

            while (iteratorMonstres.hasNext()) {

                //On récupère le monstre
                Monstre monstre = iteratorMonstres.next();

                //On vérifie si le coup est en collision avec un monstre
                if (this.collision.checkHitPersonnageCollision(coup, monstre)) {

                    //On fait perdre des pv au monstre en fonction des dégâts du héros
                    monstre.perdPv(this.heros.getAttaque().getDegats());

                    //On supprime le coup
                    this.heros.supprimerCoup(coup.getNumCoup());
                }
            }
        }
    }

    /**
     * Gère les collisions du héros avec les obstacles
     * @param x les coordonnées X du héros
     * @param y les coordonnées Y du héros
     */
    public void gestionCollisionHeros(double x, double y) {

        // On regarde le type de collision
        switch (this.heros.getTypeObstacleCollision()){

            // Si c'est un mur, on ne fait rien
            case "Murs":
                break;

            // Peut se déplacer si le héros sait nager, plus lentement dans l'eau et la lave
            case "Eau", "Lave":
                if(getHeros().saitNager()) this.heros.deplacer(x * 0.5, y * 0.5);
                break;

            // Si c'est une porte, on change de salle (si possible)
            case "Porte":
                this.changerSalle();

            // Si c'est un pont, on se déplace normalement
            case "Ponts":
                this.heros.deplacer(x,y);
                break;

            case "Sanctuaire":

                // On déplace le héros
                heros.deplacer(x,y);

                // Lance le timer de collision s'il n'est pas déjà lancé
                if (collisionStartTime == 0) {
                    collisionStartTime = System.currentTimeMillis();
                } else {

                    // Si le héros est dans le sanctuaire depuis plus de 5 secondes, il peut nager
                    if (System.currentTimeMillis() - collisionStartTime > 5000 && !heros.saitNager()) {

                        // Le héros gagne la capacité de nager
                        heros.setSaitNager(true);

                        // On réinitialise le timer de collision
                        collisionStartTime = System.currentTimeMillis();

                        // On lance l'animation de dégâts
                        heros.startAnimation(ETAT_ANIMATION_DEGAT);
                    }
                }
                break;


            // Si c'est une porte de sortie, on quitte le jeu
            case "PorteSortie":
                quitterJeu();
                break;
            default:

                // On réinitialise le timer de collision
                collisionStartTime = System.currentTimeMillis();
                break;
        }
    }

    /**********************************************************************************************/











    /**
     * Vérifie si un coup doit être supprimé (Pour le héros et les monstres), en fonction du temps d'ajout du coup
     */
    private void verifierSuppressionCoup(Personnage personnage) {
        if(personnage.haveCoups()) {

            //On récupère le dernier coup
            Coup coup = personnage.getDernierCoup();

            //On récupère le temps actuel
            long currentTime = System.currentTimeMillis();

            //On récupère le temps d'ajout du coup
            long coupAddedTime = personnage.getAttaque().getGestionnaireCoups().getTempsAjoutCoup(coup.getNumCoup());

            //On calcule le temps écoulé depuis l'ajout du coup
            long coupTime = currentTime - coupAddedTime;

            //Si le temps d'ajout du coup est supérieur à la vitesse de l'attaque, on supprime le coup
            if (coupTime >= personnage.getAttaque().getVitesse()) {
                personnage.supprimerCoup(coup.getNumCoup());
            }
        }
    }











    /************************************ Gestions des monstres ************************************/

    /**
     * Gère les déplacements du monstre et les collisions avec le héros (déplacement, dégâts, ...)
     */
    public void gestionMonstre() {
        //Pour chaque monstre présent dans la pièce où le héros se situe
        SalleMonstre currentSalleMonstre = (SalleMonstre) this.currentSalle;

        //Pour chaque monstre présent dans la pièce
        Iterator<Monstre> iteratorMonstres = currentSalleMonstre.iterator();
        while (iteratorMonstres.hasNext()) {

            //On récupère le monstre
            Monstre monstre = iteratorMonstres.next();

            //On gère les coups du monstre
            gestionCoupMonstre(monstre);

            //On vérifie si le monstre est mort et on le supprime si c'est le cas
            if (monstre.estMort()) iteratorMonstres.remove();

            // On déplace le monstre dans le monde et on gère ses collisions
            deplacementMonstre(monstre);
        }
    }

    /**
     * Gère les coups du monstre et ses collisions avec le héros
     * @param monstre le monstre
     */
    private void gestionCoupMonstre(Monstre monstre) {
        // Si le monstre n'a pas de coups on en ajoute un
        if (!monstre.haveCoups()) monstre.ajouterCoup();

        //On vérifie si le monstre a des coups d'actifs
        if (monstre.haveCoups()) {

            //On vérifie si le dernier coup est en collision avec le héros
            this.verifierCoupCollisionAvecHero(monstre);

            //On vérifie si le dernier coup doit être supprimé
            this.verifierSuppressionCoup(monstre);

            //On met à jour les coordonnées des coups
            monstre.updateCoup();
        }
    }

    /**
     * Vérifie si un coup est en collision avec le héros et gère les dégâts
     * @param monstre le monstre qui lance le coup
     */
    private void verifierCoupCollisionAvecHero(Monstre monstre) {
        //On récupère le dernier coup
        Coup coup = monstre.getAttaque().getGestionnaireCoups().getDernierCoup();

        //On vérifie si le coup est en collision avec le héros
        if (this.collision.checkHitPersonnageCollision(coup, this.heros)) {

            //On fait perdre des pv au héros en fonction des dégâts du monstre
            this.heros.perdPv(monstre.getAttaque().getDegats());

            //On supprime le coup
            monstre.supprimerCoup(coup.getNumCoup());

            //On définit le temps de la dernière attaque
            monstre.setLastAttackTime();
        }
    }

    /**
     * Déplace le monstre dans le monde et gère les collisions de celui-ci
     * @param monstre le monstre
     */
    private void deplacementMonstre(Monstre monstre) {

        // On récupère les coordonnées de déplacement du monstre
        double[] coordonnesDeplacement = monstre.gestionDeplacement(heros.getPosX(), heros.getPosY(), currentSalle.getMapArray());
        double x = coordonnesDeplacement[0]; double y = coordonnesDeplacement[1];

        // Si il y a collision on gère la collision
        if(this.collision.checkCollision(monstre,x,y)) {
            gestionCollisionMonstre(x,y,monstre, monstre.getTypeObstacleCollision());

        // Sinon on déplace le monstre (si il peut se déplacer)
        }else {
            if (monstre.canDeplacer())
                monstre.deplacer(x, y);
        }
    }

    /**
     * Gère les collisions avec les monstres
     * @param x les coordonnées X du monstre
     * @param y les coordonnées Y du monstre
     * @param monstre le monstre
     * @param typeCollision le type de collision
     */
    public void gestionCollisionMonstre(double x, double y, Monstre monstre, String typeCollision){
        // On regarde le type de collision
        switch (typeCollision){
            case "Murs", "Lave", "Porte", "Eau":
                if(monstre.getNomPersonnage().equals("MonstreBasique"))
                    monstre.deplacer(x,y);
                break;
            default:
                break;
        }
    }

    /**********************************************************************************************/















    /************************************ Gestion des debug collisions ************************************/

    private boolean debugCollision = false;

    /**
     * Retourne si le debug des collisions est activé ou pas
     * @return vrai ou faux
     */
    public boolean getDebugCollision() {
        return this.debugCollision;
    }

    /**********************************************************************************************/
}
