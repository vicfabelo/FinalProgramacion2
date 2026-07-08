package tdas.implementaciones;

import tdas.interfaces.IListaDoble;
import tdas.nodos.NodoDoble;

public class ListaDoble<T> implements IListaDoble<T> {

    private NodoDoble<T> primero;
    private NodoDoble<T> ultimo;
    private int tamanio;

    public ListaDoble() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    @Override
    public void agregar(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);

        if (estaVacia()) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.setAnterior(ultimo);
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }

        tamanio++;
    }

    @Override
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return null;
        }

        NodoDoble<T> actual = primero;
        int posicion = 0;

        while (posicion < indice) {
            actual = actual.getSiguiente();
            posicion++;
        }

        return actual.getDato();
    }

    @Override
    public boolean eliminar(T dato) {
        NodoDoble<T> actual = primero;

        while (actual != null) {

            if (actual.getDato().equals(dato)) {

                if (actual.getAnterior() != null) {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                } else {
                    primero = actual.getSiguiente();
                }

                if (actual.getSiguiente() != null) {
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                } else {
                    ultimo = actual.getAnterior();
                }

                tamanio--;
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }

    @Override
    public boolean estaVacia() {
        return primero == null;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }
}