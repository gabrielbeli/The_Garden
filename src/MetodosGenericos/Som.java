package MetodosGenericos;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class Som {

    public static void tocarSom(String caminhoDoArquivo) {
        try {
            // Carrega o arquivo de som
            File arquivoSom = new File(caminhoDoArquivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoSom);

            // Obtém o clip de som
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Toca o som
            //clip.start();

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Espera o som terminar de tocar
            //clip.drain();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar som: " + e.getMessage());
        }
    }
}

