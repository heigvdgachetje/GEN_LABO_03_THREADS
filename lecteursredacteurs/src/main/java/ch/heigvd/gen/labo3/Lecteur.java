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
    volatile boolean reading;
    volatile boolean waiting;

    volatile static int ctr = 0;
    int id = nextId();
    private synchronized static int nextId() {
        return ++Lecteur.ctr;
    }

    public Lecteur(Controleur _controleur) {
        controleur = _controleur;
        reading = false;
        waiting = false;
    }

    public void startRead() {
        controleur.askToRead(this);
        try
        {
            Thread.sleep(10);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void stopRead() {
        controleur.stopReading(this);

        try
        {
            Thread.sleep(10);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
