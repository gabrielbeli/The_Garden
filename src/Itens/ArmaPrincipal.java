package Itens;

public class ArmaPrincipal extends ItemHeroi {
    private int ataque;
    private int ataqueEspecial;

    public ArmaPrincipal(String nome, int precoOuro, int ataque, int ataqueEspecial) {
        super(nome, precoOuro);
        this.ataque = ataque;
        this.ataqueEspecial = ataqueEspecial;
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("Arma: " + getNome() + " | Preço: " + getPrecoOuro() + " | Ataque: " + ataque + " | Ataque Especial: " + ataqueEspecial);
        System.out.println("Pode ser usado por: " + String.join(", ", getHeroisPermitidos()));
    }

    public int getAtaque() {
        return ataque;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }
}