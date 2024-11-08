package br.com.duckchain.model;

public class TipoTransacao {
    private int id;
    private String descricao;

    public TipoTransacao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "TipoTransacao{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
