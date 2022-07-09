package tec.bd.app.dao.mysql.routine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.dao.CursoDAO;
import tec.bd.app.dao.mysql.GenericMySqlDAOImpl;
import tec.bd.app.database.mysql.DBProperties;
import tec.bd.app.domain.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CursoMySqlDAOImpl extends GenericMySqlDAOImpl<Curso, Integer> implements CursoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CursoMySqlDAOImpl.class);

    private static final String SQL_SELECT_CURSO = "{call seleccionar_todos_cursos()}";
    private static final String SQL_SELECT_CURSO_ID = "{call encontrar_curso_por_id(?)}";
    private static final String SQL_INSERT_CURSO = "{call agregar_nuevo_curso(?, ?, ?, ?)}";
    private static final String SQL_UPDATE_CURSO = "{call actualizar_curso_existente(?, ?, ?, ?)}";
    private static final String SQL_DELETE_CURSO = "{call eliminar_curso(?)}";
    private static final String SQL_FINDBYDEPARTMENT_CURSO = "{call find_by_department_course(?)}";

    private final DBProperties dbProperties;

    public CursoMySqlDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Curso> findByDepartment(String department) {
        try (Connection connection = this.dbProperties.getConnection();
        CallableStatement stmt = connection.prepareCall(SQL_FINDBYDEPARTMENT_CURSO)){
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs);
        } catch (SQLException c) {
            LOG.error("Error when running {}", SQL_FINDBYDEPARTMENT_CURSO, c);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Curso> findAll() {
        try(Connection connection = this.dbProperties.getConnection();
            CallableStatement stmt = connection.prepareCall(SQL_SELECT_CURSO)){
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs);
        }catch (SQLException e){
            LOG.error("Error when running {}",SQL_SELECT_CURSO, e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Curso> findById(Integer id) {
        try(Connection connection = this.dbProperties.getConnection();
            CallableStatement stmt = connection.prepareCall(SQL_SELECT_CURSO_ID)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs).stream().findFirst();
        }catch (SQLException e){
            LOG.error("Error when running {}",SQL_SELECT_CURSO_ID, e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Curso curso) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_INSERT_CURSO)){

            stmt.setInt(1, curso.getId());
            stmt.setString(2, curso.getNombre());
            stmt.setInt(3, curso.getCreditos());
            stmt.setString(4, curso.getDepartamento());
            stmt.executeUpdate();
        } catch (SQLException c) {
            LOG.error("Error when running {}", SQL_INSERT_CURSO, c);
        }
    }

    @Override
    public Optional<Curso> update(Curso curso) {

        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_UPDATE_CURSO)){

            stmt.setInt(1, curso.getId());
            stmt.setString(2, curso.getNombre());
            stmt.setInt(3, curso.getCreditos());
            stmt.setString(4, curso.getDepartamento());
            stmt.executeUpdate();
        } catch (SQLException c) {
            LOG.error("Error when running {}", SQL_UPDATE_CURSO, c);
        }

        return Optional.empty();
    }


    @Override
    public void delete(Integer id) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_DELETE_CURSO)){
            stmt.setInt(1, id);
            stmt.executeQuery();
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_DELETE_CURSO, e);
        }
    }

    @Override
    protected Curso resultSetToEntity(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var nombre = resultSet.getString("nombre");
        var departamento = resultSet.getString("departamento");
        var creditos = resultSet.getInt("creditos");


        return new Curso(id, nombre, creditos, departamento);
    }

    @Override
    protected List<Curso> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        while(resultSet.next()) {
            cursos.add(this.resultSetToEntity(resultSet));
        }
        return cursos;
    }
}