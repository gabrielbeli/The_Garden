package Entidades;

import Entidades.Categorias.Categoria;

public abstract class Entidade {

    private String nome;
    private int vidaMax;
    private int vidaAtual;
    private int forca;
    private Categoria categoria;

    public Entidade(String nome, int vidaMax, int forca) {
        this.nome = nome;
        this.vidaMax = vidaMax;
        this.vidaAtual = vidaMax;
        this.forca = forca;
        this.categoria = null;
    }

    /**
     * Método utilizado para mostrar informações basicas do personagem, nome, vida e força...
     */
    public void mostrarDetalhes() {
        System.out.println("\uD83D\uDFE2 Nome: " + nome);
        System.out.println("❤\uFE0F\u200D\uD83D\uDD25 Vida: " + vidaAtual + "/" + vidaMax);
        System.out.println("\uD83D\uDCAA Força: " + forca);
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = Math.min(vidaAtual, this.vidaMax);
    }

    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getForca() {
        return forca;
    }

    public String getNome() {
        return nome;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }
}