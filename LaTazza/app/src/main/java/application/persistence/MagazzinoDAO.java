package application.persistence;

import application.utils.TipoCialda;
import java.util.HashMap;

public interface MagazzinoDAO {
    HashMap<TipoCialda, Integer> caricaMagazzino();
    void aggiornaQuantita(TipoCialda tipoCialda, int quantita);
}