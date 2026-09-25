package application.persistence;

import application.model.vendite.Vendita;
import java.util.ArrayList;

public interface VenditaDAO {
    ArrayList<Vendita> caricaVendite();
    void salvaVendita(Vendita vendita);
}