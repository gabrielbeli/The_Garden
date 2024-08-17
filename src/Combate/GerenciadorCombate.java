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
            System.out.println(heroi.getNome() + " derrotou " + inimigo.getNome() + " e ganhou 50 de experiência e " + inimigo.getOuro() + " de ouro!");
        }

        exibirStatusFinal(heroi, companheiros, inimigo);

        return heroi.getVidaAtual() > 0;
    }

    private void exibirStatusCombate(Heroi heroi, List<NPCCompanheiro> companheiros, NPCInimigo inimigo) {
        System.out.println("\n--- Status do Combate ---");
        System.out.println("Herói: " + heroi.getNome() + " | Vida: " + heroi.getVidaAtual() + "/" + heroi.getVidaMax());
        System.out.println("Inimigo: " + inimigo.getNome() + " | Vida: " + inimigo.getVidaAtual() + "/" + inimigo.getVidaMax());

        if (!companheiros.isEmpty()) {
            System.out.println("Companheiros:");
            for (NPCCompanheiro companheiro : companheiros) {
                System.out.println(companheiro.getNome() + " | Vida: " + companheiro.getVidaAtual() + "/" + companheiro.getVidaMax());
            }
        }

        System.out.println("--------------------------\n");
    }

    private void exibirStatusFinal(Heroi heroi, List<NPCCompanheiro> companheiros, NPCInimigo inimigo) {
        System.out.println("\n--- Status Final do Combate ---");
        System.out.println("Herói: " + heroi.getNome() + " | Vida: " + heroi.getVidaAtual() + "/" + heroi.getVidaMax() + " | Ouro: " + heroi.getOuro() + " | Experiência: " + heroi.getExperiencia());

        if (!companheiros.isEmpty()) {
            System.out.println("Companheiros:");
            for (NPCCompanheiro companheiro : companheiros) {
                System.out.println(companheiro.getNome() + " | Vida: " + companheiro.getVidaAtual() + "/" + companheiro.getVidaMax());
            }
        }

        if (heroi.getVidaAtual() <= 0) {
            System.out.println("O Jardin foi extinto");
        } else if (inimigo.getVidaAtual() <= 0) {
            System.out.println("Grande vitória");
        }

        System.out.println("-------------------------------\n");
    }

    private void turnoHeroi(Heroi heroi, NPCInimigo inimigo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha seu ataque:");
        System.out.println("1. Ataque Normal");
        System.out.println("2. Ataque Especial");
        System.out.println("3. Acessar Iventário");

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
                System.out.println("Opção inválida. Perdeu a vez.");
        }
    }

    private void turnoCompanheiro(NPCCompanheiro companheiro, NPCInimigo inimigo) {
        int dano = companheiro.calcularDano();
        inimigo.receberDano(dano);
        System.out.println(companheiro.getNome() + " atacou " + inimigo.getNome() + " causando " + dano + " de dano.");
    }

    private void turnoInimigo(NPCInimigo inimigo, Heroi heroi, List<NPCCompanheiro> companheiros) {
        boolean temCompanheiro = !companheiros.isEmpty();
        int forcaAjustada = inimigo.calcularForca(heroi, temCompanheiro);

        if (random.nextBoolean() || companheiros.isEmpty()) {
            heroi.receberDano(forcaAjustada);
            System.out.println(inimigo.getNome() + " atacou " + heroi.getNome() + " causando " + forcaAjustada + " de dano.");
        } else {
            NPCCompanheiro alvo = companheiros.get(random.nextInt(companheiros.size()));
            alvo.receberDano(forcaAjustada);
            System.out.println(inimigo.getNome() + " atacou " + alvo.getNome() + " causando " + forcaAjustada + " de dano.");
        }
    }
}
