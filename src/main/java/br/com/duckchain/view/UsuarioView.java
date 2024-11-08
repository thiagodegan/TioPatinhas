package br.com.duckchain.view;

import br.com.duckchain.dao.ContaDao;
import br.com.duckchain.dao.UsuarioDao;
import br.com.duckchain.model.Conta;
import br.com.duckchain.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class UsuarioView extends BaseView {
    private void incluir(Scanner scanner) {
        System.out.println("--- INCLUSÃO DE USUÁRIO ---");
        scanner.nextLine(); // LIMPA ENTRADA
        System.out.print("Informe o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Informe o email: ");
        String email = scanner.nextLine();
        System.out.print("Informe a senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = new Usuario(0, nome, email, senha, LocalDateTime.now());

        System.out.print("Confirma a inculusão (s/n)? ");
        String confirma = scanner.nextLine();

        if (confirma.equalsIgnoreCase("s")) {
            UsuarioDao dao = new UsuarioDao();
            usuario = dao.create(usuario);
            System.out.println(usuario);

            System.out.print("Usuário incluído com sucesso, pressione enter para voltar ao menu.");
            scanner.nextLine();
        } else {
            System.out.println("Inclusão de usuário cancelada.");
        }
    }

    private void alterar(Scanner scanner) {
        UsuarioDao usuarioDao = new UsuarioDao();
        System.out.println("--- ALTERAÇÃO DE USUÁRIO ---");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.print("Informe o ID do usuário que deseja alterar: ");
        int idUsuario = scanner.nextInt();
        Usuario usuario = usuarioDao.findById(idUsuario);

        if (usuario == null) {
            System.out.println("Usuário não cadastrado!");
            alterar(scanner);
            return;
        }

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println(usuario);

        System.out.print("Informe o nome: ");
        usuario.setNome(scanner.nextLine());
        System.out.print("Informe o email: ");
        usuario.setEmail(scanner.nextLine());
        System.out.print("Informe a senha: ");
        usuario.setSenha(scanner.nextLine());

        System.out.println(usuario);

        System.out.print("Confirma a alteração (s/n)? ");
        String confirma = scanner.nextLine();

        if (confirma.equalsIgnoreCase("s")) {
            usuario = usuarioDao.update(usuario);
            System.out.println(usuario);

            System.out.print("Usuário alterado com sucesso, pressione enter para voltar ao menu.");
            scanner.nextLine();
        } else {
            System.out.println("Alteração de usuário cancelada.");
        }
    }

    private void consultar(Scanner scanner) {
        boolean continuar = true;
        UsuarioDao usuarioDao = new UsuarioDao();
        System.out.println("--- CONSULTA USUÁRIO ---");

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID do usuário que deseja consultar: ");
            int idUsuario = scanner.nextInt();
            Usuario usuario = usuarioDao.findById(idUsuario);

            if (usuario == null) {
                System.out.println("Usuário não cadastrado!");

            } else {
                System.out.println(usuario);
            }
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja consultar outro usuário (s/n)? ");
            String confirma = scanner.nextLine();

            continuar = confirma.equalsIgnoreCase("s");
        } while (continuar);
    }

    private void listar() {
        UsuarioDao usuarioDao = new UsuarioDao();
        System.out.println("--- LISTA USUÁRIOS ---");

        List<Usuario> usuarios = usuarioDao.findAll();

        if (usuarios != null && !usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        } else {
            System.out.println("Nenhum usuário cadastrado!");
        }
    }

    private void excluir(Scanner scanner) {
        boolean continuar = true;
        UsuarioDao usuarioDao = new UsuarioDao();
        ContaDao contaDao = new ContaDao();
        System.out.println("--- EXCLUI USUÁRIO ---");
        String confirma;

        do {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Informe o ID do usuário que deseja excluir: ");
            int idUsuario = scanner.nextInt();
            Usuario usuario = usuarioDao.findById(idUsuario);

            if (usuario == null) {
                System.out.println("Usuário não cadastrado!");

            } else {
                System.out.println(usuario);

                System.out.print("Confirma a exclusão desse usuário, se já existir uma conta para esse usuário será" +
                        " necessário excluir ela primeiro (s/n)? ");

                confirma = scanner.nextLine();

                if (confirma.equalsIgnoreCase("s")){
                    List<Conta> contas = contaDao.findByIdUsuario(usuario.getId());
                    if (contas == null || contas.isEmpty()) {
                        usuarioDao.delete(usuario);
                    } else {
                        System.out.println("Já existe conta para esse usuário, por favor exclua primeiro!");
                    }
                }
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.print("Deseja excluir outro usuário (s/n)? ");
            confirma = scanner.nextLine();

            continuar = confirma.equalsIgnoreCase("s");
        } while (continuar);
    }

    public void showMenu(Scanner scanner) {
        int escolha;
        do {
            System.out.println("--- MENU USUÁRIOS ---");
            super.showDefaultMenu();

            escolha = super.readOption(scanner, 0, 5);

            switch (escolha) {
                case 1: // INCLUIR
                    incluir(scanner);
                    break;
                case 2: // ALTERAR
                    alterar(scanner);
                    break;
                case 3: // CONSULTAR
                    consultar(scanner);
                    break;
                case 4: // LISTAR
                    listar();
                    break;
                case 5: // EXCLUIR
                    excluir(scanner);
                    break;
            }
        } while (escolha != 0);
    }
}
