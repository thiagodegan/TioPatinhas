package br.com.duckchain.model;

import java.time.LocalDateTime;

public class Deposito extends Transacao {

    // Construtor
    public Deposito(int id, Conta conta, LocalDateTime data, Double montante, String observacao) {
        super(id, conta, new TipoTransacao(2, "Depósito"), data, montante, null, observacao);
        conta.deposita(montante);
    }
}
