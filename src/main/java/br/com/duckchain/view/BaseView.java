package br.com.duckchain.view;

import java.util.Scanner;

public abstract class BaseView {
    protected void showDefaultMenu () {
        System.out.println();
        System.out.println("1 - Incluir");
        System.out.println("2 - Alterar");
        System.out.println("3 - Consultar");
        System.out.println("4 - Listar");
        System.out.println("5 - Excluir");
        System.out.println("0 - Voltar");
        System.out.print("Informe uma das opções acima:");
    }

    protected int readOption(Scanner scanner, int minOption, int maxOption) {
        boolean ok = false;
        int option = 0;

        do {
            try {
                option = scanner.nextInt();
                if (option < minOption || option > maxOption) {
                    throw new Exception();
                }
                ok = true;
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                System.out.printf("Informe de %d a %2d%n", minOption, maxOption);
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
        } while (!ok);

        return  option;
    }
}

