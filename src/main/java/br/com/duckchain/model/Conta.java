package br.com.duckchain.model;

import java.time.LocalDateTime;

public class Conta {
    private int id;
    private int idUsuario;
    private int idTipoMoeda;
    private Usuario usuario;
    private TipoMoeda tipoMoeda;
    private LocalDateTime dataCriacao;
    private String numeroConta;
    private double saldo;

    // Construtor
    public Conta(int id, Usuario usuario, TipoMoeda tipoMoeda, LocalDateTime dataCriacao, String numeroConta, double saldo) {
        this.id = id;
        this.usuario = usuario;
        this.tipoMoeda = tipoMoeda;
        this.dataCriacao = dataCriacao;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }
    public Conta(int id, int idUsuario, int idTipoMoeda, LocalDateTime dataCriacao, String numeroConta, double saldo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idTipoMoeda = idTipoMoeda;
        this.dataCriacao = dataCriacao;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() { return this.idUsuario; }

    public void  setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdTipoMoeda() { return this.idTipoMoeda; }

    public void setIdTipoMoeda(int idTipoMoeda) { this.idTipoMoeda = idTipoMoeda; }

    public TipoMoeda getTipoMoeda() { return this.tipoMoeda; }

    public void setTipoMoeda(TipoMoeda tipoMoeda) {
        this.tipoMoeda = tipoMoeda;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void deposita(double valor) {
        this.saldo += valor;
    }

    public void saque(double valor) throws Exception {
        if (valor > this.saldo) {
            throw new Exception("Saldo insuficiente para saque");
        }
        this.saldo -= valor;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idTipoMoeda=" + idTipoMoeda +
                ", usuario=" + usuario +
                ", tipoMoeda=" + tipoMoeda +
                ", dataCriacao=" + dataCriacao +
                ", numeroConta='" + numeroConta + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
