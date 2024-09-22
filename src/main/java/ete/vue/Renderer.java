package ete.vue;

import ete.attaques.Coup;
import ete.monde.Monde;
import ete.monde.Monstre;
import ete.monde.Personnage;
import ete.monde.SalleMonstre;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.Objects;

import static ete.outils.Constantes.*;

/**
 * Classe représentant le renderer du jeu (permet de dessiner la map, les personnages, etc...)
 */
public class Renderer {

    /**
     * Canvas pour dessiner
     */
    private Canvas canvas;

    /**
     * Contexte graphique
     */
    private GraphicsContext context;

    /**
     * La caméra pour gérer le déplacement de la map
     */
    private Camera camera;

    /**
     * Le héros du jeu
     */
    private Personnage heros;

    /**
     * Le monde du jeu
     */
    private Monde monde;

    /**
     * Image de la vie du héros
     */
    private Image imageVie;

    /**
     * Texte pour les indications
     */
    private String texteIndications;

    /**
     * Constructeur du renderer (initialise le canvas, la caméra, le contexte graphique, le monde, le héros et l'image de la vie)
     * @param canvas Canvas pour dessiner
     * @param camera Caméra pour gérer le déplacement de la map
     * @param monde Monde du jeu
     */
    public Renderer(Canvas canvas, Camera camera, Monde monde){
        this.canvas = canvas;
        this.camera = camera;
        this.context = this.canvas.getGraphicsContext2D();
        this.context.setStroke(Color.WHITE);
        this.monde = monde;
        this.heros = this.monde.getHeros();
        this.imageVie = new Image(Objects.requireNonNull(getClass().getResourceAsStream(URL_COEUR)));
        this.texteIndications = """
                Bienvenue dans le jeu !
                Pour vous déplacer, utilisez les touches ZQSD ou les flèches directionnelles.
                Pour attaquer, utilisez la touche espace.
                Pour sprinter, utilisez la touche shift.
                Pour passer dans la salle suivante, tuez tous les monstres.
                """;
    }

    /**
     * Dessine la map et les personnages (héros et monstres)
     */
    public void render() {

        // Initialisation du renderer
        initilisationRender();

        //Dessin de la map
        dessinerMap();

        //Dessins des monstres pour les salles de monstres
        if (this.monde.getCurrentSalle().getTypeSalle().equals("SalleMonstre")) {
            dessinerMonstres();
        }

        // Dessin de la vie du héros
        dessinerVie();

        // Dessin des coups
        if (monde.getDebugCollision()) {
            dessinerCoup();
        }

        // Dessin des indications
        if(monde.getCurrentSalle().getNumeroSalle() == 0) this.context.strokeText(texteIndications, 100, 100);

        //On affiche le résultat
        this.context.restore();
    }

    /**
     * Dessine la map actuelle
     */
    private void dessinerMap() {
        // Pour chaque couche de la map
        for(ImageView layer : this.monde.getCurrentSalle().getMap()){

            //On récupère l'image de la couche
            Image tileImage = layer.getImage();

            //On calcule les coordonnées de la map par rapport au héros
            Point2D dessinMap = this.camera.relatifHeros(layer.getTranslateX(),layer.getTranslateY());

            // On dessine la map
            context.drawImage(tileImage,dessinMap.getX(), dessinMap.getY());
        }
    }

    /**
     * Dessine la vie du héros (chaque coeur représente un point de vie)
     */
    private void dessinerVie() {
        // Pour chaque point de vie du héros
        for(int i = 0; i < this.heros.getPv(); i++){

            // On dessine un coeur espacé de 25 pixels
            this.context.drawImage(imageVie,
                    15 + i*25,
                    SCREEN_HEIGHT - 40,
                    25,
                    25);
        }
    }

    /**
     * Dessine les coups des monstres et du héros (pour debug)
     */
    private void dessinerCoup() {
        //Dessin des sprites
        for (Coup coup : this.heros.getAttaque().getGestionnaireCoups()) {
            //On récupère la position du monstre
            Rectangle2D coordSprite = coup.getCoordonnees();

            //On calcule les coordonnées du monstre par rapport au héros
            Point2D dessinSprite = this.camera.relatifHeros(coordSprite.getMinX(), coordSprite.getMinY());

            // On dessine le coup
            this.context.drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/epee_courte_debug.png"))),
                    dessinSprite.getX(),
                    dessinSprite.getY(),
                    coordSprite.getWidth(),
                    coordSprite.getHeight());
        }

        // Dessin des sprites des coups des monstres
        if (this.monde.getCurrentSalle().getTypeSalle().equals("SalleMonstre")) {
            for (Monstre monstre : ((SalleMonstre) this.monde.getCurrentSalle()).getMonstres().values()) {
                for (Coup coup : monstre.getAttaque().getGestionnaireCoups()) {
                    //On récupère la position du monstre
                    Rectangle2D coordSprite = coup.getCoordonnees();

                    //On calcule les coordonnées du monstre par rapport au héros
                    Point2D dessinSprite = this.camera.relatifHeros(coordSprite.getMinX(), coordSprite.getMinY());

                    // On dessine le coup
                    this.context.drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/epee_courte_debug.png"))),
                            dessinSprite.getX(),
                            dessinSprite.getY(),
                            coordSprite.getWidth(),
                            coordSprite.getHeight());
                }
            }
        }
    }

    /**
     * Dessine les monstres de la salle
     */
    private void dessinerMonstres() {
        // Déclaration de l'itérateur
        Iterator<Monstre> it = ((SalleMonstre) this.monde.getCurrentSalle()).iterator();

        // Tant qu'il y a un monstre
        while (it.hasNext()) {

            //On récupère le monstre
            Monstre monstre = it.next();

            //On récupère la position du monstre
            Point2D positionMonstre = new Point2D(monstre.getPosX(),monstre.getPosY());

            //On calcule les coordonnées du monstre par rapport au héros
            Point2D dessinMonstre = this.camera.relatifHeros(positionMonstre.getX(), positionMonstre.getY());

            // On dessine le monstre
            this.context.drawImage(monstre.getImage(),
                    dessinMonstre.getX(),
                    dessinMonstre.getY(),
                    monstre.getLargeur(),
                    monstre.getHauteur());
        }
    }

    /**
     * Initialise le renderer (efface le canvas et met un fond noir)
     */
    public void initilisationRender(){

        //On efface d'abord
        this.context.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        //On sauvegarde le contexte
        this.context.save();

        // On met un fond noir
        this.context.setFill(Color.BLACK);
        this.context.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
}
