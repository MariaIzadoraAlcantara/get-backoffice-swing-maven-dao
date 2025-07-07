
package controller;

// importa os DAOs e modelos necessários
import dao.UtilizadorDAO;
import dao.PerfilDAO;
import model.Perfil;
import model.Utilizador;
import services.PasswordEncryptionService;

/**
 * Controlador responsável pela autenticação de utilizadores no sistema
 */
public class LoginController {

    /**
     * Método para autenticar o utilizador no login
     *
     * @param email email do utilizador (campo utilizador na BD)
     * @param palavraChave password em texto simples
     * @return 1 se for válido e com perfil Administrador
     *         0 se credenciais inválidas
     *        -1 se perfil não autorizado
     */
    public int autenticar(String email, String palavraChave) {
        // cria o DAO para aceder à tabela utilizador
        UtilizadorDAO dao = new UtilizadorDAO();

        // tenta obter o utilizador através do email
        Utilizador u = dao.obterPorEmail(email);

        // verifica se encontrou o utilizador e se a password coincide
        if (u != null && PasswordEncryptionService.verifyPassword(palavraChave, u.getPalavraChave())) {

            // cria o DAO para perfis
            PerfilDAO perfilDAO = new PerfilDAO();

            // obtém o perfil associado ao utilizador
            Perfil p = perfilDAO.obterPorId(u.getPerfilId());

            // verifica se o perfil é Administrador
            if (p != null && p.getDescricao().equalsIgnoreCase("Administrador")) {
                return 1; // sucesso
            } else {
                return -1; // perfil existe mas não é administrador
            }
        }

        // se não encontrou ou a password falhou
        return 0; // credenciais inválidas
    }
}