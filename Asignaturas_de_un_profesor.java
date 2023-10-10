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
public class Asignaturas_de_un_profesor {

    Profesor profesor;
    Asignatura asignaturas[];

    public Asignaturas_de_un_profesor(Profesor profesor_local, Asignatura asignaturas_local[]) {
        profesor = (Profesor) profesor_local.clone();
        asignaturas = (Asignatura[]) asignaturas_local.clone();
    }

    public Asignaturas_de_un_profesor(Asignaturas_de_un_profesor another) {
        profesor = (Profesor) another.profesor.clone();
        asignaturas = (Asignatura[]) another.asignaturas.clone();
    }

}
