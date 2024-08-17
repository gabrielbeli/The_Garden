package Entidades.Categorias;

public class Druida extends Categoria {

    @Override
    public int calcularDano(int forcaBase) {
        return forcaBase + 6;
    }
}