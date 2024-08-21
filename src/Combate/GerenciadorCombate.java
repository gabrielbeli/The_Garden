package Combate;

import Entidades.Heroi;
import Entidades.NPC.NPCInimigo;
import Entidades.NPC.NPCCompanheiro;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GerenciadorCombate {

    private Random random;

    public GerenciadorCombate() {
        this.random = new Random();
    }

    /**
     * Método para realizar a dinamica de combate entre herois e inimigos
     * @param heroi Avatar criado pelo jogador
     * @param companheiros NPCs configurados pela narrativa que acompanham o heroi
     * @param inimigo NPCs configurados para antagonizar a narrativa do heroi
     * @return Retorna se heroi venceu ou perdeu o combate
     */
    public boolean realizarCombate(Heroi heroi, List<NPCCompanheiro> companheiros, NPCInimigo inimigo) {
        boolean inimigoDerrotado = false;

        while (heroi.getVidaAtual() > 0 && inimigo.getVidaAtual() > 0) {

            // Mostrar status antes de cada turno
            exibirStatusCombate(heroi, companheiros, inimigo);

            // Turno do herói
            turnoHeroi(heroi, inimigo);

            // Verifica se o inimigo foi derrotado
            if (inimigo.getVidaAtual() <= 0) {
                inimigoDerrotado = true;
                break;
            }

            // Turno dos companheiros
            for (NPCCompanheiro companheiro : companheiros) {
                if (companheiro.getVidaAtual() > 0) {
                    turnoCompanheiro(companheiro, inimigo);
                }
            }

            // Verifica se o inimigo foi derrotado após o turno dos companheiros
            if (inimigo.getVidaAtual() <= 0) {
                inimigoDerrotado = true;
                break;
            }

            // Turno do inimigo
            turnoInimigo(inimigo, heroi, companheiros);

            // Verifica se o herói foi derrotado após o turno do inimigo
            if (heroi.getVidaAtual() <= 0) {
                break;
            }
        }

        if (inimigoDerrotado) {
            heroi.ganharExperiencia(50);
            heroi.setOuro(heroi.getOuro() + inimigo.getOuro());
            System.out.println("\n" + heroi.getNome() + " derrotou " + inimigo.getNome() + " e ganhou 50 de experiência e " + inimigo.getOuro() + " de ouro!");
        }

        exibirStatusFinal(heroi, companheiros, inimigo);

        return heroi.getVidaAtual() > 0;
    }

    /**
     * Método utilizado para informar o status de vida dos personagens durante a realização do combate
     * @param heroi Avatar criado pelo jogador
     * @param companheiros NPCs configurados pela narrativa que acompanham o heroi
     * @param inimigo NPCs configurados para antagoniar a narrativa do heroi
     */
    private void exibirStatusCombate(Heroi heroi, List<NPCCompanheiro> companheiros, NPCInimigo inimigo) {
        System.out.println("\n------------------- Status do Combate ------------------\n");
        System.out.println("\uD83C\uDF31 Semente: " + heroi.getNome() + " | ❤\uFE0F\u200D\uD83D\uDD25 Vida: " + heroi.getVidaAtual() + "/" + heroi.getVidaMax() + " | \uD83D\uDCAA Força: " + heroi.getForca());
        System.out.println("\n\uD83D\uDC80 Inimigo: " + inimigo.getNome() + " | ❤\uFE0F\u200D\uD83D\uDD25 Vida: " + inimigo.getVidaAtual() + "/" + inimigo.getVidaMax() + " | \uD83D\uDCAA Força: " + inimigo.getForca());

        if (!companheiros.isEmpty()) {
            System.out.println("\n\uD83E\uDD1D Companheiros:");
            for (NPCCompanheiro companheiro : companheiros) {
                System.out.println("\n"+companheiro.getNome() + " | ❤\uFE0F\u200D\uD83D\uDD25 Vida: " + companheiro.getVidaAtual() + "/" + companheiro.getVidaMax() + " | \uD83D\uDCAA Força: " + companheiro.getForca());
            }
        }

        System.out.println("---------------------------------------------------------\n");
    }

    /**
     * Método utilizado para informar o status final de vida dos personagens após a realização do combate
     * @param heroi Avatar criado pelo jogador
     * @param companheiros NPCs configurados pela narrativa que acompanham o heroi
     * @param inimigo NPCs configurados para antagoniar a narrativa do heroi
     */
    private void exibirStatusFinal(Heroi heroi, List<NPCCompanheiro> companheiros, NPCInimigo inimigo) {
        System.out.println("\n-------------------- Status Final ---------------------\n");
        System.out.println("Herói: " + heroi.getNome() + " | Vida: " + heroi.getVidaAtual() + "/" + heroi.getVidaMax() + " | Ouro: " + heroi.getOuro() + " | Experiência: " + heroi.getExperiencia());

        if (!companheiros.isEmpty()) {
            System.out.println("Companheiros:");
            for (NPCCompanheiro companheiro : companheiros) {
                System.out.println("\n" + companheiro.getNome() + " | Vida: " + companheiro.getVidaAtual() + "/" + companheiro.getVidaMax());
            }
        }

        if (heroi.getVidaAtual() <= 0) {
            System.out.println("O Jardin será extinto! \uD83D\uDE2D");
        } else if (inimigo.getVidaAtual() <= 0) {
            System.out.println("\uD83D\uDCE3 Grande vitória \uD83D\uDCE3");
        }

        System.out.println("--------------------------------------------------------\n");
    }

    /**
     * Método que cofigura o turno do heroi dentro do combate
     * @param heroi Avatar criado eplo jogador
     * @param inimigo NPCs configurados para antagonizar a narrativa do heroi
     */
    private void turnoHeroi(Heroi heroi, NPCInimigo inimigo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("⚔\uFE0F Opções:");
        System.out.println("1. ⚡\uFE0F Ataque Normal");
        System.out.println("2. \uD83D\uDCA5 Ataque Especial");
        System.out.println("3. \uD83C\uDF92 Acessar Iventário");

        System.out.print("\n\uD83D\uDD79\uFE0F Faça sua escolha: ");
        int escolha = scanner.nextInt();

        switch (escolha) {
            case 1:
                heroi.atacar(inimigo);
                break;
            case 2:
                heroi.usarAtaqueEspecial(inimigo);
                break;
            case 3:
                heroi.acessarInventario(true, inimigo);
                break;
            default:
                System.out.println("\uD83D\uDEA8 Opção inválida. Perdeu a vez.");
        }
    }

    /**
     * Método que configura o turno dos NPCs companheiros
     * @param companheiro NPCs configurados pela narrativa que acompanham o heroi
     * @param inimigo NPCs configurados para antagonizar a narrativa do heroi
     */
    private void turnoCompanheiro(NPCCompanheiro companheiro, NPCInimigo inimigo) {
        int dano = companheiro.calcularDano();
        inimigo.receberDano(dano);
        System.out.println("\n⚡\uFE0F " + companheiro.getNome() + " atacou " + inimigo.getNome() + " causando " + dano + " de dano.");
    }

    /**
     * Método que configura o turno dos NPCs inimigos
     * @param inimigo NPCs configurados para antagonizar a narrativa do heroi
     * @param heroi Avatar criado pelo jogador
     * @param companheiros NPCs configurados pela narrativa que acompanham o heroi
     */
    private void turnoInimigo(NPCInimigo inimigo, Heroi heroi, List<NPCCompanheiro> companheiros) {
        boolean temCompanheiro = !companheiros.isEmpty();
        int forcaAjustada = inimigo.calcularForca(heroi, temCompanheiro);

        if (random.nextBoolean() || companheiros.isEmpty()) {
            heroi.receberDano(forcaAjustada);
            System.out.println("\n\uD83E\uDDA0 " + inimigo.getNome() + " atacou " + heroi.getNome() + " causando " + forcaAjustada + " de dano.");
        } else {
            NPCCompanheiro alvo = companheiros.get(random.nextInt(companheiros.size()));
            alvo.receberDano(forcaAjustada);
            System.out.println("\n\uD83E\uDDA0 " + inimigo.getNome() + " atacou " + alvo.getNome() + " causando " + forcaAjustada + " de dano.");
        }
    }
}
