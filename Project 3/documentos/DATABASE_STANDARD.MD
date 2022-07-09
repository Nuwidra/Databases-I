
# MySQL
Jonathan Quesada Salas
Carné: 2020023583

## Introducción
En el presente documento se van a presentar el estándar que se usará para el nombramiento de los diversos componentes que puede tener MySQL para el manejo adecuado de las bases de datos, de forma que el estándar que se elija tenga como objetivos principales la mayor comprensión del código, mayor legibilidad y que este de la manera más ordenada posible, para que de está forma se evite en la medida de lo posible problematicas de compatibilidad con los estándares de futuros colaboradores.

## Justificación
El estándar seleccionado será el siguiente, que todo sea expresado con letras minúsculas y el lenguaje por el cual será expresado será el español, cabe recalcar que los acentos como son las tildes o la letra "ñ" serán excluidas de dicho estándar ya que pueden llegar a ser potencial causante de posibles problemas a la hora de la realización de bases de datos, eso en cuanto a los caracteres, a lo que se refiere al nombramiento de la variables **NO** se va a hacer por medio de Camel Case, se hará por medio del guión bajo "_" para separar posibles palabras entre nombres de variables, de está forma ayuda a dar una mayor visualización de las variables y visualmente se ve mucho más ordenado

## Índice con hipervinculos a las secciones

### 1.[`Introducción`](##Introducción)
### 2.[`Justificación`](##Justificación)
### 2.[`Definición del estándar`](##Estándar)

## Definición del estándar para:

### Entidades
- Descripción del estándar: Básicamente las entidades serían los nombres que tendrán su propia tabla, el nombre se escribirá totalmente en minúscula, si es el caso de haber dos palabras en ella, se va a separar con un guión bajo "_" y la siguiente palabra iniciará con minúscula, pueden llegar la posibilidad de usar pronombres plurales en caso de que se necesiten, en caso de que por alguna razón ya se esté utilizando "estudiente" para alguna otra cosa se usará mayúscula para iniciar "Estudiante", cabe resaltar que se usará e idioma español para expresarlos
- Ejemplos:
~~~
create table estudiante_becado();
create table estudiante();
~~~
- Excepciones:
~~~
create table Estudiante_becado();
create table Estudiante();
~~~

### Atributos
- Descripción del estándar: Los atributos serán lo que compondrán a las entidades, serán el nombre de las columnas y están será compuesta únicamente por letras minúsculas y si son atributos de varias palabras separadas por el guión bajo, puede existir la posibilidad de usar atributo de manera plural, la única excepción que podría existir sería para el caso de que el atributo se general para muchas tablas en este caso deberá ir el atributo totalmente en mayúscula. Cabe resaltar que se usará e idioma español para expresarlos
- Ejemplos:
~~~
id
estudiante
~~~
- Excepciones:
~~~
ID_UNIVERSAL
ESTUDIANTE
~~~

### Tipo de datos a usar en los atributos
- Descripción del estándar: El tipo de dato que se usará será el **varchar** ya que este es lo suficientemente capaz para almacenar los datos que se podrán usar en un futuro. Las variaciones que pueden llegar llegar a existir son las que puedan almacenar más que varchar como es el caso de char, pero su capacidad se ve reducida a solo 0 a 255 carácteres, aunque se puedan usar, es inútil usar alguna otra siendo que varchar es lo que ocupamos.
- Ejemplos:
~~~
varchar(30);
~~~
- Excepciones:
~~~
char(30);
~~~

### Llaves primarias
- Descripción del estándar: Para el estándar de las llaves primaria se va a determinar que sean todo en minúscula y en singular, la única excepción que podría resaltar es que la llave primaria se usará en varias tablas entonces ser pondría en mayúscula el "primary key" para así resaltar la importancia de esa llave
- Ejemplos:
~~~
create table persona (
    id int NOT NULL,
    apellido varchar(255) NOT NULL,
    primer_nombre varchar(255),
    primary key (id)
);
~~~
- Excepciones:
~~~
create table persona (
    id int NOT NULL,
    apellido varchar(255) NOT NULL,
    primer_nombre varchar(255),
    PRIMARY KEY (id)
);
~~~

### Llaves foráneas
- Descripción del estándar: Para el estándar de las llaves foráneas se va a determinar que sean todo en minúscula y en singular, la única excepción que podría resaltar es que la llave primaria se usará en varias tablas entonces ser pondría en mayúscula el "foreign key" para así resaltar la importancia de esa llave en cuestión
- Ejemplos:
~~~
add foreign key (persona_id);
~~~
- Excepciones:
~~~
add FOREIGN KEY (persona_id);
~~~

### Índices
- Descripción del estándar: Los índices se van a trabajar de forma que todos los carácteres esten en minúscula, en el caso de haber varias palabras en un índice se separará por medio del guión bajo "_" y se expresará en singular, en caso que el índice sea universal y se use para todo se usara las mayúsculas en todo el índice para que resalte. Cabe resaltar que se usará e idioma español para expresarlos
- Ejemplos:
~~~
create indice nombre_indice
~~~
- Excepciones:
~~~
create INDICE_UNIVERSAL nombre
~~~

