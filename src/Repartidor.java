/**
 * Programa de reparto de asignaturas, entre profesores de escuelas de
 * educación.
 *
 * <p>
 * Un colegio ofrece varias asignaturas a sus estudiantes. Estas asignaturas son
 * impartidas por profesores. Antes del inicio de las clases, los profesores
 * reparten entre ellos las asignaturas que van a enseñar.
 * <p>
 * Este programa asigna automáticamente asignaturas a profesores. Este reparto
 * cumple el límite de horas que cada profesor puede enseñar.
 * <p>
 * Este programa también averigua si un reparto es válido, es decir, si cumple
 * las dos condiciones de un buen reparto: cada profesor recibe las horas que
 * puede dar, y no quedará ninguna asignatura sin repartir.
 * <p>
 * El programa necesita para funcionar el nombre de las asignaturas, la duración
 * en horas de cada asignatura, la cantidad mínima de horas laborable por un
 * profesor, y la cantidad máxima de horas laborable por un profesor.
 *
 * @author Fernando Martín
 * @version %I%, %G%
 * @since 1.0
 */
package com.mycompany.asignaturas;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author fermitanio
 */
public class Repartidor {

    final static int MIN_HORAS_POR_PROFESOR = 1;
    final static int MAX_HORAS_POR_PROFESOR = 40;

    // Variables utilizadas en varias funciones recursivas.
    static Asignatura[] v_asignaturas;
    static Profesor[] v_profesores;
    static ArrayList<Asignatura[]> l_combi_asig_global = new ArrayList<>();
    static ArrayList<ArrayList<Asignaturas_de_un_profesor>> repartos_entre_profesores_global = new ArrayList<>();
    // semanales de asignaturas.
    // static int min_horas_por_profesor;
    // static int max_horas_por_profesor;

    private static <T> ArrayList<T> ArrayToList(T[] v_asignaturas) {

        ArrayList<T> l_asignaturas = new ArrayList<>();

        for (int i = 0; i < v_asignaturas.length; i++) {
            l_asignaturas.add(v_asignaturas[i]);
        }
        return l_asignaturas;
    }

    static public int[] cantidad_de_profesores_para_el_reparto(ArrayList<ArrayList<Asignatura[]>> l_repartos_asignaturas) {

        int n_profesores[] = new int[2];
        int n_reparto_más_pequeño = 0;
        int n_reparto_más_grande = 0;
        for (int i = 0, j = l_repartos_asignaturas.size(); i < j; i++) {

            ArrayList<Asignatura[]> l_reparto_asignaturas = l_repartos_asignaturas.get(i);
            for (int k = 0, l = l_repartos_asignaturas.size(); k < l; k++) {
                int tamaño_del_reparto = l_reparto_asignaturas.size();
                if (tamaño_del_reparto > n_reparto_más_grande) {
                    n_reparto_más_grande = tamaño_del_reparto;
                } else if (tamaño_del_reparto < n_reparto_más_pequeño) {
                    n_reparto_más_pequeño = tamaño_del_reparto;
                }
            }
        }

        n_profesores[0] = n_reparto_más_pequeño;
        n_profesores[1] = n_reparto_más_grande;
        return n_profesores;
    }

    public static Asignatura[] cargar_asignaturas(String ruta_archivo_asignaturas) {

        String line = "";
        ArrayList<Asignatura> l_asignaturas = new ArrayList<>();

        try {
            File myObj = new File(ruta_archivo_asignaturas);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                String items[] = line.split(";");
                if (items.length != 2) {
                    return null;
                }
                l_asignaturas.add(new Asignatura(items[0], Integer.parseInt(items[1])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }

        Asignatura[] v_asignaturas = ListoArray(l_asignaturas);
        return v_asignaturas;
    }

    public static int[] cargar_horas_por_profesor(String ruta_archivo_horas) {

        String line = "";
        String s_items[];
        int n_items[];

        try {
            File myObj = new File(ruta_archivo_horas);
            Scanner myReader = new Scanner(myObj);
            line = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        s_items = line.split(";");
        n_items = new int[2];
        n_items[0] = Integer.parseInt(s_items[0]);
        n_items[1] = Integer.parseInt(s_items[1]);

        return n_items;
    }

    public static Profesor[] cargar_profesores(String ruta_archivo_profesores) {

        String line = "";
        ArrayList<Profesor> l_profesores = new ArrayList<>();

        try {
            File myObj = new File(ruta_archivo_profesores);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                String items[] = line.split(";");
                if (items.length != 3) {
                    return null;
                }
                l_profesores.add(
                        new Profesor(
                                items[0],
                                Integer.parseInt(items[1]),
                                Integer.parseInt(items[2])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }

        Profesor v_profesores[] = (Profesor[]) l_profesores.toArray(new Profesor[l_profesores.size()]);

        // Puede haber nombres repetidos. Ponemos números detrás de tales 
        // nombres.
        for (int i = 0, j = v_profesores.length; i < j; i++) {
            int contador = 1;
            for (int k = 0; k < i; k++) {
                if (v_profesores[i].nombre_abreviado
                        == v_profesores[k].nombre_abreviado) {
                    contador++;
                }
            }
            if (contador > 1) {
                v_profesores[i].nombre_abreviado
                        = v_profesores[i].nombre_abreviado + " " + contador;
            }
        }

        return v_profesores;
    }

    public static ArrayList<Asignatura[]> combinaciones_n_horas(ArrayList<Asignatura[]> l_combinaciones_asignaturas, int horas_de_clase) {

        int horas_de_clase_aux;
        ArrayList<Asignatura[]> combinaciones_n_horas = new ArrayList<>();

        for (int i = 0, j = l_combinaciones_asignaturas.size(); i < j; i++) {
            Asignatura[] asignaturas = l_combinaciones_asignaturas.get(i);

            horas_de_clase_aux = 0;
            for (int k = 0, l = asignaturas.length; k < l; k++) {
                horas_de_clase_aux += asignaturas[k].horas;
            }
            if (horas_de_clase_aux == horas_de_clase) {
                combinaciones_n_horas.add(asignaturas);
            }
        }
        return combinaciones_n_horas;
    }

    /**
     * Devuelve combinaciones de asignaturas. Las combinaciones tienen entre
     * min_elementos y max_elementos elementos. Cada combinación es un vector de
     * asignaturas.
     *
     * Explicación: Supongamos que combinaciones_sin_repetición recibe cinco
     * asignaturas: RL, CE, PRL, IN, y SR. Además, min_elementos vale 1, y
     * max_elementos vale 2. Entonces la función devolverá estas combinaciones:
     *
     * Combinaciones de 1 elemento: {RL}, {CE}, {SR}, {PRL} y {IN}.
     * Combinaciones de 2 elementos: {RL, CE}, {RL, PRL}, {RL, IN}), {RL, SR},
     * {CE, PRL}, {CE, IN}, {CE, SR}, {PRL, IN}, {PRL, SR}, {IN, SR}.
     *
     * El ejemplo anterior puede programarse con el siguiente código:
     *
     * ArrayList<Asignatura[]> l_combi_asig = new ArrayList<>();
     * l_combi_asig.add(new Asignatura[]{RL}); l_combi_asig.add(new
     * Asignatura[]{CE}); l_combi_asig.add(new Asignatura[]{SR});
     * l_combi_asig.add(new Asignatura[]{PRL}); l_combi_asig.add(new
     * Asignatura[]{IN}); l_combi_asig.add(new Asignatura[]{RL, CE});
     * l_combi_asig.add(new Asignatura[]{RL, PRL}); l_combi_asig.add(new
     * Asignatura[]{RL, IN}); l_combi_asig.add(new Asignatura[]{RL, SR});
     * l_combi_asig.add(new Asignatura[]{CE, PRL}); l_combi_asig.add(new
     * Asignatura[]{CE, IN}); l_combi_asig.add(new Asignatura[]{CE, SR});
     * l_combi_asig.add(new Asignatura[]{PRL, IN}); l_combi_asig.add(new
     * Asignatura[]{PRL, SR}); l_combi_asig.add(new Asignatura[]{IN, SR});
     *
     * @param v_asignaturas Vector que contiene las asignaturas a combinar.
     * @param min_elementos Cantidad mínima de asignaturas de las combinaciones.
     * @param max_elementos Cantidad máxima de asignaturas de las combinaciones.
     * @return Listado que contiene combinaciones de asignaturas.
     */
    private static ArrayList<Asignatura[]> combinaciones_sin_repetición(
            Asignatura[] v_asignaturas, int min_elementos, int max_elementos) {

        ArrayList<Asignatura[]> l_combinaciones_asignaturas = new ArrayList<>();
        for (int i = min_elementos; i <= max_elementos; i++) {

            // Calcular combinaciones válidas de varios elementos.
            ArrayList<Asignatura[]> l_combinaciones_asignaturas_aux;
            l_combinaciones_asignaturas_aux = combinaciones_sin_repetición_de_n_elementos(
                    v_asignaturas, i);

            // Almacenar las combinaciones válidas en un listado.
            for (int j = 0; j < l_combinaciones_asignaturas_aux.size(); j++) {
                l_combinaciones_asignaturas.add(l_combinaciones_asignaturas_aux.get(j));
            }
        }

        return l_combinaciones_asignaturas;
    }

    /**
     * Devuelve combinaciones de asignaturas. Las combinaciones tienen
     * num_elementos. Cada combinación es un vector de asignaturas.
     *
     * Nota: esta función puede mejorarse, ya que es recursiva. Para mejorarla,
     * se puede redactar una versión iterativa de la función.
     *
     * Explicación de la función: supongamos que
     * combinaciones_sin_repetición_de_n_elementos recibe cinco asignaturas: RL,
     * CE, PRL, IN, y SR. Además, num_elementos vale 2. Entonces la función
     * devolverá estas combinaciones:
     *
     * Combinaciones de 2 elementos: {RL, CE}, {RL, PRL}, {RL, IN}), {RL, SR},
     * {CE, PRL}, {CE, IN}, {CE, SR}, {PRL, IN}, {PRL, SR}, {IN, SR}.
     *
     * El ejemplo anterior puede programarse con el siguiente código:
     *
     * ArrayList<Asignatura[]> l_combi_asig = new ArrayList<>();
     * l_combi_asig.add(new Asignatura[]{RL, CE}); l_combi_asig.add(new
     * Asignatura[]{RL, PRL}); l_combi_asig.add(new Asignatura[]{RL, IN});
     * l_combi_asig.add(new Asignatura[]{RL, SR}); l_combi_asig.add(new
     * Asignatura[]{CE, PRL}); l_combi_asig.add(new Asignatura[]{CE, IN});
     * l_combi_asig.add(new Asignatura[]{CE, SR}); l_combi_asig.add(new
     * Asignatura[]{PRL, IN}); l_combi_asig.add(new Asignatura[]{PRL, SR});
     * l_combi_asig.add(new Asignatura[]{IN, SR});
     *
     * @param v_asignaturas Vector que contiene las asignaturas a combinar.
     * @param num_elementos Cantidad de asignaturas de cada combinación.
     * @return Listado que contiene combinaciones de asignaturas.
     */
    private static ArrayList<Asignatura[]> combinaciones_sin_repetición_de_n_elementos(
            Asignatura[] v_asignaturas, int num_elementos) {
        ArrayList<Asignatura> l_combi_asig = new ArrayList<>();
        combinaciones_sin_repetición_recursivo(v_asignaturas, 0, num_elementos, l_combi_asig);
//        combinaciones_sin_repetición_iterativo(v_asignaturas, num_elementos);

        // Liberar la variable global por si se utiliza varias veces.
        ArrayList<Asignatura[]> list = (ArrayList<Asignatura[]>) l_combi_asig_global.clone();
        l_combi_asig_global.clear();
        return list;
    }

    /**
     * Devuelve combinaciones de asignaturas. Las combinaciones tienen
     * num_elementos. Cada combinación es un vector de asignaturas.
     *
     * Explicación: esta función almacena las combinaciones en una variable
     * global, llamada l_combi_asig_global. De esta manera, evitamos pasar esa
     * variable como parámetro, a la función l_combi_asig_global. Para más
     * información léase la documentación de la función
     * combinaciones_sin_repetición_de_n_elementos.
     *
     * @param v_asignaturas Vector que contiene las asignaturas a combinar.
     * @param index Posición desde la que comenzar a leer el vector de
     * asignaturas.
     * @param num_elementos Cantidad de asignaturas de cada combinación.
     * @param l_combi_asig Listado provisional que contiene combinaciones de
     * asignaturas. Sirve para componer el resultado definitivo.
     * @return Listado que contiene combinaciones de asignaturas.
     */
    private static void combinaciones_sin_repetición_recursivo(Asignatura[] v_asignaturas, int index, int num_elementos, ArrayList<Asignatura> l_combi_asig) {

        if (num_elementos == 0) {
            // Variable global.
            l_combi_asig_global.add(ListoArray(l_combi_asig));
            return;
        }

        for (int i = index; i < v_asignaturas.length - num_elementos + 1; i++) {
            l_combi_asig.add(v_asignaturas[i]);
            combinaciones_sin_repetición_recursivo(
                    v_asignaturas, i + 1, num_elementos - 1, (ArrayList<Asignatura>) l_combi_asig.clone());
            l_combi_asig.remove(l_combi_asig.size() - 1);
        }
    }

    // Generated with ChatGPT.
    private static void combinaciones_sin_repetición_eficiente(Asignatura[] v_asignaturas, int num_elementos) {
        int n = v_asignaturas.length;

        // Inicializar el primer conjunto de índices
        int[] indices = new int[num_elementos];
        for (int i = 0; i < num_elementos; i++) {
            indices[i] = i;
        }

        while (indices[0] <= n - num_elementos) {
            Asignatura[] combinacion = new Asignatura[num_elementos];

            // Crear la combinación actual
            for (int i = 0; i < num_elementos; i++) {
                combinacion[i] = v_asignaturas[indices[i]];
            }

            // Agregar la combinación actual a l_combi_asig_global
            l_combi_asig_global.add(combinacion);

            // Encontrar el siguiente conjunto de índices
            int i = num_elementos - 1;
            while (i >= 0 && indices[i] == n - num_elementos + i) {
                i--;
            }

            // Si i es -1, hemos terminado
            if (i == -1) {
                break;
            }

            // Incrementar el índice en la posición i y ajustar los índices posteriores
            indices[i]++;
            for (int j = i + 1; j < num_elementos; j++) {
                indices[j] = indices[j - 1] + 1;
            }
        }
    }

    /*
    // Generated with ChatGPT.
    private static void combinaciones_sin_repetición_iterativo(
            Asignatura[] v_asignaturas, int num_elementos) {

        // Pila para almacenar las asignaturas que se han elegido.
        Stack<Asignatura> pila = new Stack<>();

        // Añadimos la primera asignatura a la pila.
        pila.push(v_asignaturas[0]);

        // Iteramos mientras la pila no esté vacía.
        while (!pila.isEmpty()) {

            // Si la pila tiene `num_elementos`, hemos generado una combinación.
            if (pila.size() == num_elementos) {
                l_combi_asig_global.add(pila.toArray(new Asignatura[0]));
            } else {

                // Iteramos sobre el resto de asignaturas.
                for (int i = 1; i < v_asignaturas.length; i++) {

                    // Si la asignatura no está en la pila, la añadimos.
                    if (!pila.contains(v_asignaturas[i])) {
                        pila.push(v_asignaturas[i]);
                    }
                }

                // Eliminamos la última asignatura de la pila.
                pila.pop();
            }
        }
    }
     */
    // Generated with ChatGPT.
    private static void combinaciones_sin_repetición_iterativo(
            Asignatura[] v_asignaturas, int num_elementos) {
        int n = v_asignaturas.length;

        // Crear un arreglo de índices para mantener un seguimiento de los elementos seleccionados
        int[] indices = new int[num_elementos];

        // Inicializar los primeros índices
        for (int i = 0; i < num_elementos; i++) {
            indices[i] = i;
        }

        while (indices[0] <= n - num_elementos) {
            // Crear una nueva combinación
            Asignatura[] combinacion = new Asignatura[num_elementos];
            for (int i = 0; i < num_elementos; i++) {
                combinacion[i] = v_asignaturas[indices[i]];
            }

            // Agregar la combinación actual a l_combi_asig_global
            l_combi_asig_global.add(combinacion);

            // Encontrar el siguiente conjunto de índices
            int i = num_elementos - 1;
            while (i >= 0 && indices[i] == n - num_elementos + i) {
                i--;
            }

            // Si i es -1, hemos terminado
            if (i == -1) {
                break;
            }

            // Incrementar el índice en la posición i y ajustar los índices posteriores
            indices[i]++;
            for (int j = i + 1; j < num_elementos; j++) {
                indices[j] = indices[j - 1] + 1;
            }
        }
    }

    /*
    // Generated with ChatGPT.
    private static void combinaciones_sin_repetición_iterativo(
            Asignatura[] v_asignaturas, int num_elementos) {
        int n = v_asignaturas.length;
        ArrayList<Asignatura> l_combi_asig = new ArrayList<>();

        // Inicializar un arreglo para mantener un índice para cada elemento en l_combi_asig
        int[] indices = new int[num_elementos];

        // Inicializar los primeros índices
        for (int i = 0; i < num_elementos; i++) {
            indices[i] = i;
        }

        while (indices[0] < n - num_elementos + 1) {
            // Agregar elementos correspondientes a los índices actuales a l_combi_asig
            for (int i = 0; i < num_elementos; i++) {
                l_combi_asig.add(v_asignaturas[indices[i]]);
            }

            // Agregar la combinación actual a l_combi_asig_global
            l_combi_asig_global.add(ListoArray(l_combi_asig));

            // Encontrar el siguiente conjunto de índices
            int i = num_elementos - 1;
            while (i >= 0 && indices[i] == n - num_elementos + i) {
                i--;
            }

            // Si i es -1, hemos terminado
            if (i == -1) {
                break;
            }

            // Incrementar el índice en la posición i y ajustar los índices posteriores
            indices[i]++;
            for (int j = i + 1; j < num_elementos; j++) {
                indices[j] = indices[j - 1] + 1;
            }

            // Limpiar l_combi_asig para la próxima iteración
            l_combi_asig.clear();
        }
    }
     */
 /*
    private static void combinaciones_sin_repetición_iterativo(
            Asignatura[] v_asignaturas, int num_elementos) {

        // Matriz para almacenar las asignaturas que se han elegido.
        boolean[][] matriz = new boolean[v_asignaturas.length][num_elementos];

        // Iteramos sobre el conjunto de asignaturas.
        for (int i = 0; i < v_asignaturas.length; i++) {

            // Marcamos la asignatura como no elegida.
            matriz[i][0] = false;

            // Iteramos sobre el resto de asignaturas.
            for (int j = 1; j <= num_elementos; j++) {

                // Si la asignatura no está elegida, la elegimos.
                if (!matriz[i][j - 1]) {
                    matriz[i][j - 1] = true;

                    // Si la matriz tiene `num_elementos`, añadimos la combinación a la lista.
                    if (j == num_elementos) {
                        ArrayList<Asignatura> l_combi_asig = new ArrayList<>();
                        for (int k = 0; k < num_elementos; k++) {
                            if (!matriz[i][k]) {
                                l_combi_asig.add(v_asignaturas[i]);
                            }
                        }
                        l_combi_asig_global.add(l_combi_asig.toArray(new Asignatura[0]));
                    }
                }
            }
        }
    }
     */
 /*
    private static void combinaciones_sin_repetición_iterativo(
            Asignatura[] v_asignaturas, int num_elementos) {

        // Variables auxiliares.
        int i = 0;
        int j = 0;

        // Iteramos sobre el conjunto de asignaturas.
        while (i < v_asignaturas.length) {

            // Si la asignatura no está elegida, la añadimos a la lista.
            if (!estáElegida(v_asignaturas, i)) {
                ArrayList<Asignatura> l_combi_asig = new ArrayList<>();
                for (int k = 0; k < j; k++) {
                    l_combi_asig.add(v_asignaturas[i]);
                }
                l_combi_asig_global.add(l_combi_asig.toArray(new Asignatura[0]));
            }

            // Incrementamos el número de asignaturas elegidas.
            j++;

            // Si el número de asignaturas elegidas es igual a `num_elementos`, se ha generado una combinación.
            if (j == num_elementos) {
                j = 0;
                i++;
            }
        }
    }

// Función auxiliar para comprobar si una asignatura está elegida.
    private static boolean estáElegida(Asignatura[] v_asignaturas, int i) {

        for (int j = 0; j < i; j++) {
            if (v_asignaturas[i].equals(v_asignaturas[j])) {
                return true;
            }
        }
        return false;
    }
     */
    /**
     * - Las combinaciones para ser válidas, deben contener asignaturas cuya
     * suma de horas esté en el intervalo [4, 8]. // Horas por profesor: (4, 8)
     */
    private static ArrayList<Asignatura[]> combinaciones_válidas(
            ArrayList<Asignatura[]> l_combi_asig,
            int horas_min_por_profesor, int horas_max_por_profesor) {

        int suma;
        for (int i = 0, j = l_combi_asig.size(); i < j; i++) {

            Asignatura[] v_asignaturas_ex = l_combi_asig.get(i);
            suma = 0;
            for (int k = 0, l = v_asignaturas_ex.length; k < l; k++) {
                suma += v_asignaturas_ex[k].horas;
            }

            if (!(suma >= horas_min_por_profesor && suma <= horas_max_por_profesor)) {
                l_combi_asig.remove(i);
                j = l_combi_asig.size();
                i--;
            }
        }

        // No es necesario devolver una variable, pero añade claridad.
        return l_combi_asig;
    }

    private static boolean compare_repartos_asignaturas(ArrayList<Asignatura[]> l_reparto_asignaturas_a, ArrayList<Asignatura[]> l_reparto_asignaturas_b) {

        ArrayList<Asignatura[]> reparto_mayor, reparto_menor;
        if (l_reparto_asignaturas_a.size() >= l_reparto_asignaturas_b.size()) {
            reparto_mayor = l_reparto_asignaturas_a;
            reparto_menor = l_reparto_asignaturas_b;
        } else {
            reparto_mayor = l_reparto_asignaturas_b;
            reparto_menor = l_reparto_asignaturas_a;
        }

        int j = 0, l = l = reparto_menor.size();
        for (int i = 0, k = reparto_mayor.size();
                i < k && j < l; i++) {
            Asignatura[] asignaturas_mayor = reparto_mayor.get(i);
            Asignatura[] asignaturas_menor = reparto_menor.get(j);
            if (equal_vectores_asignaturas(asignaturas_mayor, asignaturas_menor)) {
                j++;
            }
        }
        return j < l ? false : true;
    }

    private static boolean equal_vectores_asignaturas(Asignatura[] asignaturas_a, Asignatura[] asignaturas_b) {

        if (asignaturas_a.length != asignaturas_b.length) {
            return false;
        }

        for (int i = 0; i < asignaturas_a.length; i++) {
            if (asignaturas_a[i].nombre.compareToIgnoreCase(asignaturas_b[i].nombre) != 0) {
                return false;
            }
        }

        return true;
    }

    private static boolean included_vectores_asignaturas(Asignatura[] sup_asignaturas, Asignatura[] sub_asignaturas) {

        if (sub_asignaturas.length > sup_asignaturas.length) {
            return false;
        }

        boolean found = false;
        for (int i = 0; i < sub_asignaturas.length; i++) {
            for (int j = 0; j < sup_asignaturas.length; j++) {
                if (sup_asignaturas[j].nombre.equalsIgnoreCase(sub_asignaturas[i].nombre)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
            found = false;
        }

        return true;
    }

    private static boolean is_asignatura_repartida(Asignatura asig, ArrayList<Asignatura[]> l_asig_repartidas) {

        String nombre_asignatura = asig.nombre;
        for (int i = 0, j = l_asig_repartidas.size(); i < j; i++) {
            Asignatura asignaturas_repartidas_aux[] = l_asig_repartidas.get(i);
            for (int k = 0, l = asignaturas_repartidas_aux.length; k < l; k++) {
                if (asignaturas_repartidas_aux[k].nombre == nombre_asignatura) {
                    return true;
                }
            }
        }
        return false;
    }

    static ArrayList<String> DEBUG_repartos_to_list(
            ArrayList<ArrayList<Asignaturas_de_un_profesor>> l_repartos_asignaturas) {
        ArrayList<String> object = new ArrayList<>();

        for (int i = 0, j = l_repartos_asignaturas.size();
                i < j;
                i++) {
            ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas
                    = l_repartos_asignaturas.get(i);
            object.add(Integer.toString(i + 1));
            for (int k = 0, l = l_reparto_asignaturas.size(); k < l; k++) {

                Asignaturas_de_un_profesor asignaturas_de_un_profesor = l_reparto_asignaturas.get(k);
                if (asignaturas_de_un_profesor.profesor.nombre_abreviado.equals("Julia")) {
                    object.add("----------" + asignaturas_de_un_profesor.profesor.nombre_abreviado);
                } else {
                    object.add(asignaturas_de_un_profesor.profesor.nombre_abreviado);
                }
                for (int m = 0, n = asignaturas_de_un_profesor.asignaturas.length; m < n; m++) {
                    if (asignaturas_de_un_profesor.asignaturas[m].nombre.equals("SR")) {
                        object.add("**********" + asignaturas_de_un_profesor.asignaturas[m].nombre);
                    } else {
                        object.add(asignaturas_de_un_profesor.asignaturas[m].nombre);
                    }
                }

            }
        }
        return object;
    }

    public static boolean is_reparto_valido_ex(
            ArrayList<ArrayList<Asignaturas_de_un_profesor>> l_repartos_asignaturas,
            ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas) {

        ArrayList<String> object = DEBUG_repartos_to_list(l_repartos_asignaturas);
        //
//        Quicksort.quicksort_reparto_asignaturas(l_reparto_asignaturas);
//
//        //
//        for (int i = 0, j = l_repartos_asignaturas.size();
//                i < j;
//                i++) {
//            ArrayList<Asignatura[]> l_repartos_asignaturas_aux = l_repartos_asignaturas.get(i);
//            Quicksort.quicksort_reparto_asignaturas(l_repartos_asignaturas_aux);
//        }
        //
        boolean found = false;
        for (int i = 0, j = l_reparto_asignaturas.size(); i < j; i++) {

            Asignaturas_de_un_profesor asignaturas_de_un_profesor1 = l_reparto_asignaturas.get(i);

            found = false;
            for (int k = 0, l = l_repartos_asignaturas.size(); k < l; k++) {
                ArrayList<Asignaturas_de_un_profesor> asignaturas_de_un_profesor = l_repartos_asignaturas.get(k);
//                Asignaturas_de_un_profesor asignaturas_de_un_profesor2 = l_reparto_asignaturas.get(0);
                for (int m = 0; m < asignaturas_de_un_profesor.size(); m++) {
                    Asignaturas_de_un_profesor asignaturas_de_un_profesor2 = asignaturas_de_un_profesor.get(m);

                    if (asignaturas_de_un_profesor2.profesor.nombre_abreviado.equals("Fermín")) {
                        int n = 1;
                    }
                    if (!(asignaturas_de_un_profesor1.profesor.nombre_completo.equals(asignaturas_de_un_profesor2.profesor.nombre_completo))) {
                        continue;
                    }

                    Asignatura[] asignaturas1 = asignaturas_de_un_profesor1.asignaturas;
                    Asignatura[] asignaturas2 = asignaturas_de_un_profesor2.asignaturas;

                    Quicksort.quicksort_asignaturas_por_nombre(asignaturas1, 0, asignaturas1.length - 1);
                    Quicksort.quicksort_asignaturas_por_nombre(asignaturas2, 0, asignaturas2.length - 1);
                    if (included_vectores_asignaturas(asignaturas2, asignaturas1)) {
                        found = true;
                        break;
                    }

                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }

    private static boolean is_reparto_valido(ArrayList<Asignatura[]> l_reparto_asignaturas, ArrayList<ArrayList<Asignatura[]>> l_repartos_asignaturas) {

        //
        Quicksort.quicksort_reparto_asignaturas(l_reparto_asignaturas);

        //
        for (int i = 0, j = l_repartos_asignaturas.size();
                i < j;
                i++) {
            ArrayList<Asignatura[]> l_repartos_asignaturas_aux = l_repartos_asignaturas.get(i);
            Quicksort.quicksort_reparto_asignaturas(l_repartos_asignaturas_aux);
        }

        //
        for (int i = 0, j = l_repartos_asignaturas.size(); i < j; i++) {

            ArrayList<Asignatura[]> l_reparto_asignaturas_aux = l_repartos_asignaturas.get(i);
            if (compare_repartos_asignaturas(l_reparto_asignaturas, l_reparto_asignaturas_aux)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Esta función sirve para probar el programa, sin utilizar una interfaz
     * gráfica. Para que esta función funcione: 1º. Hacer que la clase
     * "Repartidor" sea la clase principal. Si al ejecutar el programa
     * apareciera el error "Could not find or load main class", especificar el
     * nombre completo de la clase "Repartidor", que es
     * com.mycompany.asignaturas.Repartidor. 2º. Activar la línea que aparece
     * dentro de la función "main", es decir, la línea
     * "probar_programa_sin_utilizar_interfaz_gráfica();".
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        probar_programa_sin_utilizar_interfaz_gráfica();
        probar_programa_sin_utilizar_interfaz_gráfica_prueba_2();
    }

    private static void quitar_asignaturas(
            ArrayList<Asignatura[]> l_combinaciones_asignaturas,
            Asignatura[] asignaturas) {

        // Obtener las asignaturas que aparecen en la combinación obtenida.
        String v_nombres_asignaturas[] = new String[asignaturas.length];
        for (int i = 0, j = asignaturas.length; i < j; i++) {
            v_nombres_asignaturas[i] = asignaturas[i].nombre;
        }

        for (int i = 0, j = l_combinaciones_asignaturas.size(); i < j; i++) {
            asignaturas = l_combinaciones_asignaturas.get(i);
            for (int k = 0, l = asignaturas.length; k < l; k++) {
                for (int m = 0, n = v_nombres_asignaturas.length; m < n; m++) {
                    if (asignaturas[k].nombre == v_nombres_asignaturas[m]) {
                        // Quitar esa combinación de asignaturas.
                        l_combinaciones_asignaturas.remove(i);
                        m = n;
                        k = l;
                        i--;
                        j--;
                    }
                }
            }
        }
    }

    private static ArrayList<Asignatura[]> marcar_asignaturas(
            ArrayList<Asignatura[]> l_combi_asig,
            int index,
            boolean repartida,
            ArrayList<Asignatura[]> l_asig_repartidas) {

        // Obtener una de las combinaciones de asignaturas.
        Asignatura[] asignaturas = l_combi_asig.get(index);

        // Obtener las asignaturas que aparecen en la combinación obtenida.
        String v_nombres_asignaturas[] = new String[asignaturas.length];
        for (int i = 0, j = asignaturas.length; i < j; i++) {
            v_nombres_asignaturas[i] = asignaturas[i].nombre;
        }

        for (int i = 0, j = l_combi_asig.size(); i < j; i++) {
            asignaturas = l_combi_asig.get(i);
            for (int k = 0, l = asignaturas.length; k < l; k++) {
                for (int m = 0, n = v_nombres_asignaturas.length; m < n; m++) {
                    if (asignaturas[k].nombre == v_nombres_asignaturas[m]) {
                        // Marcar todas las asignaturas de esa combinación.
                        for (int o = 0, p = asignaturas.length; o < p; o++) {
                            if (repartida == false) {
                                if (!is_asignatura_repartida(asignaturas[o], l_asig_repartidas)) {
                                    asignaturas[o].repartida = repartida;
                                }
                            } else {
                                asignaturas[o].repartida = repartida;
                            }
                        }
                        m = n;
                        k = l;
                    }
                }
            }
        }

        return l_combi_asig;
    }

    public static void print_to_console(ArrayList<ArrayList<Asignaturas_de_un_profesor>> l_repartos_asignaturas) {

        for (int i = 0, j = repartos_entre_profesores_global.size(); i < j; i++) {
            ArrayList<Asignaturas_de_un_profesor> a = repartos_entre_profesores_global.get(i);
            System.out.print("Reparto ");
            System.out.println(i + 1);
            for (int k = 0, l = a.size(); k < l; k++) {
                Asignaturas_de_un_profesor b = a.get(k);
                System.out.print(b.profesor.nombre_completo);
                System.out.print("(");
                System.out.print(b.profesor.horas_mínimas_de_clase);
                System.out.print(", ");
                System.out.print(b.profesor.horas_máximas_de_clase);
                System.out.print("):  ");
                Asignatura c[] = b.asignaturas;
                for (int m = 0, n = c.length; m < n; m++) {
                    System.out.print(c[m].nombre);
                    System.out.print("(");
                    System.out.print(c[m].horas);
                    System.out.print(")");
                    System.out.print(", ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Esta función prueba la función is_reparto_valido_ex. Contiene código para
     * hacer tres pruebas, con la función is_reparto_valido_ex.
     */
    public static void probar_programa_sin_utilizar_interfaz_gráfica_prueba_2() {
        /*
        Código para probar el programa sin utilizar archivos:
        
        // Un departamento ofrece cinco asignaturas:
        Asignatura RL = new Asignatura("RL", 4);
        Asignatura CE = new Asignatura("CE", 4);
        Asignatura PRL = new Asignatura("PRL", 3);
        Asignatura IN = new Asignatura("IN", 3);
        Asignatura SR = new Asignatura("SR", 4);

        Asignatura[] v_asignaturas = {RL, CE, PRL, IN, SR};
        
        // Obtener la duración del curso, en horas. Para ello, sumar la 
        // duración de cada asignatura.
        short horas_del_curso = 0;
        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            horas_del_curso += v_asignaturas[i].horas;
        }
         */
        repartos_entre_profesores_global = new ArrayList<>();
        v_asignaturas = cargar_asignaturas("F:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\asignaturas.csv");
        v_profesores = cargar_profesores("F:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\profesores.csv");

        // Comprobar si, aunque pueden repartirse las asignaturas, en algunos
        // repartos no intervienen todos los profesores propuestos en el archivo
        // de profesores.
        String[] profesores_sobrantes = profesores_sobrantes(v_asignaturas, v_profesores);

        // Quitar los profesores sobrantes:
        if (profesores_sobrantes != null) {
            List<Profesor> l_profesores = Arrays.asList(v_profesores);
            l_profesores = new ArrayList<Profesor>(l_profesores);
            for (int i = 0, j = l_profesores.size(); i < j; i++) {
                for (int k = 0, l = profesores_sobrantes.length; k < l; k++) {
                    if (l_profesores.get(i).nombre_completo == profesores_sobrantes[k]) {
                        l_profesores.remove(i);
                        i--;
                        j = l_profesores.size();
                        break;
                    }
                }
            }
            v_profesores = (Profesor[]) l_profesores.toArray(
                    new Profesor[l_profesores.size()]);
        }

        // Hallar los repartos de asignaturas que pueden realizarse.
        repartos_entre_profesores_global = repartir_asignaturas(v_asignaturas, v_profesores);

        // Esos repartos son:
        /*
        Reparto 1
        Fermín Sáez Valero(6, 9):  PRL(3), RL(4), 
        Indalecio Martínez Navarro(6, 9):  CE(4), SR(4), 
        Sara Segovia Moreno(3, 4):  IN(3), 

        Reparto 2
        Fermín Sáez Valero(6, 9):  RL(4), IN(3), 
        Indalecio Martínez Navarro(6, 9):  CE(4), SR(4), 
        Sara Segovia Moreno(3, 4):  PRL(3), 

        Reparto 3
        Fermín Sáez Valero(6, 9):  CE(4), PRL(3), 
        Indalecio Martínez Navarro(6, 9):  RL(4), SR(4), 
        Sara Segovia Moreno(3, 4):  IN(3), 

        Reparto 4
        Fermín Sáez Valero(6, 9):  CE(4), IN(3), 
        Indalecio Martínez Navarro(6, 9):  RL(4), SR(4), 
        Sara Segovia Moreno(3, 4):  PRL(3), 

        Reparto 5
        Fermín Sáez Valero(6, 9):  PRL(3), SR(4), 
        Indalecio Martínez Navarro(6, 9):  RL(4), CE(4), 
        Sara Segovia Moreno(3, 4):  IN(3), 

        Reparto 6
        Fermín Sáez Valero(6, 9):  IN(3), SR(4), 
        Indalecio Martínez Navarro(6, 9):  RL(4), CE(4), 
        Sara Segovia Moreno(3, 4):  PRL(3), 

        Reparto 7
        Fermín Sáez Valero(6, 9):  RL(4), CE(4), 
        Indalecio Martínez Navarro(6, 9):  PRL(3), SR(4), 
        Sara Segovia Moreno(3, 4):  IN(3), 

        Reparto 8
        Fermín Sáez Valero(6, 9):  RL(4), CE(4), 
        Indalecio Martínez Navarro(6, 9):  IN(3), SR(4), 
        Sara Segovia Moreno(3, 4):  PRL(3), 

        Reparto 9
        Fermín Sáez Valero(6, 9):  RL(4), SR(4), 
        Indalecio Martínez Navarro(6, 9):  CE(4), PRL(3), 
        Sara Segovia Moreno(3, 4):  IN(3), 

        Reparto 10
        Fermín Sáez Valero(6, 9):  RL(4), SR(4), 
        Indalecio Martínez Navarro(6, 9):  CE(4), IN(3), 
        Sara Segovia Moreno(3, 4):  PRL(3), 

        Reparto 11
        Fermín Sáez Valero(6, 9):  CE(4), SR(4), 
        Indalecio Martínez Navarro(6, 9):  RL(4), PRL(3), 
        Sara Segovia Moreno(3, 4):  IN(3), 

        Reparto 12
        Fermín Sáez Valero(6, 9):  CE(4), SR(4), 
        Indalecio Martínez Navarro(6, 9):  RL(4), IN(3), 
        Sara Segovia Moreno(3, 4):  PRL(3),
         */
        // Un departamento ofrece cinco asignaturas:
        Asignatura RL = new Asignatura("RL", 4);
        Asignatura CE = new Asignatura("CE", 4);
        Asignatura PRL = new Asignatura("PRL", 3);
        Asignatura IN = new Asignatura("IN", 3);
        Asignatura SR = new Asignatura("SR", 4);

        // Prueba 1, debe devolver true. Consultar los repartos anteriores.
        Profesor profesor = new Profesor("Fermín Sáez Valero", 6, 9);
        Asignatura[] v_asignaturas = {RL};

        // Prueba 2, debe devolver true. Consultar los repartos anteriores.
//        Profesor profesor = new Profesor("Fermín Sáez Valero", 6, 9);
//        Asignatura[] v_asignaturas = {RL, PRL};
        // Prueba 3, debe devolver false. Consultar los repartos anteriores.
//        Profesor profesor = new Profesor("Fermín Sáez Valero", 6, 9);
//        Asignatura[] v_asignaturas = {PRL, IN};
        Profesor profesor2 = new Profesor("Indalecio A B", 6, 9);
        Asignatura[] v_asignaturas2 = {SR};

        Asignaturas_de_un_profesor a = new Asignaturas_de_un_profesor(profesor, v_asignaturas);
        Asignaturas_de_un_profesor b = new Asignaturas_de_un_profesor(profesor2, v_asignaturas2);
//        Asignatura[] v_asignaturas = {RL, CE, PRL, IN, SR};

        ArrayList< Asignaturas_de_un_profesor> l_reparto_asignaturas = new ArrayList<>();
        l_reparto_asignaturas.add(a);
        l_reparto_asignaturas.add(b);

        boolean bb
                = is_reparto_valido_ex(
                        repartos_entre_profesores_global,
                        l_reparto_asignaturas);
        // Imprimir los repartos hallados.
        print_to_console(repartos_entre_profesores_global);
    }

    public static void probar_programa_sin_utilizar_interfaz_gráfica() {

        /*
        Código para probar el programa sin utilizar archivos:
        
        // Un departamento ofrece cinco asignaturas:
        Asignatura RL = new Asignatura("RL", 4);
        Asignatura CE = new Asignatura("CE", 4);
        Asignatura PRL = new Asignatura("PRL", 3);
        Asignatura IN = new Asignatura("IN", 3);
        Asignatura SR = new Asignatura("SR", 4);

        Asignatura[] v_asignaturas = {RL, CE, PRL, IN, SR};
        
        // Obtener la duración del curso, en horas. Para ello, sumar la 
        // duración de cada asignatura.
        short horas_del_curso = 0;
        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            horas_del_curso += v_asignaturas[i].horas;
        }
         */
        repartos_entre_profesores_global = new ArrayList<>();
        v_asignaturas = cargar_asignaturas("F:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\asignaturas.csv");
        v_profesores = cargar_profesores("F:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\profesores.csv");

        // Comprobar si, aunque pueden repartirse las asignaturas, en algunos
        // repartos no intervienen todos los profesores propuestos en el archivo
        // de profesores.
        String[] profesores_sobrantes = profesores_sobrantes(v_asignaturas, v_profesores);

        // Quitar los profesores sobrantes:
        if (profesores_sobrantes != null) {
            List<Profesor> l_profesores = Arrays.asList(v_profesores);
            l_profesores = new ArrayList<Profesor>(l_profesores);
            for (int i = 0, j = l_profesores.size(); i < j; i++) {
                for (int k = 0, l = profesores_sobrantes.length; k < l; k++) {
                    if (l_profesores.get(i).nombre_completo == profesores_sobrantes[k]) {
                        l_profesores.remove(i);
                        i--;
                        j = l_profesores.size();
                        break;
                    }
                }
            }
            v_profesores = (Profesor[]) l_profesores.toArray(
                    new Profesor[l_profesores.size()]);
        }

        // Hallar los repartos de asignaturas que pueden realizarse.
        repartos_entre_profesores_global = repartir_asignaturas(v_asignaturas, v_profesores);

        // Imprimir los repartos hallados.
        print_to_console(repartos_entre_profesores_global);
    }

    static public String[] profesores_sobrantes(Asignatura[] v_asignaturas, Profesor[] v_profesores) {

        repartos_entre_profesores_global = new ArrayList<>();
//        v_asignaturas = cargar_asignaturas("D:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\asignaturas.csv");
//        v_profesores = cargar_profesores("D:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\profesores.csv");

        // Hallar los repartos de asignaturas que pueden realizarse:
        repartos_entre_profesores_global = repartir_asignaturas(v_asignaturas, v_profesores);

        // Almacenar en "l_cantidad_de_profesores_necesitados", la cantidad de
        // profesores que se han necesitado en cada reparto:
        ArrayList<Integer> l_cantidad_de_profesores_necesitados = new ArrayList<>();
        for (int i = 0, j = repartos_entre_profesores_global.size(); i < j; i++) {
            ArrayList<Asignaturas_de_un_profesor> un_reparto
                    = repartos_entre_profesores_global.get(i);
            l_cantidad_de_profesores_necesitados.add(un_reparto.size());
        }

        // Pasar a un vector el listado "l_cantidad_de_profesores_necesitados":
        Integer[] v_cantidad_de_profesores_necesitados
                = (Integer[]) l_cantidad_de_profesores_necesitados.toArray(
                        new Integer[l_cantidad_de_profesores_necesitados.size()]);
        Arrays.sort(v_cantidad_de_profesores_necesitados);

        // Averiguar qué cantidad de profesores se han necesitado en la 
        // mayoría de repartos:
        int contador = 0;
        int contador_previo = 0;
        int cantidad_de_profesores_necesitados = v_cantidad_de_profesores_necesitados[0];
        for (int i = 0, j = v_cantidad_de_profesores_necesitados.length; i < j; i++) {
            if (cantidad_de_profesores_necesitados != v_cantidad_de_profesores_necesitados[i]) {
                // Hay más repartos para una cantidad de profesores, que otros
                // repartos para otras cantidades de profesores:
                if (contador > contador_previo) {
                    cantidad_de_profesores_necesitados = v_cantidad_de_profesores_necesitados[i];
                    contador_previo = contador;
                    contador = 1;
                }
            } else {
                contador++;
            }
        }

        ArrayList<Par_profesor_y_contador> l_pares_profesor_y_veces_que_aparece_en_los_repartos
                = new ArrayList<>();

        // Hay la cantidad adecuada de profesores para hacer los repartos.
        // Es decir, no faltan ni sobran profesores para hacer los repartos.
        if (cantidad_de_profesores_necesitados == v_profesores.length) {
            return null;
        }

        // Se pretende repartir las asignaturas a más profesores que
        // los necesarios. Hay que avisar de esto al usuario.
        if (cantidad_de_profesores_necesitados < v_profesores.length) {

            // Recopilaremos los nombres de los profesores que aparecen en
            // los repartos preferidos.
            for (int i = 0, j = repartos_entre_profesores_global.size(); i < j; i++) {
                ArrayList<Asignaturas_de_un_profesor> un_reparto
                        = repartos_entre_profesores_global.get(i);

                boolean b_profesor_encontrado = false;
                // Un reparto se hace entre la cantidad preferida de profesores.
                if (un_reparto.size() == cantidad_de_profesores_necesitados) {
                    // Obtener los profesores que han intervenido en el reparto:
                    for (int k = 0, l = un_reparto.size(); k < l; k++) {
                        String s_profesor = un_reparto.get(k).profesor.nombre_completo;

                        // Buscar el profesor en el listado de profesores:
                        int m, n;
                        for (m = 0, n = l_pares_profesor_y_veces_que_aparece_en_los_repartos.size(); m < n; m++) {
                            // Se ha encontrado el profesor en el listado, así
                            // que se incrementa la cantidad de veces que se ha
                            // encontrado.
                            if (l_pares_profesor_y_veces_que_aparece_en_los_repartos.get(m).nombre_profesor == s_profesor) {
                                l_pares_profesor_y_veces_que_aparece_en_los_repartos.get(m).veces_que_el_profesor_aparece_en_los_repartos++;
                                b_profesor_encontrado = true;
                                break;
                            }
                        }
                        // No se ha encontrado el profesor en el listado, así
                        // que se añade al listado de profesores:
                        if (!b_profesor_encontrado) {
                            l_pares_profesor_y_veces_que_aparece_en_los_repartos.add(new Par_profesor_y_contador(s_profesor, 0));
                        } else {
                            b_profesor_encontrado = false;
                        }

                    }
                }
            }
        }

        Par_profesor_y_contador[] v_pares_profesor_y_veces_que_aparece_en_los_repartos
                = (Par_profesor_y_contador[]) l_pares_profesor_y_veces_que_aparece_en_los_repartos.toArray(
                        new Par_profesor_y_contador[l_pares_profesor_y_veces_que_aparece_en_los_repartos.size()]);
        Quicksort.quicksort_pares_ordenados_por_presencia(
                v_pares_profesor_y_veces_que_aparece_en_los_repartos,
                0, v_pares_profesor_y_veces_que_aparece_en_los_repartos.length - 1);

        // Quitar uno de los profesores sobrantes: los que menos aparezcan:
        ArrayList<String> l_profesores = new ArrayList<>();
        for (int i = 0,
                j = v_pares_profesor_y_veces_que_aparece_en_los_repartos.length
                - cantidad_de_profesores_necesitados;
                i < j;
                i++) {
            l_profesores.add(v_pares_profesor_y_veces_que_aparece_en_los_repartos[i].nombre_profesor);
        }

        String[] v_profesores_aux
                = (String[]) l_profesores.toArray(new String[l_profesores.size()]);
        return v_profesores_aux;
    }

    public static ArrayList<ArrayList<Asignaturas_de_un_profesor>> repartir_asignaturas(
            Asignatura[] v_asignaturas,
            Profesor[] v_profesores) {

        if (v_asignaturas == null || v_asignaturas.length == 0) {
            return null;
        }

        if (v_profesores == null || v_profesores.length == 0) {
            return null;
        }

        /*
        int n_cantidad_de_profesores[] = Repartidor.cantidad_de_profesores_para_el_reparto(l_repartos_asignaturas);

        if (v_profesores.length > n_cantidad_de_profesores[1]) {
            JOptionPane.showMessageDialog(this, "Demasiados profesores. El máximo es " + n_cantidad_de_profesores[1] + " profesores.", "Profesores", JOptionPane.INFORMATION_MESSAGE);
        }
        if (v_profesores.length < n_cantidad_de_profesores[0]) {
            JOptionPane.showMessageDialog(this, "Pocos profesores. El mínimo es " + n_cantidad_de_profesores[0] + " profesores.", "Profesores", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
         */
        int max_horas_por_profesor;
        int min_horas_por_profesor;
        for (int i = 0, j = v_profesores.length; i < j; i++) {
            min_horas_por_profesor = v_profesores[i].horas_mínimas_de_clase;
            max_horas_por_profesor = v_profesores[i].horas_máximas_de_clase;

            if (min_horas_por_profesor < Repartidor.MIN_HORAS_POR_PROFESOR) {
                return null;
            }
            if (max_horas_por_profesor < Repartidor.MIN_HORAS_POR_PROFESOR + 1) {
                return null;
            }
            if (max_horas_por_profesor < min_horas_por_profesor) {
                return null;
            }
            if (min_horas_por_profesor > Repartidor.MAX_HORAS_POR_PROFESOR - 1) {
                return null;
            }
            if (max_horas_por_profesor > Repartidor.MAX_HORAS_POR_PROFESOR) {
                return null;
            }
        }

        max_horas_por_profesor = 0;
        for (int i = 0, j = v_profesores.length; i < j; i++) {
            if (v_profesores[i].horas_máximas_de_clase > max_horas_por_profesor) {
                max_horas_por_profesor = v_profesores[i].horas_máximas_de_clase;
            }
        }

        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            int duracion = v_asignaturas[i].horas;
            if (duracion > max_horas_por_profesor) {
                return null;
            }
        }

        short horas_del_curso = 0;
        ArrayList<Asignatura[]> l_asig_repartidas = new ArrayList<>();

        // Obtener la duración del curso, en horas. Para ello, sumar la 
        // duración de cada asignatura.
        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            horas_del_curso += v_asignaturas[i].horas;
        }

        // Hallar la cantidad mínima y máxima de asignaturas de cada 
        // combinación, tal que cada combinación contenga entre 
        // horas_min_por_profesor y horas_max_por_profesor de horas en 
        // asignaturas.
        short num_elems[] = numero_de_elementos_de_las_combinaciones(
                v_asignaturas,
                v_profesores);

        // Obtener combinaciones de asignaturas. Cada combinación puede contener
        // entre num_elems[0] y num_elems[1] asignaturas.
        ArrayList<Asignatura[]> l_combinaciones_asignaturas;
        l_combinaciones_asignaturas = combinaciones_sin_repetición(
                v_asignaturas,
                num_elems[0],
                num_elems[1]);

        min_horas_por_profesor = v_profesores[0].horas_mínimas_de_clase;
        max_horas_por_profesor = v_profesores[0].horas_máximas_de_clase;
        for (int i = 1, j = v_profesores.length; i < j; i++) {
            if (v_profesores[i].horas_máximas_de_clase > max_horas_por_profesor) {
                max_horas_por_profesor = v_profesores[i].horas_máximas_de_clase;
            }
            if (v_profesores[i].horas_mínimas_de_clase < min_horas_por_profesor) {
                min_horas_por_profesor = v_profesores[i].horas_mínimas_de_clase;
            }
        }

        // Calcular combinaciones válidas de varias asignaturas, y
        // almacenarlas en un listado de combinaciones válidas.
        // La variable l_combinaciones_asignaturas es modificada.
        combinaciones_válidas(
                l_combinaciones_asignaturas,
                min_horas_por_profesor,
                max_horas_por_profesor);

        repartos_entre_profesores_global.clear();
        ArrayList<Asignaturas_de_un_profesor> reparto_entre_profesores = new ArrayList<>();
        repartir_asignaturas_recursivo(
                l_combinaciones_asignaturas,
                v_profesores,
                0,
                reparto_entre_profesores);

        // Devolver el resultado.
        ArrayList<ArrayList<Asignaturas_de_un_profesor>> repartos_entre_profesores = new ArrayList<>();
        repartos_entre_profesores = (ArrayList<ArrayList<Asignaturas_de_un_profesor>>) repartos_entre_profesores_global.clone();
        repartos_entre_profesores_global.clear();

        return repartos_entre_profesores;
    }

    public static void repartir_asignaturas_recursivo(
            ArrayList<Asignatura[]> l_combinaciones_asignaturas,
            Profesor profesores[],
            int index,
            ArrayList<Asignaturas_de_un_profesor> reparto_entre_profesores) {

        if (l_combinaciones_asignaturas.size() == 0) {
            repartos_entre_profesores_global.add(reparto_entre_profesores);
            return;
        }

        for (int i = index, j = profesores.length; i < j; i++) {

            int horas_mínimas_de_clase = profesores[i].horas_mínimas_de_clase;
            int horas_máximas_de_clase = profesores[i].horas_máximas_de_clase;

            for (int horas_de_clase = horas_mínimas_de_clase;
                    horas_de_clase <= horas_máximas_de_clase;
                    horas_de_clase++) {

                ArrayList<Asignatura[]> l_combinaciones_n_horas_asignaturas
                        = combinaciones_n_horas(
                                l_combinaciones_asignaturas,
                                horas_de_clase);
                // v_combinaciones_asignaturas = ListoArray(l_combinaciones_asignaturas);

                for (int k = 0, l = l_combinaciones_n_horas_asignaturas.size(); k < l; k++) {

                    reparto_entre_profesores.add(
                            new Asignaturas_de_un_profesor(
                                    profesores[i],
                                    l_combinaciones_n_horas_asignaturas.get(k)));
                    ArrayList<Asignatura[]> l_combinaciones_asignaturas_aux
                            = (ArrayList<Asignatura[]>) l_combinaciones_asignaturas.clone();
                    quitar_asignaturas(l_combinaciones_asignaturas, l_combinaciones_n_horas_asignaturas.get(k));

                    repartir_asignaturas_recursivo(
                            l_combinaciones_asignaturas,
                            profesores,
                            i + 1,
                            (ArrayList<Asignaturas_de_un_profesor>) reparto_entre_profesores.clone());

                    reparto_entre_profesores.remove(reparto_entre_profesores.size() - 1);
                    l_combinaciones_asignaturas = (ArrayList<Asignatura[]>) l_combinaciones_asignaturas_aux.clone();
                }
            }
        }
    }

    private static boolean hay_asignaturas_repartidas(Asignatura[] asignaturas) {

        for (int i = 0, j = asignaturas.length; i < j; i++) {
            if (asignaturas[i].repartida == true) {
                return true;
            }
        }

        return false;
    }

    private static short suma_horas_de_asignaturas(Asignatura[] asignaturas) {

        // Obtener una de las combinaciones de asignaturas.
        short suma = 0;
        boolean repartida = false;

        for (int i = 0, j = asignaturas.length; i < j; i++) {
            suma += asignaturas[i].horas;
        }

        return suma;
    }

    private static Asignatura[] ListoArray(ArrayList<Asignatura> l_combi_asig) {

        Asignatura[] v_combi_asig = new Asignatura[l_combi_asig.size()];
        for (int i = 0; i < v_combi_asig.length; i++) {
            Asignatura asignatura = new Asignatura(l_combi_asig.get(i));
            v_combi_asig[i] = asignatura;
        }
        return v_combi_asig;
    }

    /**
     * Devuelve el tamaño mínimo y máximo de las combinaciones de asignaturas.
     *
     * Cada combinación contiene varias asignaturas, y cada asignatura dura una
     * cantidad de horas. Cada combinación debe sumar una cantidad de horas
     * menor que horas_min_por_profesor, y mayor que horas_max_por_profesor.
     * Cuanto más duren las asignaturas, cada combinación contendrá menos
     * asignaturas. Cuanto menos duren las asignaturas, cada combinación
     * contendrá más asignaturas. La función
     * numero_de_elementos_de_las_combinaciones, calcula la cantidad mínima y
     * máxima de asignaturas que debe contener cada combinación, para sumar
     * entre horas_min_por_profesor y horas_max_por_profesor horas. Esta función
     * es necesaria para obtener después, las combinaciones de asignaturas que
     * útiles, es decir, aquéllas que tienen la cantidad de asignaturas
     * indicadas por numero_de_elementos_de_las_combinaciones.
     *
     * @param v_asignaturas Vector que contiene asignaturas.
     * @param horas_min_por_profesor Cantidad mínima de horas que debe contener
     * cada combinación. La duración de cada combinación resulta de sumar la
     * duración de cada asignatura de la combinación.
     * @param horas_max_por_profesor Cantidad máxima de horas que debe contener
     * cada combinación. La duración de cada combinación resulta de sumar la
     * duración de cada asignatura de la combinación.
     * @return Cantidad mínima y máxima de asignaturas, que puede contener cada
     * combinación.
     */
    public static short[] numero_de_elementos_de_las_combinaciones(
            Asignatura[] v_asignaturas,
            Profesor[] v_profesores) {

        int min_horas_por_profesor = v_profesores[0].horas_mínimas_de_clase;
        int max_horas_por_profesor = v_profesores[0].horas_máximas_de_clase;
        for (int i = 1, j = v_profesores.length; i < j; i++) {
            if (v_profesores[i].horas_máximas_de_clase > max_horas_por_profesor) {
                max_horas_por_profesor = v_profesores[i].horas_máximas_de_clase;
            }
            if (v_profesores[i].horas_mínimas_de_clase < min_horas_por_profesor) {
                min_horas_por_profesor = v_profesores[i].horas_mínimas_de_clase;
            }
        }

        // Almacenamos en un vector, la duración de cada asignatura.
        int v_horas[] = new int[v_asignaturas.length];
        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            v_horas[i] = v_asignaturas[i].horas;
        }

        // Ordenamos el vector.
        Arrays.sort(v_horas);

        int num_maximo_de_elementos = 0;
        for (int i = 0, j = v_horas.length, suma = 0; i < j && suma < max_horas_por_profesor; i++) {
            suma += v_horas[i];
            num_maximo_de_elementos++;
        }
        num_maximo_de_elementos--;

        int num_minimo_de_elementos = 0;
        for (int i = v_horas.length - 1, suma = 0; i > -1 && suma < min_horas_por_profesor; i--) {
            suma += v_horas[i];
            num_minimo_de_elementos++;
        }

        short[] v_combi_horas = new short[2];
        v_combi_horas[0] = (short) num_minimo_de_elementos;
        v_combi_horas[1] = (short) num_maximo_de_elementos;

        return v_combi_horas;

    }
}

class Par_profesor_y_contador {

    String nombre_profesor;
    int veces_que_el_profesor_aparece_en_los_repartos;

    Par_profesor_y_contador(String nombre_profesor_local, int contador_local) {
        nombre_profesor = nombre_profesor_local;
        veces_que_el_profesor_aparece_en_los_repartos = contador_local;
    }
}
