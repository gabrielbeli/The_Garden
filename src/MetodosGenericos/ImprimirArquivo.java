package MetodosGenericos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImprimirArquivo {

    /**
     * Método: Usado para impressão do conteudo do arquivo em tela
     * @param caminho para o arquivo
     * @throws FileNotFoundException Excessão quando não se encontra o caminho especificado
     */
    public static void imprimirAquivo(String caminho) throws FileNotFoundException {

        Scanner fileScanner = new Scanner(new File(caminho));

        String linha;

        while (fileScanner.hasNextLine()) {
            linha = fileScanner.nextLine();
            System.out.println("     "+linha );
        }

        System.out.println();

    }
}
