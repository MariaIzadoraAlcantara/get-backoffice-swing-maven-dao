package controller;

// importa os DAOs que irão interagir com a base de dados
import dao.PerfilDAO;
import dao.UtilizadorDAO;
import model.Perfil;
import model.Utilizador;

import java.util.List;

/**
 * Controlador do Dashboard
 *
 * Esta classe atua como ponte entre a View (DashboardView)
 * e os DAOs, garantindo a separação de responsabilidades.
 */
public class DashboardController {

    // referência ao DAO de utilizadores
    private UtilizadorDAO utilizadorDAO;

    // referência ao DAO de perfis
    private PerfilDAO perfilDAO;

    /**
     * Construtor
     *
     * Inicializa os DAOs ao criar o controller.
     */
    public DashboardController() {
        utilizadorDAO = new UtilizadorDAO();
        perfilDAO = new PerfilDAO();
    }

    /**
     * Atualiza a password de um determinado utilizador
     *
     * @param id ID do utilizador
     * @param novaPasswordHash hash da nova password
     */
    public void atualizarPassword(int id, String novaPasswordHash) {
        utilizadorDAO.atualizarPassword(id, novaPasswordHash);
    }

    /**
     * Lista todos os utilizadores registados
     *
     * @return lista de utilizadores
     */
    public List<Utilizador> listarUtilizadores() {
        return utilizadorDAO.listarTodos();
    }

    /**
     * Atualiza os dados de um utilizador (nome, email, perfil)
     *
     * @param u objeto utilizador com os dados já atualizados
     */
    public void atualizarUtilizador(Utilizador u) {
        utilizadorDAO.atualizar(u);
    }

    /**
     * Apaga um utilizador da base de dados
     *
     * @param id ID do utilizador a eliminar
     */
    public void apagarUtilizador(int id) {
        utilizadorDAO.apagar(id);
    }

    /**
     * Lista todos os perfis disponíveis na tabela de perfis
     *
     * @return lista de perfis
     */
    public List<Perfil> listarPerfis() {
        return perfilDAO.listarTodos();
    }

    /**
     * Obtém um perfil a partir da sua descrição
     *
     * @param desc texto da descrição do perfil
     * @return perfil correspondente, ou null se não existir
     */
    public Perfil obterPerfilPorDescricao(String desc) {
        return perfilDAO.obterPorDescricao(desc);
    }
}
