package tec.bd.app.dao.mysql.routine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.dao.EstudianteDAO;
import tec.bd.app.dao.mysql.GenericMySqlDAOImpl;
import tec.bd.app.database.mysql.DBProperties;
import tec.bd.app.domain.Estudiante;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EstudianteMySqlDAOImpl extends GenericMySqlDAOImpl<Estudiante, Integer> implements EstudianteDAO {

    private static final Logger LOG = LoggerFactory.getLogger(EstudianteMySqlDAOImpl.class);

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_ESTUDIANTES = "{call selecionar_todos_estudiantes()}";
    private static final String SQL_INSERT_ESTUDIANTE = "{call agregar_nuevo_estudiante(?, ?, ?, ?, ?)}";

    // ===================================
    private static final String SQL_UPDATE_ESTUDIANTE = "{call actualizar_estudiante_existente(?, ?, ?, ?, ?)}";
    private static final String SQL_SELECT_ESTUDIANTE_ID = "{call encontrar_estudiante_por_id(?)}";
    private static final String SQL_DELETE_ESTUDIANTE = "{call eliminar_estudiante(?)}";
    // ===================================

    private static final String SQL_FINDBYLASTNAME_ESTUDIANTE = "{call find_by_last_name_student(?)}";
    private static final String SQL_FINDALLSORTBYLASTNAME_ESTUDIANTE = "{call order_by_last_name_student()}";


    public EstudianteMySqlDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Estudiante> findByLastName(String apellido) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_FINDBYLASTNAME_ESTUDIANTE)){
            stmt.setString(1, apellido);
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs);
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_FINDBYLASTNAME_ESTUDIANTE, e);
        }

        return Collections.emptyList();
    }

    @Override
    public List<Estudiante> findAllSortByLastName() {
        try(Connection connection = this.dbProperties.getConnection();
            CallableStatement stmt = connection.prepareCall(SQL_FINDALLSORTBYLASTNAME_ESTUDIANTE)){
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs);
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_FINDALLSORTBYLASTNAME_ESTUDIANTE, e);
        }

        return Collections.emptyList();
    }

    @Override
    public List<Estudiante> findAll() {
        try(Connection connection = this.dbProperties.getConnection();
            CallableStatement stmt = connection.prepareCall(SQL_SELECT_ESTUDIANTES)){
                ResultSet rs = stmt.executeQuery();
                return this.resultSetToList(rs);
        }catch (SQLException e){
            LOG.error("Error when running {}",SQL_SELECT_ESTUDIANTES, e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Estudiante> findById(Integer id) {
        try(Connection connection = this.dbProperties.getConnection();
            CallableStatement stmt = connection.prepareCall(SQL_SELECT_ESTUDIANTE_ID)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            return this.resultSetToList(rs).stream().findFirst();
        }catch (SQLException e){
            LOG.error("Error when running {}",SQL_SELECT_ESTUDIANTE_ID, e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Estudiante estudiante) {
        try (Connection connection = this.dbProperties.getConnection();
        CallableStatement stmt = connection.prepareCall(SQL_INSERT_ESTUDIANTE)){
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            String sfecha = fecha.format(estudiante.getFechaNacimiento());
            java.sql.Date sqlfecha = java.sql.Date.valueOf(sfecha);

            stmt.setInt(1, estudiante.getId());
            stmt.setString(2, estudiante.getNombre());
            stmt.setString(3, estudiante.getApellido());
            stmt.setDate(4, sqlfecha);
            stmt.setInt(5, estudiante.getTotalCreditos());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_INSERT_ESTUDIANTE, e);
        }
    }

    @Override
    public Optional<Estudiante> update(Estudiante estudiante) {
        System.out.println("Actualizar Estudiantes");
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_UPDATE_ESTUDIANTE)){
            //connection.setAutoCommit(false);
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            String stringfecha = fecha.format(estudiante.getFechaNacimiento());
            java.sql.Date sqlfecha = java.sql.Date.valueOf(stringfecha);
            stmt.setInt(1, estudiante.getId());
            stmt.setString(2, estudiante.getNombre());
            stmt.setString(3, estudiante.getApellido());
            stmt.setDate(4, sqlfecha);
            stmt.setInt(5, estudiante.getTotalCreditos());
            var result = stmt.executeUpdate();
            System.out.println("Resultado " + result);
            //connection.commit();
            //connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            // LOG.error("Error when running {}", SQL_UPDATE_ESTUDIANTE, e);
        }

        return Optional.empty();
    }



    @Override
    public void delete(Integer id) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(SQL_DELETE_ESTUDIANTE)){
            stmt.setInt(1, id);
            stmt.executeQuery();
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_DELETE_ESTUDIANTE, e);
        }
    }

    @Override
    protected Estudiante resultSetToEntity(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var nombre = resultSet.getString("nombre");
        var apellido = resultSet.getString("apellido");
        var fechaNacimiento = resultSet.getDate("fecha_nacimiento");
        var totalCreditos = resultSet.getInt("total_creditos");
        return new Estudiante(id, nombre, apellido, fechaNacimiento, totalCreditos);
    }

    @Override
    protected List<Estudiante> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        while(resultSet.next()) {
            estudiantes.add(this.resultSetToEntity(resultSet));
        }
        return estudiantes;
    }

}
