package ete.monde;

import ete.attaques.Attaque;
import ete.attaques.Coup;
import ete.outils.FabriqueNumPerso;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static ete.outils.Constantes.*;

/**
 * Classe abstraite représentant un personnage (héros ou monstre), et qui contient les données et les animations
 */
public abstract class Personnage extends ImageView implements Runnable {
    /**
     * Numéro unique du personnage
     */
    protected int numPersonnage;

    /**
     * Nom du personnage
     */
    protected String nomPersonnage;

    /**
     * Booléen pour savoir si le personnage sait nager
     */
    protected boolean saitNager;

    /**
     * Constructeur du personnage (initialise les variables : PV, vitesse, attaque)
     */
    public Personnage(String urlSprite, int pv, float vitesse, Attaque attaque, String nomPersonnage, int invulnerabiliteTime) {

        // Initialisation de l'image du personnage
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(urlSprite))));

        // Initialisation des variables
        this.pv = pv;

        // Initialisation de la vitesse
        this.vitesse = vitesse;

        // Initialisation de l'attaque
        this.attaque = attaque;

        // Initialisation de la variable de collision avec les obstacles
        this.typeObstacleCollision = "";

        // Initialisation du nom du personnage
        this.nomPersonnage = nomPersonnage;

        // Initialisation du numéro unique du personnage
        numPersonnage = FabriqueNumPerso.getInstance().getNumPerso();

        // Initialisation de la direction
        lastDirection = DIRECTION_BAS;

        // Initialisation de l'image dans un thread
        animationThread = new Thread(this);

        // Initialisation de l'animation
        initializeAnimation();

        // Initialisation du temps de la dernière fois que le personnage a été touché
        this.invulnerabiliteTime = invulnerabiliteTime;

        // Initialisation du booléen saitNager
        this.saitNager = false;
    }

    /**
     * Retourne le numéro unique du personnage
     *
     * @return Numéro unique du personnage
     */
    public int getNumPersonnage() {
        return numPersonnage;
    }

    public String getNomPersonnage() {
        return nomPersonnage;
    }

    /**
     * Modifie la dernière direction du héros
     * @param lastDirection Nouvelle direction
     */
    public void setLastDirection(String lastDirection) {
        this.lastDirection = lastDirection;
    }

    /**
     * Permet de réinitialiser le personnage, pour le remettre à son état initial
     */
    public void reinitialiser(int pv, float vitesse, Attaque attaque) {
        this.pv = pv;
        this.vitesse = vitesse;
        this.attaque = attaque;
        lastDirection = DIRECTION_BAS;
        lastHitTime = 0;
    }

    public boolean saitNager(){
        return saitNager;
    }

    public void setSaitNager(boolean saitNager) {
        this.saitNager = saitNager;
    }









    /************************************* GESTION DES COUPS *************************************/

    /**
     * Attaque du personnage
     */
    protected Attaque attaque;

    /**
     * Temps d'invulnérabilité du personnage
     */
    protected int invulnerabiliteTime;

    /**
     * Dernière fois que le personnage a été touché
     */
    protected long lastHitTime;

    /**
     * Permet d'ajouter un coup à la liste des coups
     */
    public void ajouterCoup() {
        // Si le personnage peut attaquer
        if(canAttack()) {

            // On ajoute un nouveau coup
            Coup coup = new Coup(lastDirection, this.attaque.getCoordonneesAttaque(), this.getPosX() + this.getLargeur() / 2, this.getPosY() + this.getHauteur() / 2);
            this.attaque.getGestionnaireCoups().ajouterCoup(coup);

            // On met à jour le temps de la dernière attaque
            this.attaque.setLastAttackTime();
        }
    }

    /**
     * Met à jour les coordonnées des coups en fonction de la dernière direction du personnage (coups dynamiques)
     */
    public void updateCoup() {
        for (Coup coup : this.attaque.getGestionnaireCoups()) {
            coup.updateCoordonneesDirections(lastDirection, this.getPosX() + this.getLargeur() / 2, this.getPosY() + this.getHauteur() / 2);
        }
    }

    /**
     * Permet de supprimer un coup de la liste des coups
     * @param numCoup Numéro du coup à supprimer
     */
    public void supprimerCoup(int numCoup) {
        // On supprime le coup
        this.attaque.getGestionnaireCoups().supprimerCoup(numCoup);
    }

    public Attaque getAttaque() {
        return attaque;
    }

    public Coup getDernierCoup() {
        return attaque.getGestionnaireCoups().getDernierCoup();
    }

    public boolean haveCoups() {
        return this.attaque.getGestionnaireCoups().getNbCoups() > 0;
    }

    public void setLastAttackTime() {
        this.attaque.setLastAttackTime();
    }

    public boolean canAttack() {
        return this.attaque.canAttack();
    }

    public boolean canBeHit() {
        return System.currentTimeMillis() > lastHitTime + invulnerabiliteTime;
    }

    /***********************************************************************************************/










    /******************************** GESTION DES TYPES D'OBSTACLES EN COLLISION *********************************/

    /**
     * Type d'obstacle en collision avec le personnage
     */
    private String typeObstacleCollision;

    public String getTypeObstacleCollision() {
        return typeObstacleCollision;
    }

    public void setTypeObstacleCollision(String type) {
        this.typeObstacleCollision = type;
    }


    /*************************************************************************************************************/












    /*********************** ANIMATIONS ***********************/

    /**
     * Liste d'images pour l'animation du héros
     */
    protected List<Image> imagesIdle, imagesDead, imagesAttack, imagesHit;

    /**
     * Thread pour l'animation du héros
     */
    private final Thread animationThread;

    /**
     * Etat d'animation du héros
     */
    protected String etatAnimation;

    /**
     * Temps de l'animation du héros
     */
    protected long timeAnimation;

    /**
     * Fonction pour initialiser toute l'animation du héros
     */
    private void initializeAnimation() {
        // Initialisation des listes d'images
        initialisationListeImages();

        // Charge les sprites
        loadSprites();

        // Initialisation de l'état d'animation du héros
        etatAnimation = "Statique";

        // Initialisation du temps de l'animation
        timeAnimation = 0;

        // Start le thread d'animation
        animationThread.start();
    }

    /**
     * Fonction pour initialiser les listes d'images
     */
    private void initialisationListeImages() {
        imagesIdle = new LinkedList<>();
        imagesDead = new LinkedList<>();
        imagesAttack = new LinkedList<>();
        imagesHit = new LinkedList<>();
    }

    /**
     * Charge les sprites du héros en ajoutant les images dans les listes
     */
    protected void loadSprites() {

        // On définit le chemin des images
        String path = "/sprites/" + getNomPersonnage();

        // On déclare les directions
        ArrayList<String> directions = new ArrayList<>(List.of(DIRECTION_HAUT, DIRECTION_BAS, DIRECTION_GAUCHE, DIRECTION_DROITE));

        // Pour chaque direction
        for (String direction : directions) {

            // Pour chaque image de l'animation
            for (int i = 1; i <= NB_IMAGES_ANIMATION; i++) {

                // On ajoute à la liste des images statiques
                imagesIdle.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + "_statique/" + direction + "/" + i + ".png"))));

                // On ajoute à la liste des images d'attaque
                imagesAttack.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + "_epee_courte/" + direction + "/" + i + ".png"))));

                // On ajoute à la liste des images de mort
                imagesDead.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + "_mort/" + direction + "/" + i + ".png"))));

                // On ajoute à la liste des images de dégats
                imagesHit.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + "_degat/" + direction + "/" + i + ".png"))));
            }
        }
    }

    /**
     * Modifie la variable de la fin du temps de l'animation en fonction du temps réel et du temps de l'animation
     */
    private void startNewAnimation() {
        // Si le héros est touché, on rend le temps d'animation égale au temps d'invulnérabilité + le temps actuel
        /*if(etatAnimation.equals("Degat"))
            timeAnimation = System.currentTimeMillis() + invulnerabiliteTime;

        // Sinon on rend le temps d'animation égale au temps actuel + le temps de l'animation
        else*/
            timeAnimation = System.currentTimeMillis() + TIME_ANIMATION;
    }

    public void setEtatAnimation(String etatAnimation) {
        this.etatAnimation = etatAnimation;
    }

    /**
     * Permet de lancer une nouvelle animation
     * @param etatAnimation Etat de l'animation
     */
    public void startAnimation(String etatAnimation) {
        // Si le héros n'est pas mort, on change l'état d'animation et on lance une nouvelle animation
        if (!this.etatAnimation.equals(ETAT_ANIMATION_MORT)) {
            this.etatAnimation = etatAnimation;
            startNewAnimation();
        }
    }

    /**
     * Retourne l'image suivante dans la liste
     * @param image Image actuelle
     * @param list  Liste d'images
     * @return Image suivante
     */
    protected Image getNextImage(Image image, List<Image> list) {
        // On récupère la position de l'image dans la liste
        int position = list.indexOf(image);

        // Si on est à la fin de la liste, on revient au début
        if (position >= NB_IMAGES_ANIMATION-1)
            position = 0;

        // Sinon on passe à l'image suivante
        else position++;

        // Si le héros est mort, on affiche l'image de mort en boucle
        if (etatAnimation.equals("Mort")) position = NB_IMAGES_ANIMATION-1;

        // On retourne l'image
        return list.get(position);
    }

    /**
     * Retourne la liste d'images en fonction de la dernière direction du héros
     * @param images Liste d'images
     * @return Liste d'images
     */
    protected List<Image> getNextImageDirection(List<Image> images) {
        switch (lastDirection) {
            case DIRECTION_HAUT:
                return images.subList(0, NB_IMAGES_ANIMATION);
            case DIRECTION_BAS:
                return images.subList(NB_IMAGES_ANIMATION, NB_IMAGES_ANIMATION*2);
            case DIRECTION_GAUCHE:
                return images.subList(NB_IMAGES_ANIMATION*2, NB_IMAGES_ANIMATION*3);
            case DIRECTION_DROITE:
                return images.subList(NB_IMAGES_ANIMATION*3, NB_IMAGES_ANIMATION*4);
        }
        return images;
    }

    @Override
    public void run() {

        // Boucle infinie
        while (true) {

            // On attend 50ms pour changer l'image
            try {
                Thread.sleep(TIME_BETWEEN_ANIMATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // On réinitialise l'image
            Image image = null;

            // On change l'image du héros en fonction de son état
            switch (etatAnimation) {
                case "Mort":
                    image = getNextImage(getImage(), getNextImageDirection(imagesDead));
                    break;
                case "Statique":
                    image = getNextImage(getImage(), getNextImageDirection(imagesIdle));
                    break;
                case "Attaque":
                    image = getNextImage(getImage(), getNextImageDirection(imagesAttack));
                    break;
                case "Degat":
                    image = getNextImage(getImage(), getNextImageDirection(imagesHit));
                    break;
            }

            // Si le temps de l'animation est dépassé, on remet le héros en mode statique
            if (!etatAnimation.equals("Mort")) {
                if (timeAnimation < System.currentTimeMillis())
                    etatAnimation = "Statique";
            }

            // On set l'image
            setImage(image);
        }
    }


    /***********************************************************************************************/

























    /************************************* GESTION DES COORDONNEES *************************************/

    /**
     * Coordonnée du personnage
     */
    protected double posX, posY;

    /**
     * Dernière direction du personnage
     */
    protected String lastDirection;

    /**
     * Permet de déplacer le personnage en fonction de ces anciennes coordonnées et change la dernière direction du personnage
     * @param x Coordonnée X à ajouter
     * @param y Coordonnée Y à ajouter
     */
    public void deplacer(double x, double y) {
        this.posX += x;
        this.posY += y;
    }

    public double getPosX() { // Position par rapport à la map
        return this.posX;
    }

    public double getPosY() { // Position par rapport à la map
        return this.posY;
    }

    public double getLargeur(){
        return this.getImage().getWidth();
    }

    public double getHauteur() {
        return this.getImage().getHeight();
    }

    /**
     * Permet de set les coordonnées du personnage
     * @param posX Nouvelle position X
     * @param posY Nouvelle position Y
     */
    public void setCoordonnees(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }

    /***************************************************************************************************/













    /******************************** GESTION DE LA VIE DU PERSONNAGE ********************************/

    /**
     * Point de vie du personnage
     */
    protected int pv;

    public int getPv() {
        return this.pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    /**
     * Permet de faire perdre des points de vie au personnage en fonction des dégats
     * @param degat Le nombre de points de vie à perdre
     */
    public void perdPv(int degat) {

        // Si le personnage peut être touché (temps d'invulnérabilité)
        if(canBeHit()) {

            // Enlève les points de vie
            this.pv -= degat;

            // Met à jour le temps de la dernière fois que le personnage a été touché
            lastHitTime = System.currentTimeMillis();

            // Lance l'animation de dégats
            startAnimation(ETAT_ANIMATION_DEGAT);
        }
    }

    /**
     * Vérifie si le personnage est mort
     * @return Vrai si le personnage est mort, faux sinon
     */
    public boolean estMort(){
        return pv <= 0;
    }

    /***************************************************************************************************/











    /**************************** GESTION DE LA VITESSE DU PERSONNAGE ********************************/

    /**
     * Vitesse du personnage
     */
    protected float vitesse;

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

    /***************************************************************************************************/
}
