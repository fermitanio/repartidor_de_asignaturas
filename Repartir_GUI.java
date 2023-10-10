/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.asignaturas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 *
 * @author fermitanio
 */
public class Repartir_GUI extends javax.swing.JFrame {

    private static javax.swing.JScrollPane jScrollPane1;
    private DefaultListModel model = new DefaultListModel();
    static private Repartidor repartidor;
    ArrayList<ArrayList<Asignatura[]>> l_reparto_asignaturas_global;
    Asignatura[] v_asignaturas;
    Profesor[] v_profesores;
    ArrayList<ArrayList<String>> matrix = new ArrayList<>();

    private void initComponents_ex(
            Cargar_datos_GUI cargar_datos_gui,
            Asignatura[] v_asignaturas,
            Profesor v_profesores[]) {

//        setResizable(false);
        jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        // jScrollPane1.add(jPanel1);

        jScrollPane1.setViewportView(jPanel1);
        this.add(jScrollPane1);

        GridLayout experimentLayout = new GridLayout(0, v_profesores.length + 1);
        // this.setLayout(experimentLayout);

        // RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS, 10);
        // jScrollPane1.setLayout(rl);
        int n_width = this.getWidth();
        int n_height = this.getHeight();
        jScrollPane1.setPreferredSize(new Dimension(n_width - 15, n_height - 60));
        jScrollPane1.setSize(n_width - 15, n_height - 60);
        jPanel1.setLayout(experimentLayout);

        /*
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Asignaturas:");
        jPanel1.add(jLabel1);

        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Profesores:");
        jPanel1.add(jLabel2);
         */
        ArrayList<ArrayList<Asignaturas_de_un_profesor>> l_repartos_asig
                = Repartidor.repartir_asignaturas(
                        v_asignaturas,
                        v_profesores);

        if (l_repartos_asig == null || l_repartos_asig.size() == 0) {
            JOptionPane.showMessageDialog(this, "Con los datos indicados no hay repartos válidos.", "Sin repartos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        this.v_asignaturas = v_asignaturas;
        this.v_profesores = v_profesores;

//        short num_elems[] = Repartidor.numero_de_elementos_de_las_combinaciones(
//                v_asignaturas,
//                v_profesores);

        // Arrays.sort(v_profesores);
        // Repartidor.quicksort_asignaturas(v_asignaturas, 0, v_asignaturas.length - 1);
        JLabel jlabel = new JLabel();
        jlabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        jlabel.setText("");
        jlabel.setMinimumSize(new Dimension(20, 18));
        jlabel.setPreferredSize(new Dimension(20, 18));
        jlabel.setMaximumSize(new Dimension(20, 18));
        jPanel1.add(jlabel);

        for (int i = 0, j = v_profesores.length; i < j; i++) {
            jlabel = new JLabel();
            jlabel.setName("0," + (i + 1) + "-"
                    + v_profesores[i].nombre_completo + "-"
                    + v_profesores[i].horas_mínimas_de_clase + "," + v_profesores[i].horas_máximas_de_clase);
            jlabel.setFont(new java.awt.Font("Tahoma", 0, 18));
            // jlabel.setSize(18, 20);
            jlabel.setText(v_profesores[i].nombre_abreviado);
            jlabel.setOpaque(true);
//            jlabel.setBackground(Color.red);
            jlabel.setMinimumSize(new Dimension(20, 18));
            jlabel.setPreferredSize(new Dimension(20, 18));
            jlabel.setMaximumSize(new Dimension(20, 18));
            jPanel1.add(jlabel);
        }

        for (int i = 0, j = v_asignaturas.length; i < j; i++) {
            //
            jlabel = new JLabel();
            jlabel.setFont(new java.awt.Font("Tahoma", 0, 18));
            // jlabel.setSize(18, 20);
            jlabel.setText(v_asignaturas[i].nombre);
            jlabel.setMinimumSize(new Dimension(20, 18));
            jlabel.setPreferredSize(new Dimension(20, 18));
            jlabel.setMaximumSize(new Dimension(20, 18));
            jPanel1.add(jlabel);

            for (int k = 0, l = v_profesores.length; k < l; k++) {
                JToggleButton button = new JToggleButton();
                button.setFont(new java.awt.Font("Tahoma", 0, 18));
                // jlabel.setSize(18, 20);
//              button.setName(i + "," + k + "-" + v_asignaturas[i].nombre + "," + v_asignaturas[i].horas + "-" + v_profesores[k].nombre_completo);
                button.setName(i + "," + k + "-" + v_asignaturas[i].nombre + "," + v_asignaturas[i].horas + "-" + v_profesores[k].nombre_completo + "," + v_profesores[k].horas_mínimas_de_clase + "," + v_profesores[k].horas_máximas_de_clase);
                button.setText(v_asignaturas[i].nombre + "," + v_asignaturas[i].horas + "-" + v_profesores[k].nombre_completo);
                // button.setText("Sin asignar");
                //jlabel.setMinimumSize(new Dimension(20, 18));
                //jlabel.setPreferredSize(new Dimension(20, 18));
                //jlabel.setMaximumSize(new Dimension(20, 18));
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        JToggleButton_MouseClicked(evt);
                    }
                });
                jPanel1.add(button);
            }
        }

        int n_altura = 18;

        // jPanel1.setPreferredSize(new Dimension(n_width - 15, n_altura));
        //jPanel1.setSize(n_width - 15, n_altura);
        //jPanel1.setMaximumSize(new Dimension(n_width - 15, n_altura));
        jPanel1.setPreferredSize(new Dimension(200, 200));
        jPanel1.setSize(200, 200);
        jPanel1.setMaximumSize(new Dimension(200, 200));

        jScrollPane1.setVisible(true);
        jPanel1.setVisible(true);

        // this.setResizable(false);
        this.setVisible(true);

    }

    /*
    // Añadir un bucle a esta función. Este bucle modifica matrix 
// mientras encuentre botones coloreados de verde. Además, actualiza
    // matrix según se ha pulsado o liberado el último botón en ser
    // pulsado. Este bucle debe explorar sólo botones en la columna
    // del botón pulsado o liberado.
    // Además, el segundo bucle vuelve a iterar sobre los botones en 
    // la columna del botón pulsado o liberado, coloreando de rojo o gris
    // según el valor de matrix.
    private void JToggleButton_MouseClicked(java.awt.event.MouseEvent evt) {

        Component[] components = getContentPane().getComponents();
        components = ((JScrollPane) components[0]).getViewport().getComponents();
        components = ((JPanel) components[0]).getComponents();

//        ArrayList<ArrayList<String>> matrix = new ArrayList<>();
        ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas_global = new ArrayList<>();
//        ArrayList<ArrayList<String>> matrix = new ArrayList<>();
//        ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas = new ArrayList<>();

        Object control = evt.getSource();
        if (!(control instanceof JToggleButton)) {
            return;
        }

        JToggleButton button = (JToggleButton) control;
        ArrayList<Integer> columnCoordinates = getColumnCoordinates(button);
// Now we're going to mark in red, buttons in the column
        // of the pressed button.
        for (int i = 0;
                i < components.length;
                i++) {

            // Ignore row where the pressed button is.
//            if (i >= startIndexOfRow && i < endIndexOfRow) {
//                continue;
//            }
            // Ignore buttons are not in the colums of the pressed button.
            if (columnCoordinates.indexOf(i) < 0) {
                continue;
            }

            Object component = components[i];
            if (component instanceof JToggleButton) {

                JToggleButton buttonAux = (JToggleButton) component;

//        String items[] = button.getName().split("-");
//        String s_asignatura = items[1].trim();
//        String s_profesor = items[2].trim();
                if (buttonAux.equals(button)) {
                    if (buttonAux.isSelected()) {
//            if (button.getBackground() == Color.LIGHT_GRAY) {
                        updateMatrix(matrix, button, true);
                        // Once the matrix is filled, build a list containing the matrix's data.
//                l_reparto_asignaturas = matrixToObject(matrix);

                        deactivateRow(button, components, true);
//                    matrix.clear();
                    } else {
                        updateMatrix(matrix, button, false);
                        deactivateRow(button, components, false);
                    }
                } 
//                else {
//                    if (buttonAux.getBackground() == Color.GREEN) {
//                        updateMatrix(matrix, buttonAux, true);
//                    }
//                }
            }
        }
        // Fill a matrix with teachers's names and theirs subjects. The first
        // element in each row is a teacher's name. Example:
        //
        // Ramiro, RL, CE
        // Pérez, IN, SR
        // Tárrega, PRL
        // ...
        ArrayList<Integer> rowCoordinates = getRowCoordinates(button);
        int startIndexOfRow = rowCoordinates.get(0);
        int endIndexOfRow = rowCoordinates.get(1);

//        ArrayList<Integer> columnCoordinates = getColumnCoordinates(button);
        // Now we're going to mark in red, buttons in the column
        // of the pressed button.
        for (int i = 0;
                i < components.length;
                i++) {

            // Ignore row where the pressed button is.
            if (i >= startIndexOfRow && i < endIndexOfRow) {
                continue;
            }

            // Ignore buttons are not in the colums of the pressed button.
            if (columnCoordinates.indexOf(i) < 0) {
                continue;
            }
            Object component = components[i];
            if (component instanceof JToggleButton) {

                JToggleButton buttonAux = (JToggleButton) component;

//                button.setBackground(Color.GREEN);
//                button.setEnabled(false);
                // Creo que hay que modificar esta línea:
//                if (buttonAux.getBackground() == Color.LIGHT_GRAY) {
                if (buttonAux.isEnabled() == true && buttonAux.getBackground() != Color.GREEN) {
                    ArrayList<ArrayList<String>> matrix_aux = copyMatrix(matrix);
                    updateMatrix(matrix_aux, buttonAux, true);

                    // Once the matrix is filled, build a list containing the matrix's data.
                    ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas_aux = matrixToObject(matrix_aux);

                    ArrayList<ArrayList<Asignaturas_de_un_profesor>> l_repartos_asignaturas = Repartidor.repartir_asignaturas(v_asignaturas, v_profesores);
                    boolean b = Repartidor.is_reparto_valido_ex(l_repartos_asignaturas, l_reparto_asignaturas_aux);
                    if (!b) {
                        buttonAux.setBackground(Color.red);
                    } else {
                        buttonAux.setBackground(Color.LIGHT_GRAY);
                    }
//                    else if (button.getBackground() == Color.red) {
//                        button.setBackground(Color.LIGHT_GRAY);
//                    }
//                    matrix.clear();
                }
            }
        }
    }
     */
    private void JToggleButton_MouseClicked(java.awt.event.MouseEvent evt) {

        Component[] components = getContentPane().getComponents();
        components = ((JScrollPane) components[0]).getViewport().getComponents();
        components = ((JPanel) components[0]).getComponents();

//        ArrayList<ArrayList<String>> matrix = new ArrayList<>();
//        ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas_global = new ArrayList<>();
//        ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas = new ArrayList<>();
        Object control = evt.getSource();
        if (!(control instanceof JToggleButton)) {
            return;
        }

        JToggleButton button = (JToggleButton) control;
        if (button.isEnabled() == false) {
            return;
        }

        if (button.getBackground() == Color.red) {
            return;
        }
//        String items[] = button.getName().split("-");
//        String s_asignatura = items[1].trim();
//        String s_profesor = items[2].trim();

        // This condition avoids activating a button already green coloured.
        if (button.isSelected() && button.getBackground() == Color.GREEN) {
            return;
        }

        if (button.isSelected()) {
//            if (button.getBackground() == Color.LIGHT_GRAY) {
            updateMatrix(matrix, button, true);
            // Once the matrix is filled, build a list containing the matrix's data.
//                l_reparto_asignaturas = matrixToObject(matrix);

            deactivateRow(button, components, true);
//                    matrix.clear();
        } else {
            updateMatrix(matrix, button, false);
            deactivateRow(button, components, false);
        }

        // Fill a matrix with teachers's names and theirs subjects. The first
        // element in each row is a teacher's name. Example:
        //
        // Ramiro, RL, CE
        // Pérez, IN, SR
        // Tárrega, PRL
        // ...
//        ArrayList<Integer> rowCoordinates = getRowCoordinates(button);
//        int startIndexOfRow = rowCoordinates.get(0);
//        int endIndexOfRow = rowCoordinates.get(1);
        ArrayList<Integer> columnCoordinates = getColumnCoordinates(button);

        // Now we're going to mark in red, buttons in the column
        // of the pressed button.
        for (int i = 0;
                i < components.length;
                i++) {

            // Ignore row where the pressed button is.
//            if (i >= startIndexOfRow && i < endIndexOfRow) {
//                continue;
//            }
            // Ignore buttons are not in the colums of the pressed button.
            if (columnCoordinates.indexOf(i) < 0) {
                continue;
            }

            Object component = components[i];
            if (!(component instanceof JToggleButton)) {
                continue;
            }

            JToggleButton buttonAux = (JToggleButton) component;

//            if (buttonAux.isEnabled() == false) {
//                continue;
//            }
            // Ignore the button just pressed.
            if (buttonAux.equals(button)) {
                continue;
            }
//                button.setBackground(Color.GREEN);
//                button.setEnabled(false);
            // Creo que hay que modificar esta línea:
//                if (buttonAux.getBackground() == Color.LIGHT_GRAY) {
            if (buttonAux.getBackground() != Color.GREEN) {
                ArrayList<ArrayList<String>> matrix_aux = copyMatrix(matrix);
                updateMatrix(matrix_aux, buttonAux, true);

                // Once the matrix is filled, build a list containing the matrix's data.
                ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas_aux = matrixToObject(matrix_aux);

                ArrayList<ArrayList<Asignaturas_de_un_profesor>> l_repartos_asignaturas = Repartidor.repartir_asignaturas(v_asignaturas, v_profesores);
                boolean b = Repartidor.is_reparto_valido_ex(l_repartos_asignaturas, l_reparto_asignaturas_aux);
                if (!b) {
                    buttonAux.setBackground(Color.red);
                } else {
                    buttonAux.setBackground(Color.LIGHT_GRAY);
                }
//                    else if (button.getBackground() == Color.red) {
//                        button.setBackground(Color.LIGHT_GRAY);
//                    }
//                    matrix.clear();
            }
        }

        setFirstRow(components);
    }

    public void setFirstRow(Component[] components) {

        for (int i = 1;
                i <= this.v_profesores.length;
                i++) {

            if (!(components[i] instanceof JLabel)) {
                continue;
            }

            JLabel firstLabel = (JLabel) components[0];
            Color defaultColor = firstLabel.getBackground();
            
            JLabel label = (JLabel) components[i];
            int horas_asignadas = 0;

            /*
            jlabel.setName("0," + i + "-" + 
                    v_profesores[i].nombre_completo + "-" + 
                    v_profesores[i].horas_mínimas_de_clase + "," + v_profesores[i].horas_máximas_de_clase);

             */
//            String asignatura = datos[1];
            String[] datos = label.getName().split("-");
            String s_string = datos[2];
            String[] a_strings = s_string.split(",");
            int horas_min_del_profesor = Integer.parseInt(a_strings[0]);

            ArrayList<Integer> columnCoordinates = getColumnCoordinates(label);
            for (int j = 0;
                    j < components.length;
                    j++) {

                // Ignore row where the pressed button is.
//            if (i >= startIndexOfRow && i < endIndexOfRow) {
//                continue;
//            }
                // Ignore buttons are not in the colums of the pressed button.
                if (columnCoordinates.indexOf(j) < 0) {
                    continue;
                }

                Object component = components[j];
                if (!(component instanceof JToggleButton)) {
                    continue;
                }

                JToggleButton button = (JToggleButton) component;
                if (button.getBackground() == Color.GREEN) {
                    // button.setName(i + "," + k + "-" + v_asignaturas[i].nombre + "," + v_asignaturas[i].horas + "-" + v_profesores[k].nombre_completo + "," + v_profesores[k].horas_mínimas_de_clase + "," + v_profesores[k].horas_máximas_de_clase);
                    String[] datosAux = button.getName().split("-");
                    String s_stringAux = datosAux[1];
                    String[] a_stringsAux = s_stringAux.split(",");
                    int horas_de_la_asignatura = Integer.parseInt(a_stringsAux[1]);
                    horas_asignadas += horas_de_la_asignatura;
                }
            }
            if (horas_asignadas >= horas_min_del_profesor) {
                label.setBackground(Color.GREEN);
            } else {
                label.setBackground(defaultColor);
            }
        }
    }

    private ArrayList<Integer> getRowCoordinates(JToggleButton button) {

        ArrayList<Integer> coordinates = new ArrayList<>();

        String items[] = button.getName().split("-");
        String s_string = items[0].trim();
        String[] s_coordinates = s_string.split(",");
        int row = Integer.parseInt(s_coordinates[0]) + 1;
        int column = Integer.parseInt(s_coordinates[1]) + 1;

        int startIndex = row * (this.v_profesores.length + 1) + 1;
        int endIndex = row * (this.v_profesores.length + 1) + this.v_profesores.length + 1;

        coordinates.add(startIndex);
        coordinates.add(endIndex);
        return coordinates;
    }

    private ArrayList<Integer> getColumnCoordinates(JToggleButton button) {

        ArrayList<Integer> coordinates = new ArrayList<>();

        String items[] = button.getName().split("-");
        String s_string = items[0].trim();
        String[] s_coordinates = s_string.split(",");
//        int row = Integer.parseInt(s_coordinates[0]) + 1;
        int column = Integer.parseInt(s_coordinates[1]);

        for (int i = 0, index = column + this.v_asignaturas.length + 1;
                i < this.v_asignaturas.length;
                i++, index += this.v_profesores.length + 1) {

            coordinates.add(index);
        }
        return coordinates;
    }

    private ArrayList<Integer> getColumnCoordinates(JLabel label) {

        ArrayList<Integer> coordinates = new ArrayList<>();

//        String items[] = label.getName().split("-");
//        String s_string = items[0].trim();
        String[] a_strings = label.getName().split("-");
        String[] a_coordenates = a_strings[0].split(",");
        int column = Integer.parseInt(a_coordenates[1]);

        for (int i = 0, index = column + this.v_profesores.length + 1;
                i < this.v_asignaturas.length;
                i++, index += this.v_profesores.length + 1) {

            coordinates.add(index);
        }
        return coordinates;
    }

    private void deactivateRow(JToggleButton button, Component[] components, boolean deactivate) {

        ArrayList<Integer> coordinates = getRowCoordinates(button);
//        String items[] = button.getName().split("-");
//        String s_string = items[0].trim();
//        String[] s_coordinates = s_string.split(",");
//        int row = Integer.parseInt(s_coordinates[0]) + 1;
//        int column = Integer.parseInt(s_coordinates[1]) + 1;

//        int startIndex = row * (this.v_profesores.length + 1) + 1;
//        int endIndex = row * (this.v_profesores.length + 1) + this.v_profesores.length + 1;
        int startIndex = coordinates.get(0);
        int endIndex = coordinates.get(1);

        for (int i = startIndex; i < endIndex; i++) {
            Object component = components[i];
            if (component instanceof JToggleButton) {

                JToggleButton buttonAux = (JToggleButton) component;

//                buttonAux.setOpaque(true);
                // Don't work:
                if (deactivate) {
                    if (button.equals(buttonAux)) {
                        button.setBackground(Color.GREEN);
                    } else/* if (buttonAux.getBackground() != Color.red) */ {
                        buttonAux.setEnabled(false);
//                        buttonAux.setBackground(Color.red);

                    }
                } else /*if (buttonAux.getBackground() != Color.red) */ {
                    buttonAux.setEnabled(true);
                    if (buttonAux.getBackground() == Color.GREEN) {
                        buttonAux.setBackground(Color.LIGHT_GRAY);
                    }
                }
//                buttonAux.repaint();

//                buttonAux.repaint();
            }
        }
    }

    private ArrayList<ArrayList<String>> copyMatrix(ArrayList<ArrayList<String>> matrix) {
        ArrayList<ArrayList<String>> matrixCopy = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            matrixCopy.add(new ArrayList<String>());
            for (int j = 0; j < matrix.get(i).size(); j++) {
                matrixCopy.get(i).add(matrix.get(i).get(j));
            }
        }
        return matrixCopy;
    }

    private ArrayList<Asignaturas_de_un_profesor> matrixToObject(ArrayList<ArrayList<String>> matrix) {
        ArrayList<Asignaturas_de_un_profesor> l_reparto_asignaturas = new ArrayList<>();
        ArrayList<Asignatura> l_asignaturas = new ArrayList<>();

        for (int k = 0; k < matrix.size(); k++) {
            l_asignaturas.clear();
            for (int j = 1; j < matrix.get(k).size(); j++) {
                String data = matrix.get(k).get(j);
                String v_data[] = data.split(",");
                Asignatura asignatura = new Asignatura(v_data[0], Integer.parseInt(v_data[1]));
                l_asignaturas.add(asignatura);
            }
            Profesor profesor = new Profesor(matrix.get(k).get(0), 0, 0);
            Asignatura[] v_asignaturas = (Asignatura[]) l_asignaturas.toArray(new Asignatura[l_asignaturas.size()]);
            l_reparto_asignaturas.add(new Asignaturas_de_un_profesor(profesor, v_asignaturas));
        }

        return l_reparto_asignaturas;
    }

    private void updateMatrix(
            ArrayList<ArrayList<String>> matrix,
            JToggleButton button,
            boolean addButton) {
        String[] datos = button.getName().split("-");
        String asignatura = datos[1];
        String s_string = datos[2];
        String[] a_strings = s_string.split(",");
        String profesor = a_strings[0];

        // Añadir una asignatura al reparto de asignaturas.
        if (addButton) {
            boolean found = false;
            for (int j = 0; j < matrix.size(); j++) {
                // Ha encontrado al profesor en la matriz. Por tanto, añadir 
                // a la fila j de la matriz donde está el profesor, la 
                // asignatura de ese profesor.
                if (matrix.get(j).get(0).equalsIgnoreCase(profesor)) {
                    matrix.get(j).add(asignatura);
                    found = true;
                    break;
                }
            }
            // El profesor no está en la matriz. Por tanto, añadir 
            // a una fila nueva de la matriz, el profesor y su asignatura.
            if (!found) {
                ArrayList<String> s_lista = new ArrayList<>();
                s_lista.add(profesor);
                s_lista.add(asignatura);
                matrix.add(s_lista);
            }
            // Se va a borrar una asignatura del reparto de asignaturas.
        } else {
            for (int j = 0; j < matrix.size(); j++) {
                // Ha encontrado al profesor en la matriz. Por tanto, borrar 
                // de la fila j de la matriz donde está el profesor, la asignatura
                // de ese profesor.
                if (matrix.get(j).get(0).equalsIgnoreCase(profesor)) {
                    // Buscar la asignatura en la fila j de la matriz
                    // donde está el profesor
                    ArrayList<String> list = matrix.get(j);
                    for (int k = 1; k < list.size(); k++) {
                        // Ha encontrado la asignatura en
                        // la columna k de la fila j de la matriz.
                        if (list.get(k).equalsIgnoreCase(asignatura)) {
                            // Borrar la asignatura.
                            matrix.get(j).remove(k);
                            // Si en la fila j de la matriz hay sólo
                            // un elemento (un profesor), entonces
                            // borrar la fila j de la matriz.
                            if (list.size() == 1) {
                                matrix.remove(j);
                            }
                            j = matrix.size();
                            break;
                        }
                    }
                }
            }
        }
    }

    private Asignatura[] get_asignaturas_GUI() {

        ArrayList<Asignatura> l_asignaturas = new ArrayList<>();
        Component controls[] = this.getComponents();

        for (int i = 0, j = controls.length; i < j; i++) {
            Component control = controls[i];
            if (control instanceof JComboBox) {
                if (((JComboBox) control).getName() == "asignatura") {
                    JComboBox jCombo_aux = (JComboBox) control;
                    Asignatura asignatura = jCombo_to_Asignatura((String) jCombo_aux.getSelectedItem());
                    if (asignatura != null) {
                        l_asignaturas.add(asignatura);
                    }
                }
            }
        }
        return (Asignatura[]) l_asignaturas.toArray();
    }

    private String[] get_profesores_GUI() {

        ArrayList<String> l_profesores = new ArrayList<>();
        Component controls[] = this.getComponents();

        for (int i = 0, j = controls.length; i < j; i++) {
            Component control = controls[i];
            if (control instanceof JComboBox) {
                if (((JComboBox) control).getName() == "profesor") {
                    JComboBox jCombo_aux = (JComboBox) control;
                    String profesor = (String) jCombo_aux.getSelectedItem();
                    if (profesor != null && profesor != "") {
                        l_profesores.add(profesor);
                    }
                }
            }
        }
        return (String[]) l_profesores.toArray();
    }

    /*
    private void add_ItemListener_jCombo(JComboBox<String> jCombo) {

        jCombo.addItemListener(event -> {
            // The item affected by the event.
            if (event.getStateChange() == ItemEvent.SELECTED) {
                v_asignaturas = get_asignaturas_GUI();
                v_profesores = get_profesores_GUI();
            }
        });
    }
     */
    static private Asignatura jCombo_to_Asignatura(String texto) {

        String item = texto.trim();

        if (item == "") {
            return null;
        }

        String items[] = item.split("-");
        String nombre = items[0].trim();
        int n = nombre.indexOf(" ");
        nombre = nombre.substring(n + 1, nombre.length());

        String s_horas = items[1].trim();
        n = s_horas.lastIndexOf(" ");
        s_horas = s_horas.substring(0, n);
        int horas = Integer.parseInt(s_horas);

        return new Asignatura(nombre, horas);
    }

    /**
     * Creates new form NewJFrame
     */
    public Repartir_GUI() {
        initComponents();
        initComponents_ex(null, null, null);
    }

    /**
     * Creates new form NewJFrame
     */
    public Repartir_GUI(
            Cargar_datos_GUI cargar_datos_gui,
            Asignatura[] v_asignaturas,
            Profesor v_profesores[]) {

        initComponents();
        initComponents_ex(cargar_datos_gui, v_asignaturas, v_profesores);
        l_reparto_asignaturas_global = new ArrayList<>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        int n = this.getWidth();
        int o = this.getHeight();
        jScrollPane1.setPreferredSize(new Dimension(n - 15, o - 60));
        jScrollPane1.setSize(n - 15, o - 60);
    }//GEN-LAST:event_formComponentResized

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Repartir_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Repartir_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Repartir_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Repartir_GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Repartir_GUI().setVisible(true);
            }
        });
    }

    /*
    public javax.swing.JList<String> get_listado() {
        return this.jList1;
    }
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
