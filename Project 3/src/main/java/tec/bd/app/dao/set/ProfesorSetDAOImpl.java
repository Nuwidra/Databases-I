package tec.bd.app.dao.set;

import tec.bd.app.dao.ProfesorDAO;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Profesor;

import java.util.List;

public class ProfesorSetDAOImpl extends GenericSetDAOImpl<Profesor, Integer> implements ProfesorDAO {

    public ProfesorSetDAOImpl(SetDB setDB) {
        super(setDB, Profesor.class);
    }

    @Override
    protected Profesor rowToEntity(Row row) {
        return null;
    }

    @Override
    protected Row entityToRow(Profesor e) {
        return null;
    }

    @Override
    public List<Profesor> findByCity(String ciudad) {
        return null;
    }
}
