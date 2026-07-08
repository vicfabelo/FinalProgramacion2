package tdas.interfaces;

import modelo.Observacion;

public interface IPilaObservaciones {
    public Observacion apilarObservacion(Observacion observacion);
    public Observacion desapilarObservacion();
    public Observacion topeObservacion();
    public int tamanio();
}
