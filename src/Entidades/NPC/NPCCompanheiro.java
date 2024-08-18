package Entidades.NPC;

import Entidades.Categorias.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NPCCompanheiro extends NPC {
    private static final List<Categoria> CATEGORIAS = Arrays.asList(new Guerreiro(), new Druida(), new Bardo(), new Ranger());
    private static final Random random = new Random();

    public NPCCompanheiro(String nome, int vidaMax, int forca, int ouro) {
        super(nome, vidaMax, forca, ouro);
        this.atribuirCategoriaAleatoria();
    }

    /**
     * Método que configura de forma alletória uma categoria para os NPCs companheiros diferindo da do heroi.
     */
    private void atribuirCategoriaAleatoria() {
        Categoria categoriaAleatoria = CATEGORIAS.get(random.nextInt(CATEGORIAS.size()));
        this.setCategoria(categoriaAleatoria);
    }

    @Override
    public int getForca() {
        return super.getForca();
    }

    @Override
    public int calcularDano() {
        Categoria categoria = getCategoria();
        if (categoria != null) {
            return categoria.calcularDano(this.getForca());
        } else {
            return this.getForca();
        }
    }
}
