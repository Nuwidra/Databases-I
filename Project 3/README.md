Mi-TEC-Digital
==============
[![Java CI with Maven](https://github.com/Nuwidra/mi-tec-digital/actions/workflows/maven.yml/badge.svg)](https://github.com/Nuwidra/mi-tec-digital/actions/workflows/maven.yml)

Aplicación Demo para gestionar Estudiantes, Profesores y Cursos.


**Observaciones sobre algunas sentencias:**

mvn exec:java -Dexec.args="-cc 1 Genetica Biologia 4", para agregar un nuevo curso

mvn exec:java -Dexec.args="-cu 1 Generica Biologia 3", para actualizar un curso existente.

**Por favor usar:**

mvn exec:java -Dexec.args="-cc 1 Genetica 4 Biologia", para agregar un nuevo curso

mvn exec:java -Dexec.args="-cu 1 Generica 3 Biologia", para actualizar un curso existente.


**Y en las sentencias:** 

mvn exec:java -Dexec.args="-eld Rojas", para obtenerlos Estudiantes con apellido“Rojas”

mvn exec:java -Dexec.args="-erld 1", para obtenerla lista de todos los estudiantesordenados alfabéticamente por apellido

**En el App estaban como:**

mvn exec:java -Dexec.args="-eln Rojas", para obtenerlos Estudiantes con apellido “Rojas”

mvn exec:java -Dexec.args="-erln 1", para obtenerla lista de todos los estudiantesordenados alfabéticamente por apellido
