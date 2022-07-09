package tec.bd.app.service;

import tec.bd.app.domain.Profesor;

import java.util.List;
import java.util.Optional;

public interface ProfesorService {

    List<Profesor> getAll();

    Optional<Profesor> findById(Integer id);

    void addNew(Profesor p);

    Optional<Profesor> updateProfessor(Profesor p);

    void deleteProfessor(Integer id);

    // nuevo metodo para ver los profesores por ciudad
    List<Profesor> getProfesorByCity(String department);

}
