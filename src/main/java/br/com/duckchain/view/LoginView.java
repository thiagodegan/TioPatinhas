package br.com.duckchain.view;

import br.com.duckchain.dao.ContaDao;
import br.com.duckchain.dao.TipoMoedaDao;
import br.com.duckchain.dao.TransacaoDao;
import br.com.duckchain.dao.UsuarioDao;
import br.com.duckchain.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class LoginView {
    private Usuario usuario;

    private void abrirConta(Scanner scanner) {
        System.out.println("--- ABERTURA DE CONTA ----");
        boolean voltar = false;
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();
        ContaDao contaDao = new ContaDao();

        List<TipoMoeda> tipoMoedas = tipoMoedaDao.findAll();
        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.println("Tipos de moedas disponíveis:");

            for (TipoMoeda tipoMoeda : tipoMoedas) {
                System.out.println(tipoMoeda);
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da moeda que deseja utilizar nessa conta: ");
            int idMoeda = scanner.nextInt();
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            System.out.print("Informe um numero para a conta, pode ser alfanumérico: ");
            String numeroConta = scanner.nextLine();

            Conta conta = new Conta(0, usuario.getId(), idMoeda, LocalDateTime.now(), numeroConta, 0);
            contaDao.create(conta);

            System.out.println(conta);

            System.out.println("Conta cadastrada com sucesso.");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja cadastrar outra conta (s/n): ");
            String continuarCadastro = scanner.nextLine();

            voltar = !continuarCadastro.equalsIgnoreCase("s");
        } while (!voltar);
    }

    private void consultarContas(Scanner scanner) {
        System.out.println("--- CONSULTA CONTA ---");
        boolean voltar = false;
        ContaDao contaDao = new ContaDao();

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da conta que deseja consultar: ");
            int idConta = scanner.nextInt();
            Conta conta = contaDao.findById(idConta);

            if (conta != null && conta.getIdUsuario() == usuario.getId()) {
                System.out.println(conta);
            } else {
                System.out.println("Conta não localizada!");
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja consultar outra conta (s/n): ");
            String continuarConsulta = scanner.nextLine();

            voltar = !continuarConsulta.equalsIgnoreCase("s");

        } while (!voltar);
    }

    private void listarContas() {
        ContaDao contaDao = new ContaDao();

        List<Conta> contas = contaDao.findByIdUsuario(this.usuario.getId());

        if (contas != null && !contas.isEmpty()) {
            for(Conta conta : contas) {
                System.out.println(conta);
            }
        } else {
            System.out.println("Nenhuma conta localizada para você.");
        }
    }

    private void extrato(Scanner scanner) {
        System.out.println("--- EXTRATO DA CONTA ---");
        boolean voltar = false;
        ContaDao contaDao = new ContaDao();
        TransacaoDao transacaoDao = new TransacaoDao();

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da conta que deseja consultar o extrato: ");
            int idConta = scanner.nextInt();
            Conta conta = contaDao.findById(idConta);

            if (conta != null && conta.getIdUsuario() == usuario.getId()) {
                System.out.println(conta);
                List<Transacao> transacoes = transacaoDao.listar(conta.getId());

                if (transacoes != null && !transacoes.isEmpty()) {
                    for (Transacao transacao : transacoes) {
                        System.out.println(transacao);
                    }
                } else {
                    System.out.println("Nenhuma transação encontrada na conta solicitada.");
                }


            } else {
                System.out.println("Conta não localizada!");
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja consultar outra conta (s/n): ");
            String continuarConsulta = scanner.nextLine();

            voltar = !continuarConsulta.equalsIgnoreCase("s");
        } while (!voltar);
    }

    private void depositar(Scanner scanner) {
        System.out.println("--- DEPOSITAR ---");
        boolean voltar = false;
        ContaDao contaDao = new ContaDao();
        TransacaoDao transacaoDao = new TransacaoDao();

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da conta que deseja depositar: ");
            int idConta = scanner.nextInt();
            Conta conta = contaDao.findById(idConta);

            if (conta != null && conta.getIdUsuario() == usuario.getId()) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                System.out.print("Informe o valor do depósito: ");
                double valor = scanner.nextDouble();

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                System.out.print("Informe uma observação: ");
                String observacao = scanner.nextLine();

                Deposito deposito = new Deposito(0, conta, LocalDateTime.now(), valor, observacao);
                deposito = transacaoDao.depositar(deposito);

                if (deposito != null) {
                    System.out.println("Deposito realizado com sucesso!");
                }

            } else {
                System.out.println("Conta não localizada!");
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja realizar outro depósito (s/n): ");
            String continuarConsulta = scanner.nextLine();

            voltar = !continuarConsulta.equalsIgnoreCase("s");
        } while (!voltar);
    }

    private void sacar(Scanner scanner) {
        System.out.println("--- SACAR ---");
        boolean voltar = false;
        ContaDao contaDao = new ContaDao();
        TransacaoDao transacaoDao = new TransacaoDao();

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da conta que deseja sacar: ");
            int idConta = scanner.nextInt();
            Conta conta = contaDao.findById(idConta);

            if (conta != null && conta.getIdUsuario() == usuario.getId()) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                System.out.print("Informe o valor do saque: ");
                double valor = scanner.nextDouble();

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                if (valor > conta.getSaldo()) {
                    System.out.println("Saldo insuficiente para saque.");
                } else {
                    System.out.print("Informe uma observação: ");
                    String observacao = scanner.nextLine();

                    Saque saque = null;
                    try {
                        saque = new Saque(0, conta, LocalDateTime.now(), valor, observacao);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    saque = transacaoDao.sacar(saque);

                    if (saque != null) {
                        System.out.println("Saque realizado com sucesso!");
                    }
                }

            } else {
                System.out.println("Conta não localizada!");
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja realizar outro saque (s/n): ");
            String continuarConsulta = scanner.nextLine();

            voltar = !continuarConsulta.equalsIgnoreCase("s");
        } while (!voltar);
    }

    private void transferir(Scanner scanner) {
        System.out.println("--- TRANSFERIR ---");
        boolean voltar = false;
        ContaDao contaDao = new ContaDao();
        TransacaoDao transacaoDao = new TransacaoDao();

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da conta que deseja sacar: ");
            int idConta = scanner.nextInt();
            Conta conta = contaDao.findById(idConta);

            if (conta != null && conta.getIdUsuario() == usuario.getId()) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                System.out.print("Informe o valor do saque: ");
                double valor = scanner.nextDouble();

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                if (valor > conta.getSaldo()) {
                    System.out.println("Saldo insuficiente para saque.");
                } else {

                    System.out.print("Informe o ID da conta que deseja depositar: ");
                    int idContaDestino = scanner.nextInt();
                    Conta contaDestino = contaDao.findById(idContaDestino);

                    if (contaDestino != null && contaDestino.getIdUsuario() == usuario.getId()) {
                        if (scanner.hasNextLine()) {
                            scanner.nextLine();
                        }
                        System.out.print("Informe uma observação: ");
                        String observacao = scanner.nextLine();

                        Transferencia transferencia = null;
                        try {
                            transferencia = new Transferencia(0, conta, contaDestino, LocalDateTime.now(), valor, null, observacao);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        transferencia = transacaoDao.transferencia(transferencia);

                        if (transferencia != null) {
                            System.out.println("Transferencia realizada com sucesso!");
                        }
                    } else {
                        System.out.println("Conta não localizada!");
                    }
                }

            } else {
                System.out.println("Conta não localizada!");
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja realizar outro depósito (s/n): ");
            String continuarConsulta = scanner.nextLine();

            voltar = !continuarConsulta.equalsIgnoreCase("s");
        } while (!voltar);
    }

    private void showMenu (Scanner scanner) {
        int escolha;

        do {
            System.out.println("--- DUCKCHAIN ---");
            System.out.println("1 - Abrir Conta");
            System.out.println("2 - Consultar Conta");
            System.out.println("3 - Listar Contas");
            System.out.println("4 - Extrato de Conta");
            System.out.println("5 - Depositar");
            System.out.println("6 - Sacar");
            System.out.println("7 - Transferir");
            System.out.println("0 - Voltar");

            System.out.print("Informe uma das opções acima: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1: // ABRIR CONTA
                    abrirConta(scanner);
                    break;
                case 2: // CONSULTAR CONTA
                    consultarContas(scanner);
                    break;
                case 3: // LISTAR CONTA
                    listarContas();
                    break;
                case 4: // EXTRATO CONTA
                    extrato(scanner);
                    break;
                case 5: // DEPOSITAR
                    depositar(scanner);
                    break;
                case 6: // SACAR
                    sacar(scanner);
                    break;
                case 7: // TRANSFERIR
                    transferir(scanner);
                    break;
            }

        } while (escolha != 0);
    }

    public void login (Scanner scanner) {
        System.out.println("--- LOGIN ----");
        boolean voltar = false;

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.println("obs: usuario teste: teste@teste.com senha: 123456");

            System.out.print("Informe seu email: ");
            String email = scanner.nextLine();

            System.out.print("Informe a sua senha: ");
            String senha = scanner.nextLine();

            UsuarioDao usuarioDao = new UsuarioDao();

            Usuario usuarioLogin = usuarioDao.findByEmail(email);

            if (usuarioLogin != null && usuarioLogin.getSenha().equals(senha)) {
                this.usuario = usuarioLogin;

                System.out.println("Olá " + this.usuario.getNome());
                showMenu(scanner);
                voltar = true;
            } else {
                System.out.println("Usuário ou Senha inválidos!");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                System.out.print("Tentar novamente (s/n): ");

                String tentarNovamente = scanner.nextLine();

                voltar = !tentarNovamente.equalsIgnoreCase("s");
            }
        } while (!voltar);
    }
}
