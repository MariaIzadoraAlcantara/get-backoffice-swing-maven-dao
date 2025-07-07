package dao;

// importa o modelo Utilizador e a classe de ligação à base de dados
import model.Utilizador;
import utils.ConexaoDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para operações sobre a tabela 'utilizador'
 *
 * Permite efetuar todas as ações CRUD sobre os utilizadores
 */
public class UtilizadorDAO {

    /**
     * Lista todos os utilizadores presentes na tabela
     *
     * @return lista de objetos Utilizador
     */
    public List<Utilizador> listarTodos() {
        List<Utilizador> lista = new ArrayList<>();
        try (Connection con = ConexaoDB.getConnection()) {
            String sql = "SELECT * FROM utilizador";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // percorre todos os registos e cria objetos Utilizador
            while (rs.next()) {
                Utilizador u = new Utilizador();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setUtilizador(rs.getString("utilizador"));
                u.setPalavraChave(rs.getString("palavra_chave"));
                u.setPerfilId(rs.getInt("perfil_id"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Insere um novo utilizador na base de dados
     *
     * @param u objeto Utilizador a inserir
     * @return true se inseriu, false caso contrário
     */
    public boolean inserir(Utilizador u) {
        try (Connection con = ConexaoDB.getConnection()) {
            String sql = "INSERT INTO utilizador (nome, utilizador, palavra_chave, perfil_id) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getUtilizador());
            ps.setString(3, u.getPalavraChave());
            ps.setInt(4, u.getPerfilId());
            ps.executeUpdate();
            return true;
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            // tratamento para email repetido
            JOptionPane.showMessageDialog(null,
                    "O email já existe na base de dados.\nPor favor escolha outro.",
                    "Erro ao criar utilizador",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifica se um email já existe na tabela
     *
     * @param email email a validar
     * @return true se existir, false se não existir
     */
    public boolean existeEmail(String email) {
        try (Connection con = ConexaoDB.getConnection()) {
            String sql = "SELECT COUNT(*) FROM utilizador WHERE utilizador=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // devolve true se contar > 0
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Apaga um utilizador com base no seu ID
     *
     * @param id identificador do utilizador
     */
    public void apagar(int id) {
        try (Connection con = ConexaoDB.getConnection()) {
            String sql = "DELETE FROM utilizador WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza os dados de um utilizador (sem alterar a password)
     *
     * @param u objeto Utilizador atualizado
     */
    public void atualizar(Utilizador u) {
        try (Connection con = ConexaoDB.getConnection()) {
            String sql = "UPDATE utilizador SET nome=?, utilizador=?, perfil_id=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getUtilizador());
            ps.setInt(3, u.getPerfilId());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtém um utilizador através do email
     *
     * @param email email a pesquisar
     * @return objeto Utilizador se existir, senão null
     */
    public Utilizador obterPorEmail(String email) {
        Utilizador u = null;
        try (Connection con = ConexaoDB.getConnection()) {
            String sql = "SELECT * FROM utilizador WHERE utilizador=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Utilizador();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setUtilizador(rs.getString("utilizador"));
                u.setPalavraChave(rs.getString("palavra_chave"));
                u.setPerfilId(rs.getInt("perfil_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * Atualiza apenas a password de um utilizador
     *
     * @param id ID do utilizador
     * @param novaPasswordHash password já cifrada
     */
    public void atualizarPassword(int id, String novaPasswordHash) {
        try (Connection con = ConexaoDB.getConnection()) {
            String sql = "UPDATE utilizador SET palavra_chave=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novaPasswordHash);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
