# Repartidor_de_asignaturas
Programa de reparto de asignaturas entre profesores


English (translation by Google)
-------

A college offers various subjects to its students. These subjects are taught by teachers. Before the start of classes, the teachers distribute among themselves the subjects they are going to teach. Without prior planning, this deal can be difficult, wrong, or unfair. A distribution is difficult when it requires hours of meetings, since there are numerous subjects to distribute, or many teachers involved. A distribution may not be valid when there are subjects that cannot be distributed, because the minimum or maximum number of hours assignable to each teacher would be breached. A distribution can be unfair when some teachers receive few subjects, and others receive many. These difficulties can be avoided with a computer to make the distribution.

The subject distribution computer program automatically finds the distributions that can be made. This distribution complies with the limit of hours that each teacher can teach. The program also finds out if a distribution is valid, that is, if it meets the two conditions of a good distribution: each teacher receives the hours they can give, and no subject will be left undistributed.

In order to work, the program needs the name of the subjects, the duration in hours of each subject, the minimum number of working hours for a teacher, and the maximum number of working hours for a teacher.

The program uses a backtracking technique. This technique builds all the casts it can, by combining subjects. The program accepts only the combinations that meet the two conditions of a good distribution: each teacher receives the hours they can give, and there is no subject left to distribute. Other features of the program are: it performs an in-depth search; it is a recursive program; it does not use "brute force" since it discards combinations before and during the distribution; it is a technique used in games like sudoku, mazes and chess.

We can represent the n subjects by means of a vector [1-n]. During the distribution, each teacher receives a valid number of hours, that is, within a minimum and maximum allowed range. The distribution ends when there are no subjects left to distribute. The cast can be represented as a search tree.

Bibliography:

  https://en.wikipedia.org/wiki/Eight_queens_puzzle
  https://www.chessprogramming.org/Backtracking
  https://simple.wikipedia.org/wiki/Backtracking
  

Espa??ol
-------

Un colegio ofrece varias asignaturas a sus estudiantes. Estas asignaturas son impartidas por profesores. Antes del inicio de las clases, los profesores reparten entre ellos las asignaturas que van a ense??ar. Sin una planificaci??n previa, este reparto puede resultar dif??cil, err??neo o injusto. Un reparto es dif??cil cuando requiere horas de reuniones, pues hay numerosas asignaturas a repartir, o bastantes profesores involucrados. Un reparto puede no ser v??lido cuando quedan asignaturas que no pueden repartirse, porque se incumplir??a la cantidad de horas m??nima o m??xima asignable a cada profesor. Un reparto puede ser injusto cuando unos profesores reciben pocas asignaturas, y otros reciben muchas. Estas dificultades pueden evitarse con un ordenador para hacer el reparto.

El programa inform??tico de reparto de asignaturas, halla autom??ticamente los repartos que pueden hacerse. Este reparto cumple el l??mite de horas que cada profesor puede ense??ar. El programa tambi??n averigua si un reparto es v??lido, es decir, si cumple las dos condiciones de un buen reparto: cada profesor recibe las horas que puede dar, y no quedar?? ninguna asignatura sin repartir.

El programa necesita para funcionar el nombre de las asignaturas, la duraci??n en horas de cada asignatura, la cantidad m??nima de horas laborable por un profesor, y la cantidad m??xima de horas laborable por un profesor.

El programa utiliza una t??cnica de vuelta atr??s o backtracking. Esta t??cnica construye todos los repartos que puede, mediante la combinaci??n de asignaturas. El programa acepta s??lo las combinaciones que cumplen las dos condiciones de un buen reparto: cada profesor recibe las horas que puede dar, y no queda ninguna asignatura por repartir. Otras caracter??sticas del programa son: realiza una b??squeda en profundidad; es un programa recursivo; no utiliza "fuerza bruta" pues descarta combinaciones antes y durante el reparto; es una t??cnica empleada en juegos como el sudoku, laberintos y ajedrez.

Podemos representar las n asignaturas mediante un vector [1-n]. Durante el reparto, cada profesor recibe una cantidad de horas v??lida, es decir, dentro de un rango m??nimo y m??ximo permitido. El reparto termina cuando no quedan asignaturas que repartir. El reparto puede representarse como un ??rbol de b??squeda.

Bibliograf??a:

  https://es.m.wikipedia.org/wiki/Problema_de_las_ocho_reinas
  https://www.chessprogramming.org/Backtracking
  https://es.m.wikipedia.org/wiki/Vuelta_atr%C3%A1s
