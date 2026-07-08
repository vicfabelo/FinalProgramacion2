package modelo;

public class Ubicacion {
    private String sector;
    private int estanteria;
    private int nivel;

    public Ubicacion(String sector, int estanteria, int nivel) {
        this.sector = sector;
        this.estanteria = estanteria;
        this.nivel = nivel;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getEstanteria() {
        return estanteria;
    }

    public void setEstanteria(int estanteria) {
        this.estanteria = estanteria;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "sector='" + sector + '\'' +
                ", estanteria=" + estanteria +
                ", nivel=" + nivel +
                '}';
    }
}
