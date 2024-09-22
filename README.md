# Epopée des Terres Ethérées



## Pour commencer

Pour jouer au jeu il faut : 
- Cloner le jeu
- Avoir javafx dans ses librairies
- Utiliser java 16
- Lancer la commande mvn package shade:shade
- Soit utiliser IntelliJ IDEA pour lancer le jar avec en VM options (Recommandé)
``` 
--module-path path\javafx-sdk-17.0.9\lib --add-modules javafx.controls,javafx.fxml --illegal-access=permit (Windows)
ou  --module-path /usr/share/java/javafx --add-modules javafx.controls,javafx.fxml --illegal-access=permit (Linux)
```
- Ou 
```
cd projet_s7_m1_game_ACL/target
java -cp ETE-1-shaded.jar ete.Main --module-path ..\..\javafx-sdk-17.0.9\lib --add-modules javafx.controls,javafx.fxml --illegal-access=permit (Windows)
ou java -cp ETE-1-shaded.jar ete.Main --module-path /usr/share/java/javafx --add-modules javafx.controls,javafx.fxml --illegal-access=permit (Linux)
```