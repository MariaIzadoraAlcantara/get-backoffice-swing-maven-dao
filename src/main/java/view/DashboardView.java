package view;

// importa controladores e modelos necessários
import controller.DashboardController;
import model.Perfil;
import model.Utilizador;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Classe que representa a janela principal do backoffice,
 * permitindo gerir os utilizadores (CRUD).
 */
public class DashboardView extends JFrame {

    // controller que gere a comunicação com os DAOs
    private DashboardController controller;

    // tabela que apresenta os utilizadores
    private JTable tabelaUtilizadores;

    // botões de operações
    private JButton btnCriar;
    private JButton btnEliminar;
    private JButton btnAlterarPassword;
    private JButton btnLogout;

    // modelo da tabela (permite alterar os dados dinamicamente)
    private DefaultTableModel modelo;

    // status em baixo para mostrar mensagens ao utilizador
    private JLabel lblStatus;

    /**
     * Construtor do dashboard
     */
    public DashboardView() {
        controller = new DashboardController();

        setTitle("Dashboard - Gestão de Utilizadores");
        setSize(800, 500);
        setLocationRelativeTo(null); // centra a janela no ecrã
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
        carregarUtilizadores();
    }

    /**
     * Inicializa todos os componentes gráficos
     */
    private void inicializarComponentes() {
        JPanel painel = new JPanel(new BorderLayout());

        // configurar botões
        btnCriar = new JButton("Criar Utilizador");
        btnCriar.addActionListener(e -> abrirCriarUtilizador());

        btnEliminar = new JButton("Eliminar Utilizador");
        btnEliminar.addActionListener(e -> eliminarUtilizador());

        btnAlterarPassword = new JButton("Alterar Password");
        btnAlterarPassword.addActionListener(e -> abrirAlterarPassword());

        btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> fazerLogout());

        // painel de botões no topo
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnCriar);
        painelBotoes.add(btnEliminar);
        painelBotoes.add(btnAlterarPassword);
        painelBotoes.add(btnLogout);

        painel.add(painelBotoes, BorderLayout.NORTH);

        // configurar a tabela
        String[] colunas = {"ID", "Nome", "Email", "Perfil"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // apenas colunas diferentes do ID são editáveis
                return column != 0;
            }
        };

        tabelaUtilizadores = new JTable(modelo);

        // melhorar legibilidade da tabela
        tabelaUtilizadores.setRowHeight(30);
        tabelaUtilizadores.setFont(new Font("Arial", Font.PLAIN, 14));

        // preencher o editor de perfil com dropdown
        List<Perfil> listaPerfis = controller.listarPerfis();
        String[] descricoesPerfis = listaPerfis.stream()
                .map(Perfil::getDescricao)
                .toArray(String[]::new);

        JComboBox<String> comboPerfil = new JComboBox<>(descricoesPerfis);
        tabelaUtilizadores.getColumnModel().getColumn(3)
                .setCellEditor(new DefaultCellEditor(comboPerfil));

        JScrollPane scroll = new JScrollPane(tabelaUtilizadores);
        painel.add(scroll, BorderLayout.CENTER);

        // label de status (mensagens)
        lblStatus = new JLabel(" ");
        lblStatus.setOpaque(true);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
        lblStatus.setForeground(Color.BLACK);
        lblStatus.setBackground(Color.LIGHT_GRAY);

        painel.add(lblStatus, BorderLayout.SOUTH);

        // adiciona listener para detectar alterações
        modelo.addTableModelListener(e -> tratarAlteracaoTabela(e));

        add(painel);
    }

    /**
     * Carrega os utilizadores na tabela
     */
    private void carregarUtilizadores() {
        modelo.setRowCount(0); // limpa a tabela

        List<Utilizador> lista = controller.listarUtilizadores();

        for (Utilizador u : lista) {
            // obter descrição do perfil do utilizador
            Perfil perfil = controller.obterPerfilPorDescricao(
                    controller.listarPerfis().stream()
                            .filter(p -> p.getId() == u.getPerfilId())
                            .map(Perfil::getDescricao)
                            .findFirst()
                            .orElse("")
            );

            // adiciona linha à tabela
            modelo.addRow(new Object[]{
                    u.getId(),
                    u.getNome(),
                    u.getUtilizador(),
                    perfil != null ? perfil.getDescricao() : ""
            });
        }
    }

    /**
     * Trata alterações diretamente editadas na grelha
     */
    private void tratarAlteracaoTabela(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            int linha = e.getFirstRow();
            int id = (int) modelo.getValueAt(linha, 0);
            String nome = (String) modelo.getValueAt(linha, 1);
            String email = (String) modelo.getValueAt(linha, 2);
            String perfilDescricao = (String) modelo.getValueAt(linha, 3);

            Perfil perfil = controller.obterPerfilPorDescricao(perfilDescricao);

            Utilizador u = new Utilizador();
            u.setId(id);
            u.setNome(nome);
            u.setUtilizador(email);
            u.setPerfilId(perfil != null ? perfil.getId() : 1);

            try {
                controller.atualizarUtilizador(u);
                lblStatus.setText("Dado atualizado na base de dados com sucesso.");
                lblStatus.setBackground(Color.GREEN);
                lblStatus.setForeground(Color.BLACK);
                new javax.swing.Timer(3000, ev -> {
                    lblStatus.setText(" ");
                    lblStatus.setBackground(Color.LIGHT_GRAY);
                }).start();
            } catch (Exception ex) {
                lblStatus.setText("Erro ao atualizar na base de dados!");
                lblStatus.setBackground(Color.RED);
                lblStatus.setForeground(Color.BLACK);
                new javax.swing.Timer(5000, ev -> {
                    lblStatus.setText(" ");
                    lblStatus.setBackground(Color.LIGHT_GRAY);
                }).start();
                ex.printStackTrace();
            }
        }
    }

    /**
     * Abre a janela para criar um novo utilizador
     */
    private void abrirCriarUtilizador() {
        new CriarUtilizadorDialog(this).setVisible(true);
        carregarUtilizadores();
    }

    /**
     * Abre o diálogo para alterar a password do utilizador
     */
    private void abrirAlterarPassword() {
        int linha = tabelaUtilizadores.getSelectedRow();
        if (linha >= 0) {
            int id = (int) modelo.getValueAt(linha, 0);
            new AlterarPasswordDialog(this, id).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione primeiro um utilizador.");
        }
    }

    /**
     * Elimina um utilizador selecionado da tabela
     */
    private void eliminarUtilizador() {
        int linha = tabelaUtilizadores.getSelectedRow();
        if (linha >= 0) {
            int id = (int) modelo.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem a certeza que pretende eliminar o utilizador ID " + id + "?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.apagarUtilizador(id);
                    carregarUtilizadores();
                    lblStatus.setText("Utilizador eliminado com sucesso.");
                    lblStatus.setBackground(Color.GREEN);
                    lblStatus.setForeground(Color.BLACK);
                    new javax.swing.Timer(3000, ev -> {
                        lblStatus.setText(" ");
                        lblStatus.setBackground(Color.LIGHT_GRAY);
                    }).start();
                } catch (Exception ex) {
                    lblStatus.setText("Erro ao eliminar o utilizador!");
                    lblStatus.setBackground(Color.RED);
                    lblStatus.setForeground(Color.BLACK);
                    new javax.swing.Timer(5000, ev -> {
                        lblStatus.setText(" ");
                        lblStatus.setBackground(Color.LIGHT_GRAY);
                    }).start();
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione primeiro um utilizador.");
        }
    }

    /**
     * Termina a sessão e volta ao ecrã de login
     */
    private void fazerLogout() {
        dispose(); // fecha o dashboard
        new LoginView().setVisible(true); // reabre o login
    }

    /**
     * Torna a janela visível
     */
    public void mostrar() {
        setVisible(true);
    }
}