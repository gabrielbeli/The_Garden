package Entidades.NPC;

import Entidades.Entidade;

public class NPC extends Entidade {
    private int ouro;

    public NPC(String nome, int vidaMax, int forca, int ouro) {
        super(nome, vidaMax, forca);
        this.ouro = ouro;
    }

    @Override
    public void mostrarDetalhes() {
        super.mostrarDetalhes();
        System.out.println("\uD83E\uDE99 Ouro: " + ouro);
    }

    /**
     * Método que configura o recebimento de dano dos personagens
     * @param dano
     */
    public void receberDano(int dano) {
        this.setVidaAtual(this.getVidaAtual() - dano);
        if (this.getVidaAtual() < 0) this.setVidaAtual(0);
    }

    /**
     * Metódo que calcula o dano que os personagens cusam em combate
     * @return Retorna o valor do dano causado
     */
    public int calcularDano() {
        return this.getForca();
    }

    public int getOuro() {
        return this.ouro;
    }
}
