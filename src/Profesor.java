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
public class Profesor {

    public String nombre_completo;
    public String nombre_abreviado;
    public int horas_mínimas_de_clase;
    public int horas_máximas_de_clase;

    public Profesor(String nombre_completo,
            int horas_mínimas_de_clase,
            int horas_máximas_de_clase) {
        this.nombre_completo = nombre_completo;
        
        String nombres[] = nombre_completo.split(" ");
        this.nombre_abreviado = nombres[0];
        this.horas_mínimas_de_clase = horas_mínimas_de_clase;
        this.horas_máximas_de_clase = horas_máximas_de_clase;
    }

    public Profesor(Profesor another) {
        this.nombre_completo = another.nombre_completo;
        this.nombre_abreviado = another.nombre_abreviado;
        this.horas_mínimas_de_clase = another.horas_mínimas_de_clase;
        this.horas_máximas_de_clase = another.horas_máximas_de_clase;
    }

    public Profesor clone() {
        return new Profesor(this);
    }
}
