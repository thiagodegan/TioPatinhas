package br.com.duckchain.model;

import java.time.LocalDateTime;

public class Transferencia extends Transacao {
    private Conta contaOrigem;
    private Conta contaDestino;


    // Construtor
    public Transferencia(int id, Conta contaOrigem, Conta contaDestino, LocalDateTime data, Double montante, Double preco, String observacao) {
        super(id, contaOrigem, new TipoTransacao(3, "Transferência"), data, montante, preco, observacao);
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    // Getters e Setters
    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "contaOrigem=" + contaOrigem +
                ", contaDestino=" + contaDestino +
                '}';
    }
}
