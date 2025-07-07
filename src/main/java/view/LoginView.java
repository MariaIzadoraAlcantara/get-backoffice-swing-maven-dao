package view;

// importa o controlador responsável pela autenticação
import controller.LoginController;

import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável pela interface de login do backoffice
 */
public class LoginView extends JFrame {

    // campo de texto para o email (utilizador)
    private JTextField txtEmail;

    // campo de password
    private JPasswordField txtPassword;

    // botão de login
    private JButton btnLogin;

    /**
     * Construtor da interface de login
     */
    public LoginView() {
        setTitle("Backoffice - Login");
        setSize(400, 200);
        setLocationRelativeTo(null); // centra a janela no ecrã
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarComponentes();
    }

    /**
     * Inicializa todos os componentes do ecrã de login
     */
    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // etiqueta e campo de email
        JLabel lblEmail = new JLabel("Utilizador (Email):");
        txtEmail = new JTextField();

        // etiqueta e campo de password
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();

        // botão de login
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> fazerLogin());

        // organiza os componentes no painel
        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(lblPassword);
        painel.add(txtPassword);
        painel.add(new JLabel()); // espaço vazio para alinhamento
        painel.add(btnLogin);

        add(painel);
    }

    /**
     * Realiza a ação de autenticação do utilizador
     */
    private void fazerLogin() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        LoginController controller = new LoginController();
        int resultado = controller.autenticar(email, password);

        if (resultado == 1) {
            // se autenticado com sucesso
            new DashboardView().mostrar();
            dispose();
        } else if (resultado == -1) {
            // perfil sem acesso (não administrador)
            JOptionPane.showMessageDialog(this,
                    "Apenas utilizadores com perfil Administrador podem aceder ao backoffice.",
                    "Acesso Negado",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // credenciais inválidas
            JOptionPane.showMessageDialog(this,
                    "Credenciais inválidas.",
                    "Erro de Autenticação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método main para arrancar a aplicação diretamente
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}