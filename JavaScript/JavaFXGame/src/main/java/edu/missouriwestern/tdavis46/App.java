package edu.missouriwestern.tdavis46;

import edu.missouriwestern.csc346.monsters.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Implementing a simple game in a JavaFX App
 * @author Tristan Davis
 * @since 2022
 */
public class App extends Application {

    static VBox vBox = new VBox();
    static VBox rosterBox = new VBox();
    static VBox deathBox = new VBox();
    static BorderPane borderPane = new BorderPane();
    static TextField fightAnnouncement = new TextField("No fights to announce. Please stand by...");

    @Override
    public void start(Stage stage) {
        stage.setTitle("Monsters Game");
        String start = "Welcome to Monsters Arena";
        addToVBox(start);

        ScrollPane center = new ScrollPane(vBox);
        borderPane.setCenter(center);

        rosterBox.setMinWidth(250);
        borderPane.setRight(rosterBox);

        TextField title = new TextField("Here Lies the Dead");
        title.setAlignment(Pos.CENTER);
        title.setFont(Font.font("arial", FontWeight.BOLD, 14));
        title.setBackground(new Background(new BackgroundFill(Color.SILVER, null, null)));
        deathBox.getChildren().add(title);
        deathBox.setMinWidth(250);

        fightAnnouncement.setAlignment(Pos.CENTER);
        fightAnnouncement.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
        fightAnnouncement.setFont(Font.font("arial", FontWeight.BOLD, 14));
        borderPane.setBottom(fightAnnouncement);

        var scene = new Scene(borderPane, 1024, 768);
        stage.setScene(scene);
        stage.show();

        appDriverMain();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void addToVBox(String message) {
        vBox.getChildren().add(new Label("  " + message));
    }

    public static void appDriverMain() {

        ArrayList<Player> roster = new ArrayList<>();
        roster.add(new Player("Ptomain Tony", new Cook()));
        roster.add(new Player("Broccoli Spears", new Cook()));
        roster.add(new Player("Ricky", new GiantRat()));
        roster.add(new Player("Randy", new GiantRat()));
        roster.add(new Player("SpideyMan", new GiantSpider()));
        roster.add(new Player("Charlotte Webber", new GiantSpider()));
        roster.add(new Player("Gator Gates", new Crocodile()));
        roster.add(new Player("Tick-Tok Croc", new Crocodile()));
        roster.add(new Player("Mushu", new Dragon()));
        roster.add(new Player("Toothless", new Dragon()));

        Professor p = new Professor();

        System.out.println(p);

        displayRoster(roster);

        JFXGameManager game = new JFXGameManager();
        game.contest(roster);

        displayRoster(roster);

    }
    public static void blank() {
        addToVBox(" ");
    }
    public static void displayRoster(ArrayList<Player> roster){
        rosterBox.getChildren().clear();
        TextField title = new TextField("The Roster");
        title.setAlignment(Pos.CENTER);
        title.setFont(Font.font("arial", FontWeight.BOLD, 14));
        title.setBackground(new Background(new BackgroundFill(Color.SILVER, null, null)));
        rosterBox.getChildren().add(title);

        for(Player player : roster) {
            rosterBox.getChildren().add(new TextField(player.toString()));
        }
        blank();
    }
}

