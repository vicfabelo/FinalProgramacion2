package tdas.interfaces;

import modelo.Pedido;

public interface IColaPedidos {

    public Pedido encolarPedido(Pedido pedido);
    public Pedido desencolarPedido();
    public Pedido verFrente();
    public Pedido buscarPedido(int idPedido);
    public void mostrarPedidos();
    public boolean estaVacia();
    public boolean estaLlena();
    public int tamanio();
}
