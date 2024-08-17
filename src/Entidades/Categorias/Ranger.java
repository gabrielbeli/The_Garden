package Entidades.Categorias;

public class Ranger extends Categoria {

    @Override
    public int calcularDano(int forcaBase) {
        return forcaBase + 8;
    }
}