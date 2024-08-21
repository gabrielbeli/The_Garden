package Entidades;

import Itens.ArtefatoPrincipal;
import Itens.Consumivel;
import Itens.ItemHeroi;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendedor {
    private String nome;
    private List<ItemHeroi> loja;

    public Vendedor(String nome) {
        this.nome = nome;
        this.loja = new ArrayList<>();
    }

    public void addItem(ItemHeroi item) {
        loja.add(item);
    }

    /**
     * Método utilizado para adicionar items a loja de venda
     * @param itens Lista geral de itens
     */
    public void addItens(List<? extends ItemHeroi> itens) {
        loja.addAll(itens);
    }

    /**
     * Metodo que remove um item da loja após ele ser vendido
     * @param indiceItem Valor que identifica a posição do item na lista/loja
     */
    public void removerItemVendido(int indiceItem) {
        if (indiceItem >= 0 && indiceItem < loja.size()) {
            loja.remove(indiceItem);
        }
    }

    /**
     * Metodo que apresenta a lista/loja ao jogador
     */
    public void imprimirLoja() {
        System.out.println("\uD83D\uDC8E Itens disponíveis na loja:");
        for (int i = 0; i < loja.size(); i++) {
            ItemHeroi item = loja.get(i);
            System.out.println("\n\uD83D\uDD36 " + (i + 1) + ":");
            item.mostrarDetalhes();
        }
    }

    /**
     * Método que realiza a venda dos items na loja
     * @param heroi Avatar configurado pelo jogador
     * @param indiceItem Valor que identifica a posição do item na lista/loja
     * @return Retorna se o item pode e foi adiquirido ou não pelo heroi.
     */
    public boolean vender(Heroi heroi, int indiceItem) {
        if (indiceItem < 0 || indiceItem >= loja.size()) {
            System.out.println("⛔ Item inválido.");
            return false;
        }

        ItemHeroi item = loja.get(indiceItem);

        if (!item.podeSerUsadoPor(heroi.getCategoria().getClass().getSimpleName())) {
            System.out.println("Este item não pode ser usado por " + heroi.getCategoria() + ". \uD83D\uDEA8");
            return false;
        }

        if (heroi.getOuro() < item.getPrecoOuro()) {
            System.out.println("Ouro insuficiente para comprar este item. \uD83D\uDEA8");
            return false;
        }

        heroi.setOuro(heroi.getOuro() - item.getPrecoOuro());

        if (item instanceof ArtefatoPrincipal) {
            heroi.setArmaPrincipal((ArtefatoPrincipal) item);
            System.out.println("\n\uD83D\uDD31 Artefato principal adquirida: " + item.getNome());
        } else if (item instanceof Consumivel) {
            heroi.addAoInventario((Consumivel) item);
            System.out.println("\n\uD83C\uDF92 Item adicionado ao inventário: " + item.getNome());
        }

        removerItemVendido(indiceItem);

        System.out.println("\n\uD83E\uDE99 Compra concluída com sucesso! \uD83E\uDE99");

        return true;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Método que configura a interação/acesso ao vendedor
     * @param heroi Avatar criado pelo jogador
     */
    public void interagir(Heroi heroi) {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("\uD83C\uDF35 Bem-vindo à loja de " + nome);

            System.out.println("----------------------------------------------------------------------------------------\n");
            imprimirLoja();
            System.out.println("\n----------------------------------------------------------------------------------------\n");

            System.out.print("\uD83D\uDD79\uFE0F Digite o número do item que deseja comprar ou 0 para sair da loja: ");
            int escolha = scanner.nextInt();

            if (escolha == 0) {
                System.out.println("\nVocê saiu da loja. \uD83D\uDC4B");

                System.out.println("\n----------------------------------------------------------------------------------------\n");
                break;
            } else {
                if (vender(heroi, escolha - 1)) {

                    System.out.print("\n\uD83E\uDE99 Deseja comprar outro item? (1 para Sim, 0 para Não): ");
                    int continuar = scanner.nextInt();

                    if (continuar == 0) {

                        System.out.println("\nVocê saiu da loja.\uD83D\uDC4B");
                        break;
                    }
                }
            }
        }
    }
}
