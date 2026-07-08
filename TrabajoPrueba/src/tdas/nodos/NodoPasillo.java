package tdas.nodos;

public class NodoPasillo {

    private String codigoPasillo;

    private NodoPasillo siguiente;

    private NodoAdyacente adyacentes;

    private boolean visitado;

    public NodoPasillo(String codigoPasillo) {

        this.codigoPasillo = codigoPasillo;

        this.siguiente = null;
        this.adyacentes = null;
        this.visitado = false;
    }

    public String getCodigoPasillo() {
        return codigoPasillo;
    }

    public void setCodigoPasillo(String codigoPasillo) {
        this.codigoPasillo = codigoPasillo;
    }

    public NodoPasillo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPasillo siguiente) {
        this.siguiente = siguiente;
    }

    public NodoAdyacente getAdyacentes() {
        return adyacentes;
    }

    public void setAdyacentes(NodoAdyacente adyacentes) {
        this.adyacentes = adyacentes;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
}