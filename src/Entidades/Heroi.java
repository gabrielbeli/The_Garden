package Entidades;
import java.util.Scanner;

import Entidades.Categorias.Categoria;
import Entidades.NPC.NPC;
import Entidades.NPC.NPCInimigo;
import Itens.ArmaPrincipal;
import Itens.Consumivel;
import Itens.MagiaCombate;
import Itens.Pocao;
import java.util.ArrayList;

public abstract class Heroi extends Entidade {

    private int nivel;
    private int ouro;
    private ArmaPrincipal armaPrincipal;
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

    public boolean atacar(NPC inimigo) {
        int dano = this.calcularDano();
        inimigo.receberDano(dano);
        System.out.println(this.getNome() + " ataca " + inimigo.getNome() + " causando " + dano + " de dano!");
        return inimigo.getVidaAtual() <= 0;
    }

    public void usarAtaqueEspecial(NPC inimigo) {
        if (podeUsarAtaqueEspecial()) {
            int danoEspecial = calcularDanoEspecial();
            inimigo.receberDano(danoEspecial);
            System.out.println(this.getNome() + " causou " + danoEspecial + " de dano especial em " + inimigo.getNome());
            this.ataqueEspecialUsado = true;
        } else {
            System.out.println("Você já usou o ataque especial nesta sala.");
        }
    }

    public void resetarAtaqueEspecial() {
        this.ataqueEspecialUsado = false;
    }

    public boolean podeUsarAtaqueEspecial() {
        return !ataqueEspecialUsado;
    }

    public void addAoInventario(Consumivel item) {
        this.inventario.add(item);
    }

    public void usarPocao() {
        if (!inventario.isEmpty()) {
            for (Consumivel item : inventario) {
                if (item instanceof Pocao pocao) {
                    this.setVidaAtual(this.getVidaAtual() + pocao.getVidaCurar());
                    this.setForca(this.getForca() + pocao.getAumentoForca());
                    inventario.remove(item);
                    System.out.println("Você usou " + pocao.getNome() + "!");
                    return;
                }
            }
        }
        System.out.println("Você não tem nenhuma poção no inventário!");
    }

    public void usarMagiaCombate(NPCInimigo inimigo) {
        if (!inventario.isEmpty()) {
            for (Consumivel item : inventario) {
                if (item instanceof MagiaCombate magiaCombate) {
                    inimigo.setVidaAtual(inimigo.getVidaAtual() - magiaCombate.getAtaqueInstantaneo());
                    inventario.remove(item);
                    System.out.println("Você usou " + magiaCombate.getNome() + "!");
                    return;
                }
            }
        }
        System.out.println("Você não tem nenhuma poção no inventário!");
    }

    public void ganharExperiencia(int exp) {
        this.experiencia += exp;
        if (this.experiencia >= 50 * this.nivel) {
            this.nivel++;
            this.experiencia -= 50 * (this.nivel - 1);
            System.out.println("Você subiu para o nível " + this.nivel + "!");
        }
    }

    @Override
    public void mostrarDetalhes() {
        super.mostrarDetalhes();
        System.out.println("Nível: " + nivel);
        System.out.println("Ouro: " + ouro);
        System.out.println("Categoria: " + (getCategoria() != null ? getCategoria().getClass().getSimpleName() : "Não definida"));
        System.out.println("Arma Principal: " + (armaPrincipal != null ? armaPrincipal.getNome() : "Nenhuma"));
    }

    public void receberDano(int dano) {
        this.setVidaAtual(this.getVidaAtual() - dano);
        if (this.getVidaAtual() < 0) this.setVidaAtual(0);
    }

    public int calcularDano() {
        Categoria categoria = getCategoria();

        if (categoria != null) {
            return categoria.calcularDano(this.getForca() + armaPrincipal.getAtaque());
        }

        if (armaPrincipal != null) {
            return categoria.calcularDano(this.getForca() + armaPrincipal.getAtaque());
        }

        return this.getForca();
    }

    public int calcularDanoEspecial() {
        Categoria categoria = getCategoria();

        if (categoria != null) {
                return categoria.calcularDano(this.getForca() + armaPrincipal.getAtaqueEspecial());
        }

        if (armaPrincipal != null) {
                return categoria.calcularDano(this.getForca() + armaPrincipal.getAtaqueEspecial());
        }

        return this.getForca() + 5;
    }

    public void acessarInventario(boolean emCombate, NPCInimigo inimigo) {
        Scanner scanner = new Scanner(System.in);

        // Verifica se o inventário está vazio
        if (inventario.isEmpty()) {
            System.out.println("O seu inventário está vazio.");
            return;
        }

        // Exibe o inventário
        System.out.println("Itens no inventário:");
        for (int i = 0; i < inventario.size(); i++) {
            Consumivel item = inventario.get(i);
            System.out.println((i + 1) + ". " + item.getNome());
        }

        // Escolha de item
        System.out.print("Escolha um item pelo número (0 para cancelar): ");
        int escolha = scanner.nextInt();

        if (escolha == 0) {
            System.out.println("Você optou por não usar nenhum item.");
            return;
        }

        // Verifica se a escolha é válida
        if (escolha < 1 || escolha > inventario.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        Consumivel itemEscolhido = inventario.get(escolha - 1);

        // Verifica o tipo de item escolhido e realiza a ação apropriada
        if (itemEscolhido instanceof Pocao) {
            this.usarPocao();
        } else if (itemEscolhido instanceof MagiaCombate) {
            if (emCombate) {
                this.usarMagiaCombate(inimigo); // Usa a magia de combate se estiver em combate
            } else {
                System.out.println("Magias de combate só podem ser usadas durante o combate.");
            }
        } else {
            System.out.println("Item não utilizável diretamente.");
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

    public ArmaPrincipal getArmaPrincipal() {
        return armaPrincipal;
    }

    public void setArmaPrincipal(ArmaPrincipal armaPrincipal) {
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
}