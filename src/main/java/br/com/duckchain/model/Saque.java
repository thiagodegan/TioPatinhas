package br.com.duckchain.model;

import java.time.LocalDateTime;

public class Saque extends Transacao {

    // Construtor
    public Saque(int id, Conta conta, LocalDateTime data, Double montante, String observacao) throws Exception {
        super(id, conta, new TipoTransacao(1, "Saque"), data, montante, null, observacao);
        conta.saque(montante);
    }

    // Método para validar saldo
    public boolean validarSaldo() {
        return getConta().getSaldo() >= getMontante();
    }
}
