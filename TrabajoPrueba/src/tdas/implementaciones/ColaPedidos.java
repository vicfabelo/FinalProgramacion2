package tdas.implementaciones;

import modelo.Pedido;
import tdas.interfaces.IColaPedidos;
import tdas.nodos.NodoCola;

public class ColaPedidos implements IColaPedidos {
    private NodoCola frente;
    private NodoCola fin;
    private int tamanio;

    public ColaPedidos() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    @Override
    public Pedido encolarPedido(Pedido pedido) {
        NodoCola nuevo= new NodoCola(pedido);
        if (estaVacia()){
            frente= nuevo;
            fin= nuevo;
        }else{
            fin.setSiguiente(nuevo);
            fin=nuevo;
        }
        tamanio++;
        return pedido;

    }

    @Override
    public Pedido desencolarPedido() {
        if (estaVacia()) {
            return null;
        }
        Pedido pedido = frente.getPedido();

        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }

        tamanio--;

        return pedido;
    }

    @Override
    public Pedido verFrente() {
        if (estaVacia()) {
            return null;
        }

        return frente.getPedido();
    }

    @Override
    public Pedido buscarPedido(int idPedido) {
        NodoCola aux = frente;

        while (aux != null) {
            if (aux.getPedido().getIdPedido() == idPedido) {
                return aux.getPedido();
            }
            aux = aux.getSiguiente();
        }

        return null;
    }

    @Override
    public void mostrarPedidos() {

        NodoCola aux = frente;

        while (aux != null) {
            System.out.println(aux.getPedido());
            aux = aux.getSiguiente();
        }


    }

    @Override
    public boolean estaVacia() {
        return frente == null;
    }

    @Override
    public boolean estaLlena() {
        return false;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }
}
