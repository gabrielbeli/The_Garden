package Jogo;

import Entidades.*;
import Combate.GerenciadorCombate;
import Entidades.Categorias.*;
import Entidades.NPC.NPC;
import Entidades.NPC.NPCCompanheiro;
import Entidades.NPC.NPCInimigo;
import Itens.Pocao;

import java.util.List;
import java.util.Scanner;

public class Jogo {
    private Heroi heroi;
    private GerenciadorSalas gerenciadorSalas;
    private GerenciadorCombate gerenciadorCombate;

    public Jogo() {
        this.gerenciadorSalas = new GerenciadorSalas();
        this.gerenciadorCombate = new GerenciadorCombate();
    }

    /**
     * Método para definição de nivel de jogo e criação de personagem
     */
    public void criarPersonagem() {
        Scanner scanner = new Scanner(System.in);

        // Escolha do modo de jogo
        System.out.println("Modo de jogo\n");
        System.out.println("1. Ensolarado (300 de ouro)");
        System.out.println("2. Nebuloso (200 de ouro)");
        System.out.print("\nFaça sua escolha de modo: ");

        int escolhaModo = scanner.nextInt();
        int ouroInicial = (escolhaModo == 1) ? 300 : 200;

        System.out.println("\nVocê tem " + ouroInicial + " de ouro para distribuir entre Vida e Força.");

        // Distribuição de pontos de vida (1 ponto de vida custa 1 moeda)
        int vidaMax = definirValor("vida", 100, scanner, ouroInicial, 1);
        ouroInicial -= vidaMax;

        // Distribuição de pontos de força (1 ponto de força custa 10 moedas)
        int forca = definirValor("força", 10, scanner, ouroInicial, 10);
        ouroInicial -= forca * 10;

        // Escolha do nome do herói
        System.out.print("Escolha o nome do seu herói: ");
        String nomeHeroi = scanner.next();

        // Criar o objeto heroi
        heroi = new HeroiBase(nomeHeroi, vidaMax, forca);
        heroi.setOuro(ouroInicial);

        System.out.println("Herói criado com sucesso!");
        System.out.println("-------------------------------\n");
        heroi.mostrarDetalhes();
    }

    /**
     * Método auxiliar que ajuda na construção do personagem
     * @param atributo Nome do atributo (vida ou força)
     * @param maximo Valor máximo permitido para o atributo
     * @param scanner Entrada de dados
     * @param ouroDisponivel Ouro disponível para distribuição
     * @param custoPorUnidade Custo de cada unidade do atributo
     * @return valor determinado pelo jogador
     */
    private int definirValor(String atributo, int maximo, Scanner scanner, int ouroDisponivel, int custoPorUnidade) {
        int valor;
        while (true) {
            System.out.print("Defina o valor para " + atributo + " (1 a " + maximo + "): ");
            valor = scanner.nextInt();
            int custoTotal = valor * custoPorUnidade;
            if (valor >= 1 && valor <= maximo && custoTotal <= ouroDisponivel) {
                return valor;
            } else {
                System.out.println("Valor inválido. Deve estar entre 1 e " + maximo + ", e você deve ter ouro suficiente (custo total: " + custoTotal + " moedas).");
            }
        }
    }

    /**
     * Metodo para definir a categoria do heroi;
     * Guerreiro | Druida | Bardo | Ranger
     * @param scanner Entrada de dados
     */
    private void definirCategoriaHeroi(Scanner scanner) {
        System.out.println("Escolha a categoria do herói:\n");
        System.out.println("1. Guerreiro");
        System.out.println("2. Bardo");
        System.out.println("3. Druida");
        System.out.println("4. Ranger");

        int escolhaCategoria = scanner.nextInt();
        Categoria categoriaSelecionada;

        switch (escolhaCategoria) {
            case 1:
                categoriaSelecionada = new Guerreiro();
                break;
            case 2:
                categoriaSelecionada = new Bardo();
                break;
            case 3:
                categoriaSelecionada = new Druida();
                break;
            case 4:
                categoriaSelecionada = new Ranger();
                break;
            default:
                System.out.println("Categoria inválida. Definindo como padrão (Guerreiro).");
                categoriaSelecionada = new Guerreiro();
                break;
        }

        heroi.setCategoria(categoriaSelecionada);
        System.out.println("\nCategoria do herói definida como: " + categoriaSelecionada.getClass().getSimpleName());
    }

    /**
     * Método que implementa a eploração de acordo com o tipo de sala.
     * @param salaAtual Identifica a sala que o heroi se encontra.
     * @param scanner Entrada de dados
     */
    private void explorarSala(Sala salaAtual, Scanner scanner) {
        if (salaAtual.isExplorada()) {
            System.out.println("Você já explorou esta sala. Não há mais nada para descobrir aqui.");
            return;
        }

        // Caso a sala ainda não tenha sido explorada
        salaAtual.setExplorada(true);

        if (salaAtual.getTipo() == TipoSala.VENDEDOR) {
            salaAtual.setExplorada(false);
            Vendedor vendedor = salaAtual.getVendedor();
            if (vendedor != null) {
                if (!heroi.isCategoriaDefinida()) {
                    definirCategoriaHeroi(scanner);
                }
                System.out.println("-------------------------------\n");
                vendedor.interagir(heroi);
            }
        } else if (salaAtual.getTipo() == TipoSala.COMBATE) {
            List<NPCInimigo> inimigos = salaAtual.getInimigos();
            for (NPCInimigo inimigo : inimigos) {
                System.out.println("Um inimigo aparece: " + inimigo.getNome());
                boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                if (!vitoria) {
                    System.out.println("Você foi derrotado! Fim de jogo.");
                    return;
                }
            }
        } else if (salaAtual.getTipo() == TipoSala.EVENTO) {
            System.out.println("Você encontrou um evento especial!");

            NPC npc = salaAtual.getNPCsComuns().get(0);

            switch (salaAtual.getNome()) {
                case "Planícies Verdejantes":
                    heroi.setOuro(heroi.getOuro() + npc.getOuro());
                    System.out.println("Você interagiu com o NPC e ganhou " + npc.getOuro() + " de ouro!");
                    heroi.mostrarDetalhes();
                    break;

                case "Clareira das Lavandas":
                    heroi.setOuro(heroi.getOuro() + npc.getOuro());
                    System.out.println("Você interagiu com o NPC e ganhou " + npc.getOuro() + " de ouro!");
                    heroi.mostrarDetalhes();
                    break;

                case "Clareira das Margaridas":
                    Pocao pocao = new Pocao("Poção de Vida", 50, 10, 0);
                    heroi.addAoInventario(pocao);
                    System.out.println("Você interagiu com o NPC e ganhou uma " + pocao.getNome() + "!");
                    heroi.mostrarDetalhes();
                    break;

                case "Vale Solar":
                    heroi.setVidaAtual(heroi.getVidaMax());
                    heroi.setVidaAtual(heroi.getVidaAtual() + 20);
                    System.out.println("Você interagiu com o NPC e sua vida foi restaurada ao máximo, com um acréscimo de 20 pontos!");
                    heroi.mostrarDetalhes();
                    break;

                case "Vale das Brisas":

                    System.out.println("Você tem " + heroi.getOuro() + " moedas de ouro.");
                    System.out.print("Quantos pontos de vida você deseja adicionar (cada ponto custa 1 moeda de ouro)? ");
                    int pontosVida = scanner.nextInt();

                    while (pontosVida < 0 || pontosVida > heroi.getOuro()) {
                        System.out.print("Entrada inválida. Insira um valor entre 0 e " + heroi.getOuro() + ": ");
                        pontosVida = scanner.nextInt();
                    }

                    heroi.setVidaAtual(heroi.getVidaMax() + pontosVida);
                    heroi.setOuro(heroi.getOuro() - pontosVida);

                    List<NPCCompanheiro> companheirosSala = salaAtual.getCompanheiros();
                    for (NPCCompanheiro companheiro : companheirosSala) {
                        companheiro.setVidaAtual(companheiro.getVidaMax() + pontosVida);
                    }

                    System.out.println("Você aumentou sua vida e a dos seus companheiros em " + pontosVida + " pontos!");
                    heroi.mostrarDetalhes();
                    break;

                case "Vale das Orquídeas":

                    System.out.println("Você tem " + heroi.getOuro() + " moedas de ouro.");
                    System.out.print("Quantos pontos de força você deseja adicionar (cada ponto custa 5 moedas de ouro)? ");
                    int pontosForca = scanner.nextInt() / 5;

                    while (pontosForca < 0 || (pontosForca * 5) > heroi.getOuro()) {
                        System.out.print("Entrada inválida. Insira um valor entre 0 e " + heroi.getOuro() + ": ");
                        pontosForca = scanner.nextInt();
                    }

                    heroi.setForca(heroi.getForca() + pontosForca);
                    heroi.setOuro(heroi.getOuro() - pontosForca * 5);

                    companheirosSala = salaAtual.getCompanheiros();
                    for (NPCCompanheiro companheiro : companheirosSala) {
                        companheiro.setForca(companheiro.getForca() + pontosForca);
                    }

                    System.out.println("Você aumentou a sua força e a dos seus companheiros em " + pontosForca + " pontos!");
                    heroi.mostrarDetalhes();
                    break;
            }
        }
    }

    /**
     * Método para apresentar as salas que estão conectadas a sala atual
     * @param scanner Entrada de dados de escolha
     * @param salaAtual Identifica a sala que o heroi se encontra.
     */
    private void mostrarSalasProximas(Scanner scanner, Sala salaAtual) {
        List<Sala> salasProximas = salaAtual.getSalasProximas();
        System.out.println("Salas disponíveis:");
        for (int i = 0; i < salasProximas.size(); i++) {
            System.out.println((i + 1) + ". " + salasProximas.get(i).getNome());
        }

        // Escolha a próxima sala
        System.out.print("Escolha para onde ir: ");
        int escolhaSala = scanner.nextInt() - 1;
        gerenciadorSalas.avancarParaProximaSala(escolhaSala);
    }

    /**
     * Metodo que dá inicio ao jogo depois que o heroi foi criado.
     */
    public void iniciarAventura() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sua aventura começa agora!");
        System.out.println("-------------------------------\n");

        while (!gerenciadorSalas.estaNaSalaFinal()) {
            Sala salaAtual = gerenciadorSalas.getSalaAtual();
            heroi.resetarAtaqueEspecial();

            System.out.println("Você está em: " + salaAtual.getNome());
            System.out.println(salaAtual.getDescricao());

            String[] opcoes = {"Explorar", "Próximas Salas", "Inventário", "Desvendar Sala(custo 25 moedas)"};
            salaAtual.setOpcoes(opcoes);
            salaAtual.mostrarOpcoes();

            System.out.print("Escolha uma opção: ");
            int escolhaOpcao = scanner.nextInt();

            switch (escolhaOpcao) {
                case 1:
                    explorarSala(salaAtual, scanner);
                    break;

                case 2:
                    mostrarSalasProximas(scanner, salaAtual);
                    break;

                case 3:
                    System.out.println("Acessando o inventário...");
                    NPCInimigo inimigo = null;
                    heroi.acessarInventario(false, inimigo);
                    break;
                case 4:
                    System.out.println("Os misterios dessa area...");
                    heroi.setOuro(heroi.getOuro() - 25);
                    salaAtual.mostrarDetalhes();
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        System.out.println("Parabéns! Você chegou ao Umbral Espinhento. Prepare-se para a batalha final!");


        // Batalha final na sala final
        Sala salaFinal = gerenciadorSalas.getSalaAtual();
        List<NPCInimigo> inimigosFinais = salaFinal.getInimigos();
        for (NPCInimigo inimigoFinal : inimigosFinais) {
            boolean vitoriaFinal = gerenciadorCombate.realizarCombate(heroi, salaFinal.getCompanheiros(), inimigoFinal);
            if (!vitoriaFinal) {
                System.out.println("Você foi derrotado na batalha final. Fim de jogo.");
                return;
            }
        }

        System.out.println("Você derrotou todos os inimigos! Parabéns, você completou a aventura com sucesso!");
    }

}
