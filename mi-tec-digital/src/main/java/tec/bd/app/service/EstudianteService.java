package tec.bd.app.service;

import tec.bd.app.bd.SetDB;
import tec.bd.app.domain.Estudiante;

import java.util.List;
import java.util.stream.Collectors;

public class EstudianteService {

    private SetDB database;

    public EstudianteService(SetDB database) {
        this.database = database;
    }

    public List<Estudiante> getAll() {
        return this.database.getEstudianteTable()
                .stream()
                .collect(Collectors.toList());
    }

    public Estudiante getById(long carne) {
        return this.database.getEstudianteTable().stream().filter(e -> e.getCarne() == carne).findFirst().get();
    }

    public void addNew(Estudiante e) {
        // Verificar si el estudiante que viene por parametro ya existe en la BD
        boolean estado = false;
        var tabla = this.database.getEstudianteTable();
        for(Estudiante persona : tabla){
            if(persona.getCarne() == e.getCarne()){
                estado = true;
                break;}}
        if (!estado){
            this.database.getEstudianteTable().add(e);
        }else {
            System.out.println("\n==El estudiante ya esta reguistrado==");}
    }

    public void updateStudent(Estudiante e) {
        // Implementar codigo de actualizacion
        // Traer el estudiante con carne X
        // modificar el estudiante con los datos del estudiante e
        boolean estado = false;
        var tabla = this.database.getEstudianteTable();
        for (Estudiante persona : tabla){
            if (persona.getCarne() == e.getCarne()){
                persona.setNombre(e.getNombre());
                persona.setApellido(e.getApellido())
                ;persona.setEdad(e.getEdad());
                estado = true;
            }
        }if (!estado){
            System.out.println("\n==La persona no existe en la base==");
        }
    }
    public void deleteStudent(long carne) {
        // implementar codigo de borrado
        // HashSet.remove(e);
        boolean estado = false;
        var tabla = this.database.getEstudianteTable();
        for (Estudiante persona : tabla) {
            if (persona.getCarne() == carne){
                this.database.getEstudianteTable().remove(persona);
                estado = true;
            }
        }if (!estado){
            System.out.println("\n==La persona no existe en la base==");
        }
    }

}
