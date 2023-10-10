# Repartidor_de_asignaturas
Programa de reparto de asignaturas entre profesores


English
-------

A school offers several subjects to its students. These subjects are taught by teachers. Before the start of the school year, teachers distribute the subjects they will teach among themselves. Without prior planning, this distribution can be difficult, incorrect, or unfair. A distribution is difficult when it requires hours of meetings, because there are many subjects to distribute, or quite a few teachers involved. A distribution may not be valid when there are subjects that cannot be distributed, because the minimum or maximum number of hours assigned to each teacher would be violated. A distribution may be unfair when some teachers receive few subjects, and others receive many. These difficulties can be avoided with a computer to do the distribution.

The computer program for distributing subjects automatically finds the distributions that can be made. This distribution meets the limit of hours that each teacher can teach. The program also determines if a distribution is valid, that is, if it meets the two conditions of a good distribution: each teacher receives the hours they can give, and no subject will remain undistributed.

The program needs to know the name of the subjects, the duration in hours of each subject, the minimum number of hours that a teacher can work, and the maximum number of hours that a teacher can work.

The program uses a backtracking technique. This technique builds all the distributions it can, by combining subjects. The program accepts only the combinations that meet the two conditions of a good distribution: each teacher receives the hours they can give, and no subject remains undistributed. Other features of the program are: it performs a depth-first search; it is a recursive program; it does not use "brute force" because it discards combinations before and during the distribution; it is a technique used in games like sudoku, mazes, and chess.

We can represent the n subjects by a vector [1-n]. During the distribution, each teacher receives a valid number of hours, that is, within a minimum and maximum allowed range. The distribution ends when there are no subjects left to distribute. The distribution can be represented as a search tree.

The program can process up to 7 subjects in a reasonable time frame, due to the enormous number of combinations it must calculate.

The program is offered with a simple interface.

The program was written with the Netbeans editor, using a Java Maven project.

The entry point (main) is in the file Cargar_datos_GUI.java.

Bibliography:

  https://en.wikipedia.org/wiki/Eight_queens_puzzle
  https://www.chessprogramming.org/Backtracking
  https://simple.wikipedia.org/wiki/Backtracking
  

Español
-------

Un colegio ofrece varias asignaturas a sus estudiantes. Estas asignaturas son impartidas por profesores. Antes del inicio de las clases, los profesores reparten entre ellos las asignaturas que van a enseñar. Sin una planificación previa, este reparto puede resultar difícil, erróneo o injusto. Un reparto es difícil cuando requiere horas de reuniones, pues hay numerosas asignaturas a repartir, o bastantes profesores involucrados. Un reparto puede no ser válido cuando quedan asignaturas que no pueden repartirse, porque se incumpliría la cantidad de horas mínima o máxima asignable a cada profesor. Un reparto puede ser injusto cuando unos profesores reciben pocas asignaturas, y otros reciben muchas. Estas dificultades pueden evitarse con un ordenador para hacer el reparto.

El programa informático de reparto de asignaturas, halla automáticamente los repartos que pueden hacerse. Este reparto cumple el límite de horas que cada profesor puede enseñar. El programa también averigua si un reparto es válido, es decir, si cumple las dos condiciones de un buen reparto: cada profesor recibe las horas que puede dar, y no quedará ninguna asignatura sin repartir.

El programa necesita para funcionar el nombre de las asignaturas, la duración en horas de cada asignatura, la cantidad mínima de horas laborable por un profesor, y la cantidad máxima de horas laborable por un profesor.

El programa utiliza una técnica de vuelta atrás o backtracking. Esta técnica construye todos los repartos que puede, mediante la combinación de asignaturas. El programa acepta sólo las combinaciones que cumplen las dos condiciones de un buen reparto: cada profesor recibe las horas que puede dar, y no queda ninguna asignatura por repartir. Otras características del programa son: realiza una búsqueda en profundidad; es un programa recursivo; no utiliza "fuerza bruta" pues descarta combinaciones antes y durante el reparto; es una técnica empleada en juegos como el sudoku, laberintos y ajedrez.

Podemos representar las n asignaturas mediante un vector [1-n]. Durante el reparto, cada profesor recibe una cantidad de horas válida, es decir, dentro de un rango mínimo y máximo permitido. El reparto termina cuando no quedan asignaturas que repartir. El reparto puede representarse como un árbol de búsqueda.

El programa puede procesar hasta 7 asignaturas en un plazo razonable, debido a la enorme cantidad de combinaciones que debe calcular.

El programa se ofrece con una interfaz sencilla.

El programa se ha escrito con el editor Netbeans, usando un proyecto de Java Maven.

El punto de entrada (main) está en el archivo Cargar_datos_GUI.java.

Bibliografía:

  https://es.m.wikipedia.org/wiki/Problema_de_las_ocho_reinas
  https://www.chessprogramming.org/Backtracking
  https://es.m.wikipedia.org/wiki/Vuelta_atr%C3%A1s
