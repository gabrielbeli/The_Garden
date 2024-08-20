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
     * @return Valor determinado pelo jogador
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

            NPC npc = salaAtual.getNPCsComuns().get(0);

            switch (salaAtual.getNome()) {
                case "Planícies Verdejantes":
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Essa é a planície verdejante, meu pai me contou sobre ela..." +
                            "parece tudo muito calmo, vou explorar e ver se encontro algo ou alguém que me ajude.");
                    System.out.println("Um trevo logo a frente, vou falar com ele... Olá, eu sou " + heroi.getNome() + ". " +
                            "Quem é você?\n");

                    System.out.println(npc.getNome());
                    System.out.println("Olá, me chamo "+ npc.getNome() + "! É um prazer te conhecer. O vento tem " +
                            "sussurrado seu nome por todo o reino. Queria ter sua coragem... posso ajudar... tome, " +
                            "fique com isso, pode ser útil, eu espero.");

                    heroi.setOuro(heroi.getOuro() + npc.getOuro());

                    System.out.println("-------------------------------------\n");
                    System.out.println(npc.getNome() + " te deu " + npc.getOuro() + " moedas de ouro!");
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Meus agradecimentos, "+ npc.getNome() +"... Pode me indicar o melhor caminho " +
                            "para a Gruta do Orvalho?\n");

                    System.out.println(npc.getNome());
                    System.out.println("Você realmente tem coragem... para chegar até a Gruta do Orvalho não há o melhor " +
                            "caminho, precisa escolher o que sua intuição mandar. Pode ir pelo  Campo das Papoulas ou " +
                            "Campo das Rosas.Boa sorte!\n");

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Clareira das Lavandas":
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Esse cheiro é relaxante. Parece um lugar calmo para descansar\n");

                    System.out.println(npc.getNome());
                    System.out.println("Olá, me chamo "+ npc.getNome() + "! mas pode me chamar de Lila. Você com certeza" +
                            " deve ser o espirito escolhido. Os rumores tomam o reino todo sobre uma nova esperança.\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Ahh... Olá Lila... acho que esperam demais de mim...\n");

                    System.out.println(npc.getNome());
                    System.out.println("Deixe disso, o Jardim não faria essa escolha se não tivesse certeza. " +
                            "Confie nessa força, na sua força. Aqui pode descansar e leve com você isso.\n");

                    heroi.setOuro(heroi.getOuro() + npc.getOuro());

                    System.out.println("-------------------------------------\n");
                    System.out.println(npc.getNome() + " te deu " + npc.getOuro() + " moedas de ouro!");
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println(npc.getNome() +"... Lila... Pode me indicar o melhor caminho para a Gruta do Orvalho?\n");

                    System.out.println(npc.getNome());
                    System.out.println("Claro que sim! A Gruta do Orvalho fica a perto daqui, só precisa passar pela " +
                            "Clareira das Margaridas. Elas te mostrarão o caminho.\n");

                    System.out.println(heroi.getNome());
                    System.out.println(npc.getNome() +" Que ótima notícia, meus agradecimentos Lila!\n");

                    System.out.println(npc.getNome());
                    System.out.println("Que o espirito do Jardim guie seu caminho!\n");

                    break;

                case "Clareira das Margaridas":
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Aqui estamos, a Clareira das Margaridas, a gruta deve estar por perto.\n");

                    System.out.println(npc.getNome());
                    System.out.println("Eiiiii... é você.... sim é você... não tem como não ser!\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Hum... olá... eu sou " + heroi.getNome()+".\n");

                    System.out.println(npc.getNome());
                    System.out.println("Deixe disso, sei quem você é, todos sabem. Estávamos a sua espera. Bromélia nos " +
                            "avisou de sua chegada e disse para ajudarmos você a chegar a gruta.\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Sim, preciso encontrar a Gruta do Orvalho\n");

                    System.out.println(npc.getNome());
                    System.out.println("Levaremos você até lá. Também temos uma oferenda de boas energias a você, " +
                            "acreditamos que será importante para continuar sua jornada.\n");

                    Pocao pocao = new Pocao("Poção de Vida", 50, 10, 5);
                    heroi.addAoInventario(pocao);

                    System.out.println("-------------------------------------\n");
                    System.out.println(npc.getNome() + " te de uma " + pocao.getNome() + "!");
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Meus agradecimentos... com certeza vai ajudar\n");

                    System.out.println(npc.getNome());
                    System.out.println("Agora se apresse, a gruta te espera.\n");
                    break;

                case "Vale Solar":
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(npc.getNome());
                    System.out.println("Ora viva, sejam bem vindos ao Vale Solar. Eu sou "+ npc.getNome() +"\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Esse lugar é incrível. Sinto tanta energia...\n");

                    NPCCompanheiro primeiroCompanheiro = salaAtual.getCompanheiros().get(0);
                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("Os girassóis carregam a luz solar...!\n");

                    System.out.println(npc.getNome());
                    System.out.println("E mais do que isso, somos capazes de partilhar essa luz e fortalecer bons amigos\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Definitivamente é incrivel, espero voltar aqui quando tudo acabar...\n");

                    System.out.println(npc.getNome());
                    System.out.println("São sempre bem-vindos, o espirito do Jardim escolheu vocês então são todos amigos." +
                            "por isso entrego a vocês um pouco de luz, vão precisar para terminar essa jornada\n");

                    heroi.setVidaMax(heroi.getVidaMax() + 20);
                    heroi.setVidaAtual(heroi.getVidaMax());

                    System.out.println("-------------------------------------\n");
                    System.out.println(npc.getNome() + ", restaurou ao máximo sua vida e aumentou em 20 pontos!");
                    System.out.println("\n-------------------------------------\n");

                    NPCCompanheiro segundoCompanheiro = salaAtual.getCompanheiros().get(1);
                    System.out.println(segundoCompanheiro.getNome());
                    System.out.println("Agradecemos pela ajuda." + npc.getNome() +" Precisamos continuar\n");

                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("O próximo passo é o Pântano Venenoso, encontraremos a feiticeira!\n");
                    break;

                case "Vale das Brisas":
                    System.out.println("\n-------------------------------------\n");

                    primeiroCompanheiro = salaAtual.getCompanheiros().get(0);
                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("Bem vindos ao Vale das Brisas. Aqui conseguimos upar nossa vida... " +
                            "vamos precisar disso e sorte!\n");

                    segundoCompanheiro = salaAtual.getCompanheiros().get(1);
                    System.out.println(segundoCompanheiro.getNome());
                    System.out.println("Deixe de falar besteira. Vamos conseguir!");

                    System.out.println(heroi.getNome());
                    System.out.println("Eu quero acreditar que sim, mas toda ajuda é bem vinda, então que bom que estamos aqui.\n");

                    System.out.println(npc.getNome());
                    System.out.println("Heyyyyyy... são os grandes herois ou uma brisa minha? Brincadeira, Bromélia me " +
                            "avisou sobre vocês. Eu sou," + npc.getNome() + ", sejam bem-vindos ao Vale das Brisas. " +
                            "Aproveitem, relaxem e me digam no que posso ajudar.\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Queremos upar nossas vidas...\n");

                    System.out.println(npc.getNome());
                    System.out.println("E quem não quer não é mesmo?! Brincadeira, estão no lugar certo, a brisa aqui eleva mesmo. " +
                            "Bom, sabem que tem um preço né? Cada ponto custa 1 moeda de ouro!\n");

                    System.out.println("-------------------------------------\n");
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

                    System.out.println("\n Sua vida e a dos seus companheiros aumentou em " + pontosVida + " pontos!");
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(npc.getNome());
                    System.out.println("Já é, ta feito parceirinhos! Foi uma brisa boa. Voltem quando quiserem!\n");

                    System.out.println(segundoCompanheiro.getNome());
                    System.out.println("Vamor seguir? Daqui podemos ir para o Pântano Venenoso.\n");

                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("Ou podemos ir passar antes pelo Caminho Caladiano, é perigoso, mas " +
                            "assim lutamos juntos antes enfrentar Beladona.\n");
                    break;

                case "Vale das Orquídeas":
                    System.out.println("\n-------------------------------------\n");

                    segundoCompanheiro = salaAtual.getCompanheiros().get(1);
                    System.out.println(segundoCompanheiro.getNome());
                    System.out.println("Chegamos ao Vale das Orquídeas. Aqui, "+ npc.getNome()+ ", pode nos ajudar a " +
                            "aumentar nossa força, ela foi uma grande guerreira do reino\n");

                    primeiroCompanheiro = salaAtual.getCompanheiros().get(0);
                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("E por que ela não luta ao nosso lado?.\n");

                    System.out.println(segundoCompanheiro.getNome());
                    System.out.println("Em seu ultimo combate ela perdeu a visão.\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Então como ela vai conseguir nos ajudar?\n");

                    System.out.println(npc.getNome());
                    System.out.println("Ainda sem minha visão seria capaz de lutar melhor do que vocês. " +
                            "Não preciso enxergar para saber que vocês precisam se fortalecerem, do contrário Beladona " +
                            "e seus servos vão tirar mais do que visão de vocês.\n");

                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("Nossa, que grande incentivo não é mesmo?.\n");

                    System.out.println(segundoCompanheiro.getNome());
                    System.out.println("Peço desculpas," + npc.getNome()+ " não dê ouvidos a isso. Estamos aqui pois " +
                            "precisamos de sua ajuda. Precisamos de seu treinamento!\n");

                    System.out.println(npc.getNome());
                    System.out.println("Eu sei que estão aqui para isso... Disse a Bromélia que faria meu melhor. " +
                            "O treinamento tem um custo, para cada ponto de força vão precisar de 5 moedas de ouro.\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Agradecemos, tenho comigo algumas moedas que juntei no caminho até aqui.\n");

                    System.out.println("-------------------------------------\n");

                    System.out.println("Você tem " + heroi.getOuro() + " moedas de ouro.");
                    System.out.println("Quantos pontos de força você deseja adicionar (cada ponto custa 5 moedas de ouro)?: ");
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

                    System.out.println("\nVocê aumentou a sua força e a dos seus companheiros em " + pontosForca + " pontos!");

                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Agradecemos o treinamento, " + npc.getNome() +"\n");

                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("Vamos seguir? Daqui podemos ir para o Pântano Venenoso.\n");

                    System.out.println(segundoCompanheiro.getNome());
                    System.out.println("Ou podemos ir para o Caminho Urtiguento, é perigoso, mas assim colocamos o treino em pratica.\n");

                    System.out.println(npc.getNome());
                    System.out.println("O espirito do Jardim e de todos os guerreiros que se foram, estão juntos com voces!Até mais...\n");

                    System.out.println(primeiroCompanheiro.getNome());
                    System.out.println("Ela é sempre animada assim?\n");
                    break;
            }
        }
    }

    private void esperarEnter(Scanner scanner) {
        System.out.println("Pressione Enter para continuar...");
        scanner.nextLine();
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
