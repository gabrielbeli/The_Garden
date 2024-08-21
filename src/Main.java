import Jogo.Jogo;

import java.io.FileNotFoundException;

import static MetodosGenericos.Som.tocarSom;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Jogo jogo = new Jogo();

        jogo.criarPersonagem();
        tocarSom("src/Sons/Descent_Gris.wav");
        jogo.iniciarAventura();
    }
}