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
        System.out.println("Ouro: " + ouro);
    }

    public void receberDano(int dano) {
        this.setVidaAtual(this.getVidaAtual() - dano);
        if (this.getVidaAtual() < 0) this.setVidaAtual(0);
    }

    public int calcularDano() {
        return this.getForca();
    }

    public int getOuro() {
        return this.ouro;
    }
}
