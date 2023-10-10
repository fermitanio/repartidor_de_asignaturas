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
import javax.swing.JOptionPane;

/**
 *
 * @author fermitanio
 */
public class Quicksort {

    public static void quicksort_pares_ordenados_por_presencia(Par_profesor_y_contador[] v_pares, int izq, int der) {
        Par_profesor_y_contador pivote = v_pares[izq]; // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Par_profesor_y_contador aux;

        while (i < j) {                          // mientras no se crucen las búsquedas                                   
            while (v_pares[i].veces_que_el_profesor_aparece_en_los_repartos
                    <= pivote.veces_que_el_profesor_aparece_en_los_repartos && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (v_pares[j].veces_que_el_profesor_aparece_en_los_repartos
                    > pivote.veces_que_el_profesor_aparece_en_los_repartos) {
                j--;           // busca elemento menor que pivote
            }
            if (i < j) {                        // si no se han cruzado                      
                aux = v_pares[i];                      // los intercambia
                v_pares[i] = v_pares[j];
                v_pares[j] = aux;
            }
        }

        v_pares[izq] = v_pares[j];      // se coloca el pivote en su lugar de forma que tendremos                                    
        v_pares[j] = pivote;      // los menores a su izquierda y los mayores a su derecha

        if (izq < j - 1) {
            quicksort_pares_ordenados_por_presencia(v_pares, izq, j - 1);          // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            quicksort_pares_ordenados_por_presencia(v_pares, j + 1, der);          // ordenamos subarray derecho
        }
    }

    public static void quicksort_profesores_ordenados_por_horas_máximas(Profesor[] profesores, int izq, int der) {
        Profesor pivote = profesores[izq]; // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Profesor aux;

        while (i < j) {                          // mientras no se crucen las búsquedas                                   
            while (profesores[i].horas_máximas_de_clase <= pivote.horas_máximas_de_clase && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (profesores[i].horas_máximas_de_clase > pivote.horas_máximas_de_clase) {
                j--;           // busca elemento menor que pivote
            }
            if (i < j) {                        // si no se han cruzado                      
                aux = profesores[i];                      // los intercambia
                profesores[i] = profesores[j];
                profesores[j] = aux;
            }
        }

        profesores[izq] = profesores[j];      // se coloca el pivote en su lugar de forma que tendremos                                    
        profesores[j] = pivote;      // los menores a su izquierda y los mayores a su derecha

        if (izq < j - 1) {
            quicksort_profesores_ordenados_por_horas_máximas(profesores, izq, j - 1);          // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            quicksort_profesores_ordenados_por_horas_máximas(profesores, j + 1, der);          // ordenamos subarray derecho
        }
    }

    public static void quicksort_profesores_ordenados_por_horas_mínimas(Profesor[] profesores, int izq, int der) {
        Profesor pivote = profesores[izq]; // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Profesor aux;

        while (i < j) {                          // mientras no se crucen las búsquedas                                   
            while (profesores[i].horas_mínimas_de_clase <= pivote.horas_mínimas_de_clase && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (profesores[i].horas_mínimas_de_clase > pivote.horas_mínimas_de_clase) {
                j--;           // busca elemento menor que pivote
            }
            if (i < j) {                        // si no se han cruzado                      
                aux = profesores[i];                      // los intercambia
                profesores[i] = profesores[j];
                profesores[j] = aux;
            }
        }

        profesores[izq] = profesores[j];      // se coloca el pivote en su lugar de forma que tendremos                                    
        profesores[j] = pivote;      // los menores a su izquierda y los mayores a su derecha

        if (izq < j - 1) {
            quicksort_profesores_ordenados_por_horas_mínimas(profesores, izq, j - 1);          // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            quicksort_profesores_ordenados_por_horas_mínimas(profesores, j + 1, der);          // ordenamos subarray derecho
        }
    }

    public static void quicksort_asignaturas_por_duracion(Asignatura[] asignaturas, int izq, int der) {
        Asignatura pivote = asignaturas[izq]; // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        Asignatura aux;

        while (i < j) {                          // mientras no se crucen las búsquedas                                   
            while (asignaturas[i].horas <= pivote.horas && i < j) {
                i++; // busca elemento mayor que pivote
            }
            while (asignaturas[i].horas > pivote.horas) {
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
            quicksort_asignaturas_por_nombre(asignaturas, izq, j - 1);          // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            quicksort_asignaturas_por_nombre(asignaturas, j + 1, der);          // ordenamos subarray derecho
        }
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
    public static void quicksort_asignaturas_por_nombre(Asignatura[] asignaturas, int izq, int der) {
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
            quicksort_asignaturas_por_nombre(asignaturas, izq, j - 1);          // ordenamos subarray izquierdo
        }
        if (j + 1 < der) {
            quicksort_asignaturas_por_nombre(asignaturas, j + 1, der);          // ordenamos subarray derecho
        }
    }

    public static void quicksort_reparto_asignaturas(ArrayList<Asignatura[]> l_reparto_asignaturas) {

        for (int k = 0, l = l_reparto_asignaturas.size(); k < l; k++) {
            Asignatura asignaturas[] = l_reparto_asignaturas.get(k);
            quicksort_asignaturas_por_nombre(asignaturas, 0, asignaturas.length - 1);
        }
        quicksort_reparto_asignaturas_recursivo(l_reparto_asignaturas, 0, l_reparto_asignaturas.size() - 1);
    }

    private static void quicksort_reparto_asignaturas_recursivo(ArrayList<Asignatura[]> l_reparto_asignaturas, int izq, int der) {

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

    private static int compare_vectores_asignaturas(Asignatura[] asignaturas_a, Asignatura[] asignaturas_b) {

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
}
