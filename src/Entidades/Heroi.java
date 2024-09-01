package Entidades;
import java.util.Scanner;

import Entidades.Categorias.Categoria;
import Entidades.NPC.NPC;
import Entidades.NPC.NPCInimigo;
import Itens.ArtefatoPrincipal;
import Itens.Consumivel;
import Itens.MagiaCombate;
import Itens.Pocao;
import java.util.ArrayList;

public abstract class Heroi extends Entidade {

    private int nivel;
    private int ouro;
    private ArtefatoPrincipal armaPrincipal;
    private ArrayList<Consumivel> inventario;
    private int experiencia;
    private boolean ataqueEspecialUsado;
    private Categoria categoria;
    private  boolean categoriaDefinida;

    public Heroi(String nome, int vidaMax, int forca) {
        super(nome, vidaMax, forca);
        this.nivel = 1;
        this.ouro = 0;
        this.armaPrincipal = null;
        this.inventario = new ArrayList<>();
        this.ataqueEspecialUsado = false;
        this.categoria = null;
        this.categoriaDefinida = false;
    }

    /**
     * Metódo que implementa a função de ataque do heroi
     * @param inimigo NPCs configurados que antagonizam a narrativa
     * @return Retorna os valores de dano que o heroi causou no inimigo
     */
    public boolean atacar(NPC inimigo) {
        int dano = this.calcularDano();
        inimigo.receberDano(dano);
        System.out.println("\n⚡\uFE0F " + this.getNome() + " ataca " + inimigo.getNome() + " causando " + dano + " de dano!");
        return inimigo.getVidaAtual() <= 0;
    }

    /**
     * Metódo que implementa a função de ataque especial do heroi
     * @param inimigo NPCs configurados que antagonizam a narrativa
     */
    public void usarAtaqueEspecial(NPC inimigo) {
        if (podeUsarAtaqueEspecial()) {
            int danoEspecial = calcularDanoEspecial();
            inimigo.receberDano(danoEspecial);
            System.out.println("\n\uD83D\uDCA5 " +this.getNome() + " causou " + danoEspecial + " de dano especial em " + inimigo.getNome());
            this.ataqueEspecialUsado = true;
        } else {
            System.out.println("⛔ Você já usou o ataque especial nesta sala.");
        }
    }

    /**
     * Método auxiliar usado na limitação de ataque especial
     */
    public void resetarAtaqueEspecial() {
        this.ataqueEspecialUsado = false;
    }

    /**
     * Método auxiliar usado na limitação de ataque especial no combate
     * @return Retorna o controle do ataque especial no combate
     */
    public boolean podeUsarAtaqueEspecial() {
        return !ataqueEspecialUsado;
    }

    /**
     * Método usado para adicionar items ao inventario do heroi
     * @param item Itens configurados para utilização no jogo
     */
    public void addAoInventario(Consumivel item) {
        this.inventario.add(item);
    }

    /**
     * Método usado para consumir as poções que o heroi tem no inventario
     */
    public void usarPocao() {
        if (!inventario.isEmpty()) {
            for (Consumivel item : inventario) {
                if (item instanceof Pocao pocao) {
                    this.setVidaAtual(this.getVidaAtual() + pocao.getVidaCurar());
                    this.setForca(this.getForca() + pocao.getAumentoForca());
                    inventario.remove(item);
                    System.out.println("Você usou " + pocao.getNome() + "! \uD83E\uDDEA");
                    return;
                }
            }
        }
        System.out.println("⛔ Você não tem nenhuma poção no inventário!");
    }

    /**
     * Método para usar o item magia durante o combate
     * @param inimigo
     */
    public void usarMagiaCombate(NPCInimigo inimigo) {
        if (!inventario.isEmpty()) {
            for (Consumivel item : inventario) {
                if (item instanceof MagiaCombate magiaCombate) {
                    inimigo.setVidaAtual(inimigo.getVidaAtual() - magiaCombate.getAtaqueInstantaneo());
                    inventario.remove(item);
                    System.out.println("Você usou " + magiaCombate.getNome() + "! \uD83E\uDE94");
                    return;
                }
            }
        }
        System.out.println("⛔ Você não tem nenhuma poção no inventário!");
    }

    /**
     * Método que configura o ganho de experiência do heroi de acordo com seu desempenho nos combates
     * @param exp Valor da experiência do heroi
     */
    public void ganharExperiencia(int exp) {
        this.experiencia += exp;
        if (this.experiencia >= 50 * this.nivel) {
            this.nivel++;
            this.experiencia -= 50 * (this.nivel - 1);
            System.out.println("\n\uD83D\uDD3A Você subiu para o nível " + this.nivel + "! \uD83D\uDD3A");
        }
    }

    @Override
    public void mostrarDetalhes() {
        super.mostrarDetalhes();
        System.out.println("\uD83C\uDF31 Nível: " + nivel);
        System.out.println("\uD83E\uDE99 Ouro: " + ouro);
        System.out.println("\uD83E\uDE9E Categoria: " + (getCategoria() != null ? getCategoria().getClass().getSimpleName() : "Não definida"));
        System.out.println("\uD83D\uDD31 Artefato Principal: " + (armaPrincipal != null ? armaPrincipal.getNome() : "Nenhuma"));
    }

    /**
     * Método que configura o recebimento de dano que o heroi sofre no combate quando atacado
     * @param dano Valor que o inimigo inferiu no heroi
     */
    public void receberDano(int dano) {
        this.setVidaAtual(this.getVidaAtual() - dano);
        if (this.getVidaAtual() < 0) this.setVidaAtual(0);
    }

    /**
     * Metódo que configura o calculo de dano que o heroi causa no inimigo
     * @return Retorna o valor do dano causado no inimigo conforme a categoria e arma principal do heroi.
     */
    public int calcularDano() {
        Categoria categoria = getCategoria();

        if (armaPrincipal != null) {
            return categoria.calcularDano(this.getForca()) + armaPrincipal.getAtaque();
        }
        return this.getForca();
    }

    /**
     * Método que configura o calculo de dano especial que o heroi causa no inimigo
     * @return Retorna o valor do dano especial causado no inimigo de acordo com a categoria e arma principal
     */
    public int calcularDanoEspecial() {
        Categoria categoria = getCategoria();

        if (armaPrincipal != null) {
                return categoria.calcularDano(this.getForca()) + armaPrincipal.getAtaqueEspecial();
        }

        return this.getForca() + 5;
    }

    /**
     * Método que gerencia o acesso do heroi ao seu inventário
     * @param emCombate Valor que defini se o inventario está sendo acessado dentro do combate
     * @param inimigo NPCs configurados que antagonizam a narrativa
     */
    public void acessarInventario(boolean emCombate, NPCInimigo inimigo) {
        Scanner scanner = new Scanner(System.in);

        // Verificando se o inventário está vazio
        if (inventario.isEmpty()) {
            System.out.println("\nO seu inventário está vazio. \uD83D\uDEA8");
            return;
        }

        // Exibo o inventário
        System.out.println("\uD83C\uDF92 Itens no inventário:\n");
        for (int i = 0; i < inventario.size(); i++) {
            Consumivel item = inventario.get(i);
            System.out.println((i + 1) + ". \uD83D\uDD38 " + item.getNome());
        }

        // Escolha de item
        System.out.print("\n\uD83D\uDD79\uFE0F Escolha um item pelo número (0 para cancelar): ");
        int escolha = scanner.nextInt();

        if (escolha == 0) {
            System.out.println("\n\uD83D\uDED1 Você optou por não usar nenhum item.\n");
            return;
        }

        // Verifico se a escolha é válida
        if (escolha < 1 || escolha > inventario.size()) {
            System.out.println("\n⛔ Escolha inválida.");
            return;
        }

        Consumivel itemEscolhido = inventario.get(escolha - 1);

        // Verifico o tipo de item escolhido e realizo a ação apropriada
        if (itemEscolhido instanceof Pocao) {
            this.usarPocao();
        } else if (itemEscolhido instanceof MagiaCombate) {
            if (emCombate) {
                this.usarMagiaCombate(inimigo);
            } else {
                System.out.println("\nMagias de combate só podem ser usadas durante o combate. \uD83D\uDEA8");
            }
        } else {
            System.out.println("\n⛔ Item não utilizável diretamente.");
        }
    }

    public int getExperiencia() {
        return this.experiencia;
    }

    public int getOuro() {
        return ouro;
    }

    public void setOuro(int ouro) {
        this.ouro = Math.max(0, ouro);
    }

    public ArtefatoPrincipal getArmaPrincipal() {
        return armaPrincipal;
    }

    public void setArmaPrincipal(ArtefatoPrincipal armaPrincipal) {
        this.armaPrincipal = armaPrincipal;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.categoriaDefinida = true;
    }

    public boolean isCategoriaDefinida() {
        return categoriaDefinida;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ArrayList<Consumivel> getInventario() {
        return inventario;
    }
}