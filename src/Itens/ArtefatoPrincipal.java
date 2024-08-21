package Itens;

public class ArtefatoPrincipal extends ItemHeroi {
    private int ataque;
    private int ataqueEspecial;

    public ArtefatoPrincipal(String nome, int precoOuro, int ataque, int ataqueEspecial) {
        super(nome, precoOuro);
        this.ataque = ataque;
        this.ataqueEspecial = ataqueEspecial;
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("\uD83D\uDD31 Artefato: " + getNome() + " | Preço: " + getPrecoOuro() + " | Ataque: " + ataque + " | Ataque Especial: " + ataqueEspecial);
        System.out.println("✅ Pode ser usado por: " + String.join(", ", getHeroisPermitidos()));
    }

    public int getAtaque() {
        return ataque;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }
}