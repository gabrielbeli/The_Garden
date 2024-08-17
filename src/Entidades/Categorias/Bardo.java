package Entidades.Categorias;

public class Bardo extends Categoria {

    @Override
    public int calcularDano(int forcaBase) {
        return forcaBase + 4;
    }
}