package tdas.interfaces;

import modelo.Producto;
import tdas.nodos.NodoArbolProducto;

public interface IArbolProductos {

    public NodoArbolProducto insertarProducto(
            int codigo,
            Producto producto);

    public NodoArbolProducto buscarProducto(
            int codigo);

    public NodoArbolProducto eliminarProducto(
            int codigo);

    public boolean existeProducto(
            int codigo);

    public int tamanio();
}