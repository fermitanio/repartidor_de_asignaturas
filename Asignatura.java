/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.asignaturas;

/**
 *
 * @author fermitanio
 */
public class Asignatura {

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
