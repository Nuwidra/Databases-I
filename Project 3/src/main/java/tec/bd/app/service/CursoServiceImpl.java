package tec.bd.app.service;

import tec.bd.app.dao.CursoDAO;
import tec.bd.app.domain.Curso;

import java.util.List;
import java.util.Optional;

public class CursoServiceImpl implements CursoService {

    private CursoDAO cursoDAO;

    public CursoServiceImpl(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }


    @Override
    public List<Curso> getAll() {
        return this.cursoDAO.findAll();
    }

    @Override
    public Optional<Curso> getById(Integer id) {
        if (id<0){
            return Optional.empty();
        }
        return this.cursoDAO.findById(id);
    }

    @Override
    public void addNew(Curso c) {
        Optional<Curso> curso = this.cursoDAO.findById(c.getId());
        if(!curso.isPresent()) {
            this.cursoDAO.save(c);
        }
    }

    @Override
    public Optional<Curso> updateCourse(Curso c) {
        if(this.cursoDAO.findById(c.getId()).isPresent()) {
            return this.cursoDAO.update(c);
        }
        return Optional.empty();
    }

    @Override
    public void deleteCourse(Integer id) {
        if(this.cursoDAO.findById(id).isPresent()) {
            this.cursoDAO.delete(id);
        }
        else{
            System.out.println("No existe");
        }
    }

    @Override
    public List<Curso> getByDepartment(String department) {
        // validar si department es nulo o vacio. Si NO es nulo o vacio se va a poder llamar al DAO
        // sino, se retorna una lista vacia

        if (department != null) {
            return this.cursoDAO.findByDepartment(department);
        } else {
            return null;
        }
    }
}
