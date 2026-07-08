package tdas.interfaces;

public interface IListaDoble<T> {

    public void agregar(T dato);

    public T obtener(int indice);

    public boolean eliminar(T dato);

    public boolean estaVacia();

    public int tamanio();
}