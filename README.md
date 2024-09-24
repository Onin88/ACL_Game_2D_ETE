# 🎮 Epopée des Terres Ethérées 🎮

### Groupe
- GEHIN Sandy
- GEORGEL Mathis
- SCHLESINGER Joseph
- BELUCHE Quentin

## Pour commencer

> [!IMPORTANT]
> Pour jouer au jeu il faut :
> - Cloner le jeu
> - Avoir javafx dans ses librairies
> - Utiliser java 16
> - Lancer la commande mvn package shade:shade
> - Soit utiliser IntelliJ IDEA pour lancer le jar avec en VM options (Recommandé)
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

## Description
> [!NOTE]
> Développement en équipe de 4 d’un jeu vidéo 2D inspiré de Enter the Gungeon, intégrant plusieurs mécaniques de gameplay et éléments techniques :
> - Création de maps avec gestion des collisions, différenciées selon les matériaux 🔨.
> - Animations et attaques variées pour les personnages 🗡️.
> - Pièges, objets, monstres avec une IA avancée basée sur l'algorithme A (A Star)* 🤖.
> - Système de niveaux, secrets à découvrir, sprint, paramètres personnalisables ⚙️.
> - Sauvegarde du progrès des joueurs 💾.
> - Travail collaboratif avec répartition des tâches au sein de l’équipe.

Ce projet met en avant l’aspect conception de gameplay, intelligence artificielle et gestion de groupe, offrant une expérience complète de développement de jeu.

## Screenshots
> [!NOTE]
> Map du Menu Principal :
![](https://raw.githubusercontent.com/Onin88/ACL_Game_2D_ETE/master/ScreenShots/screen1.png)

> [!NOTE]
> Map du début avec monstres simples :
![](https://raw.githubusercontent.com/Onin88/ACL_Game_2D_ETE/master/ScreenShots/screen2.png)

> [!NOTE]
> Map du milieu avec des monstres plus expérimentées avec skills, etc..., animation de mort et fenêtre de fin :
![](https://raw.githubusercontent.com/Onin88/ACL_Game_2D_ETE/master/ScreenShots/screen3.png)
