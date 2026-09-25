package application.persistence;

import application.model.utenti.PagamentoDebito;
import java.util.LinkedHashSet;

public interface PagamentoDebitoDAO {
    LinkedHashSet<PagamentoDebito> caricaPagamenti();
    void salvaPagamento(PagamentoDebito pagamento);
}