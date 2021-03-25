package ch.heigvd.gen.labo3;

/* ---------------------------------------------------------------------------------------------
Lab       : 03
File      : Transfert.java
Author(s) : Emmanuelle Comte, Jean Gachet & Fabien Terrani
Date      : 18.03.2021
Compiler  : OpenJDK 11
--------------------------------------------------------------------------------------------- */

import java.util.Random;

public class Transferts implements Runnable {
    private final int nbTransferts;
    private final Banque banque;

    public Transferts(int nbTransferts, Banque banque) {
        this.nbTransferts = nbTransferts;
        this.banque = banque;
    }

    @Override
    public void run() {
        Random rng = new Random();

        for (int i = 0; i < nbTransferts; i++) {
            banque.transfert(rng.nextInt(banque.getNbComptes()), rng.nextInt(banque.getNbComptes()), rng.nextInt(5));
        }
    }
}
