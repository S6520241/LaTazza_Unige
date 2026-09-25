package application.persistence;

import application.model.rifornimenti.Rifornimento;
import java.util.ArrayList;

public interface RifornimentoDAO {
    ArrayList<Rifornimento> caricaRifornimenti();
    void salvaRifornimento(Rifornimento rifornimento);
}