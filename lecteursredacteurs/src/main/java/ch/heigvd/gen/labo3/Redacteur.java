package ch.heigvd.gen.labo3;

/* ---------------------------------------------------------------------------------------------
Lab       : 03
File      : Redacteur.java
Author(s) : Emmanuelle Comte, Jean Gachet & Fabien Terrani
Date      : 25.03.2021
Compiler  : OpenJDK 11
--------------------------------------------------------------------------------------------- */

public class Redacteur {
    private final Controleur controleur;
    boolean writing;

    public Redacteur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void startWrite() {
        this.controleur.addRedacteur(this);
    }

    public boolean isWaiting() {
        return !writing && controleur.contains(this);
    }

    public void stopWrite() {
        writing = false;
    }
}
