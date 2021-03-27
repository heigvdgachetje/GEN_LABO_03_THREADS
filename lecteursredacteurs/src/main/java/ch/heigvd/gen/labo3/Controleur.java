package ch.heigvd.gen.labo3;

/* ---------------------------------------------------------------------------------------------
Lab       : 03
File      : Controleur.java
Author(s) : Emmanuelle Comte, Jean Gachet & Fabien Terrani
Date      : 25.03.2021
Compiler  : OpenJDK 11
--------------------------------------------------------------------------------------------- */

public class Controleur {
    private volatile int readingLecteurs = 0;
    private volatile boolean writingRedacteur = false;
    private volatile int waitingLecteurs = 0;
    private volatile int waitingRedacteurs = 0;

    private final Document document = new Document();

    public synchronized void askToRead(Lecteur l)
    {
        tryReading(l);

        Thread t = new Thread( () -> {
            synchronized (document)
            {
                while (!tryReading(l)) {
                    try
                    {
                        System.out.println("lecteur "+l.id + " attend (thread en pause)");
                        document.wait();
                        System.out.println("lecteur "+l.id + " reprend (thread relance)");
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                while (l.reading) {
                    /*System.out.println("lecteur "+l.id + " partage le doc avec d'autres lecteurs (thread en pause)");
                    try
                    {
                        document.notifyAll();
                        document.wait();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("lecteur "+l.id + " relit le document (thread relance)");*/
                }

                System.out.println("lecteur " + l.id + " a fini de lire");
                --readingLecteurs;
                //c.stopReading(l);
                document.notifyAll();
            }
        });
        t.start();
    }

    public synchronized void askToWrite(Redacteur r)
    {
        tryWriting(r);

        Thread t = new Thread( () -> {
            synchronized (document)
            {
                while (!tryWriting(r)) {
                    try
                    {
                        System.out.println("redacteur "+r.id + " attend (thread en pause)");
                        document.wait();
                        System.out.println("redacteur "+r.id + " reprend (thread relance)");
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                while (r.writing);

                System.out.println("redacteur " + r.id + " a fini d'ecrire");
                writingRedacteur = false;
                document.notifyAll();
            }
        });
        t.start();
    }

    private synchronized void stopWaiting(Lecteur l)
    {
        if (l.waiting)
        {
            --waitingLecteurs;
            l.waiting = false;
            System.out.println("lecteur " + l.id + " cesse d'attendre.");
        }
    }

    private synchronized void stopWaiting(Redacteur r)
    {
        if (r.waiting)
        {
            --waitingRedacteurs;
            r.waiting = false;
            System.out.println("redacteur " + r.id + " cesse d'attendre.");
        }
    }

    private synchronized void waitToRead(Lecteur l)
    {
        if (!l.waiting)
        {
            ++waitingLecteurs;
            l.waiting = true;
            System.out.println("lecteur " + l.id + " attend (" + waitingLecteurs + " lecteurs attendent maintenant)");
        }
    }

    private synchronized void waitToWrite(Redacteur r)
    {
        if (!r.waiting)
        {
            ++waitingRedacteurs;
            r.waiting = true;
            System.out.println("redacteur " + r.id + " attend (" + waitingRedacteurs + " redacteurs attendent maintenant)");
        }
    }

    private synchronized void startReadingDocument(Lecteur l)
    {
        stopWaiting(l);
        ++readingLecteurs;
        l.reading = true;
        System.out.println("lecteur " + l.id + " lit");
    }

    private synchronized void startWritingDocument(Redacteur r)
    {
        stopWaiting(r);
        writingRedacteur = true;
        r.writing = true;
        System.out.println("redacteur " + r.id + " ecrit");
    }

    private synchronized boolean tryReading(Lecteur l)
    {
        if (l.reading)
        {
            // System.out.println("lecteur " + l.id + " est deja en train de lire");
            return true;
        }
        else if (waitingRedacteurs == 0 && !writingRedacteur)
        {
            System.out.println("lecteur " + l.id + " peut commencer a lire car aucun redacteur n'attend ou n'ecrit");
            startReadingDocument(l);
            return true;
        }
        else
        {
            System.out.println("lecteur " + l.id + " doit attendre parce que " + waitingRedacteurs + " redacteur(s) attend(ent)");
            waitToRead(l);
            return false;
        }
    }

    private synchronized boolean tryWriting(Redacteur r)
    {
        if (r.writing)
        {
            // System.out.println("redacteur " + r.id + " est deja en train d'ecrire");
            return true;
        }
        else if (readingLecteurs == 0 && !writingRedacteur)
        {
            System.out.println("redacteur " + r.id + " peut commencer a ecrire car aucun lecteur ne lit");
            startWritingDocument(r);
            return true;
        }
        else
        {
            System.out.println("redacteur " + r.id + " doit attendre parce que " + readingLecteurs + " lecteur(s) lisent et/ou parce qu'un redacteur ecrit.");
            waitToWrite(r);
            return false;
        }
    }
}
