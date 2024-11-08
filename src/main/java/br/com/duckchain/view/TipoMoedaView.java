package br.com.duckchain.view;

import br.com.duckchain.dao.ContaDao;
import br.com.duckchain.dao.TipoMoedaDao;
import br.com.duckchain.dao.UsuarioDao;
import br.com.duckchain.model.Conta;
import br.com.duckchain.model.TipoMoeda;
import br.com.duckchain.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class TipoMoedaView extends BaseView {
    private void incluir(Scanner scanner) {
        System.out.println("--- INCLUSÃO DE TIPO DE MOEDAS ---");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.print("Informe o nome da moeda: ");
        String nome = scanner.nextLine();

        System.out.print("Informe um código para a moeda (ex: BTC): ");
        String codigo = scanner.nextLine();

        System.out.print("Informe o preço atual: ");
        double precoAtual = scanner.nextDouble();

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        TipoMoeda tipoMoeda = new TipoMoeda(0, nome, codigo, precoAtual);

        System.out.print("Confirma a inculusão (s/n)? ");
        String confirma = scanner.nextLine();

        if (confirma.equalsIgnoreCase("s")) {
            TipoMoedaDao dao = new TipoMoedaDao();
            tipoMoeda = dao.create(tipoMoeda);
            System.out.println(tipoMoeda);

            System.out.print("Moeda incluída com sucesso, pressione enter para voltar ao menu.");
            scanner.nextLine();
        } else {
            System.out.println("Inclusão de moeda cancelada.");
        }
    }

    private void alterar(Scanner scanner) {
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();

        System.out.println("--- ALTERAÇÃO DE MOEDA ---");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.print("Informe o ID da moeda que deseja alterar: ");

        int idMoeda = scanner.nextInt();

        TipoMoeda tipoMoeda = tipoMoedaDao.findById(idMoeda);

        if (tipoMoeda == null) {
            System.out.println("Moeda não cadastrada!");
            alterar(scanner);
            return;
        }

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println(tipoMoeda);

        System.out.print("Informe o nome: ");
        tipoMoeda.setNome(scanner.nextLine());

        System.out.print("Informe o codigo: ");
        tipoMoeda.setCodigo(scanner.nextLine());

        System.out.print("Informe a cotação atual: ");
        tipoMoeda.setPrecoAtual(scanner.nextDouble());

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.print(tipoMoeda);

        System.out.print("Confirma a alteração (s/n)? ");
        String confirma = scanner.nextLine();

        if (confirma.equalsIgnoreCase("s")) {
            tipoMoeda = tipoMoedaDao.update(tipoMoeda);
            System.out.println(tipoMoeda);

            System.out.print("Moeda alterada com sucesso, pressione enter para voltar ao menu.");
            scanner.nextLine();
        } else {
            System.out.println("Alteração de moeda cancelada.");
        }
    }

    private void consultar(Scanner scanner) {
        boolean continuar = true;
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();
        System.out.println("--- CONSULTA MOEDA ---");

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da moeda que deseja consultar: ");
            int idMoeda = scanner.nextInt();
            TipoMoeda tipoMoeda = tipoMoedaDao.findById(idMoeda);

            if (tipoMoeda == null) {
                System.out.println("Moeda não cadastrado!");

            } else {
                System.out.println(tipoMoeda);
            }
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja consultar outra moeda (s/n)? ");
            String confirma = scanner.nextLine();

            continuar = confirma.equalsIgnoreCase("s");
        } while (continuar);
    }

    private void listar() {
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();
        System.out.println("--- LISTA MOEDAS ---");

        List<TipoMoeda> tipoMoedas = tipoMoedaDao.findAll();

        if (tipoMoedas != null && !tipoMoedas.isEmpty()) {
            for (TipoMoeda tipoMoeda : tipoMoedas) {
                System.out.println(tipoMoeda);
            }
        } else {
            System.out.println("Nenhuma moeda cadastrada!");
        }
    }

    private void excluir(Scanner scanner) {
        boolean continuar = true;
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();
        ContaDao contaDao = new ContaDao();
        System.out.println("--- EXCLUI MOEDA ---");
        String confirma;

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID da moeda que deseja excluir: ");
            int idMoeda = scanner.nextInt();
            TipoMoeda moeda = tipoMoedaDao.findById(idMoeda);

            if (moeda == null) {
                System.out.println("Moeda não cadastrada!");

            } else {
                System.out.println(moeda);

                System.out.print("Confirma a exclusão dessa moeda, se já existir uma conta para esse moeda será" +
                        " necessário excluir ela primeiro (s/n)? ");
                confirma = scanner.nextLine();

                if (confirma.equalsIgnoreCase("s")){
                    List<Conta> contas = contaDao.findByIdMoeda(moeda.getId());
                    if (contas == null || contas.isEmpty()) {
                        tipoMoedaDao.delete(moeda);
                    } else {
                        System.out.println("Já existe conta para essa moeda, por favor exclua ela primeiro!");
                    }
                }
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja excluir outra moeda (s/n)? ");
            confirma = scanner.nextLine();

            continuar = confirma.equalsIgnoreCase("s");
        } while (continuar);
    }

    public void showMenu(Scanner scanner) {
        int escolha;
        do {
            System.out.println("--- MENU TIPO DE MOEDA ---");
            super.showDefaultMenu();

            escolha = super.readOption(scanner, 0, 5);

            switch (escolha) {
                case 1: incluir(scanner);
                    break;
                case 2: alterar(scanner);
                    break;
                case 3: consultar(scanner);
                    break;
                case 4: listar();
                    break;
                case 5: excluir(scanner);
                    break;
            }

        } while (escolha != 0);
    }
}
