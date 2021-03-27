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
    volatile boolean writing;
    volatile boolean waiting;

    volatile static int ctr = 0;
    int id = nextId();
    private synchronized static int nextId() {
        return ++Redacteur.ctr;
    }

    public Redacteur(Controleur _controleur) {
        controleur = _controleur;
        writing = false;
        waiting = false;
    }

    public void startWrite() {
        controleur.askToWrite(this);
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void stopWrite() {
        writing = false;
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
