package MetodosGenericos;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Som {
    private static Clip clip;

    /**
     * Método usado para tocar a trilha sonora
     *
     * @param caminhoDoArquivo gerencia o local que se enontra o arquivo de som
     */
    public static void tocarSom(String caminhoDoArquivo) {
        try {
            File arquivoSom = new File(caminhoDoArquivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoSom);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar som: " + e.getMessage());
        }
    }

    /**
     * Método usado para parar o looping da trilha e possibilitar novas trilhas.
     */
    public static void pararSom() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}