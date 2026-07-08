package tdas.nodos;

public class NodoColaPasillo {

    private NodoPasillo dato;
    private NodoColaPasillo siguiente;

    public NodoColaPasillo(NodoPasillo dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public NodoPasillo getDato() {
        return dato;
    }

    public void setDato(NodoPasillo dato) {
        this.dato = dato;
    }

    public NodoColaPasillo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoColaPasillo siguiente) {
        this.siguiente = siguiente;
    }
}