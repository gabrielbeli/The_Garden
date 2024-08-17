package Entidades.NPC;

import Entidades.Heroi;

public class NPCInimigo extends NPC {

    public NPCInimigo(String nome, int vidaMax, int forca, int ouro) {
        super(nome, vidaMax, forca, ouro);
    }

    /**
     * metodo que calcula a força do inimigo com contexto individual ou acompanhado
     * @param heroi
     * @param temCompanheiro
     * @return força base do inimigo
     */
    public int calcularForca(Heroi heroi, boolean temCompanheiro) {
        int forcaBase = heroi.getForca();
        if (temCompanheiro) {
            return (int) (forcaBase * (1.5));
        } else {
            return forcaBase;
        }
    }

    /**
     * metodo que subscreve o recebimento de dano
     * @param dano
     */
    @Override
    public void receberDano(int dano) {
        this.setVidaAtual(this.getVidaAtual() - dano);
        if (this.getVidaAtual() < 0) this.setVidaAtual(0);
    }

    /**
     * metodo que subcreve o calculo de dano com base na força
     * @return força
     */
    @Override
    public int calcularDano() {
        return this.getForca();
    }

}
