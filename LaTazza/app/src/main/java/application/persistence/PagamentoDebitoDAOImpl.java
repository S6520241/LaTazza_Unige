package application.persistence;

import application.model.utenti.PagamentoDebito;
import application.model.utenti.Persona;
import application.utils.Euro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashSet;

public class PagamentoDebitoDAOImpl implements PagamentoDebitoDAO {

    public PagamentoDebitoDAOImpl() {
        createTableIfNeeded();
    }

    private void createTableIfNeeded() {
        String createSql = "CREATE TABLE IF NOT EXISTS pagamenti_debito ("
                         + "id INT AUTO_INCREMENT PRIMARY KEY, "
                         + "data_pagamento BIGINT, "
                         + "nome_persona VARCHAR(255), "
                         + "ammontare BIGINT)";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedHashSet<PagamentoDebito> caricaPagamenti() {
        LinkedHashSet<PagamentoDebito> pagamenti = new LinkedHashSet<>();
        String sql = "SELECT data_pagamento, nome_persona, ammontare FROM pagamenti_debito ORDER BY data_pagamento ASC";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                long dataEpoch = rs.getLong("data_pagamento");
                String nome = rs.getString("nome_persona");
                long ammontare = rs.getLong("ammontare");
                
                pagamenti.add(new PagamentoDebito(
                        new Persona(nome), 
                        new Euro(0, ammontare), 
                        new Date(dataEpoch)
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagamenti;
    }

    @Override
    public void salvaPagamento(PagamentoDebito pagamento) {
        String sql = "INSERT INTO pagamenti_debito (data_pagamento, nome_persona, ammontare) VALUES (?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, pagamento.getEpoch());
            pstmt.setString(2, pagamento.getPersona().getNome());
            pstmt.setLong(3, pagamento.getAmmontare().getValore());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}