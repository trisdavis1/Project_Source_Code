package edu.missouriwestern.tdavis46;

import edu.missouriwestern.csc346.monsters.GameManager;
import edu.missouriwestern.csc346.monsters.Player;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class JFXGameManager extends GameManager {
    @Override
    public String displayMessage(String message) {
        if(message.contains("Maximum remaining rounds")){
            TextField roundAnnouncement = new TextField(message);
            roundAnnouncement.setAlignment(Pos.CENTER);
            roundAnnouncement.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
            roundAnnouncement.setFont(Font.font("arial", FontWeight.BOLD, 14));
            App.borderPane.setTop(roundAnnouncement);
        }else if(message.contains("is dead")){
            TextField deathAnnouncement = new TextField(message);
            deathAnnouncement.setAlignment(Pos.CENTER);
            App.deathBox.getChildren().add(deathAnnouncement);
            App.borderPane.setLeft(App.deathBox);
        }
        else {
            App.addToVBox(message);
        }
        return message;
    }

    @Override
    public void fightAnnouncement(Player p1, Player p2) {
        String message = String.format("\n----- %s (%1.0f%%) and %s (%1.0f%%) enter the game -----", p1, p1.getBody().getHealth() * 100.0D, p2, p2.getBody().getHealth() * 100.0D);
        App.fightAnnouncement.setText(message);
    }
}
