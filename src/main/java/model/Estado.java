package model;

/**
 * Classe modelo que representa o estado de um equipamento
 * Esta classe pode ser usada para indicar, por exemplo,
 * se um equipamento está ativo, inativo, em manutenção, etc.
 */
public class Estado {

    // campo que identifica unicamente o estado
    private int id;

    // descrição textual do estado (ex: "Ativo", "Inativo")
    private String descricao;

    /**
     * Obtém o ID do estado
     *
     * @return valor inteiro do ID
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do estado
     *
     * @param id identificador único
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a descrição textual do estado
     *
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição textual do estado
     *
     * @param descricao texto descritivo
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
