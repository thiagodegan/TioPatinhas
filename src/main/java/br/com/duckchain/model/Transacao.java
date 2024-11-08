package br.com.duckchain.model;

import java.time.LocalDateTime;

public class Transacao {
    private int id;
    private int idConta;
    private Conta conta;
    private int idTipoTransacao;
    private TipoTransacao tipoTransacao;
    private  int idTransferencia;
    private Transferencia transferencia;
    private LocalDateTime data;
    private Double montante;
    private Double preco;
    private String observacao;

    // Construtor
    public Transacao(int id, Conta conta, TipoTransacao tipoTransacao, LocalDateTime data, Double montante, Double preco, String observacao) {
        this.id = id;
        this.conta = conta;
        this.tipoTransacao = tipoTransacao;
        this.data = data;
        this.montante = montante;
        this.preco = preco;
        this.observacao = observacao;
    }

    public Transacao(int id, int idConta, int idTipoTransacao, int idTransferencia, LocalDateTime data, double montante, double preco, String observacao) {
        this.id = id;
        this.idConta = idConta;
        this.idTipoTransacao = idTipoTransacao;
        this.idTransferencia = idTransferencia;
        this.data = data;
        this.montante = montante;
        this.preco = preco;
        this.observacao = observacao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getMontante() {
        return montante;
    }

    public void setMontante(Double montante) {
        this.montante = montante;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public void setIdTipoTransacao(int idTipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
    }

    public int getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(int idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", idConta=" + idConta +
                ", conta=" + conta +
                ", idTipoTransacao=" + idTipoTransacao +
                ", tipoTransacao=" + tipoTransacao +
                ", idTransferencia=" + idTransferencia +
                ", transferencia=" + transferencia +
                ", data=" + data +
                ", montante=" + montante +
                ", preco=" + preco +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
