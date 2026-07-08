package tdas.implementaciones;

import modelo.Producto;
import tdas.interfaces.IArbolProductos;
import tdas.nodos.NodoArbolProducto;

public class ArbolProductos implements IArbolProductos {

    private NodoArbolProducto raiz;
    private int tamanio;

    public ArbolProductos() {
        raiz = null;
        tamanio = 0;
    }

    public NodoArbolProducto getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoArbolProducto raiz) {
        this.raiz = raiz;
    }

    @Override
    public NodoArbolProducto insertarProducto(
            int codigo,
            Producto producto) {

        NodoArbolProducto nuevo =
                new NodoArbolProducto(
                        codigo,
                        producto);

        if (raiz == null) {

            raiz = nuevo;
            tamanio++;

            return nuevo;
        }

        NodoArbolProducto actual = raiz;
        NodoArbolProducto padre = null;

        while (actual != null) {

            padre = actual;

            if (codigo < actual.getCodigo()) {

                actual = actual.getHijoIzq();

            } else if (codigo > actual.getCodigo()) {

                actual = actual.getHijoDer();

            } else {

                return actual;
            }
        }

        nuevo.setPadre(padre);

        if (codigo < padre.getCodigo()) {

            padre.setHijoIzq(nuevo);

        } else {

            padre.setHijoDer(nuevo);
        }

        tamanio++;

        return nuevo;
    }

    @Override
    public NodoArbolProducto buscarProducto(
            int codigo) {

        NodoArbolProducto actual = raiz;

        while (actual != null) {

            if (codigo == actual.getCodigo()) {
                return actual;
            }

            if (codigo < actual.getCodigo()) {

                actual = actual.getHijoIzq();

            } else {

                actual = actual.getHijoDer();
            }
        }

        return null;
    }

    @Override
    public boolean existeProducto(
            int codigo) {

        return buscarProducto(codigo) != null;
    }

    @Override
    public NodoArbolProducto eliminarProducto(
            int codigo) {

        NodoArbolProducto nodo =
                buscarProducto(codigo);

        if (nodo == null) {
            return null;
        }

        eliminarNodo(nodo);

        tamanio--;

        return nodo;
    }

    private void eliminarNodo(NodoArbolProducto nodo) {

        if (nodo.getHijoIzq() == null &&
                nodo.getHijoDer() == null) {

            reemplazarEnPadre(
                    nodo,
                    null);

        } else if (nodo.getHijoIzq() == null) {

            reemplazarEnPadre(
                    nodo,
                    nodo.getHijoDer());

        } else if (nodo.getHijoDer() == null) {

            reemplazarEnPadre(
                    nodo,
                    nodo.getHijoIzq());

        } else {

            NodoArbolProducto sucesor =
                    minimo(nodo.getHijoDer());

            nodo.setCodigo(
                    sucesor.getCodigo());

            nodo.setProducto(
                    sucesor.getProducto());

            eliminarNodo(sucesor);
        }
    }

    private NodoArbolProducto minimo(
            NodoArbolProducto nodo) {

        while (nodo.getHijoIzq() != null) {

            nodo = nodo.getHijoIzq();
        }

        return nodo;
    }

    private void reemplazarEnPadre(
            NodoArbolProducto nodo,
            NodoArbolProducto nuevo) {

        if (nodo.getPadre() == null) {

            raiz = nuevo;

        } else if (nodo ==
                nodo.getPadre()
                        .getHijoIzq()) {

            nodo.getPadre()
                    .setHijoIzq(nuevo);

        } else {

            nodo.getPadre()
                    .setHijoDer(nuevo);
        }

        if (nuevo != null) {

            nuevo.setPadre(
                    nodo.getPadre());
        }
    }

    @Override
    public int tamanio() {
        return tamanio;
    }


}