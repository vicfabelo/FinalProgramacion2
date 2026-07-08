package tdas.nodos;


import modelo.Observacion;

public class NodoPila {
    private Observacion dato;
    private NodoPila siguiente;

    public NodoPila(Observacion dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Observacion getDato() {
        return dato;
    }

    public void setDato(Observacion dato) {
        this.dato = dato;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila siguiente) {
        this.siguiente = siguiente;
    }

}
