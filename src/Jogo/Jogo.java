package Jogo;

import Entidades.*;
import Combate.GerenciadorCombate;
import Entidades.Categorias.*;
import Entidades.NPC.NPC;
import Entidades.NPC.NPCCompanheiro;
import Entidades.NPC.NPCInimigo;
import Itens.Consumivel;
import Itens.Pocao;
import MetodosGenericos.ImprimirArquivo;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static MetodosGenericos.Som.tocarSom;

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

        System.out.println("\n\uD83C\uDFAE Modo de jogo\n");
        System.out.println("1. \uD83D\uDD05 Ensolarado (350 moedas \uD83E\uDE99)");
        System.out.println("2. \uD83C\uDF29\uFE0F Nebuloso (250 moedas \uD83E\uDE99)");
        System.out.print("\n\uD83D\uDD79\uFE0F Faça sua escolha de modo: ");

        int escolhaModo = scanner.nextInt();
        int ouroInicial = (escolhaModo == 1) ? 350 : 250;

        System.out.println("\n\uD83E\uDDEE Você tem " + ouroInicial + " de ouro para distribuir entre Vida e Força.\n");

        int vidaMax = definirValor("vida", 100, scanner, ouroInicial, 1);
        ouroInicial -= vidaMax;

        int forca = definirValor("força", 10, scanner, ouroInicial, 10);
        ouroInicial -= forca * 10;

        System.out.print("\n\uD83C\uDF31 Escolha o nome da sua semente: ");
        String nomeHeroi = scanner.next();

        heroi = new HeroiBase(nomeHeroi, vidaMax, forca);
        heroi.setOuro(ouroInicial);

        System.out.println("\n\uD83C\uDF31 Semente criada com sucesso!");
        System.out.println("-------------------------------");
        heroi.mostrarDetalhes();
    }

    /**
     * Método auxiliar que ajuda na construção do personagem
     *
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
            System.out.print("❤\uFE0F\uD83D\uDCAA Defina o valor para " + atributo + " (1 a " + maximo + "): ");
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

        System.out.println("\uD83E\uDE9E Quem você gostaria de ser\n");
        System.out.println("1. \uD83D\uDDE1\uFE0F Guerreiro(a)");
        System.out.println("2. \uD83C\uDFF9 Ranger");
        System.out.println("3. \uD83D\uDCA0 Druida");
        System.out.println("4. \uD83E\uDE95 Bardo(a)");

        System.out.print("\n\uD83D\uDD79\uFE0F Faça sua escolha: ");
        int escolhaCategoria = scanner.nextInt();

        Categoria categoriaSelecionada;

        switch (escolhaCategoria) {
            case 1:
                categoriaSelecionada = new Guerreiro();
                break;
            case 2:
                categoriaSelecionada = new Ranger();
                break;
            case 3:
                categoriaSelecionada = new Druida();
                break;
            case 4:
                categoriaSelecionada = new Bardo();
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
     * @param salaAtual Identifica a sala que o heroi se encontra.
     * @param scanner   Entrada de dados
     */
    private void explorarSala(Sala salaAtual, Scanner scanner) throws FileNotFoundException {
        if (salaAtual.isExplorada()) {
            System.out.println("Você já explorou esta sala. Não há mais nada para descobrir aqui.");
            return;
        }

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

                    definirCategoriaHeroi(scanner);
                    System.out.println("\n------------------------------------------------------------------------------\n");

                    System.out.println("\uD83C\uDF35 " + vendedor.getNome());
                    System.out.println("Agora que já sabemos quem você quer ser, tenho muitos itens que podem te ajudar em sua missão.\n" +
                            "Você precisa escolher seu artefato principal. Vamos dar uma olhada...\n");

                    System.out.println("--------------------------------------------------------------------------------\n");
                    vendedor.interagir(heroi);
                    System.out.println("\n------------------------------------------------------------------------------\n");

                    System.out.println("\uD83C\uDF35 " + vendedor.getNome());
                    System.out.println("Já passa da hora de começar sua jornada. Lembre-se, o espirito do Jardim escolheu você,\n" +
                            "pense bem em suas escolhas. Siga para a Gruta do Orvalho, ela é a entrada para o caminho que busca.\n" +
                            "Você pode seguir pela Planície Verdejante ou pelo Grande Penedo.\n");

                    break;

                case "Gruta do Orvalho":

                    imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

                    if (heroi.getCategoria() == null) {
                        definirCategoriaHeroi(scanner);
                    }

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoGrutaOrvalho.txt");

                    vendedor.interagir(heroi);
                    System.out.println("\n------------------------------------------------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoGrutaOrvalho02.txt");

                    break;

                case "Lagoa dos Cristais":

                    if (heroi.getCategoria() == null) {
                        definirCategoriaHeroi(scanner);
                    }

                    imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa04.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoLagoaCristais.txt");

                    vendedor.interagir(heroi);

                    System.out.println("\uD83C\uDF31 " + heroi.getNome());
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

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoGrandePenedo.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println("\uD83C\uDF44\u200D\uD83D\uDFEB " + inimigo.getNome());
                            System.out.println("Tão insignificante! O que é a coragem sem força? Vida longa a Beladona!\n");
                            return;
                        } else {
                            System.out.println("\uD83C\uDF44\u200D\uD83D\uDFEB " + inimigo.getNome());
                            System.out.println("Você encontrará seu fim, nossa rainha vencerá, morremos honrados...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println("\uD83C\uDF31 " + heroi.getNome());
                            System.out.println("Para uma primeira vez até que me sai bem! Por onde devo seguir?\n");
                            return;
                        }
                    }
                    break;

                case "Campo das Papoulas":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCampoPapoulas.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println("\uD83C\uDF37 " + inimigo.getNome());
                            System.out.println("Nossa rainha ficará encantada com a beleza de nossa conquista. Vida longa a Beladona.\n");
                            return;
                        } else {
                            System.out.println("\uD83C\uDF37 " + inimigo.getNome());
                            System.out.println("Maldição! Veja o que fez com a gente...estamos murchando...como pode destruir algo tão belo como nós...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println("\uD83C\uDF31 " + heroi.getNome());
                            System.out.println("Para uma primeira vez até que me sai bem! Por onde devo seguir?\n");
                            return;
                        }
                    }
                    break;

                case "Campo das Rosas":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa2A.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCampoRosas.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println("\uD83C\uDF39 " + inimigo.getNome());
                            System.out.println("Você falhou! Mas isso já era esperado de alguém que nem mesmo sabem quem é. Vida longa à Beladona.\n");
                            return;
                        } else {
                            System.out.println("\uD83C\uDF39 " + inimigo.getNome());
                            System.out.println("Impossível! Você nem mesmo sabe quem você é e nos somos as Rosas, a elite das flores,\n" +
                                    "senhoras do Jardim... como ousa nos tratar assim... que Beladona acabe com sua existência.\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println("\uD83C\uDF31 " + heroi.getNome());
                            System.out.println("Esse desafo foi maior, mas não posso parar, preciso chegar a Gruta do Orvalho.\n");
                            return;
                        }
                    }
                    break;

                case "Caminho Urtiguento":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCaminhoUrtiguento.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println("\uD83D\uDC7E  " + inimigo.getNome());
                            System.out.println("Eu disse que não importava. Vocês encontraram seu fim criaturas medíocres. Vida longa a Beladona.\n");
                            return;
                        } else {
                            System.out.println("\uD83D\uDC7E  " + inimigo.getNome());
                            System.out.println("Como? Vocês não passam de seres medíocres. Como pude ser derrotado assim?\n " +
                                    "Espero que Beladona coloque um fim a existência de vocês...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println("\uD83C\uDF31 " + heroi.getNome());
                            System.out.println("Parece que formamos uma boa equipe afinal!\n");
                            return;
                        }
                    }
                    break;

                case "Caminho Caladiano":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoCaminhoCaladiano.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println("\uD83D\uDC7E  " + inimigo.getNome());
                            System.out.println("Vocês até que tentaram, um belo tira-gosto. Vida longa a Beladona.\n");
                            return;
                        } else {
                            System.out.println("\uD83D\uDC7E  " + inimigo.getNome());
                            System.out.println("Pelo visto subestimei vocês, mas não importa, minha rainha terminara esse trabalho, boa morte...\n");

                            ImprimirArquivo.esperarEnter();

                            System.out.println("\uD83C\uDF31 " + heroi.getNome());
                            System.out.println("Parece que formamos uma boa equipe afinal!\n");
                            return;
                        }
                    }
                    break;

                case "Pântano Venenoso":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa03.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPantanoVenenoso.txt");

                    for (NPCInimigo inimigo : inimigos) {
                        System.out.println(heroi.getNome() + " VS " + inimigo.getNome());
                        boolean vitoria = gerenciadorCombate.realizarCombate(heroi, salaAtual.getCompanheiros(), inimigo);
                        if (!vitoria) {
                            System.out.println("\uD83E\uDEB7 " + inimigo.getNome());
                            System.out.println("Eu disse a Bromélia que era uma questão de tempo, que ela não conseguiria me manter aqui para sempre.\n " +
                                    "Parece que ela depositou suas últimas energias nessa tentativa patética. Agora é hora de terminar o que comecei!\n");
                            return;
                        } else {
                            imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPantanoVenenoso02.txt");
                            return;
                        }
                    }
                    break;
            }

        } else if (salaAtual.getTipo() == TipoSala.EVENTO) {
            ImprimirArquivo imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);
            NPC npc = salaAtual.getNPCsComuns().get(0);

            switch (salaAtual.getNome()) {
                case "Planícies Verdejantes":

                    imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPlanicieVerdejante.txt");

                    heroi.setOuro(heroi.getOuro() + npc.getOuro());

                    System.out.println("☘\uFE0F "+ npc.getNome() + " te deu " + npc.getOuro() + " moedas de ouro! \uD83E\uDE99");
                    System.out.println("\n---------------------------------------------------------------------------------------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoPlanicieVerdejante02.txt");

                    break;

                case "Clareira das Lavandas":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoClareiraLavandas.txt");

                    heroi.setOuro(heroi.getOuro() + npc.getOuro());

                    System.out.println("\uD83E\uDEBB " + npc.getNome() + " te deu " + npc.getOuro() + " moedas de ouro! \uD83E\uDE99");
                    System.out.println("\n--------------------------------------------------------------------------------------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoClareiraLavandas02.txt");

                    break;

                case "Clareira das Margaridas":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativa2B.txt");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoClareiraMargaridas.txt");

                    Pocao pocao = new Pocao("Poção de Vida", 50, 10, 5);
                    heroi.addAoInventario(pocao);

                    System.out.println("\uD83C\uDF3C " + npc.getNome() + " te de uma " + pocao.getNome() + "! \uD83E\uDDEA");
                    System.out.println("\n--------------------------------------------------------------------------------------------------------------------\n");

                    System.out.println("\uD83C\uDF31 " + heroi.getNome());
                    System.out.println("Meus agradecimentos... com certeza vai ajudar\n");

                    ImprimirArquivo.esperarEnter();

                    System.out.println("\uD83C\uDF3C " + npc.getNome());
                    System.out.println("Agora se apresse, a gruta te espera.\n");

                    break;

                case "Vale Solar":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeSolar.txt");

                    heroi.setVidaMax(heroi.getVidaMax() + 20);
                    heroi.setVidaAtual(heroi.getVidaMax());

                    break;

                case "Vale das Brisas":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeBrisas.txt");

                    System.out.println("Você tem " + heroi.getOuro() + " moedas de ouro. \uD83E\uDE99");
                    System.out.print("Quantos pontos de vida você deseja adicionar (cada  1 \uD83E\uDE99 = 1 ❤\uFE0F )?: ");

                    int pontosVida = scanner.nextInt();

                    while (pontosVida < 0 || pontosVida > heroi.getOuro()) {
                        System.out.print("Entrada inválida. Insira um valor entre 0 e " + heroi.getOuro() + ": ");
                        pontosVida = scanner.nextInt();
                    }
                    heroi.setVidaMax(heroi.getVidaMax()+ pontosVida);
                    heroi.setVidaAtual(heroi.getVidaMax());
                    heroi.setOuro(heroi.getOuro() - pontosVida);

                    List<NPCCompanheiro> companheirosSala = salaAtual.getCompanheiros();
                    for (NPCCompanheiro companheiro : companheirosSala) {
                        companheiro.setVidaAtual(companheiro.getVidaMax() + pontosVida);
                    }

                    System.out.println("\nSua vida e a dos seus companheiros aumentou em " + pontosVida + " pontos! ❤\uFE0F\u200D\uD83D\uDD25");
                    System.out.println("\n--------------------------------------------------------------------------------------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeBrisas02.txt");

                    break;

                case "Vale das Orquídeas":

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeOrquideas.txt");

                    System.out.println("Você tem " + heroi.getOuro() + " moedas de ouro. \uD83E\uDE99");
                    System.out.println("Quantos pontos de força você deseja adicionar (cada 5 \uD83E\uDE99 = 1 \uD83D\uDCAA )?: ");
                    int pontosForca = scanner.nextInt() / 5;

                    while (pontosForca < 0 || (pontosForca * 5) > heroi.getOuro()) {
                        System.out.print("⛔Entrada inválida. Insira um valor entre 0 e " + heroi.getOuro() + ": ");
                        pontosForca = scanner.nextInt();
                    }

                    heroi.setForca(heroi.getForca() + pontosForca);
                    heroi.setOuro(heroi.getOuro() - pontosForca * 5);

                    companheirosSala = salaAtual.getCompanheiros();
                    for (NPCCompanheiro companheiro : companheirosSala) {
                        companheiro.setForca(companheiro.getForca() + pontosForca);
                    }

                    System.out.println("\nVocê aumentou a sua força e a dos seus companheiros em " + pontosForca + " pontos! \uD83D\uDCAA");
                    System.out.println("\n--------------------------------------------------------------------------------------\n");

                    imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoValeOrquideas02.txt");

                    break;
            }
        }
    }

    /**
     * Método para apresentar as salas que estão conectadas a sala atual
     * @param scanner   Entrada de dados de escolha
     * @param salaAtual Identifica a sala que o heroi se encontra.
     */
    private void mostrarSalasProximas(Scanner scanner, Sala salaAtual) {
        List<Sala> salasProximas = salaAtual.getSalasProximas();
        System.out.println("\uD83D\uDDFA\uFE0F Salas disponíveis:\n");
        for (int i = 0; i < salasProximas.size(); i++) {
            System.out.println((i + 1) + ". \uD83D\uDEA9 " + salasProximas.get(i).getNome());
        }

        System.out.print("\n\uD83E\uDDED Escolha para onde ir: ");
        int escolhaSala = scanner.nextInt() - 1;
        System.out.println("\n-------------------------");
        gerenciadorSalas.avancarParaProximaSala(escolhaSala);
    }

    /**
     * Metodo que dá início ao jogo depois que o heroi foi criado.
     */
    public void iniciarAventura() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\uD83D\uDCDC Sua aventura começa agora! \uD83E\uDEB6");
        System.out.println("-------------------------------\n");

        while (!gerenciadorSalas.estaNaSalaFinal()) {
            Sala salaAtual = gerenciadorSalas.getSalaAtual();
            heroi.resetarAtaqueEspecial();

            System.out.println("\uD83D\uDEA9 Você está em: " + salaAtual.getNome() + "\n");

            String[] opcoes = {"\uD83E\uDDED Explorar", "\uD83D\uDDFA\uFE0F Próximas Salas", "\uD83C\uDF92 Inventário", "\uD83D\uDCA1 Iluminar Sala(custo 25 \uD83E\uDE99)", "\uD83D\uDCD2 Meu status"};
            salaAtual.setOpcoes(opcoes);
            salaAtual.mostrarOpcoes();

            System.out.print("\n\uD83D\uDD79\uFE0F Escolha uma opção: ");
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
                    System.out.println("\uD83D\uDC40 Acessando o inventário...\n");
                    NPCInimigo inimigo = null;
                    heroi.acessarInventario(false, inimigo);
                    break;
                case 4:
                    System.out.println("\uD83E\uDEAC Os misterios dessa area...");
                    heroi.setOuro(heroi.getOuro() - 25);
                    salaAtual.mostrarDetalhes();
                    break;
                case 5:
                    System.out.println("\uD83C\uDF31 Como estou...\n");
                    heroi.mostrarDetalhes();
                    break;

                default:
                    System.out.println("⛔ Opção inválida. Tente novamente.");
                    break;
            }
        }

        System.out.println("☢\uFE0F Você chegou ao Umbral Espinhento. Prepare-se para a grande batalha final! ☢\uFE0F\n");
        ImprimirArquivo imprimirArquivo = new ImprimirArquivo(heroi, gerenciadorSalas, gerenciadorCombate);

        imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/dialogoUmbralEspinhento.txt");

        // Batalha final
        Sala salaFinal = gerenciadorSalas.getSalaAtual();
        List<NPCInimigo> inimigosFinais = salaFinal.getInimigos();

        if (inimigosFinais.size() > 1) {

            NPCInimigo inimigoParaRemover = inimigosFinais.get(1);

            salaFinal.removerInimigo(inimigoParaRemover);
        }

        for (NPCInimigo inimigoFinal : inimigosFinais) {

            boolean vitoriaFinal = gerenciadorCombate.realizarCombate(heroi, salaFinal.getCompanheiros(), inimigoFinal);


            if (!vitoriaFinal) {
                System.out.println("⚜\uFE0F " + inimigoFinal.getNome());
                System.out.println("Foi como você disse irmão, me chamam de justiça sombria.\n " +
                        " Então, agora o Jardim verá o meu lado sombrio até a completa extinção.");
                return;
            }


            boolean possuiSementeLirica = false;
            for (Consumivel item : heroi.getInventario()) {
                if (item.getNome().equals("Semente Lirica")) {
                    possuiSementeLirica = true;
                    break;
                }
            }

             if (heroi.getCategoria().getClass().getSimpleName().equals("Bardo") && possuiSementeLirica) {

                 tocarSom("src/Sons/Semente-Magica.wav");
                imprimirArquivo.imprimirNarrativa("src/FicheirosNarrativas/narrativaFinal03.txt");
            }

            else {
                scanner = new Scanner(System.in);
                System.out.println("\uD83D\uDD30 Destinos finais \uD83D\uDD30\n");
                System.out.println("1. \uD83D\uDDDD\uFE0F Ciclo natural");
                System.out.println("2. \uD83D\uDDDD\uFE0F Caminho da glória");
                System.out.print("\n\uD83D\uDD79\uFE0F Escolha: ");

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

            System.out.println("\uD83E\uDDE1 Fim de jornada " + heroi.getNome() +"! \uD83C\uDF31");
        }

    }
}
