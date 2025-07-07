package model;

/**
 * Classe modelo que representa um perfil de acesso
 *
 * Permite identificar diferentes tipos de utilizadores
 * no sistema, como "Administrador", "Editor", etc.
 */
public class Perfil {

    // identificador único do perfil (chave primária)
    private int id;

    // descrição do perfil (ex: "Administrador", "Formador")
    private String descricao;

    /**
     * Obtém o ID do perfil
     *
     * @return id numérico
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do perfil
     *
     * @param id identificador numérico
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a descrição textual do perfil
     *
     * @return descrição do perfil
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do perfil
     *
     * @param descricao texto descritivo
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Sobrescreve o método toString para apresentar
     * a descrição do perfil quando o objeto é exibido,
     * por exemplo num JComboBox.
     *
     * @return descrição do perfil
     */
    @Override
    public String toString() {
        return descricao;
    }
}
