package application.persistence;

import application.utils.Euro;

public interface CassaDAO {
    Euro caricaCassa();
    void aggiornaCassa(Euro disponibilita);
}