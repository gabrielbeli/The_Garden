package Entidades.NPC;

import Entidades.Heroi;

public class NPCInimigo extends NPC {

    public NPCInimigo(String nome, int vidaMax, int forca, int ouro) {
        super(nome, vidaMax, forca, ouro);
    }

    /**
     * Método que calcula a força do inimigo com contexto individual ou acompanhado
     * @param heroi Avatar criado pelo jogador
     * @param temCompanheiro Verificação se o heroi está sozinho ou acompanhado
     * @return Retorna a força base do inimigo
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
     * @param dano Valor de dano que o heroi ou companheiros causaram
     */
    @Override
    public void receberDano(int dano) {
        this.setVidaAtual(this.getVidaAtual() - dano);
        if (this.getVidaAtual() < 0) this.setVidaAtual(0);
    }

    /**
     * Metodo que subcreve o calculo de dano com base na força
     * @return Valor da força do inimigo
     */
    @Override
    public int calcularDano() {
        return this.getForca();
    }

}
