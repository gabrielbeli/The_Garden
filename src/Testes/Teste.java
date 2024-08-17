package Testes;

import Entidades.Categorias.Guerreiro;
import Combate.GerenciadorCombate;
import Entidades.HeroiBase;
import Entidades.NPC.NPC;
import Entidades.NPC.NPCCompanheiro;
import Entidades.NPC.NPCInimigo;
import Entidades.Vendedor;
import Itens.ArmaPrincipal;
import Itens.MagiaCombate;
import Itens.Pocao;
import Jogo.Sala;
import Jogo.TipoSala;

import java.util.List;


public class Teste {
    public static void main(String[] args) {

        GerenciadorCombate gerenciadorCombate = new GerenciadorCombate();

        HeroiBase vitorConquistador = new HeroiBase("Vitor O Conquistador", 100, 15);
        NPCInimigo inimigo = new NPCInimigo("Java", 100, 20, 100);
        NPCCompanheiro hugoBoss = new NPCCompanheiro("Hugo boss", 100, 10, 50);
        Vendedor maquinaAutomatica = new Vendedor("Maquina");
        NPC batman = new NPC("Batman", 10000, 10000, 1000000000);

        ArmaPrincipal notebook = new ArmaPrincipal("Notebook", 100, 10, 15);
        Pocao kinderBueno = new Pocao("Kinder Bueno", 180, 20, 10);
        MagiaCombate aulaPoo = new MagiaCombate("Aula POO", 100, 50);

        Sala salaTeste = new Sala("Sala Teste", TipoSala.INICIAL, "Sala para testar metodos");

        List<NPCCompanheiro> companheiroList = List.of(hugoBoss);
        List<NPCInimigo> inimigoList = List.of(inimigo);
        List<NPC> npcList = List.of(batman);

        vitorConquistador.setOuro(1000);
        maquinaAutomatica.addItem(notebook);
        maquinaAutomatica.addItem(kinderBueno);
        maquinaAutomatica.addItem(aulaPoo);
        notebook.addHeroiPermitido("Guerreiro");
        kinderBueno.addHeroiPermitido("Guerreiro");
        aulaPoo.addHeroiPermitido("Guerreiro");

        Guerreiro guerreiro = new Guerreiro();
        vitorConquistador.setCategoria(guerreiro);

        // testar loja
        //maquinaAutomatica.interagir(vitorConquistador);

        // testar acesso ao iventario
        //vitorConquistador.acessarInventario(false, inimigo);

        // testar combate
        //gerenciadorCombate.realizarCombate(vitorConquistador, companheiroList,inimigo);

        // testar detalhes heroi
        //vitorConquistador.mostrarDetalhes();

        System.out.println("-------------------------------\n");

        // testar informação sala
        salaTeste.addInimigos(inimigoList);
        salaTeste.addCompanheiros(companheiroList);
        salaTeste.addNPCsComuns(npcList);
       // salaTeste.mostrarDetalhes();

    }
}
