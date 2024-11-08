package br.com.duckchain.model;

public class TipoMoeda {
    private int id;
    private String nome;
    private String codigo;
    private Double precoAtual;

    public TipoMoeda(int id, String nome, String codigo, Double precoAtual) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.precoAtual = precoAtual;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(Double precoAtual) {
        this.precoAtual = precoAtual;
    }

    @Override
    public String toString() {
        return "TipoMoeda{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", precoAtual=" + precoAtual +
                '}';
    }
}
