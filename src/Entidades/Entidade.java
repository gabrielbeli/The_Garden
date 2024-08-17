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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public void mostrarDetalhes() {
        System.out.println("Nome: " + nome);
        System.out.println("Vida: " + vidaAtual + "/" + vidaMax);
        System.out.println("Força: " + forca);
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
}