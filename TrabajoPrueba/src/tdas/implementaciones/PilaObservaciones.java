package tdas.implementaciones;

import modelo.Observacion;
import tdas.interfaces.IPilaObservaciones;
import tdas.nodos.NodoPila;

public class PilaObservaciones implements IPilaObservaciones {

    private NodoPila tope;
    private int tamanio;

    public PilaObservaciones() {
        tope = null;
        tamanio = 0;
    }

    @Override
    public Observacion apilarObservacion(Observacion observacion) {

        NodoPila nuevo = new NodoPila(observacion);

        nuevo.setSiguiente(tope);

        tope = nuevo;

        tamanio++;

        return observacion;
    }

    @Override
    public Observacion desapilarObservacion() {

        if (tope == null) {
            return null;
        }

        Observacion dato = tope.getDato();

        tope = tope.getSiguiente();

        tamanio--;

        return dato;
    }

    @Override
    public Observacion topeObservacion() {

        if (tope == null) {
            return null;
        }

        return tope.getDato();
    }

    @Override
    public int tamanio() {
        return tamanio;
    }
}