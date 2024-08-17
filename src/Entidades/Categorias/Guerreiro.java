package Entidades.Categorias;

public class Guerreiro extends Categoria {

    @Override
    public int calcularDano(int forcaBase) {
        return forcaBase + 10;
    }
}