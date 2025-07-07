package dao;

// importa o modelo Estado e a classe de ligação à base de dados
import model.Estado;
import utils.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO (Data Access Object) para operações sobre a tabela 'estado'
 *
 * Esta classe permite consultar o estado dos equipamentos de forma isolada
 */
public class EstadoDAO {

    /**
     * Obtém um estado a partir do seu ID
     *
     * @param id identificador do estado
     * @return objeto Estado preenchido ou null se não existir
     */
    public Estado obterPorId(int id) {
        Estado e = null;

        try (Connection con = ConexaoDB.getConnection()) {
            // prepara a consulta SQL para buscar o registo com o ID fornecido
            String sql = "SELECT * FROM estado WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            // executa a consulta
            ResultSet rs = ps.executeQuery();

            // se encontrou o registo, popula o objeto Estado
            if (rs.next()) {
                e = new Estado();
                e.setId(rs.getInt("id"));
                e.setDescricao(rs.getString("descricao"));
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // em caso de erro, imprime a stack trace
        }

        return e;
    }
}
