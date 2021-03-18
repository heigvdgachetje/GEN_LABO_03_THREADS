//package ch.heigvd.gen.labo3;
//
//import ch.heigvd.gen.labo3.Controleur;
//import ch.heigvd.gen.labo3.Lecteur;
//import ch.heigvd.gen.labo3.Redacteur;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
///**
// * Created by Patrick Lachaize on 13.03.2017.
// */
//public class LecteursRedacteursTest {
//    private Controleur controleur;
//    private Lecteur lecteur1;
//    private Lecteur lecteur2;
//    private Lecteur lecteur3;
//    private Redacteur redacteur1;
//    private Redacteur redacteur2;
//
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        controleur = new Controleur();
//        lecteur1 = new Lecteur(controleur);
//        lecteur2 = new Lecteur(controleur);
//        lecteur3 = new Lecteur(controleur);
//        redacteur1 = new Redacteur(controleur);
//        redacteur2 = new Redacteur(controleur);
//    }
//
//    @Test
//    public void lecteursRedacteurs() throws InterruptedException {
//        lecteur1.startRead();
//        lecteur2.startRead();
//        redacteur1.startWrite();
//        lecteur3.startRead();
//
//        // lecteurs 1 et 2 passent
//        // puis redacteur1 attends et donc lecteur3 aussi
//        assertTrue(redacteur1.isWaiting());
//        assertTrue(lecteur3.isWaiting());
//        assertFalse(lecteur1.isWaiting());
//        lecteur1.stopRead();
//        assertFalse(lecteur2.isWaiting());
//        lecteur2.stopRead();
//
//        // Après lecteurs 1 et 2, c'est à redacteur1
//        assertTrue(lecteur3.isWaiting());
//        assertFalse(redacteur1.isWaiting());
//        redacteur2.startWrite();
//        redacteur1.stopWrite();
//
//        // redacteur 1 libère mais redacteur 2 passe avant lecteur 3
//        assertTrue(lecteur3.isWaiting());
//        assertFalse(redacteur2.isWaiting());
//        redacteur2.stopWrite();
//
//        // après les redacteurs , lecteur3 est libéré
//        assertFalse(lecteur3.isWaiting());
//        lecteur3.stopRead();
//    }
//
//}