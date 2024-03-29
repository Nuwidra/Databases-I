package tec.bd.app;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.domain.Curso;
import tec.bd.app.domain.Estudiante;
import tec.bd.app.domain.Profesor;
import tec.bd.app.service.CursoService;
import tec.bd.app.service.EstudianteService;
import tec.bd.app.service.ProfesorService;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;



public class App  {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public static void main(String[] args) {

        ApplicationContext applicationContext = ApplicationContext.init();
        var estudianteService = applicationContext.getEstudianteService();
        var cursoService = applicationContext.getCursoService();
        var profesorService = applicationContext.getProfesorService();


//        var estudianteController = new EstudianteController(estudianteService);
//        var cursoController = new CursoController(cursoService);

//        get("/estudiante", estudianteController::estudiantes);
//        get("/estudiante/:carnet", estudianteController::estudiante);
//        get("/cursos", cursoController::cursos);

        Options options = new Options();

        //------------------------------------------------------------------------
        // Opciones para estudiante

        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Ayuda: ver argumentos soportados")
                .required(false)
                .build());

        options.addOption(Option.builder("ec")
                .longOpt("estudiante-nuevo")
                .hasArg(true)
                .numberOfArgs(5)
                .desc("Agregar Estudiante: carne, nombre, apellido, fecha de nacimiento y total creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("er")
                .longOpt("estudiate-ver-todos")
                .desc("Ver todos los estudiantes")
                .required(false)
                .build());

        options.addOption(Option.builder("eid")
                .longOpt("estudiante-por-carne")
                .hasArg()
                .desc("Ver Estudiante por carne: ver un estudiante por carne")
                .required(false)
                .build());

        options.addOption(Option.builder("eu")
                .longOpt("estudiante-actualizar")
                .numberOfArgs(5)
                .desc("Actualizar estudiante: carne, nombre, apellido, fecha de nacimiento y total creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("ed")
                .longOpt("estudiante-borrar")
                .hasArg(true)
                .desc("Borrar estudiante: el carne es requerido")
                .required(false)
                .build());

        options.addOption(Option.builder("erln")
                .longOpt("estudiante-ordenar-por-apellido")
                .desc("Ver los estudiantes ordenados por apellido")
                .required(false)
                .build());

        options.addOption(Option.builder("eln")
                .longOpt("estudiante-buscar-por-apellido")
                .hasArg(true)
                .desc("Buscar los estudiantes por apellido")
                .required(false)
                .build());

        //------------------------------------------------------------------------
        // Opciones para curso

        options.addOption(Option.builder("cc")
                .longOpt("curso-nuevo")
                .hasArg(true)
                .numberOfArgs(4)
                .desc("Agregar Curso: id, nombre, departamento, creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("cr")
                .longOpt("cursos-ver-todos")
                .desc("Ver todos los cursos.")
                .required(false)
                .build());

        options.addOption(Option.builder("cid")
                .longOpt("curso-por-id")
                .hasArg()
                .desc("Ver curso por id")
                .required(false)
                .build());

        options.addOption(Option.builder("cu")
                .longOpt("curso-actualizar")
                .numberOfArgs(4)
                .desc("Actualizar curso: id, nombre, departamento, creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("cd")
                .longOpt("curso-borrar")
                .hasArg(true)
                .desc("Borrar curso: el id es requerido")
                .required(false)
                .build());

        options.addOption(Option.builder("crd")
                .longOpt("curso-ver-por-departamento")
                .hasArg(true)
                .desc("Ver curso por departamento")
                .required(false)
                .build());

        //------------------------------------------------------------------------
        // Opciones para profesor

        options.addOption(Option.builder("pc")
                .longOpt("profesor-nuevo")
                .hasArg(true)
                .numberOfArgs(4)
                .desc("Agregar Profesor: id, nombre, apellido, ciudad son requeridos.")
                .required(false)
                .build());

        options.addOption(Option.builder("pr")
                .longOpt("profesor-ver-todos")
                .desc("Ver todos los profesores.")
                .required(false)
                .build());

        options.addOption(Option.builder("pid")
                .longOpt("profesor-por-id")
                .hasArg()
                .desc("Ver profesor por id")
                .required(false)
                .build());

        options.addOption(Option.builder("pu")
                .longOpt("profesor-actualizar")
                .numberOfArgs(4)
                .desc("Actualizar profesor: id, nombre, apellido, ciudad son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("pd")
                .longOpt("profesor-borrar")
                .hasArg(true)
                .desc("Borrar profesor: el id es requerido")
                .required(false)
                .build());

        options.addOption(Option.builder("prc")
                .longOpt("profesor-por-ciudad")
                .hasArg(true)
                .desc("Ver profesores por ciudad")
                .required(false)
                .build());

        CommandLineParser parser = new DefaultParser();

        try {
            var cmd = parser.parse(options, args);

            //------------------------------------------------------------------------
            // Opciones para estudiante

            if(cmd.hasOption("er")) {
                // Mostrar todos los estudiantes
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("eid")) {
                // Mostrar un estudiante por carne
                var carne = cmd.getOptionValue("eid");
                showStudentInfo(estudianteService, Integer.parseInt(carne));
            } else if(cmd.hasOption("ec")) {
                // Crear/Agregar un nuevo estudiante
                var newStudentValues = cmd.getOptionValues("ec");
                addNewStudent(estudianteService,
                        Integer.parseInt(newStudentValues[0]),
                        newStudentValues[1],
                        newStudentValues[2],
                        dateFromString(newStudentValues[3]),
                        Integer.parseInt(newStudentValues[4]));
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("ed")) {
                // Borrar/remover un estudiante
                var carne = cmd.getOptionValue("ed");
                deleteStudent(estudianteService, Integer.parseInt(carne));
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("eu")) {
                // Actualizar datos de un estudiante
                var newStudentValues = cmd.getOptionValues("eu");
                updateStudent(estudianteService,
                        Integer.parseInt(newStudentValues[0]),
                        newStudentValues[1],
                        newStudentValues[2],
                        dateFromString(newStudentValues[3]),
                        Integer.parseInt(newStudentValues[4]));
                showAllStudents(estudianteService);

            } else if(cmd.hasOption("erln")) {
                // Ver todos los estudiantes ordenados por apellido
                System.out.println("IMPLEMENTAR: Ver todos los estudiantes ordenados por apellido");
                showAllStudentsSortedByLastname(estudianteService);

            } else if(cmd.hasOption("eln")) {
                // Ejemplo: -eln Rojas
                // Ver todos los estudiantes con un apellido en particular
                System.out.println("IMPLEMENTAR: Ver todos los estudiantes con un apellido en particular");
                var apellido = cmd.getOptionValue("eln");
                showAllStudentsByLastname(estudianteService,apellido);

            //------------------------------------------------------------------------
            // Opciones para curso

            } else if(cmd.hasOption("cr")) {
                // Mostrar todos los estudiantes
//                System.out.println("IMPLEMENTAR: Mostrar todos los cursos");
                showAllCourses(cursoService);

            } else if(cmd.hasOption("cid")) {
                // Mostrar un curso por id
                System.out.println("IMPLEMENTAR: Mostrar curso por id");
                var id = cmd.getOptionValue("cid");
                showCourseInfo(cursoService, Integer.parseInt(id));

            } else if(cmd.hasOption("cc")) {
                // Crear/Agregar un nuevo curso
                System.out.println("IMPLEMENTAR: Crear/Agregar un nuevo curso");
                var newCurseValues = cmd.getOptionValues("cc");
                addNewCourse(cursoService,
                        Integer.parseInt(newCurseValues[0]),
                        newCurseValues[1],
                        Integer.parseInt(newCurseValues[2]),
                        (newCurseValues[3]));
                showAllCourses(cursoService);

            } else if(cmd.hasOption("cd")) {
                // Borrar/remover un curso
                System.out.println("IMPLEMENTAR: Borrar/remover un curso");
                var id = cmd.getOptionValue("cd");
                deleteCourse(cursoService, Integer.parseInt(id));
                showAllCourses(cursoService);

            } else if(cmd.hasOption("cu")) {
                // Actualizar datos de un curso
                System.out.println("IMPLEMENTAR: Actualizar datos de un curso");
                var newCurseValues = cmd.getOptionValues("cu");
                updateCourse(cursoService,
                        Integer.parseInt(newCurseValues[0]),
                        newCurseValues[1],
                        Integer.parseInt(newCurseValues[2]),
                        (newCurseValues[3]));
                showAllCourses(cursoService);

            } else if(cmd.hasOption("crd")) {
                // Ver cursos por departamento
                System.out.println("IMPLEMENTAR: ver cursos por departamento");
                var departamento = cmd.getOptionValue("crd");
                showCoursesByDepartment(cursoService,departamento);

            //------------------------------------------------------------------------
            // Opciones para profesor

            } else if(cmd.hasOption("pr")) {
                // Mostrar todos los profesores
                System.out.println("IMPLEMENTAR: Mostrar todos los profesores");
                showAllProfessors(profesorService);

            } else if(cmd.hasOption("pid")) {
                // Mostrar un profesor por id
                System.out.println("IMPLEMENTAR: Mostrar profesor por id");
                var id = cmd.getOptionValue("pid");
                showProfessorInfo(profesorService, Integer.parseInt(id));

            } else if(cmd.hasOption("pc")) {
                // Crear/Agregar un nuevo profesor
                System.out.println("IMPLEMENTAR: Crear/Agregar un nuevo profesor");
                var newProffesorValues = cmd.getOptionValues("pc");
                addNewProfessor(profesorService,
                        Integer.parseInt(newProffesorValues[0]),
                        newProffesorValues[1],
                        newProffesorValues[2],
                        newProffesorValues[3]);
                showAllProfessors(profesorService);

            } else if(cmd.hasOption("pd")) {
                // Borrar/remover un profesor
                System.out.println("IMPLEMENTAR: Borrar/remover un profesor");
                var indice  = cmd.getOptionValue("pd");
                deleteProfessor(profesorService, Integer.parseInt(indice));
                showAllProfessors(profesorService);

            } else if(cmd.hasOption("pu")) {
                // Actualizar datos de un profesor
                System.out.println("IMPLEMENTAR: Actualizar datos de un profesor");
                var newProffesorValues = cmd.getOptionValues("pu");
                updateProfessor(profesorService,
                        Integer.parseInt(newProffesorValues[0]),
                        newProffesorValues[1],
                        newProffesorValues[2],
                        newProffesorValues[3]);
                showAllProfessors(profesorService);

            } else if(cmd.hasOption("prc")) {
                // Ver profesores por ciudad
                System.out.println("IMPLEMENTAR: ver profesores por ciudad");
                var ciudad = cmd.getOptionValue("prc");
                showProfessorByCity(profesorService,ciudad);


            //------------------------------------------------------------------------


            } else if(cmd.hasOption("h")) {
                var formatter = new HelpFormatter();
                formatter.printHelp( "Estos son los commandos soportados", options );
            } else {
                var formatter = new HelpFormatter();
                formatter.printHelp( "Estos son los comandos soportados", options );
            }
        } catch (ParseException pe) {
            System.out.println("Error al parsear los argumentos de linea de comandos!");
            System.out.println("Por favor, seguir las siguientes instrucciones:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "Los siguientes son los comandos soportados", options );
            System.exit(1);
        }
    }

    // ------------------------------------------------------------------
    //                            ESTUDIANTES
    // ------------------------------------------------------------------
    public static void showAllStudents(EstudianteService estudianteService) {

        System.out.println("\n\n");
        System.out.println("Lista de Estudiantes");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Carne\t\tNombre\t\tApellido\tFecha Nacimiento\tCreditos");
        System.out.println("-----------------------------------------------------------------------");
        for(Estudiante estudiante : estudianteService.getAll()) {
            System.out.println(estudiante.getCarne() + "\t\t" + estudiante.getNombre() + "\t\t" +estudiante.getApellido() + "\t\t"+ estudiante.getFechaNacimiento() + "\t\t" + estudiante.getTotalCreditos());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showStudentInfo(EstudianteService estudianteService, int carne) {
        Optional<Estudiante> estudiante = estudianteService.getById(carne);
        if(estudiante.isPresent()) {
            System.out.println("Estudiante: " + estudiante.get().getNombre() + " " + estudiante.get().getApellido());
            System.out.println("Carne: " + estudiante.get().getCarne());
        } else {
            System.out.println("Estudiante con carne: " + carne + " no existe");
        }
    }

    public static void addNewStudent(EstudianteService estudianteService, int id, String nombre, String apellido, Date fechaNacimiento, int totalCreditos) {
        var nuevoEstudiante = new Estudiante(id, nombre, apellido, fechaNacimiento, totalCreditos);
        estudianteService.addNew(nuevoEstudiante);
    }


    public static void deleteStudent(EstudianteService estudianteService, int carne) {
        estudianteService.deleteStudent(carne);
    }

    public static void updateStudent(EstudianteService estudianteService, int id, String nombre, String apellido, Date fechaNacimiento, int totalCreditos) {
        var nuevoEstudiante = new Estudiante(id, nombre, apellido, fechaNacimiento, totalCreditos);
        System.out.println("1 App");
        estudianteService.updateStudent(nuevoEstudiante);
    }

    public static void showAllStudentsByLastname(EstudianteService estudianteService,String apellido) {
        var estudiantes = estudianteService.getStudentsByLastName(apellido);
        System.out.println("\n\n");
        System.out.println("Lista de Estudiantes");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ID\t\tNombre\t\tApellido\t\tEdad");
        System.out.println("-----------------------------------------------------------------------");
        for(Estudiante estudiante : estudiantes) {
            System.out.println(estudiante.getId() + "\t\t" + estudiante.getNombre() + "\t\t" +estudiante.getApellido());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }
    public static void showAllStudentsSortedByLastname(EstudianteService estudianteService){
        var estudiantes = estudianteService.getStudentsSortedByLastName();

        System.out.println("\n\n");
        System.out.println("Lista de Estudiantes");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ID\t\tNombre\t\tApellido");
        System.out.println("-----------------------------------------------------------------------");
        for(Estudiante estudiante : estudiantes) {
            System.out.println(estudiante.getId() + "\t\t" + estudiante.getNombre() + "\t\t" +estudiante.getApellido());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    // ----------------------------------------------------------------
    //                            CURSOS
    // ----------------------------------------------------------------
    public static void showAllCourses(CursoService cursoService) {

        System.out.println("\n\n");
        System.out.println("Lista de Cursos");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ID\t\tNombre\t\tCréditos\t\tDepartamento");
        System.out.println("-----------------------------------------------------------------------");
        for(Curso curso : cursoService.getAll()) {
            System.out.println(curso.getId() + "\t\t" + curso.getNombre() + "\t\t" + curso.getCreditos() + "\t\t"+ curso.getDepartamento());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showCourseInfo(CursoService cursoService, int id) {
        Optional<Curso> curso = cursoService.getById(id);
        Curso mi_curso = curso.get();
        if(curso.isPresent()) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("ID\t\tNombre\t\tCréditos\t\tDepartamento");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(mi_curso.getId() + "\t\t" + mi_curso.getNombre() + "\t\t" + mi_curso.getCreditos() + "\t\t"+ mi_curso.getDepartamento());
        } else {
            System.out.println("Curso con ID: " + id + " no existe");
        }
    }

    public static void addNewCourse(CursoService cursoService, int id, String nombre, int creditos, String departamento) {
        var nuevoCurso = new Curso(id, nombre, creditos, departamento);
        cursoService.addNew(nuevoCurso);
    }

    public static void deleteCourse(CursoService cursoService, int id) {
        cursoService.deleteCourse(id);
    }

    public static void updateCourse(CursoService cursoService, int id, String nombre, int creditos, String departamento) {
        var nuevoCurso = new Curso(id, nombre, creditos, departamento);
        cursoService.updateCourse(nuevoCurso);
    }

    public static void showCoursesByDepartment(CursoService cursoService, String departamento) {
        var cursos = cursoService.getByDepartment(departamento);
        System.out.println("\n\n");
        System.out.println("Lista de Cursos");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ID\t\tNombre\t\tCréditos\t\tDepartamento");
        System.out.println("-----------------------------------------------------------------------");
        for(Curso curso : cursos) {
            System.out.println(curso.getId() + "\t\t" + curso.getNombre() + "\t\t" + curso.getCreditos() + "\t\t"+ curso.getDepartamento());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    // ----------------------------------------------------------------
    //                            PROFESORES
    // ----------------------------------------------------------------
    public static void showAllProfessors(ProfesorService profesorService) {

        System.out.println("\n\n");
        System.out.println("Lista de Profesores");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ID\t\tNombre\t\t\tApellido\t\tCiudad");
        System.out.println("-----------------------------------------------------------------------");
        for(Profesor profesor : profesorService.getAll()) {
            System.out.println(profesor.getId() + "\t\t" + profesor.getNombre() + "\t\t" + profesor.getApellido() + "\t\t"+ profesor.getCiudad());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showProfessorInfo(ProfesorService profesorService, int id) {
        Optional<Profesor> mi_profesor = profesorService.findById(id);
        Profesor profesor = mi_profesor.get();
        if(mi_profesor.isPresent()) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("ID\t\tNombre\t\t\tApellido\t\tCiudad");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(profesor.getId() + "\t\t" + profesor.getNombre() + "\t\t" + profesor.getApellido() + "\t\t"+ profesor.getCiudad());
        } else {
            System.out.println("Curso con ID: " + id + " no existe");
        }
    }

    public static void addNewProfessor(ProfesorService profesorService, int id, String nombre, String apellido, String ciudad) {
        var nuevoProfesor = new Profesor(id, nombre, apellido, ciudad);
        profesorService.addNew(nuevoProfesor);
    }

    public static void deleteProfessor(ProfesorService profesorService, int id) {
        profesorService.deleteProfessor(id);
    }

    public static void updateProfessor(ProfesorService profesorService, int id, String nombre, String apellido, String ciudad) {
        var nuevoProfesor = new Profesor(id, nombre, apellido, ciudad);
        profesorService.updateProfessor(nuevoProfesor);
    }

    public static void showProfessorByCity(ProfesorService profesorService, String ciudad) {

        var profes = profesorService.getProfesorByCity(ciudad);
        System.out.println("\n\n");
        System.out.println("Lista de Profesores");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("ID\t\tNombre\t\tApellidos\t\tCiudad");
        System.out.println("-----------------------------------------------------------------------");
        for(Profesor profesor : profes) {
            System.out.println(profesor.getId() + "\t\t" + profesor.getNombre() + "\t\t" + profesor.getApellido() + "\t\t"+ profesor.getCiudad());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    // ---------------------------------------------------------------------
    //                          Una conversión útil
    // ---------------------------------------------------------------------
    private static Date dateFromString(String fecha) {
        Objects.requireNonNull(fecha, "Debe de proporcionar una fecha");
        try {
            return DATE_FORMAT.parse(fecha);
        } catch (java.text.ParseException e) {
            throw new RuntimeException("La fecha proporcionada es invalida", e);
        }
    }
}
