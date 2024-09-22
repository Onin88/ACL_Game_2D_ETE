package ete;

import ete.controles.Controles;
import ete.gamecontroller.GameController;
import ete.monde.Monde;
import ete.vue.Camera;
import ete.vue.Renderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static ete.outils.Constantes.*;

/**
 * Classe principale du jeu
 */
public class Main extends Application {
    /**
     * La scène pour gérer les événements
     */
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {

        //Initialisation du monde
        Monde monde = new Monde();

        //Initialisation scène
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        StackPane root = new StackPane(canvas,monde.getHeros());

        //Initialisation de la vue
        Camera camera = new Camera(SCREEN_WIDTH,SCREEN_HEIGHT,monde);

        //Initialisation renderer
        Renderer renderer = new Renderer(canvas,camera, monde);

        // Initialisation des contrôles
        Controles controles = new Controles(monde);

        // Gestion de la scène
        gestionScene(root, controles, canvas);

        // Initialisation game controller
        GameController gameController = new GameController(monde,renderer, controles);
        gameController.startGame();

        // Lance la boucle de jeu
        gameController.startGame();

        primaryStage.setScene(scene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }

    /**
     * Gère les événements de la scène
     * @param root StackPane contenant le canvas et le héros
     * @param controles Controles du jeu
     * @param canvas Canvas pour dessiner
     */
    private void gestionScene(StackPane root, Controles controles, Canvas canvas){

        //Initialisation de la scène
        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        // On met à jour la largeur de la fenetre si elle est modifiée
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            SCREEN_WIDTH = newSceneWidth.intValue();
            canvas.setWidth(SCREEN_WIDTH);
        });

        // On met à jour la hauteur de la fenetre si elle est modifiée
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            SCREEN_HEIGHT = newSceneHeight.intValue();
            canvas.setHeight(SCREEN_HEIGHT);
        });

        // On gère les touches appuyées par l'utilisateur
        scene.setOnKeyPressed((keyEvent -> {
            controles.setKeysCurrentlyDown(keyEvent.getCode());
        }));

        // On gère les touches relachées par l'utilisateur
        scene.setOnKeyReleased((keyEvent -> {
            controles.removeKeysCurrentlyDown(keyEvent.getCode());
        }));
    }

    public static void main(String[] args) {
        launch(args);
    }
}