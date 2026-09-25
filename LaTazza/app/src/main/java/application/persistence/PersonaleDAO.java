package application.persistence;

import application.model.utenti.Persona;
import java.util.LinkedHashSet;

public interface PersonaleDAO {
    LinkedHashSet<Persona> caricaPersonale();
    void salvaPersona(Persona persona);
    void aggiornaDebito(Persona persona);
    void rimuoviPersona(Persona persona);
}