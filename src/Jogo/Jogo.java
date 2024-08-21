package Jogo;

import Entidades.*;
import Combate.GerenciadorCombate;
import Entidades.Categorias.*;
import Entidades.NPC.NPC;
import Entidades.NPC.NPCCompanheiro;
import Entidades.NPC.NPCInimigo;
import Itens.Pocao;
import MetodosGenericos.ImprimirArquivo;

import java.io.FileNotFoundException;
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

        System.out.println("\nVocê tem " + ouroInicial + " de ouro para distribuir entre Vida e Força.\n");

        // Distribuição de pontos de vida (1 ponto de vida custa 1 moeda)
        int vidaMax = definirValor("vida", 100, scanner, ouroInicial, 1);
        ouroInicial -= vidaMax;

        // Distribuição de pontos de força (1 ponto de força custa 10 moedas)
        int forca = definirValor("força", 10, scanner, ouroInicial, 10);
        ouroInicial -= forca * 10;

        // Escolha do nome do herói
        System.out.print("\nEscolha o nome do seu herói: ");
        String nomeHeroi = scanner.next();

        // Criar o objeto heroi
        heroi = new HeroiBase(nomeHeroi, vidaMax, forca);
        heroi.setOuro(ouroInicial);

        System.out.println("\nHerói criado com sucesso!");
        System.out.println("-------------------------------");
        heroi.mostrarDetalhes();
    }

    /**
     * Método auxiliar que ajuda na construção do personagem
     *
     * @param atributo        Nome do atributo (vida ou força)
     * @param maximo          Valor máximo permitido para o atributo
     * @param scanner         Entrada de dados
     * @param ouroDisponivel  Ouro disponível para distribuição
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
     *
     * @param scanner Entrada de dados
     */
    private void definirCategoriaHeroi(Scanner scanner) {
        System.out.println("Categorias");
        System.out.println("1. Guerreiro");
        System.out.println("2. Bardo");
        System.out.println("3. Druida");
        System.out.println("4. Ranger");

        System.out.print("\nFaça sua escolha: ");
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
     * Método que implementa a exploração conforme o tipo de sala.
     *
     * @param salaAtual Identifica a sala que o heroi se encontra.
     * @param scanner   Entrada de dados
     */
    private void explorarSala(Sala salaAtual, Scanner scanner) throws FileNotFoundException {
        if (salaAtual.isExplorada()) {
            System.out.println("Você já explorou esta sala. Não há mais nada para descobrir aqui.");
            return;
        }
        // Caso a sala ainda não tenha sido explorada
        salaAtual.setExplorada(true);

        if (salaAtual.getTipo() == TipoSala.INICIAL) {

            ImprimirArquivo imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

            imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa01.txt");

            imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoSalaTrono.txt");

        } else if (salaAtual.getTipo() == TipoSala.VENDEDOR) {
            salaAtual.setExplorada(false);
            Vendedor vendedor = salaAtual.getVendedor();

            switch (salaAtual.getNome()) {
                case "Vendinha Cactos":

                    ImprimirArquivo imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoVendinhaCactos.txt");

                    System.out.println("-------------------------------\n");
                    definirCategoriaHeroi(scanner);
                    System.out.println("\n-------------------------------------\n");

                    System.out.println("\uD83C\uDF35 " + vendedor.getNome());
                    System.out.println("Agora que já sabemos quem você quer ser, tenho muitos itens que podem te ajudar em sua missão. " +
                            "Você precisa escolher seu artefato principal. Vamos dar uma olhada...\n");

                    System.out.println("-------------------------------\n");
                    vendedor.interagir(heroi);
                    System.out.println("\n-------------------------------------\n");

                    System.out.println("\uD83C\uDF35 " + vendedor.getNome());
                    System.out.println("Já passa da hora de começar sua jornada. Lembre-se, o espirito do Jardim escolheu você, " +
                            "pense bem em suas escolhas. Siga para a Gruta do Orvalho, ela é a entrada para o caminho que busca. " +
                            "Você pode seguir pela Planície Verdejante ou pelo Grande Penedo.\n");

                    break;

                case "Gruta do Orvalho":
                    System.out.println("\n-------------------------------------\n");
                    imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoGrutaOrvalho.txt");

                    System.out.println("-------------------------------\n");
                    vendedor.interagir(heroi);
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoGrutaOrvalho02.txt");

                    break;

                case "Lagoa dos Cristais":
                    System.out.println("\n-------------------------------------\n");
                    imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa04.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoLagoaCristais.txt");

                    System.out.println("-------------------------------\n");
                    vendedor.interagir(heroi);
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Polegar, você sabe onde podemos encontrar o príncipe Espinho?\n");

                    ImprimirArquivo.esperarEnter();

                    System.out.println("\uD83C\uDF35 " + vendedor.getNome());
                    System.out.println("Aquele rabugento deve estar no Umbral Espinhento, boa sorte.\n");

                    break;
            }

        } else if (salaAtual.getTipo() == TipoSala.COMBATE) {
            List<NPCInimigo> inimigos = salaAtual.getInimigos();
            ImprimirArquivo imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

            switch (salaAtual.getNome()) {
                case "Grande Penedo":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoGrandePenedo.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println(inimigo.getNome());
                            System.out.println("Tão insignificante! O que é a coragem sem força? Vida longa a Beladona!\n");
                            return;
                        } else {
                            System.out.println(inimigo.getNome());
                            System.out.println("Você encontrará seu fim, nossa rainha vencerá, morremos honrados...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println(heroi.getNome());
                            System.out.println("Para uma primeira vez até que me sai bem! Por onde devo seguir?\n");
                            return;
                        }
                    }

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Campo das Papoulas":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCampoPapoulas.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println(inimigo.getNome());
                            System.out.println("Nossa rainha ficará encantada com a beleza de nossa conquista. Vida longa a Beladona.\n");
                            return;
                        } else {
                            System.out.println(inimigo.getNome());
                            System.out.println("Maldição! Veja o que fez com a gente...estamos murchando...como pode destruir algo tão belo como nós...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println(heroi.getNome());
                            System.out.println("Para uma primeira vez até que me sai bem! Por onde devo seguir?\n");
                            return;
                        }
                    }

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Campo das Rosas":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa2A.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCampoRosas.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println(inimigo.getNome());
                            System.out.println("Você falhou! Mas isso já era esperado de alguém que nem mesmo sabem quem é. Vida longa à Beladona.\n");
                            return;
                        } else {
                            System.out.println(inimigo.getNome());
                            System.out.println("Impossível! Você nem mesmo sabe quem você é e nos somos as Rosas, a elite das flores,\n " +
                                    "senhoras do Jardim... como ousa nos tratar assim... que Beladona acabe com sua existência.\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println(heroi.getNome());
                            System.out.println("Esse desafo foi maior, mas não posso parar, preciso chegar a Gruta do Orvalho.\n");
                            return;
                        }
                    }

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Caminho Urtiguento":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCaminhoUrtiguento.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println(inimigo.getNome());
                            System.out.println("Eu disse que não importava. Vocês encontraram seu fim criaturas medíocres. Vida longa a Beladona.\n");
                            return;
                        } else {
                            System.out.println(inimigo.getNome());
                            System.out.println("Como? Vocês não passam de seres medíocres. Como pude ser derrotado assim?\n " +
                                    "Espero que Beladona coloque um fim a existência de vocês...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println(heroi.getNome());
                            System.out.println("Parece que formamos uma boa equipe afinal!\n");
                            return;
                        }
                    }

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Caminho Caladiano":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCaminhoCaladiano.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println(inimigo.getNome());
                            System.out.println("Vocês até que tentaram, um belo tira-gosto. Vida longa a Beladona.\n");
                            return;
                        } else {
                            System.out.println(inimigo.getNome());
                            System.out.println("Pelo visto subestimei vocês, mas não importa, minha rainha terminara esse trabalho, boa morte...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println(heroi.getNome());
                            System.out.println("Parece que formamos uma boa equipe afinal!\n");
                            return;
                        }
                    }

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Pântano Venenoso":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa03.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPantanoVenenoso.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println(inimigo.getNome());
                            System.out.println("Eu disse a Bromélia que era uma questão de tempo, que ela não conseguiria me manter aqui para sempre.\n " +
                                    "Parece que ela depositou suas últimas energias nessa tentativa patética. Agora é hora de terminar o que comecei!\n");
                            return;
                        } else {
                            imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPantanoVenenoso02.txt");
                            return;
                        }
                    }

                    System.out.println("\n-------------------------------------\n");
                    break;
            }

        } else if (salaAtual.getTipo() == TipoSala.EVENTO) {
            ImprimirArquivo imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);
            NPC npc = salaAtual.getNPCsComuns().get(0);

            switch (salaAtual.getNome()) {
                case "Planícies Verdejantes":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPlanicieVerdejante.txt");

                    heroi.setOuro(heroi.getOuro() + npc.getOuro());

                    System.out.println("-------------------------------------\n");
                    System.out.println(npc.getNome() + " te deu " + npc.getOuro() + " moedas de ouro!");
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPlanicieVerdejante02.txt");

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Clareira das Lavandas":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoClareiraLavandas.txt");

                    heroi.setOuro(heroi.getOuro() + npc.getOuro());

                    System.out.println("-------------------------------------\n");
                    System.out.println(npc.getNome() + " te deu " + npc.getOuro() + " moedas de ouro!");
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoClareiraLavandas02.txt");

                    break;

                case "Clareira das Margaridas":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa2B.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoClareiraMargaridas.txt");

                    Pocao pocao = new Pocao("Poção de Vida", 50, 10, 5);
                    heroi.addAoInventario(pocao);

                    System.out.println("-------------------------------------\n");
                    System.out.println(npc.getNome() + " te de uma " + pocao.getNome() + "!");
                    System.out.println("\n-------------------------------------\n");

                    System.out.println(heroi.getNome());
                    System.out.println("Meus agradecimentos... com certeza vai ajudar\n");

                    ImprimirArquivo.esperarEnter();

                    System.out.println(npc.getNome());
                    System.out.println("Agora se apresse, a gruta te espera.\n");

                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Vale Solar":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeSolar.txt");

                    heroi.setVidaMax(heroi.getVidaMax() + 20);
                    heroi.setVidaAtual(heroi.getVidaMax());
                    System.out.println("\n-------------------------------------\n");
                    break;

                case "Vale das Brisas":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeBrisas.txt");

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

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeBrisas02.txt");

                    break;

                case "Vale das Orquídeas":
                    System.out.println("\n-------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeOrquideas.txt");

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

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeOrquideas02.txt");

                    System.out.println("\n-------------------------------------\n");
                    break;
            }
        }
    }

    /**
     * Método para apresentar as salas que estão conectadas a sala atual
     *
     * @param scanner   Entrada de dados de escolha
     * @param salaAtual Identifica a sala que o heroi se encontra.
     */
    private void mostrarSalasProximas(Scanner scanner, Sala salaAtual) {
        List<Sala> salasProximas = salaAtual.getSalasProximas();
        System.out.println("Salas disponíveis:\n");
        for (int i = 0; i < salasProximas.size(); i++) {
            System.out.println((i + 1) + ". " + salasProximas.get(i).getNome());
        }

        // Escolha a próxima sala
        System.out.print("\nEscolha para onde ir: ");
        int escolhaSala = scanner.nextInt() - 1;
        System.out.println("\n-------------------------");
        gerenciadorSalas.avancarParaProximaSala(escolhaSala);
    }

    /**
     * Metodo que dá início ao jogo depois que o heroi foi criado.
     */
    public void iniciarAventura() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSua aventura começa agora!");
        System.out.println("-------------------------------\n");

        while (!gerenciadorSalas.estaNaSalaFinal()) {
            Sala salaAtual = gerenciadorSalas.getSalaAtual();
            heroi.resetarAtaqueEspecial();

            System.out.println("Você está em: " + salaAtual.getNome() + "\n");

            String[] opcoes = {"Explorar", "Próximas Salas", "Inventário", "Desvendar Sala(custo 25 moedas)"};
            salaAtual.setOpcoes(opcoes);
            salaAtual.mostrarOpcoes();

            System.out.print("\nEscolha uma opção: ");
            int escolhaOpcao = scanner.nextInt();
            System.out.println("------------------------------\n");

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

        System.out.println("Você chegou ao Umbral Espinhento. Prepare-se para a grande batalha final!");
        ImprimirArquivo imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

        imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoUmbralEspinhento.txt");

        // Batalha final na sala final
        Sala salaFinal = gerenciadorSalas.getSalaAtual();
        List<NPCInimigo> inimigosFinais = salaFinal.getInimigos();

        if (inimigosFinais.size() > 1) {

            NPCInimigo inimigoParaRemover = inimigosFinais.get(1);

            salaFinal.removerInimigo(inimigoParaRemover);
        }

        for (NPCInimigo inimigoFinal : inimigosFinais) {

            boolean vitoriaFinal = gerenciadorCombate.realizarCombate(heroi, salaFinal.getCompanheiros(), inimigoFinal);

            // Verifica se o herói foi derrotado na batalha final
            if (!vitoriaFinal) {
                System.out.println("Foi como você disse irmão, me chamam de justiça sombria.\n " +
                        " Então, agora o Jardim verá o meu lado sombrio até a completa extinção.");
                return;
            }

            // Condição para o herói Bardo com "Semente Lirica" no inventário
            else if (heroi.getCategoria().getClass().getSimpleName().equals("Bardo") && heroi.getInventario().contains("Semente Lirica")) {
                imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativaFinal03.txt");
            }

            // Condições para outras escolhas de narrativa
            else {
                scanner = new Scanner(System.in);
                System.out.println("Escolha seu destino final\n");
                System.out.println("1. Ciclo natural");
                System.out.println("2. Caminho da glória");
                System.out.print("\nEscolha: ");

                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativaFinal01.txt");
                        break;
                    case 2:
                        imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativaFinal02.txt");
                        break;
                    default:
                        imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativaFinal01.txt");
                        break;
                }
            }

            System.out.println("Fim de jornada" + heroi.getNome() +"!");
        }

    }
}
