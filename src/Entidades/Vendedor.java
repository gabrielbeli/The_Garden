package Entidades;

import Itens.ArmaPrincipal;
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

    public void addItens(List<? extends ItemHeroi> itens) {
        loja.addAll(itens);
    }

    public void removerItemVendido(int indiceItem) {
        if (indiceItem >= 0 && indiceItem < loja.size()) {
            loja.remove(indiceItem);
        }
    }

    public void imprimirLoja() {
        System.out.println("Itens disponíveis na loja:");
        for (int i = 0; i < loja.size(); i++) {
            ItemHeroi item = loja.get(i);
            System.out.println("\nItem " + (i + 1) + ":");
            item.mostrarDetalhes();
        }
    }

    public boolean vender(Heroi heroi, int indiceItem) {
        if (indiceItem < 0 || indiceItem >= loja.size()) {
            System.out.println("Item inválido.");
            return false;
        }

        ItemHeroi item = loja.get(indiceItem);

        if (!item.podeSerUsadoPor(heroi.getCategoria().getClass().getSimpleName())) {
            System.out.println("Este item não pode ser usado por " + heroi.getCategoria() + ".");
            return false;
        }

        if (heroi.getOuro() < item.getPrecoOuro()) {
            System.out.println("Ouro insuficiente para comprar este item.");
            return false;
        }

        heroi.setOuro(heroi.getOuro() - item.getPrecoOuro());

        if (item instanceof ArmaPrincipal) {
            heroi.setArmaPrincipal((ArmaPrincipal) item);
            System.out.println("Arma principal adquirida: " + item.getNome());
        } else if (item instanceof Consumivel) {
            heroi.addAoInventario((Consumivel) item);
            System.out.println("Item adicionado ao inventário: " + item.getNome());
        }

        removerItemVendido(indiceItem);

        System.out.println("Compra concluída com sucesso!");

        return true;
    }

    public String getNome() {
        return nome;
    }

    public void interagir(Heroi heroi) {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Bem-vindo à loja de " + nome + "! O que você gostaria de fazer?");
            System.out.println("-------------------------------\n");
            imprimirLoja();
            System.out.println("-------------------------------\n");
            System.out.println("Digite o número do item que deseja comprar ou 0 para sair da loja: ");

            int escolha = scanner.nextInt();
            if (escolha == 0) {
                System.out.println("Você saiu da loja.");
                System.out.println("-------------------------------\n");
                break;
            } else {
                if (vender(heroi, escolha - 1)) {
                    System.out.println("-------------------------------\n");
                    System.out.println("Deseja comprar outro item? (1 para Sim, 0 para Não): ");
                    int continuar = scanner.nextInt();
                    if (continuar == 0) {
                        System.out.println("-------------------------------\n");
                        System.out.println("Você saiu da loja.");
                        break;
                    }
                }
            }
        }
    }
}
