package application.persistence;

import application.utils.Euro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CassaDAOImpl implements CassaDAO {
    
    public CassaDAOImpl() {
        createTableIfNeeded();
    }

    private void createTableIfNeeded() {
        // Crea la tabella se non esiste
        String createSql = "CREATE TABLE IF NOT EXISTS cassa (id INT PRIMARY KEY, disponibilita BIGINT)";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createSql);
            String checkSql = "SELECT COUNT(*) FROM cassa";
            ResultSet rs = stmt.executeQuery(checkSql);
            if (rs.next() && rs.getInt(1) == 0) {
                String insertSql = "INSERT INTO cassa (id, disponibilita) VALUES (1, 200000)";
                stmt.execute(insertSql);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Euro caricaCassa() {
        String sql = "SELECT disponibilita FROM cassa WHERE id = 1";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                long valore = rs.getLong("disponibilita");
                return new Euro(0, valore); 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Euro(2000); 
    }

    @Override
    public void aggiornaCassa(Euro disponibilita) {
        String sql = "UPDATE cassa SET disponibilita = ? WHERE id = 1";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, disponibilita.getValore());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}