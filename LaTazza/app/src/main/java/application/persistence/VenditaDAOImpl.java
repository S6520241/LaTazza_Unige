package application.persistence;

import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import application.utils.TipoCialda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class VenditaDAOImpl implements VenditaDAO {

    public VenditaDAOImpl() {
        createTableIfNeeded();
    }

    private void createTableIfNeeded() {
        String createSql = "CREATE TABLE IF NOT EXISTS vendite ("
                         + "id INT AUTO_INCREMENT PRIMARY KEY, "
                         + "data_vendita BIGINT, "
                         + "nome_cliente VARCHAR(255), "
                         + "quantita INT, "
                         + "tipo_cialda VARCHAR(50), "
                         + "contanti BOOLEAN)";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Vendita> caricaVendite() {
        ArrayList<Vendita> vendite = new ArrayList<>();
        String sql = "SELECT data_vendita, nome_cliente, quantita, tipo_cialda, contanti FROM vendite ORDER BY data_vendita ASC";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                long dataEpoch = rs.getLong("data_vendita");
                String nomeCliente = rs.getString("nome_cliente");
                int quantita = rs.getInt("quantita");
                TipoCialda tc = TipoCialda.fromString(rs.getString("tipo_cialda"));
                boolean contanti = rs.getBoolean("contanti");
                
                // Ricreiamo la vendita leggendo dal DB (utilizziamo Persona come fa originariamente il codice di file reading)
                vendite.add(new Vendita(
                        new Persona(nomeCliente), 
                        quantita, 
                        tc, 
                        contanti, 
                        new Date(dataEpoch)
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendite;
    }

    @Override
    public void salvaVendita(Vendita vendita) {
        String sql = "INSERT INTO vendite (data_vendita, nome_cliente, quantita, tipo_cialda, contanti) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, vendita.getEpoch());
            pstmt.setString(2, vendita.getCliente().getNome());
            pstmt.setInt(3, vendita.getQuantita());
            pstmt.setString(4, vendita.getTipoCialda().toString());
            pstmt.setBoolean(5, vendita.isContanti());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}