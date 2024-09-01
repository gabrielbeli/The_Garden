package Jogo;

import Entidades.NPC.NPC;
import Entidades.NPC.NPCCompanheiro;
import Entidades.NPC.NPCInimigo;
import Entidades.Vendedor;
import Itens.ArtefatoPrincipal;
import Itens.MagiaCombate;
import Itens.Pocao;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorSalas {
    private ArrayList<Sala> salas;
    private Sala salaAtual;

    public GerenciadorSalas() {
        this.salas = new ArrayList<>();
        inicializarSalas();
    }

    /**
     * Método que instancia as salas, npcs, vendedor items, faz a suas conexões
     */
    private void inicializarSalas() {

        // Criando todas as salas do jogo
        Sala salaDoTrono = new Sala("Sala do trono", TipoSala.INICIAL, "Na grande sala do trono reina o silêncio a espera de um dia um nova coroa surgir");
        Sala vendinhaCactos = new Sala("Vendinha Cactos", TipoSala.VENDEDOR, "Vendinha Cactos, uma loja que prestas serviços ao reino há gerações.");
        Sala planiciesVerdejantes = new Sala("Planícies Verdejantes", TipoSala.EVENTO, "Uma grande planicie coberta do mais puro verde, uma lembrança de um reino feliz.");
        Sala grandePenedo = new Sala("Grande Penedo", TipoSala.COMBATE, "Uma região ao nordeste do reino, onde um grande penedo ostenta a imponência de um reino.");
        Sala campoDasPapoulas = new Sala("Campo das Papoulas", TipoSala.COMBATE, "O encantamento das belessimas papoulas é um visão viciante.");
        Sala campoDasRosas = new Sala("Campo das Rosas", TipoSala.COMBATE, "A quem diga que o aroma das rosas chega além das terras do Jardim, ultrapasando reinos .");
        Sala clareiraDasLavandas = new Sala("Clareira das Lavandas", TipoSala.EVENTO, "Na clareira das lavandas, viajantes adoram descançam enquanto sentem a calmaria no ar.");
        Sala clareiraDasMargaridas = new Sala("Clareira das Margaridas", TipoSala.EVENTO, "Sempre alegre, as margaridas, tornam essa clareira um lugar reconfortante.");
        Sala grutaDoOrvalho = new Sala("Gruta do Orvalho", TipoSala.VENDEDOR, "Existem lugares de mistérios ancestrais no reino, a gruta do orvalho é um deles.");
        Sala valeSolar = new Sala("Vale Solar", TipoSala.EVENTO, "Sentir a toque quente do sol e a sua mais pura energia que emana dos girassois, dizem que até mesmo de noite.");
        Sala valeDasOrquideas = new Sala("Vale das Orquídeas", TipoSala.EVENTO, "Nessa região a sudoeste do reino, grandes cavalheiros foram forjados pelas orquideas..");
        Sala valeDasBrisas = new Sala("Vale das Brisas", TipoSala.EVENTO, "Se busca um momento para relaxar e se divertir, o vale das brisas é capaz de te fazer sonhar acordado");
        Sala caminhoUrtiguento = new Sala("Caminho Urtiguento", TipoSala.COMBATE, "Cuidado para não se espetar, essa area requer agilidade e jovens guerreiros");
        Sala caminhoCaladiano = new Sala("Caminho Caladiano", TipoSala.COMBATE, "Uma zona do reino que requer coragem e determinação, aqui viajantes devem ter atenção");
        Sala pantanoVenenoso = new Sala("Pântano Venenoso", TipoSala.COMBATE, "Um lugar de exilamento e temido por todo o reino.");
        Sala lagoaDosCristais = new Sala("Lagoa dos Cristais", TipoSala.VENDEDOR, "Considerado um local magico, onde magia ancestral flui pela água atravez dos cristais.");
        Sala umbralEspinhento = new Sala("Umbral Espinhento", TipoSala.FINAL, "O umbral espinhento, no passado foi um grande observatório de estudos e pesquisas do reino.");

        // Criando NPCs comuns
        NPC bromelia = new NPC("Bromélia", 100, 10, 50);
        Vendedor polegarVermelho = new Vendedor("Polegar Vermelho");
        NPC trevor = new NPC("Trevor", 100, 10, 50);
        NPC lilavanda = new NPC("Lilavanda", 100, 10, 50);
        NPC marge = new NPC("Marge", 100, 10, 50);
        NPC solare = new NPC("Solare", 100, 10, 50);
        NPC srBob = new NPC("Sr.Bob", 100, 10, 50);
        NPC elquidea = new NPC("Elquidea", 100, 10, 50);

        // Criando NPCs companheiros
        NPCCompanheiro leave = new NPCCompanheiro("Leave", 100, 15, 50);
        NPCCompanheiro gyp = new NPCCompanheiro("Gyp", 100, 15, 50);
        NPCCompanheiro denteLeao = new NPCCompanheiro("Dente de Leão", 100, 30, 1000);

        // Criando NPCs inimigos
        NPCInimigo saxFunge = new NPCInimigo("Sax Funge", 100, 15, 25);
        NPCInimigo bellapapoula = new NPCInimigo("Bellapapoula", 100, 20, 30);
        NPCInimigo rosalia = new NPCInimigo("Rosalia", 120, 20, 40);
        NPCInimigo urtigao = new NPCInimigo("Urtigão", 150, 30, 50);
        NPCInimigo caladino = new NPCInimigo("Caladino", 150, 30, 50);
        NPCInimigo beladona = new NPCInimigo("Beladona", 300, 40, 100);
        NPCInimigo espinho = new NPCInimigo("Espinho", 500, 50, 1000);

        // Criando Itens
        ArtefatoPrincipal tesouraAncestral = new ArtefatoPrincipal("Tesoura Ancestral", 200, 25, 50);
        ArtefatoPrincipal tesouraPrimordial = new ArtefatoPrincipal("Tesoura Primordial", 100, 10, 20);
        ArtefatoPrincipal tesouraMaster = new ArtefatoPrincipal("Tesoura Master", 150, 20, 30);
        ArtefatoPrincipal cajadoAncestral = new ArtefatoPrincipal("Cajado Ancestral", 200, 25, 50);
        ArtefatoPrincipal cajadoPrimordial = new ArtefatoPrincipal("Cajado Primordial", 100, 10, 15);
        ArtefatoPrincipal cajadoMaster = new ArtefatoPrincipal("Cajado Master", 150, 20, 30);
        ArtefatoPrincipal bandolimAncestral = new ArtefatoPrincipal("Bandolim Ancestral", 200, 25, 50);
        ArtefatoPrincipal bandolimPrimordial = new ArtefatoPrincipal("Bandolim Primordial", 100, 10, 15);
        ArtefatoPrincipal bandolimMaster = new ArtefatoPrincipal("Bandolim Master", 150, 20, 30);
        ArtefatoPrincipal arcoAncestral = new ArtefatoPrincipal("Arco Ancestral", 200, 25, 50);
        ArtefatoPrincipal arcoPrimordial = new ArtefatoPrincipal("Arco Primordial", 100, 10, 15);
        ArtefatoPrincipal arcoMaster = new ArtefatoPrincipal("Arco Master", 150, 20, 30);
        Pocao pocaoFertilizante = new Pocao("Poção Fertilizante", 40, 25, 0);
        Pocao pocaoFertilizantePlus = new Pocao("Poção Fertilizante Plus", 60, 50, 0);
        Pocao pocaoFertilizanteSuper = new Pocao("Poção Fertilizante Super", 80, 75, 0);
        Pocao pocaoFertilizanteMaster = new Pocao("Poção Fertilizante Master", 100, 100, 5);
        Pocao pocaoFertilizanteAncestral = new Pocao("Poção Fertilizante Ancestral", 200, 150, 5);
        MagiaCombate poDeFogo = new MagiaCombate("Pó de Fogo", 100, 30);
        MagiaCombate poDeGelo = new MagiaCombate("Pó de Gelo", 100, 30);
        MagiaCombate grandePoda = new MagiaCombate("Grande Poda", 100, 50);
        MagiaCombate sementeLirica = new MagiaCombate("Semente Lirica", 100, 30);

        // Criando listas dos Itens
        List<ArtefatoPrincipal> armaPrincipalGuerreiro = List.of(tesouraAncestral, tesouraMaster, tesouraPrimordial);
        List<ArtefatoPrincipal> armaPrincipalDruida = List.of(cajadoAncestral, cajadoMaster, cajadoPrimordial);
        List<ArtefatoPrincipal> armaPrincipalBardo = List.of(bandolimAncestral, bandolimMaster, bandolimPrimordial);
        List<ArtefatoPrincipal> armaPrincipalRanger = List.of(arcoAncestral, arcoMaster, arcoPrimordial);
        List<Pocao> pocoesVida = List.of(pocaoFertilizante, pocaoFertilizanteAncestral, pocaoFertilizanteMaster, pocaoFertilizantePlus, pocaoFertilizanteSuper);
        List<MagiaCombate> magiaCombates = List.of(poDeFogo, poDeGelo, grandePoda, sementeLirica);

        // Criando listas de npc e inimigos para as salas
        List<NPC> npcsSalaDoTrono = List.of(bromelia);
        List<NPC> npcsPlanicieVerdejante = List.of(trevor);
        List<NPCInimigo> inimigosGrandePenedo = List.of(saxFunge);
        List<NPC> npcsClareiraDasLavandas = List.of(lilavanda);
        List<NPC> npcsClareiraDasMargaridas = List.of(marge);
        List<NPCInimigo> inimigosCampoDasPapoulas = List.of(bellapapoula);
        List<NPCInimigo> inimigosCampoDasRosas = List.of(rosalia);
        List<NPCCompanheiro> companheirosJornada = List.of(leave, gyp);
        List<NPC> npcsValeSolar = List.of(solare);
        List<NPC> npcsValeBrisas = List.of(srBob);
        List<NPC> npcsValeOrquideas = List.of(elquidea);
        List<NPCInimigo> inimigosCaminhoUrtiguento = List.of(urtigao);
        List<NPCInimigo> inimigosCaminhoCaladiano = List.of(caladino);
        List<NPCInimigo> inimigosPantanoVenenoso = List.of(beladona);
        List<NPCInimigo> inimigosUmbralEspinhento = List.of(espinho, beladona);
        List<NPCCompanheiro> companheirosBatalhaFinal = List.of(leave, gyp, denteLeao);

        // Adicionando as listas de armas ao vendedor
        polegarVermelho.addItens(armaPrincipalGuerreiro);
        polegarVermelho.addItens(armaPrincipalDruida);
        polegarVermelho.addItens(armaPrincipalBardo);
        polegarVermelho.addItens(armaPrincipalRanger);

        // Adicionando as poções ao vendedor
        polegarVermelho.addItens(pocoesVida);

        // Adicionando as magias ao vendedor
        polegarVermelho.addItens(magiaCombates);

        // Permissões das armas
        for (ArtefatoPrincipal arma : armaPrincipalGuerreiro) {
            arma.addHeroiPermitido("Guerreiro");
        }
        for (ArtefatoPrincipal arma : armaPrincipalDruida) {
            arma.addHeroiPermitido("Druida");
        }
        for (ArtefatoPrincipal arma : armaPrincipalBardo) {
            arma.addHeroiPermitido("Bardo");
        }
        for (ArtefatoPrincipal arma : armaPrincipalRanger) {
            arma.addHeroiPermitido("Ranger");
        }
        for (Pocao pocao : pocoesVida) {
            pocao.addHeroiPermitido("Guerreiro");
        }
        for (Pocao pocao : pocoesVida) {
            pocao.addHeroiPermitido("Druida");
        }
        for (Pocao pocao : pocoesVida) {
            pocao.addHeroiPermitido("Bardo");
        }
        for (Pocao pocao : pocoesVida) {
            pocao.addHeroiPermitido("Ranger");
        }

        grandePoda.addHeroiPermitido("Guerreiro");
        poDeFogo.addHeroiPermitido("Druida");
        poDeGelo.addHeroiPermitido("Ranger");
        sementeLirica.addHeroiPermitido("Bardo");

        // Adicionando vendedor as salas
        vendinhaCactos.setVendedor(polegarVermelho);
        grutaDoOrvalho.setVendedor(polegarVermelho);
        lagoaDosCristais.setVendedor(polegarVermelho);

        // Adicionando npcs e inimigos as salas
        salaDoTrono.addNPCsComuns(npcsSalaDoTrono);
        planiciesVerdejantes.addNPCsComuns(npcsPlanicieVerdejante);
        grandePenedo.addInimigos(inimigosGrandePenedo);
        clareiraDasLavandas.addNPCsComuns(npcsClareiraDasLavandas);
        clareiraDasMargaridas.addNPCsComuns(npcsClareiraDasMargaridas);
        campoDasPapoulas.addInimigos(inimigosCampoDasPapoulas);
        campoDasRosas.addInimigos(inimigosCampoDasRosas);
        grutaDoOrvalho.addCompanheiros(companheirosJornada);
        valeSolar.addNPCsComuns(npcsValeSolar);
        valeSolar.addCompanheiros(companheirosJornada);
        valeDasBrisas.addNPCsComuns(npcsValeBrisas);
        valeDasBrisas.addCompanheiros(companheirosJornada);
        valeDasOrquideas.addNPCsComuns(npcsValeOrquideas);
        valeDasOrquideas.addCompanheiros(companheirosJornada);
        caminhoUrtiguento.addInimigos(inimigosCaminhoUrtiguento);
        caminhoUrtiguento.addCompanheiros(companheirosJornada);
        caminhoCaladiano.addInimigos(inimigosCaminhoCaladiano);
        caminhoCaladiano.addCompanheiros(companheirosJornada);
        lagoaDosCristais.addCompanheiros(companheirosJornada);
        pantanoVenenoso.addInimigos(inimigosPantanoVenenoso);
        pantanoVenenoso.addCompanheiros(companheirosJornada);
        umbralEspinhento.addInimigos(inimigosUmbralEspinhento);
        umbralEspinhento.addCompanheiros(companheirosBatalhaFinal);

        // Definindo as conexões entre as salas
        salaDoTrono.addSalaProxima(vendinhaCactos);
        vendinhaCactos.addSalaProxima(planiciesVerdejantes);
        vendinhaCactos.addSalaProxima(grandePenedo);

        planiciesVerdejantes.addSalaProxima(campoDasPapoulas);
        planiciesVerdejantes.addSalaProxima(campoDasRosas);

        campoDasPapoulas.addSalaProxima(campoDasRosas);

        grandePenedo.addSalaProxima(clareiraDasLavandas);
        grandePenedo.addSalaProxima(clareiraDasMargaridas);
        clareiraDasLavandas.addSalaProxima(clareiraDasMargaridas);

        campoDasRosas.addSalaProxima(grutaDoOrvalho);
        clareiraDasMargaridas.addSalaProxima(grutaDoOrvalho);

        grutaDoOrvalho.addSalaProxima(valeSolar);
        grutaDoOrvalho.addSalaProxima(valeDasOrquideas);
        grutaDoOrvalho.addSalaProxima(valeDasBrisas);

        valeDasOrquideas.addSalaProxima(caminhoUrtiguento);
        valeDasOrquideas.addSalaProxima(pantanoVenenoso);

        valeDasBrisas.addSalaProxima(caminhoCaladiano);
        valeDasBrisas.addSalaProxima(pantanoVenenoso);

        caminhoCaladiano.addSalaProxima(pantanoVenenoso);
        caminhoUrtiguento.addSalaProxima(pantanoVenenoso);

        valeSolar.addSalaProxima(pantanoVenenoso);

        pantanoVenenoso.addSalaProxima(lagoaDosCristais);

        lagoaDosCristais.addSalaProxima(umbralEspinhento);

        // Adicionando as salas à lista
        salas.add(salaDoTrono);
        salas.add(vendinhaCactos);
        salas.add(planiciesVerdejantes);
        salas.add(grandePenedo);
        salas.add(campoDasPapoulas);
        salas.add(campoDasRosas);
        salas.add(clareiraDasLavandas);
        salas.add(clareiraDasMargaridas);
        salas.add(grutaDoOrvalho);
        salas.add(valeSolar);
        salas.add(valeDasOrquideas);
        salas.add(valeDasBrisas);
        salas.add(caminhoUrtiguento);
        salas.add(pantanoVenenoso);
        salas.add(lagoaDosCristais);
        salas.add(umbralEspinhento);

        // Definindo a sala inicial
        salaAtual = salaDoTrono;
    }

    public Sala getSalaAtual() {
        return salaAtual;
    }

    /**
     * Método que realiza o avanço para a proxima sala
     * @param index Endereço da sala a avançar
     */
    public void avancarParaProximaSala(int index) {
        ArrayList<Sala> proximas = salaAtual.getSalasProximas();
        if (index >= 0 && index < proximas.size()) {
            salaAtual = proximas.get(index);
        } else {
            System.out.println("Opção inválida. Não há sala disponível.");
        }
    }

    /**
     * Metodo que verifica se a sala atual é a sala final do jogo
     * @return Retorna se a sala atual é a ultima
     */
    public boolean estaNaSalaFinal() {
        return salaAtual.getTipo() == TipoSala.FINAL;
    }
}
