package application.persistence;

import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class RifornimentoDAOImpl implements RifornimentoDAO {

    public RifornimentoDAOImpl() {
        createTableIfNeeded();
    }

    private void createTableIfNeeded() {
        String createSql = "CREATE TABLE IF NOT EXISTS rifornimenti ("
                         + "id INT AUTO_INCREMENT PRIMARY KEY, "
                         + "data_rifornimento BIGINT, "
                         + "numero_scatole INT, "
                         + "tipo_cialda VARCHAR(50))";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createSql);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Rifornimento> caricaRifornimenti() {
        ArrayList<Rifornimento> rifornimenti = new ArrayList<>();
        String sql = "SELECT data_rifornimento, numero_scatole, tipo_cialda FROM rifornimenti ORDER BY data_rifornimento ASC";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                long dataEpoch = rs.getLong("data_rifornimento");
                int numScatole = rs.getInt("numero_scatole");
                // Usiamo il metodo fromString presente in TipoCialda
                TipoCialda tc = TipoCialda.fromString(rs.getString("tipo_cialda")); 
                
                rifornimenti.add(new Rifornimento(numScatole, tc, new Date(dataEpoch)));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rifornimenti;
    }

    @Override
    public void salvaRifornimento(Rifornimento rifornimento) {
        String sql = "INSERT INTO rifornimenti (data_rifornimento, numero_scatole, tipo_cialda) VALUES (?, ?, ?)";
        
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, rifornimento.getEpoch());
            pstmt.setInt(2, rifornimento.getNumeroScatole());
            pstmt.setString(3, rifornimento.getTipoCialda().toString());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}