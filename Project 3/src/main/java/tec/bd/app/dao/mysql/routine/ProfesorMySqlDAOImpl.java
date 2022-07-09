package tec.bd.app.dao.mysql.routine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.dao.ProfesorDAO;
import tec.bd.app.dao.mysql.GenericMySqlDAOImpl;
import tec.bd.app.database.mysql.DBProperties;
import tec.bd.app.domain.Profesor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProfesorMySqlDAOImpl extends GenericMySqlDAOImpl<Profesor, Integer> implements ProfesorDAO {
    private static final Logger LOG = LoggerFactory.getLogger(CursoMySqlDAOImpl.class);

    private static final String SQL_SELECT_PROFESOR = "{call seleccionar_todos_profesores()}";
    private static final String SQL_SELECT_PROFESOR_ID = "{call encontrar_profesor_por_id(?)}";
    private static final String SQL_INSERT_PROFESOR = "{call agregar_nuevo_profesor(?, ?, ?, ?)}";
    private static final String SQL_UPDATE_PROFESOR = "{call actualizar_profesor_existente(?, ?, ?, ?)}";
    private static final String SQL_DELETE_PROFESOR = "{call eliminar_profesor(?)}";
    private static final String SQL_FINDBYCITY_PROFESOR = "{call find_by_city_professor(?)}";

    private final DBProperties dbProperties;

    public ProfesorMySqlDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Profesor> findByCity(String ciudad) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_FINDBYCITY_PROFESOR)){
            stmt.setString(1, ciudad);
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs);
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_FINDBYCITY_PROFESOR, e);
        }

        return Collections.emptyList();
    }

    @Override
    public List<Profesor> findAll() {
        try(Connection connection = this.dbProperties.getConnection();
            CallableStatement stmt = connection.prepareCall(SQL_SELECT_PROFESOR)){
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs);
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_PROFESOR, e);
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<Profesor> findById(Integer id) {
        try(Connection connection = this.dbProperties.getConnection();
            CallableStatement stmt = connection.prepareCall(SQL_SELECT_PROFESOR_ID)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs).stream().findFirst();
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_PROFESOR_ID, e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Profesor profesor) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_INSERT_PROFESOR)){

            stmt.setInt(1, profesor.getId());
            stmt.setString(2, profesor.getNombre());
            stmt.setString(3, profesor.getApellido());
            stmt.setString(4, profesor.getCiudad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_INSERT_PROFESOR, e);
        }
    }

    @Override
    public Optional<Profesor> update(Profesor profesor) {

        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_UPDATE_PROFESOR)){

            stmt.setInt(1, profesor.getId());
            stmt.setString(2, profesor.getNombre());
            stmt.setString(3, profesor.getApellido());
            stmt.setString(4, profesor.getCiudad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_UPDATE_PROFESOR, e);
        }

        return Optional.empty();
    }


    @Override
    public void delete(Integer id) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_DELETE_PROFESOR)){
            stmt.setInt(1, id);
            stmt.executeQuery();
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_DELETE_PROFESOR, e);
        }
    }

    @Override
    protected Profesor resultSetToEntity(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var nombre = resultSet.getString("nombre");
        var apellido = resultSet.getString("apellido");
        var ciudad = resultSet.getString("ciudad");
        return new Profesor(id, nombre, apellido, ciudad);
    }

    @Override
    protected List<Profesor> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Profesor> profesor = new ArrayList<>();
        while(resultSet.next()) {
            profesor.add(this.resultSetToEntity(resultSet));
        }
        return profesor;
    }
}