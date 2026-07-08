package tdas.nodos;

import modelo.Producto;

public class NodoArbolProducto {
    private int codigo;
    private Producto producto;

    private NodoArbolProducto padre;
    private NodoArbolProducto hijoIzq;
    private NodoArbolProducto hijoDer;

    public NodoArbolProducto(int codigo,
                             Producto producto) {

        this.codigo = codigo;
        this.producto = producto;
        this.padre= null;
        this.hijoIzq = null;
        this.hijoDer = null;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public NodoArbolProducto getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(NodoArbolProducto hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public NodoArbolProducto getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(NodoArbolProducto hijoDer) {
        this.hijoDer = hijoDer;
    }

    public NodoArbolProducto getPadre() {
        return padre;
    }

    public void setPadre(NodoArbolProducto padre) {
        this.padre = padre;
    }
}

