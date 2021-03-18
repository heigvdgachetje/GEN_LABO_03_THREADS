package ch.heigvd.gen.labo3;

import java.util.ArrayList;
import java.util.List;

public class Banque {

    private List<Compte> comptes;
    private final int creditInitial = 5;

    public Banque(int nbComptes) {
        comptes = new ArrayList<Compte>();
        for (int i = 0; i < nbComptes; i++) {
            comptes.add(new Compte(i, creditInitial));
        }
    }


    public int getNbComptes() {
        return comptes.size();
    }

    public boolean consistent() {
        int total = 0;
        for (int i = 0; i < getNbComptes(); i++) {
            total += comptes.get(i).getMontant();
        }
        return total == creditInitial * getNbComptes();
    }

    public void transfert(int debiteur, int crediteur, int montant) {
        if (comptes.get(debiteur).debit(montant)) {
            comptes.get(crediteur).credit(montant);
        }
    }

}
