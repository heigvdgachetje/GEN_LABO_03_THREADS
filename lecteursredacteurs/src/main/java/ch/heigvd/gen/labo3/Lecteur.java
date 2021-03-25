package ch.heigvd.gen.labo3;

/* ---------------------------------------------------------------------------------------------
Lab       : 03
File      : Lecteur.java
Author(s) : Emmanuelle Comte, Jean Gachet & Fabien Terrani
Date      : 25.03.2021
Compiler  : OpenJDK 11
--------------------------------------------------------------------------------------------- */

public class Lecteur {
    private final Controleur controleur;
    boolean reading;

    public Lecteur(Controleur controleur) {
        this.controleur = controleur;
        this.reading = false;
    }

    public void startRead() {
        this.controleur.addLecteur(this);
    }

    public boolean isWaiting() {
        return !reading && controleur.contains(this);
    }

    public void stopRead() {
        reading = false;
    }
}
