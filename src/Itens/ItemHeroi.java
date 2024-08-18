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

    /** Método que configura qual categoria pode utilizar o item
     * @param tipoHeroi Categoria do heroi permitido
     */
    public void addHeroiPermitido(String tipoHeroi) {
        heroisPermitidos.add(tipoHeroi);
    }

    /**
     * Método que verifica se uma categoria pode utilizar determinado item
     * @param tipoHeroi categoria do heroi
     * @return Retorna a validação/permição da categoria
     */
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