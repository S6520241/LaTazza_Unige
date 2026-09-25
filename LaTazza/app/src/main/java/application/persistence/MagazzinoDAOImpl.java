package application.persistence;

import application.utils.TipoCialda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class MagazzinoDAOImpl implements MagazzinoDAO {

    public MagazzinoDAOImpl() {
        createTableIfNeeded();
    }

    private void createTableIfNeeded() {
        String createSql = "CREATE TABLE IF NOT EXISTS magazzino (tipo_cialda VARCHAR(50) PRIMARY KEY, quantita INT)";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createSql);
            
            String checkSql = "SELECT COUNT(*) FROM magazzino";
            ResultSet rs = stmt.executeQuery(checkSql);
            if (rs.next() && rs.getInt(1) == 0) {
                String insertSql = "INSERT INTO magazzino (tipo_cialda, quantita) VALUES (?, 0)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    for (TipoCialda tc : TipoCialda.values()) {
                        pstmt.setString(1, tc.name());
                        pstmt.executeUpdate();
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<TipoCialda, Integer> caricaMagazzino() {
        HashMap<TipoCialda, Integer> mag = new HashMap<>();
        String sql = "SELECT tipo_cialda, quantita FROM magazzino";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                // Recupera il nome dal DB e lo mappa all'enum
                TipoCialda tc = TipoCialda.valueOf(rs.getString("tipo_cialda"));
                int quantita = rs.getInt("quantita");
                mag.put(tc, quantita);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mag;
    }

    @Override
    public void aggiornaQuantita(TipoCialda tipoCialda, int quantita) {
        String sql = "UPDATE magazzino SET quantita = ? WHERE tipo_cialda = ?";
        
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, quantita);
            pstmt.setString(2, tipoCialda.name());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}