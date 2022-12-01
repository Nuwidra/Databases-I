Mi-TEC-Digital
==============
Nombre Estudiante: Jonathan Quesada Salas
Carné:             2020023583

Aplicación Demo para gestionar Estudiantes, Profesores y Cursos.

## Revisión de tarea 1

Nota 8. 

#### Observaciones
1. No está listando los estudiantes por numero de carné de forma ascendente
2. El código de borrando lanza un error. No puede modificar la colección mientras la recorre, en lugar de eso tiene que recorrer la colección entera, tomar de la colección lo que necesite y luego hacer el `remove()`.
3. Agregue un archivo .gitignore
4. En `EstudianteService` hizo uso del método `this.getAll()` para obtener un Estudiante en particular. Pudo haber utilizado `this.getById()` para no tener que recorrerlos todos.
5. En `EstudianteServiceTest` para las pruebas unitarias intente tomar un enfoque de: "lo que pasó antes" y "lo que va a pasar". Es que por ejemplo probar que ud hizo una actualización y luego verificar que la cantidad de estudiantes sigue siendo 3 es poco útil. Hubiera podido obtener de la "base de datos" actual por medio del carné (2), luego hacer la actualización (2) y, como método de verificación comparar que del paso (1) con el del paso (2). Intente un enfoque similar para las pruebas de borrado.


## Revisión de tarea 2

Nota 8.5

El CRUD de profesores no está funcionando bien.

## Revisión de tarea 4

Nota 10

## Revisión de proyecto 2

- Me parece que su archivo `universidad.sql` no fue generado por medio de `mysqldump`. Todo lo demás se ve bien.

Nota: 9