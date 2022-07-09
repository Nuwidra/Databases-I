package tec.bd.app.service;

import tec.bd.app.domain.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteService {

    List<Estudiante> getAll();

    Optional<Estudiante> getById(Integer carne);

    void addNew(Estudiante e);

    Optional<Estudiante> updateStudent(Estudiante e);

    void deleteStudent(Integer carne);

    List<Estudiante> getStudentsSortedByLastName();

    List<Estudiante> getStudentsByLastName(String lastName);

}
