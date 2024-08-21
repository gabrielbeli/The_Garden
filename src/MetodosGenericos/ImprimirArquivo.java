package MetodosGenericos;
import Combate.GerenciadorCombate;
import Entidades.Heroi;
import Jogo.GerenciadorSalas;
import Jogo.Sala;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ImprimirArquivo {

    private Heroi heroi;
    private GerenciadorSalas gerenciadorSalas;
    private GerenciadorCombate gerenciadorCombate;
    private Sala salaAtual;

    public ImprimirArquivo(Heroi heroi, GerenciadorSalas gerenciadorSalas, GerenciadorCombate gerenciadorCombate) {
        this.heroi = heroi;
        this.gerenciadorSalas = gerenciadorSalas;
        this.gerenciadorCombate = gerenciadorCombate;
        this.salaAtual = gerenciadorSalas.getSalaAtual();
    }

    public void imprimirNarrativa(String caminho) throws FileNotFoundException {
        Map<String, String> substituicoes = new HashMap<>();

        // Adiciona as substituições ao mapa
        substituicoes.put("heroi.getNome()", heroi.getNome());
        if (!salaAtual.getNPCsComuns().isEmpty()) {
            substituicoes.put("npc.getNome()", salaAtual.getNPCsComuns().get(0).getNome());
        }
        if (!salaAtual.getCompanheiros().isEmpty()) {
            substituicoes.put("primeiroCompanheiro.getNome()", salaAtual.getCompanheiros().get(0).getNome());
        }
        if (salaAtual.getCompanheiros().size() > 1) {
            substituicoes.put("segundoCompanheiro.getNome()", salaAtual.getCompanheiros().get(1).getNome());
        }
        if (salaAtual.getCompanheiros().size() > 2) {
            substituicoes.put("terceiroCompanheiro.getNome()", salaAtual.getCompanheiros().get(2).getNome());
        }
        if (salaAtual.getVendedor() != null) {
            substituicoes.put("vendedor.getNome()", salaAtual.getVendedor().getNome());
        }
        if (salaAtual.getInimigos().size() > 0) {
            substituicoes.put("inimigo.getNome()", salaAtual.getInimigos().get(0).getNome());
        }
        if (salaAtual.getInimigos().size() > 1) {
            substituicoes.put("segundoInimigo.getNome()", salaAtual.getInimigos().get(1).getNome());
        }

        Scanner fileScanner = new Scanner(new File(caminho));
        StringBuilder paragrafo = new StringBuilder();

        while (fileScanner.hasNextLine()) {
            String linha = fileScanner.nextLine();

            // Substitui as chaves pelo valor correspondente
            for (Map.Entry<String, String> entrada : substituicoes.entrySet()) {
                linha = linha.replace(entrada.getKey(), entrada.getValue());
            }

            // Adiciona a linha ao parágrafo
            paragrafo.append(linha).append("\n");

            // Se o parágrafo termina com "@", imprime-o e espera o utilizador pressionar Enter
            if (linha.contains("@")) {
                String paragrafoFormatado = paragrafo.toString().replace("@", "");
                System.out.println(paragrafoFormatado.trim());
                esperarEnter();
                paragrafo.setLength(0); // Limpa o StringBuilder para o próximo parágrafo
            }
        }

        // Caso o arquivo não termine com "@", mas ainda tenha texto a ser impresso
        if (!paragrafo.isEmpty()) {
            System.out.println(paragrafo.toString().trim());
        }

        fileScanner.close();
    }

    public static void esperarEnter() {
        System.out.println("\n------------------------------------------------------------------------------------------------------------- Continuar(aperte enter)");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
