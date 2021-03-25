package ch.heigvd.gen.labo3;

/* ---------------------------------------------------------------------------------------------
Lab       : 03
File      : Controleur.java
Author(s) : Emmanuelle Comte, Jean Gachet & Fabien Terrani
Date      : 25.03.2021
Compiler  : OpenJDK 11
--------------------------------------------------------------------------------------------- */

import java.util.ArrayList;
import java.util.List;

public class Controleur {
    private final List<Redacteur> redacteurs = new ArrayList<>();
    private final List<Lecteur> lecteurs = new ArrayList<>();

    public void addLecteur(Lecteur lecteur) {
        lecteurs.add(lecteur);
        Thread thread = new Thread(() -> {
            try {
                if (!redacteurs.isEmpty()) {
                    lecteur.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lecteur.reading = true;
            while (lecteur.reading);
            isDone(lecteur);
        });
        thread.start();
    }

    public void addRedacteur(Redacteur redacteur) {
        redacteurs.add(redacteur);
        Thread thread = new Thread(() -> {
            try {
                redacteur.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redacteur.writing = true;
            while (redacteur.writing);
            isDone(redacteur);
        });
        thread.start();
    }

    private void isDone(Object actor) {
        if (actor instanceof Lecteur) {
            lecteurs.remove(actor);
        } else if (actor instanceof Redacteur) {
            redacteurs.remove(actor);
        }

        for (Lecteur l : lecteurs) {
            if (l.reading) {
                return;
            }
        }

        if (!redacteurs.isEmpty()) {
            redacteurs.get(0).notify();
        } else {
            for (Lecteur l : lecteurs) {
                l.notify();
            }
        }
    }

    public boolean contains(Object acteur) {
        return redacteurs.contains(acteur) || lecteurs.contains(acteur);
    }
}
