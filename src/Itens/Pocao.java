package Itens;

public class Pocao extends Consumivel {
    private int vidaCurar;
    private int aumentoForca;

    public Pocao(String nome, int precoOuro, int vidaCurar, int aumentoForca) {
        super(nome, precoOuro);
        this.vidaCurar = vidaCurar;
        this.aumentoForca = aumentoForca;
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("\uD83E\uDDEA Poção: " + getNome() + " | Preço: " + getPrecoOuro() + " | Vida + : " + vidaCurar + " | Força + : " + aumentoForca);
        System.out.println("✅ Pode ser usado por: " + String.join(", ", getHeroisPermitidos()));
    }

    public int getVidaCurar() {
        return vidaCurar;
    }

    public int getAumentoForca() {
        return aumentoForca;
    }
}
