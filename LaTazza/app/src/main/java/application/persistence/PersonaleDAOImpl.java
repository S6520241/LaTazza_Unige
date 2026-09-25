package application.persistence;

import application.model.utenti.Persona;
import application.utils.Euro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

public class PersonaleDAOImpl implements PersonaleDAO {

    public PersonaleDAOImpl() {
        createTableIfNeeded();
    }

    private void createTableIfNeeded() {
        String createSql = "CREATE TABLE IF NOT EXISTS personale ("
                         + "nome VARCHAR(255) PRIMARY KEY, "
                         + "debito BIGINT)";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createSql);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedHashSet<Persona> caricaPersonale() {
        LinkedHashSet<Persona> personale = new LinkedHashSet<>();
        String sql = "SELECT nome, debito FROM personale";
        
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String nome = rs.getString("nome");
                long debito = rs.getLong("debito");
                // Crea la persona ricaricando il debito dai centesimi salvati
                personale.add(new Persona(nome, new Euro(0, debito)));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personale;
    }

    @Override
    public void salvaPersona(Persona persona) {
        String sql = "INSERT INTO personale (nome, debito) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, persona.getNome());
            pstmt.setLong(2, persona.getDebito().getValore());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void aggiornaDebito(Persona persona) {
        String sql = "UPDATE personale SET debito = ? WHERE nome = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, persona.getDebito().getValore());
            pstmt.setString(2, persona.getNome());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rimuoviPersona(Persona persona) {
        String sql = "DELETE FROM personale WHERE nome = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, persona.getNome());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}