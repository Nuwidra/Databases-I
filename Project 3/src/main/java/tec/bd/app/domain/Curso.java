package tec.bd.app.domain;

public class Curso implements Entity {

    private Integer id;
    private String nombre;
    private String departamento;
    private int creditos;

    public Curso() {

    }

    public Curso(Integer id, String nombre, int creditos, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
}
