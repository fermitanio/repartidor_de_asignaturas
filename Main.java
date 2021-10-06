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
package com.mycompany.horas;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 *
 * @author fermitanio
 */
public class Main {

    // Variables utilizadas en varias funciones recursivas.
    static ArrayList<Asignatura[]> l_combi_asig_global = new ArrayList<>();
    static ArrayList<ArrayList<Asignatura[]>> l_asig_repartidas_global = new ArrayList<>();

    // Supondremos que cada profesor, puede impartir entre 4 y 8 horas 
    // semanales de asignaturas.
    static int min_horas_por_profesor;
    static int max_horas_por_profesor;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
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
        Asignatura[] v_asignaturas = cargar_asignaturas("D:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\asignaturas.csv");

        // Obtener la duración del curso, en horas. Para ello, sumar la 
        // duración de cada asignatura.
        short horas_del_curso = 0;
        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            horas_del_curso += v_asignaturas[i].horas;
        }

        // Obtener las horas mínimas y máximas, que puede impartir un profesor.
        int n_items[] = cargar_horas_por_profesor("D:\\Manowar\\USB-2 (53'7 GB)\\04-Mis_Cosas\\02. Mis cosas. DVD 2\\25-Asignaturas\\horas.csv");
        min_horas_por_profesor = n_items[0];
        max_horas_por_profesor = n_items[1];

        // Hallar la cantidad mínima y máxima de asignaturas de cada 
        // combinación, tal que cada combinación contenga entre 
        // horas_min_por_profesor y horas_max_por_profesor de horas en 
        // asignaturas.
        short num_elems[] = numero_de_elementos_de_las_combinaciones(
                v_asignaturas,
                min_horas_por_profesor,
                max_horas_por_profesor);

        // Obtener combinaciones de asignaturas. Cada combinación puede contener
        // entre num_elems[0] y num_elems[1] asignaturas.
        ArrayList<Asignatura[]> l_combi_asig = new ArrayList<>();
        l_combi_asig = combinaciones_sin_repetición(v_asignaturas, num_elems[0], num_elems[1]);

        // Calcular combinaciones válidas de varias asignaturas, y
        // almacenarlas en un listado de combinaciones válidas.
        // La variable l_combi_asig es modificada.
        combinaciones_válidas(l_combi_asig, min_horas_por_profesor, max_horas_por_profesor);

        // Hallar los repartos de asignaturas que pueden realizarse.
        ArrayList<ArrayList<Asignatura[]>> l_repartos_asignaturas = repartir_asignaturas(l_combi_asig, horas_del_curso);

        // Imprimir los repartos hallados.
        print_to_console(l_repartos_asignaturas);
    }

    static Asignatura[] cargar_asignaturas(String ruta_archivo_asignaturas) {

        String line = "";
        ArrayList<Asignatura> l_asignaturas = new ArrayList<>();

        try {
            File myObj = new File(ruta_archivo_asignaturas);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                String items[] = line.split(";");
                l_asignaturas.add(new Asignatura(items[0], Integer.parseInt(items[1])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Asignatura[] v_asignaturas = ListoArray(l_asignaturas);
        return v_asignaturas;
    }

    static int[] cargar_horas_por_profesor(String ruta_archivo_horas) {

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

    static void print_to_console(ArrayList<ArrayList<Asignatura[]>> l_repartos_asignaturas) {
        System.out.print(
                "Horas por profesor: ");
        System.out.println(
                "(" + min_horas_por_profesor + ", " + max_horas_por_profesor + ")");

        for (int i = 0, j = l_repartos_asignaturas.size();
                i < j;
                i++) {
            System.out.println();
            System.out.println("Reparto " + (i + 1) + ":");

            ArrayList<Asignatura[]> l_reparto_asignaturas = l_repartos_asignaturas.get(i);
            for (int k = 0, l = l_reparto_asignaturas.size(); k < l; k++) {
                Asignatura asignaturas[] = l_reparto_asignaturas.get(k);

                System.out.print("(");
                for (int m = 0, n = asignaturas.length; m < n; m++) {
                    System.out.print(asignaturas[m].nombre + ", " + asignaturas[m].horas);
                    if (asignaturas.length > 1 && m < n - 1) {
                        System.out.print("; ");
                    }
                }
                System.out.println(")");
            }
        }
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
    static ArrayList<Asignatura[]> combinaciones_sin_repetición(Asignatura[] v_asignaturas, int min_elementos, int max_elementos) {

        ArrayList<Asignatura[]> l_combinaciones_asignaturas = new ArrayList<>();
        for (int i = min_elementos; i <= max_elementos; i++) {

            // Calcular combinaciones válidas de varios elementos.
            ArrayList<Asignatura[]> l_combinaciones_asignaturas_aux;
            l_combinaciones_asignaturas_aux = combinaciones_sin_repetición_de_n_elementos(v_asignaturas, i);

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
    static ArrayList<Asignatura[]> combinaciones_sin_repetición_de_n_elementos(Asignatura[] v_asignaturas, int num_elementos) {
        ArrayList<Asignatura> l_combi_asig = new ArrayList<>();
        combinaciones_sin_repetición_recursivo(v_asignaturas, 0, num_elementos, l_combi_asig);

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
    static void combinaciones_sin_repetición_recursivo(Asignatura[] v_asignaturas, int index, int num_elementos, ArrayList<Asignatura> l_combi_asig) {

        if (num_elementos == 0) {
            // Variable global.
            l_combi_asig_global.add(ListoArray(l_combi_asig));
            return;
        }

        for (int i = index; i < v_asignaturas.length - num_elementos + 1; i++) {
            l_combi_asig.add(v_asignaturas[i]);
            combinaciones_sin_repetición_recursivo(v_asignaturas, i + 1, num_elementos - 1, (ArrayList<Asignatura>) l_combi_asig.clone());
            l_combi_asig.remove(l_combi_asig.size() - 1);
        }
    }

    /**
     * - Las combinaciones para ser válidas, deben contener asignaturas cuya
     * suma de horas esté en el intervalo [4, 8]. // Horas por profesor: (4, 8)
     */
    static ArrayList<Asignatura[]> combinaciones_válidas(
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

    static ArrayList<ArrayList<Asignatura[]>> repartir_asignaturas(ArrayList<Asignatura[]> l_combi_asig, short n_horas) {

        ArrayList<Asignatura[]> l_asig_repartidas = new ArrayList<>();
        repartir_asignaturas_recursivo(l_combi_asig, (short) 0, n_horas, l_asig_repartidas);

        // Devolver el resultado.
        ArrayList<ArrayList<Asignatura[]>> l_reparto_asig = new ArrayList<>();
        l_reparto_asig = (ArrayList<ArrayList<Asignatura[]>>) l_asig_repartidas_global.clone();
        l_asig_repartidas_global.clear();

        return l_reparto_asig;
    }

    // Esta función no debe devolver algo. Devuelve algo de manera provisional,
    // para probar que la función devuelve al menos un reparto válido.
    static void repartir_asignaturas_recursivo(ArrayList<Asignatura[]> l_combi_asig,
            int index,
            short n_horas,
            ArrayList<Asignatura[]> l_asig_repartidas) {

        if (n_horas == 0) {
            l_asig_repartidas_global.add((ArrayList<Asignatura[]>) l_asig_repartidas.clone());
            // Sustituir por "return".
            return;
        } else {
            if (n_horas < 0 || n_horas < min_horas_por_profesor) {
                // Sustituir por "return".
                return;
            }
        }

        int result = -1;
        short n_horas_aux = n_horas;

        for (int i = index, j = l_combi_asig.size(); i < j; i++) {

            n_horas = n_horas_aux;
            n_horas -= suma_horas_de_asignaturas(l_combi_asig, i);

            // La asignatura no está repartida.
            if (n_horas != n_horas_aux) {
                l_combi_asig = marcar_asignaturas(l_combi_asig, i, true, l_asig_repartidas);
                l_asig_repartidas.add(l_combi_asig.get(i));
                //
                Asignatura asig[] = l_combi_asig.get(i);
                l_combi_asig = l_combi_asig;
                l_asig_repartidas = l_asig_repartidas;
                //
                repartir_asignaturas_recursivo((ArrayList<Asignatura[]>) l_combi_asig.clone(), i + 1, n_horas, (ArrayList<Asignatura[]>) l_asig_repartidas.clone());
                l_asig_repartidas.remove(l_asig_repartidas.size() - 1);
                l_combi_asig = marcar_asignaturas(l_combi_asig, i, false, l_asig_repartidas);

            }
        }
    }

    static boolean is_asignatura_repartida(Asignatura asig, ArrayList<Asignatura[]> l_asig_repartidas) {

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

    static ArrayList<Asignatura[]> marcar_asignaturas(
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

    /**
     * Comprueba que un reparto de asignaturas es válido, es decir, un reparto
     * que: dará a cada profesor las horas que puede dar, y no dejará ninguna
     * asignatura sin repartir.
     *
     * La función is_reparto_valido busca el reparto a evaluar, en una lista de
     * repartos válidos. Si encuentra el reparto en la lista, se considera
     * válido al reparto, y no válido en otro caso.
     *
     * El código siguiente sirve para probar la función is_reparto_valido:
     *
     * ArrayList<Asignatura[]> l_reparto_asignaturas = new ArrayList<>();
     * l_reparto_asignaturas.add(new Asignatura[]{SR, CE});
     * l_reparto_asignaturas.add(new Asignatura[]{RL}); boolean b =
     * is_reparto_valido(l_reparto_asignaturas, l_repartos_asignaturas);
     *
     * @param l_reparto_asignaturas Reparto a averiguar si es válido o no.
     * @param l_repartos_asignaturas Repartos válidos de asignaturas.
     * @return True si el reparto de asignaturas está entre los repartos
     * válidos. False si el reparto de asignaturas no aparece entre los repartos
     * válidos.
     */
    static boolean is_reparto_valido(ArrayList<Asignatura[]> l_reparto_asignaturas, ArrayList<ArrayList<Asignatura[]>> l_repartos_asignaturas) {

        //
        quicksort_reparto_asignaturas(l_reparto_asignaturas);

        //
        for (int i = 0, j = l_repartos_asignaturas.size();
                i < j;
                i++) {
            ArrayList<Asignatura[]> l_repartos_asignaturas_aux = l_repartos_asignaturas.get(i);
            quicksort_reparto_asignaturas(l_repartos_asignaturas_aux);
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

    public static boolean compare_repartos_asignaturas(ArrayList<Asignatura[]> l_reparto_asignaturas_a, ArrayList<Asignatura[]> l_reparto_asignaturas_b) {

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

    public static void quicksort_asignaturas(Asignatura[] asignaturas, int izq, int der) {
        Asignatura pivote = asignaturas[izq]; // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Asignatura aux;

        while (i < j) {                          // mientras no se crucen las búsquedas                                   
            while ((asignaturas[i].nombre.compareToIgnoreCase(pivote.nombre) < 0
                    || asignaturas[i].nombre.compareToIgnoreCase(pivote.nombre) == 0) && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (asignaturas[j].nombre.compareToIgnoreCase(pivote.nombre) > 0) {
                j--;           // busca elemento menor que pivote
            }
            if (i < j) {                        // si no se han cruzado                      
                aux = asignaturas[i];                      // los intercambia
                asignaturas[i] = asignaturas[j];
                asignaturas[j] = aux;
            }
        }

        asignaturas[izq] = asignaturas[j];      // se coloca el pivote en su lugar de forma que tendremos                                    
        asignaturas[j] = pivote;      // los menores a su izquierda y los mayores a su derecha

        if (izq < j - 1) {
            quicksort_asignaturas(asignaturas, izq, j - 1);          // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            quicksort_asignaturas(asignaturas, j + 1, der);          // ordenamos subarray derecho
        }
    }

    public static void quicksort_reparto_asignaturas(ArrayList<Asignatura[]> l_reparto_asignaturas) {

        for (int k = 0, l = l_reparto_asignaturas.size(); k < l; k++) {
            Asignatura asignaturas[] = l_reparto_asignaturas.get(k);
            quicksort_asignaturas(asignaturas, 0, asignaturas.length - 1);
        }
        quicksort_reparto_asignaturas_recursivo(l_reparto_asignaturas, 0, l_reparto_asignaturas.size() - 1);
    }

    public static int compare_vectores_asignaturas(Asignatura[] asignaturas_a, Asignatura[] asignaturas_b) {

        int tope = asignaturas_a.length >= asignaturas_b.length
                ? asignaturas_b.length : asignaturas_a.length;

        for (int i = 0; i < tope; i++) {
            if (asignaturas_a[i].nombre.compareToIgnoreCase(asignaturas_b[i].nombre) < 0) {
                return -1;
            } else if (asignaturas_a[i].nombre.compareToIgnoreCase(asignaturas_b[i].nombre) > 0) {
                return 1;
            }
        }

        return 0;
    }

    public static boolean equal_vectores_asignaturas(Asignatura[] asignaturas_a, Asignatura[] asignaturas_b) {

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

    public static void quicksort_reparto_asignaturas_recursivo(ArrayList<Asignatura[]> l_reparto_asignaturas, int izq, int der) {

        Asignatura[] pivote = l_reparto_asignaturas.get(izq); // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Asignatura[] aux;

        while (i < j) {                          // mientras no se crucen las búsquedas                                   
            while ((compare_vectores_asignaturas(l_reparto_asignaturas.get(i), pivote) < 0
                    || compare_vectores_asignaturas(l_reparto_asignaturas.get(i), pivote) == 0) && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (compare_vectores_asignaturas(l_reparto_asignaturas.get(j), pivote) > 0) {
                j--;           // busca elemento menor que pivote
            }
            if (i < j) {                        // si no se han cruzado                      
                aux = l_reparto_asignaturas.get(i);                      // los intercambia
                l_reparto_asignaturas.set(i, l_reparto_asignaturas.get(j));
                l_reparto_asignaturas.set(j, aux);
            }
        }

        l_reparto_asignaturas.set(izq, l_reparto_asignaturas.get(j));      // se coloca el pivote en su lugar de forma que tendremos                                    
        l_reparto_asignaturas.set(j, pivote);      // los menores a su izquierda y los mayores a su derecha

        if (izq < j - 1) {
            quicksort_reparto_asignaturas_recursivo(l_reparto_asignaturas, izq, j - 1);          // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            quicksort_reparto_asignaturas_recursivo(l_reparto_asignaturas, j + 1, der);          // ordenamos subarray derecho
        }
    }

    static short suma_horas_de_asignaturas(ArrayList<Asignatura[]> l_combi_asig, int index) {

        // Obtener una de las combinaciones de asignaturas.
        short suma = 0;
        boolean repartida = false;
        Asignatura[] asignaturas = l_combi_asig.get(index);

        for (int i = 0, j = asignaturas.length; i < j; i++) {
            if (asignaturas[i].repartida == true) {
                repartida = true;
                break;
            }
            suma += asignaturas[i].horas;
        }

        return repartida == false ? suma : 0;
    }

    static Asignatura[] ListoArray(ArrayList<Asignatura> l_combi_asig) {

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
    static short[] numero_de_elementos_de_las_combinaciones(
            Asignatura[] v_asignaturas,
            int horas_min_por_profesor,
            int horas_max_por_profesor) {

        // Almacenamos en un vector, la duración de cada asignatura.
        int v_horas[] = new int[v_asignaturas.length];
        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            v_horas[i] = v_asignaturas[i].horas;
        }

        // Ordenamos el vector.
        Arrays.sort(v_horas);

        int num_maximo_de_elementos = 0;
        for (int i = 0, j = v_horas.length, suma = 0; i < j && suma < horas_max_por_profesor; i++) {
            suma += v_horas[i];
            num_maximo_de_elementos++;
        }
        num_maximo_de_elementos--;

        int num_minimo_de_elementos = 0;
        for (int i = v_horas.length - 1, suma = 0; i > -1 && suma < horas_min_por_profesor; i--) {
            suma += v_horas[i];
            num_minimo_de_elementos++;
        }

        short[] v_combi_horas = new short[2];
        v_combi_horas[0] = (short) num_minimo_de_elementos;
        v_combi_horas[1] = (short) num_maximo_de_elementos;

        return v_combi_horas;
    }
}

class Asignatura {

    public String nombre;
    public int horas;
    public boolean repartida;

    public Asignatura(String nombre, int horas) {
        this.nombre = nombre;
        this.horas = horas;
        this.repartida = false;
    }

    public Asignatura(Asignatura another) {
        this.nombre = another.nombre;
        this.horas = another.horas;
        this.repartida = another.repartida;
    }

}
