package Entidades;

import Entidades.NPC.NPC;

public class HeroiBase extends Heroi {

    public HeroiBase(String nome, int vidaMax, int forca) {
        super(nome, vidaMax, forca);
    }

    @Override
    public boolean atacar(NPC inimigo) {
        int dano = this.calcularDano();
        inimigo.receberDano(dano);
        System.out.println(this.getNome() + " ataca " + inimigo.getNome() + " causando " + dano + " de dano!");
        return inimigo.getVidaAtual() <= 0;
    }
}
