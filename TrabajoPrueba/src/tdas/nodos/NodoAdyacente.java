package tdas.nodos;

public class NodoAdyacente {

    private String codigoPasillo;
    private NodoAdyacente siguiente;

    public NodoAdyacente(String codigoPasillo) {
        this.codigoPasillo = codigoPasillo;
        this.siguiente = null;
    }

    public String getCodigoPasillo() {
        return codigoPasillo;
    }

    public void setCodigoPasillo(String codigoPasillo) {
        this.codigoPasillo = codigoPasillo;
    }

    public NodoAdyacente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoAdyacente siguiente) {
        this.siguiente = siguiente;
    }
}