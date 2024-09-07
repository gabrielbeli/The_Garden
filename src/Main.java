import Jogo.Jogo;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static MetodosGenericos.Som.tocarSom;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        do {
            Jogo jogo = new Jogo();

            jogo.criarPersonagem();
            tocarSom("src/Sons/Descent_Gris.wav");
            jogo.iniciarAventura();

            System.out.print("\n\uD83C\uDF31 Deseja jogar novamente? (1 - Sim / 2 - Não): ");
        } while (scanner.nextInt() == 1);

        System.out.println("\n\uD83D\uDCDC Aventura encerrada. Até a proxima! \uD83D\uDC4B");
    }
}