# Repartidor_de_asignaturas
Programa de reparto de asignaturas entre profesores

Un colegio ofrece varias asignaturas a sus estudiantes. Estas asignaturas son impartidas por profesores. Antes del inicio de las clases, los profesores reparten entre ellos las asignaturas que van a enseñar. Sin una planificación previa, este reparto puede resultar difícil, erróneo o injusto. Un reparto es difícil cuando requiere horas de reuniones, pues hay numerosas asignaturas a repartir, o bastantes profesores involucrados. Un reparto puede no ser válido cuando quedan asignaturas que no pueden repartirse, porque se incumpliría la cantidad de horas mínima o máxima asignable a cada profesor. Un reparto puede ser injusto cuando unos profesores reciben pocas asignaturas, y otros reciben muchas. Estas dificultades pueden evitarse con un ordenador para hacer el reparto.

El programa informático de reparto, asigna automáticamente asignaturas a profesores. Este reparto cumple el límite de horas que cada profesor puede enseñar. 

El programa necesita para funcionar el nombre de las asignaturas, la duración en horas de cada asignatura, la cantidad mínima de horas laborable por un profesor, y la cantidad máxima de horas laborable por un profesor.

El programa utiliza la técnica de Lehmer, también llamada algoritmo de vuelta atrás o backtracking. La técnica construye todos los repartos que puede, mediante la combinación de asignaturas. El programa acepta sólo las combinaciones que cumplen las dos condiciones de un buen reparto: cada profesor recibe las horas que puede dar, y no queda ninguna asignatura por repartir. Otras características del programa son: realiza una búsqueda en profundidad; es un programa recursivo; no utiliza "fuerza bruta" pues descarta combinaciones antes y durante el reparto; es una técnica empleada en juegos como el sudoku, laberintos y ajedrez.

Podemos representar las n asignaturas mediante un vector [1-n]. Durante el reparto, cada profesor recibe una cantidad de horas válida, es decir, dentro de un rango mínimo y máximo permitido. El reparto termina cuando no quedan asignaturas que repartir. El reparto puede representarse como un árbol de búsqueda.

Bibliografía:

  https://es.m.wikipedia.org/wiki/Problema_de_las_ocho_reinas
  https://www.chessprogramming.org/Backtracking
  https://es.m.wikipedia.org/wiki/Vuelta_atr%C3%A1s
