package Itens;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemHeroi {
    private String nome;
    private int precoOuro;
    private List<String> heroisPermitidos;

    public ItemHeroi(String nome, int precoOuro) {
        this.nome = nome;
        this.precoOuro = precoOuro;
        this.heroisPermitidos = new ArrayList<>();
    }

    public void addHeroiPermitido(String tipoHeroi) {
        heroisPermitidos.add(tipoHeroi);
    }

    public boolean podeSerUsadoPor(String tipoHeroi) {
        return heroisPermitidos.contains(tipoHeroi);
    }

    public abstract void mostrarDetalhes();

    public String getNome() {
        return nome;
    }

    public int getPrecoOuro() {
        return precoOuro;
    }

    public List<String> getHeroisPermitidos() {
        return heroisPermitidos;
    }
}