package Entidades;

import Entidades.NPC.NPC;

public class HeroiBase extends Heroi {

    public HeroiBase(String nome, int vidaMax, int forca) {
        super(nome, vidaMax, forca);
    }

    /**
     * Metódo que subscreve e implementa a função de ataque do heroi
     * @param inimigo NPCs configurados que antagonizam a narrativa
     * @return Retorna os valores de dano que o heroi causou no inimigo
     */
    @Override
    public boolean atacar(NPC inimigo) {
        int dano = this.calcularDano();
        inimigo.receberDano(dano);
        System.out.println(this.getNome() + " ataca " + inimigo.getNome() + " causando " + dano + " de dano!");
        return inimigo.getVidaAtual() <= 0;
    }

    @Override
    public int getForca() {
        return super.getForca();
    }

}
