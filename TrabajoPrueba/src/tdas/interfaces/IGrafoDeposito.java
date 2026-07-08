package tdas.interfaces;

public interface IGrafoDeposito {

    void insertarPasillo(String codigoPasillo);

    void eliminarPasillo(String codigoPasillo);

    void insertarConexion(String origen,
                          String destino);

    void eliminarConexion(String origen,
                          String destino);

    boolean existePasillo(String codigoPasillo);

    boolean existeConexion(String origen,
                           String destino);

    void mostrarMapaDeposito();

    void recorridoDFS(String inicio);

    void recorridoBFS(String inicio);
}