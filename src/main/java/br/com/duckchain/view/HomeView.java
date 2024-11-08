package br.com.duckchain.view;

import java.util.Scanner;

public class HomeView extends BaseView {

    public void showMenu() {
        int escolha = 0;

        System.out.println("Bem vindo!");
        Scanner scanner = new Scanner(System.in);

        CadastroView cadastroView = new CadastroView();
        LoginView loginView = new LoginView();

        do {
            try {
                System.out.println("--- Menu Principal ---");
                System.out.println();
                System.out.println("1 - Cadastros");
                System.out.println("2 - Login");
                System.out.println("0 - Sair");
                System.out.print("Informe uma das opções acima: ");

                escolha = super.readOption(scanner, 0, 2);

                switch (escolha) {
                    case 1:
                        cadastroView.showMenu(scanner);
                        break;
                    case 2:
                        loginView.login(scanner);
                        break;
                }

            } catch (Exception e) {
                System.out.println("Um erro acontenceu no sistema!");
                System.out.println("Erro: " + e.getMessage());
                System.out.println("O sistema será reinicializado!");
                escolha = -1;
            }
        } while (escolha != 0);
        System.out.println("Encerrando o programa...");

        scanner.close();

        System.out.println("Volte sempre");
    }
}

