package view;

// importa os DAOs, modelos e serviços necessários
import dao.PerfilDAO;
import dao.UtilizadorDAO;
import model.Perfil;
import model.Utilizador;
import services.PasswordEncryptionService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Janela de diálogo (JDialog) para criar um novo utilizador
 */
public class CriarUtilizadorDialog extends JDialog {

    // campo para o nome do utilizador
    private JTextField txtNome;

    // campo para o email (login)
    private JTextField txtEmail;

    // campo para a password
    private JPasswordField txtPassword;

    // dropdown com os perfis disponíveis
    private JComboBox<Perfil> comboPerfil;

    // botão para gravar
    private JButton btnGravar;

    /**
     * Construtor do diálogo
     *
     * @param owner janela principal (DashboardView)
     */
    public CriarUtilizadorDialog(JFrame owner) {
        super(owner, "Novo Utilizador", true); // título + modal
        setSize(400, 300);
        setLocationRelativeTo(owner); // centra a janela
        inicializarComponentes();
    }

    /**
     * Configura os componentes da interface
     */
    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNome = new JTextField();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        comboPerfil = new JComboBox<>();

        // carrega os perfis a partir da base de dados
        PerfilDAO perfilDAO = new PerfilDAO();
        List<Perfil> perfis = perfilDAO.listarTodos();
        for (Perfil p : perfis) {
            comboPerfil.addItem(p);
        }

        btnGravar = new JButton("Gravar");

        // disposição dos elementos no painel
        painel.add(new JLabel("Nome:"));
        painel.add(txtNome);
        painel.add(new JLabel("Utilizador (Email):"));
        painel.add(txtEmail);
        painel.add(new JLabel("Password:"));
        painel.add(txtPassword);
        painel.add(new JLabel("Perfil:"));
        painel.add(comboPerfil);
        painel.add(new JLabel()); // célula vazia
        painel.add(btnGravar);

        // ação do botão gravar
        btnGravar.addActionListener(e -> gravar());

        add(painel);
    }

    /**
     * Valida e grava o novo utilizador
     */
    private void gravar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        Perfil perfil = (Perfil) comboPerfil.getSelectedItem();

        // valida campos obrigatórios
        if (nome.isEmpty() || email.isEmpty() || password.isEmpty() || perfil == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        // valida formato do email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                    "O email não está num formato válido (ex: nome@dominio.pt).",
                    "Erro de validação",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // verifica se o email já existe na base de dados
        UtilizadorDAO dao = new UtilizadorDAO();
        if (dao.existeEmail(email)) {
            JOptionPane.showMessageDialog(this,
                    "O email introduzido já existe.\nEscolha outro.",
                    "Erro de validação",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // cria o objeto Utilizador e preenche os dados
        Utilizador u = new Utilizador();
        u.setNome(nome);
        u.setUtilizador(email);
        u.setPalavraChave(PasswordEncryptionService.hashPassword(password));
        u.setPerfilId(perfil.getId());

        // insere o utilizador na base de dados
        if (dao.inserir(u)) {
            JOptionPane.showMessageDialog(this, "Utilizador criado com sucesso!");
            dispose(); // fecha o diálogo
        }
    }
}