package edu.missouriwestern.tdavis46;

import edu.missouriwestern.csc346.monsters.Human;

public class Professor extends Human {

    public Professor() {
        super("Professor", 0.25, "\uD83D\uDC68\u200D\uD83C\uDF93", "\uD83D\uDC69\u200D\uD83C\uDF93");
        setAggressiveness(0.25);
        setDefenseMessage("Blocks with an eraser.");
    }

    enum Weapon {
        chalk
    }
    Weapon weapon = null;

    public void attack() {
        weapon = Weapon.chalk;
        this.setAttackMessage("throws chalk.");
        this.setAttackEffectiveness(0.05 * Math.random());
    }

    public void defend() {
        this.setDefenseEffectiveness(0.2 * Math.random());
    }

    public String getNoAttackMessage() {
        String message = "pauses to tell yet another war story.";
        return message;
    }
}
