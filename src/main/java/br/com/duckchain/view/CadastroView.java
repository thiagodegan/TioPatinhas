package br.com.duckchain.view;

import java.util.Scanner;

public class CadastroView extends BaseView {
    public void showMenu(Scanner scanner) {
        int escolha;
        UsuarioView userView = new UsuarioView();
        TipoMoedaView tipoMoedaView = new TipoMoedaView();
        do {
            System.out.println("--- MENU CADASTROS ---");
            System.out.println();
            System.out.println("1 - Usuários");
            System.out.println("2 - Tipo de Moedas");
            System.out.println("0 - Voltar");
            System.out.print("Informe uma das opções acima:");

            escolha = super.readOption(scanner, 0, 2);

            switch (escolha) {
                case 1:
                    userView.showMenu(scanner);
                    break;
                case 2:
                    tipoMoedaView.showMenu(scanner);
                    break;
            }
        } while (escolha!=0);
    }
}
