package tec.bd.app.dao;

import tec.bd.app.domain.Profesor;

import java.util.List;

public interface ProfesorDAO extends GenericDAO<Profesor, Integer> {

    // un nuevo metodo para buscar por ciudad
    List<Profesor> findByCity(String ciudad);
}
