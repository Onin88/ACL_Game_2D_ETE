package ete.outils;

import ete.environnements.GestionnaireObstacles;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.mapeditor.core.*;
import org.mapeditor.io.TMXMapReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static ete.outils.Constantes.TILE_SIZE;

/**
 * Classe permettant de charger une map sous forme de fichier .tmx (Tiled Map Editor)
 */
public class TiledMap {

    protected String[] mapPath;
    private HashMap<Integer, Image> tileHash;
    private TileLayer layer;
    private Map map;
    private MapLayer mapLayer;
    protected ArrayList<ImageView> mapView;
    private GestionnaireObstacles gestionnaireObstacles;
    private TMXMapReader mapReader;
    private Tile tile;
    private int tid;
    private Image tileImage;
    private int[][] mapArray;

    /**
     * Constructeur de la classe TiledMap
     * @throws Exception si la map n'a pas pu être initialisée
     */
    public TiledMap() throws Exception{
        layer = null;
        map = null;
        mapView = new ArrayList<>();
        mapPath = new String[]{"Map_1/Map_1.tmx", "Map_1/Map_2.tmx", "Map_1/Map_3.tmx", "Map_1/Map_4.tmx"};
        gestionnaireObstacles = new GestionnaireObstacles();
        mapReader = new TMXMapReader();
        tileHash = new HashMap<>();
    }

    public int getWidht() {
        return map.getWidth()*TILE_SIZE;
    }

    public int getHeight() {
        return map.getHeight()*TILE_SIZE;
    }

    /**
     * Charge la map en fonction du numéro de salle
     * @param numeroSalle le numéro de la salle
     * @return la map chargée sous forme d'arraylist d'ImageView
     * @throws Exception si la map n'a pas pu être chargée
     */
    public ArrayList<ImageView> loadMap(int numeroSalle) throws Exception{
        // On charge la map en fonction du numéro de salle
        map = mapReader.readMap(Objects.requireNonNull(getClass().getResource("/map/"+mapPath[numeroSalle])));

        // On initialise la mapArray
        mapArray = new int[this.map.getWidth()][this.map.getHeight()];

        // On remplit la mapArray de 0
        for (int[] row: mapArray)
            Arrays.fill(row, 0);

        // On charge les différentes couches de la map
        for (int i = 0; i < map.getLayerCount(); i++) {

            // On récupère la couche i
            mapLayer = map.getLayer(i);

            // Si cette couche est null, on quitte le programme
            if (mapLayer == null) {
                System.out.println("Impossible de récuperer les couches de la map");
                System.exit(-1);
            }

            // Si cette couche est une couche layer, on l'applique
            if (mapLayer.getProperties().getProperty("Type").equals("Layer")) {
                layer = (TileLayer) mapLayer;

                // On récupère la largeur et la hauteur de les dimensions
                int layerWidth = layer.getBounds().width;
                int layerHeight = layer.getBounds().height;

                // On applique la couche avec la largeur et la hauteur
                applyLayer(layerWidth, layerHeight);

            } else {

                // On récupère les objets de la map
                getObjects();
            }
        }

        // On affiche le nombre de Tile chargées
        System.out.println("La map a chargée " + tileHash.size() + " items");

        // On réinitialise pour la prochaine couche
        tileHash = null;
        layer = null;

        return this.mapView;
    }

    /**
     * Récupère les objets de la map et les ajoute à la liste des obstacles
     */
    private void getObjects() {
        // On récupère les objets de la map
        ObjectGroup objectGroup = (ObjectGroup) mapLayer;

        // Parcourt tous les objets
        for (MapObject mapObject : objectGroup.getObjects()) {

            // On ajoute l'objet à la mapArray
            addObjectOnMapArray(mapObject);

            // Modifie le nom de l'objet pour le retrouver plus facilement
            mapObject.setName(objectGroup.getName());

            // On ajoute l'objet à la liste des obstacles
            gestionnaireObstacles.addObstacle(mapObject);
        }
    }

    /**
     * Ajoute un objet à la mapArray en fonction de sa position, de sa largeur et de sa hauteur (en mettant 1 à la place des 0)
     * @param mapObject l'objet à ajouter
     */
    private void addObjectOnMapArray(MapObject mapObject) {
        // On récupère la position de l'objet
        double x = mapObject.getX()/TILE_SIZE;
        double y = mapObject.getY()/TILE_SIZE;

        // On récupère la largeur et la hauteur de l'objet
        double width = mapObject.getWidth()/TILE_SIZE;
        double height = mapObject.getHeight()/TILE_SIZE;
        for (int j = (int)y; j < y + height; j++) {
        for (int i = (int)x; i < x + width; i++) {

                if(i < 0 || j < 0 || i >= mapArray.length || j >= mapArray[i].length)
                    continue;
                mapArray[i][j] = 1;
            }
        }
    }

    public int[][] getMapArray() {
        return mapArray;
    }

    /**
     * Applique la couche de la map en fonction de la largeur et de la hauteur de la couche
     * @param width la largeur de la couche
     * @param height la hauteur de la couche
     */
    protected void applyLayer(double width, double height) {
        // On évite d'avoir une image par tile donc => hashMap
        tileHash.clear();
        tileImage = null;

        // On parcourt la couche en hauteur
        for (int y = 0; y < height; y++) {

            // On parcourt la couche en largeur
            for (int x = 0; x < width; x++) {

                // On récupère la tile aux coordonnées x et y
                tile = layer.getTileAt(x, y);

                // On vérifie si la tile n'est pas null
                if (tile != null) {

                    // Si la tile n'est pas null, on récupère son id
                    tid = tile.getId();

                    // On vérifie si on a déjà utilisé l'image
                    if (tileHash.containsKey(tid)) {

                        // Si on a déjà utilisé l'image, on la recupère depuis la hashmap
                        tileImage = tileHash.get(tid);

                    } else {

                        // Si on a pas déjà utilisé l'image, on la converti en image javaFx
                        tileImage = convertImage(tile.getImage());

                        // On ajoute l'image à la hashmap
                        tileHash.put(tid, tileImage);

                    }

                    // On initialise l'imageView avec l'image
                    ImageView iv = new ImageView(tileImage);

                    // On modifie la position de l'imageView en fonction de la largeur et de la hauteur de la map
                    iv.setTranslateX(x * map.getTileWidth());
                    iv.setTranslateY(y * map.getTileHeight());

                    // On ajoute l'imageView à la liste des imageView
                    mapView.add(iv);
                }
            }
        }
    }

    public GestionnaireObstacles getObstacles() {
        return gestionnaireObstacles;
    }

    /**
     * Converti une image awt en image javaFx
     * @param image l'image à convertir
     * @return l'image convertie
     */
    protected static javafx.scene.image.Image convertImage(BufferedImage image){
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

}
