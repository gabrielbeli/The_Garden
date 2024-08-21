package Itens;

public class MagiaCombate extends Consumivel {
    private int ataqueInstantaneo;

    public MagiaCombate(String nome, int precoOuro, int ataqueInstantaneo) {
        super(nome, precoOuro);
        this.ataqueInstantaneo = ataqueInstantaneo;
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("\uD83D\uDD2E Magia Combate: " + getNome() + " | Preço: " + getPrecoOuro() + " | Ataque: " + ataqueInstantaneo);
        System.out.println("✅ Pode ser usado por: " + String.join(", ", getHeroisPermitidos()));
    }

    public int getAtaqueInstantaneo() {
        return ataqueInstantaneo;
    }
}
