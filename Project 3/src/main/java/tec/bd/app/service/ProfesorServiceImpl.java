package tec.bd.app.service;

import tec.bd.app.dao.ProfesorDAO;
import tec.bd.app.domain.Profesor;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorDAO profesorDAO;

    public ProfesorServiceImpl(ProfesorDAO profesorDAO) {
        this.profesorDAO = profesorDAO;
    }

    @Override
    public List<Profesor> getAll() {
        return this.profesorDAO.findAll();
    }

    @Override
    public Optional<Profesor> findById(Integer id) {
        if (id > 0){
            return this.profesorDAO.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void addNew(Profesor p) {
        Optional<Profesor> profesor = this.profesorDAO.findById(p.getId());
        if(!profesor.isPresent()) {
            this.profesorDAO.save((Profesor) p);
        }
    }

    @Override
    public Optional<Profesor> updateProfessor(Profesor p) {
        if(p.getId() == this.profesorDAO.findById(p.getId()).get().getId()){
            return this.profesorDAO.update(p);
        }
        else{
            return null;
        }
    }

    @Override
    public void deleteProfessor(Integer id) {
        this.profesorDAO.delete(id);
    }

    public List<Profesor> getProfesorByCity(String department) {
        if (department.isEmpty() || this.profesorDAO.findByCity(department).isEmpty()) {
            return Collections.emptyList();
        }
        return this.profesorDAO.findByCity(department);
    }
}
