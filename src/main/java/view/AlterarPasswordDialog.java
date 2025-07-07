package view;

// importa o controller e o serviço de cifragem
import controller.DashboardController;
import services.PasswordEncryptionService;

import javax.swing.*;
import java.awt.*;

/**
 * Janela de diálogo (JDialog) para alterar a password
 * de um determinado utilizador
 */
public class AlterarPasswordDialog extends JDialog {

    // campo para introduzir a nova password
    private JPasswordField txtPassword;

    // campo para confirmar a nova password
    private JPasswordField txtPasswordRepeat;

    // botão para gravar a alteração
    private JButton btnGravar;

    // botão para cancelar a operação
    private JButton btnCancelar;

    // ID do utilizador a quem vamos alterar a password
    private int utilizadorId;

    /**
     * Construtor do diálogo
     *
     * @param owner janela principal (DashboardView)
     * @param utilizadorId id do utilizador selecionado
     */
    public AlterarPasswordDialog(JFrame owner, int utilizadorId) {
        super(owner, "Alterar Password", true); // título e modal
        this.utilizadorId = utilizadorId;

        setSize(400, 220);
        setLocationRelativeTo(owner); // centra a janela
        inicializarComponentes();
    }

    /**
     * Configura os componentes do diálogo
     */
    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtPassword = new JPasswordField();
        txtPasswordRepeat = new JPasswordField();

        btnGravar = new JButton("Gravar");
        btnCancelar = new JButton("Cancelar");

        // adicionar os campos ao painel
        painel.add(new JLabel("Nova Password:"));
        painel.add(txtPassword);
        painel.add(new JLabel("Confirmar Password:"));
        painel.add(txtPasswordRepeat);
        painel.add(btnCancelar);
        painel.add(btnGravar);

        // ação do botão cancelar
        btnCancelar.addActionListener(e -> dispose());

        // ação do botão gravar
        btnGravar.addActionListener(e -> gravar());

        add(painel);
    }

    /**
     * Valida e grava a nova password
     */
    private void gravar() {
        String pass1 = new String(txtPassword.getPassword());
        String pass2 = new String(txtPasswordRepeat.getPassword());

        // verifica se preencheu ambos os campos
        if (pass1.isEmpty() || pass2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha ambas as passwords.");
            return;
        }

        // confirma se são iguais
        if (!pass1.equals(pass2)) {
            JOptionPane.showMessageDialog(this, "As passwords não coincidem.");
            return;
        }

        // chama o controller para atualizar a password cifrada
        DashboardController controller = new DashboardController();
        String hash = PasswordEncryptionService.hashPassword(pass1);
        controller.atualizarPassword(utilizadorId, hash);

        JOptionPane.showMessageDialog(this, "Password atualizada com sucesso.");
        dispose(); // fecha o diálogo
    }
}
