package ch.heigvd.gen.labo3;

/* ---------------------------------------------------------------------------------------------
Lab       : 03
File      : TrasfertsTest.java
Author(s) : Emmanuelle Comte, Jean Gachet & Fabien Terrani
Date      : 18.03.2021
Compiler  : OpenJDK 11
--------------------------------------------------------------------------------------------- */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TrasfertsTest {

    @Test
    void LeCompteDesMilleEtUnsThreads() throws InterruptedException {
        Banque banque = new Banque(10);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Transferts transferts = new Transferts(1000, banque);

            Thread thread = new Thread(transferts);
            thread.start();
            threads.add(thread);
        }

        for (Thread t : threads) {
            t.join();
        }

        assertTrue(banque.consistent());
    }
}
