package Jogo;

import Entidades.NPC.NPC;
import Entidades.NPC.NPCInimigo;
import Entidades.NPC.NPCCompanheiro;
import Entidades.Vendedor;
import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String nome;
    private TipoSala tipo;
    private String descricao;
    private ArrayList<Sala> salasProximas;
    private ArrayList<NPCInimigo> inimigos;
    private ArrayList<NPCCompanheiro> companheiros;
    private ArrayList<NPC> npcsComuns;
    private Vendedor vendedor;
    private String[] opcoes;
    private boolean explorada;

    public Sala(String nome, TipoSala tipo, String descricao) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.salasProximas = new ArrayList<>();
        this.inimigos = new ArrayList<>();
        this.companheiros = new ArrayList<>();
        this.npcsComuns = new ArrayList<>();
        this.opcoes = new String[]{};
        this.vendedor = null;
        this.explorada = false;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }

    /**
     * Metódo para criar conexão entre salas.
     * @param salaProxima Nome das salas a serem conectdas
     */
    public void addSalaProxima(Sala salaProxima) {
        this.salasProximas.add(salaProxima);
    }

    /**
     * Método para adicionar NPCs comuns a sala
     * @param npcs Lista dos npcs que serão adicionados a sala
     */
    public void addNPCsComuns(List<NPC> npcs) {
        this.npcsComuns.addAll(npcs);
    }

    /**
     * Método para adicionar os NPCs que serão companheiros de batalha a sala
     * @param companheiros Lista dos npcs a serem adicionados a sala
     */
    public void addCompanheiros(List<NPCCompanheiro> companheiros) {
        this.companheiros.addAll(companheiros);
    }

    /**
     * Método para adicionar inimigos a sala
     * @param inimigos lista de inimigos a serem adicionados a sala
     */
    public void addInimigos(List<NPCInimigo> inimigos) {
        this.inimigos.addAll(inimigos);
    }

    /**
     * Método para verificar as informações da sala como: companheiros, inimigos, vendedor;
     */
    public void mostrarDetalhes() {
        System.out.println("\uD83D\uDEA9 Sala: " + nome);
        System.out.println(descricao);

        if (!inimigos.isEmpty()) {
            System.out.println("\uD83D\uDC80 Inimigos nesta sala:");
            for (NPCInimigo inimigo : inimigos) {
                System.out.println("- " + inimigo.getNome());
            }
        }

        if (!companheiros.isEmpty()) {
            System.out.println("\uD83E\uDD1D Companheiros nesta sala:");
            for (NPCCompanheiro companheiro : companheiros) {
                System.out.println("- " + companheiro.getNome());
            }
        }

        if (!npcsComuns.isEmpty()) {
            System.out.println("\uD83E\uDEB4 Outros nesta sala:");
            for (NPC npc : npcsComuns) {
                System.out.println("- " + npc.getNome());
            }
        }

        if (vendedor != null) {
            System.out.println("\uD83C\uDF35 Vendedor presente na sala: " + vendedor.getNome());
        }
    }

    /**
     * Método que auxilia a mostrar as opções da sala
     */
    public void mostrarOpcoes() {
        System.out.println("\uD83E\uDEB6 Opções:\n");
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((i + 1) + ". " + opcoes[i]);
        }
    }

    /**
     * Metódo para permitir ou impedir que o jogador explore novamente a sala;
     * @return Retorna uma flag para identificar se a sala ja foi.
     */
    public boolean isExplorada() {
        return explorada;
    }

    public void removerNPCComum(NPC npc) {
        this.npcsComuns.remove(npc);
    }

    public void removerCompanheiro(NPCCompanheiro companheiro) {
        this.companheiros.remove(companheiro);
    }

    public void removerInimigo(NPCInimigo inimigo) {
        this.inimigos.remove(inimigo);
    }

    public void setOpcoes(String[] opcoes) {
        this.opcoes = opcoes;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return this.nome;
    }

    public TipoSala getTipo() {
        return this.tipo;
    }

    public ArrayList<Sala> getSalasProximas() {
        return salasProximas;
    }

    public ArrayList<NPCInimigo> getInimigos() {
        return this.inimigos;
    }

    public ArrayList<NPCCompanheiro> getCompanheiros() {
        return this.companheiros;
    }

    public ArrayList<NPC> getNPCsComuns() {
        return this.npcsComuns;
    }

    public void setExplorada(boolean explorada) {
        this.explorada = explorada;
    }
}
