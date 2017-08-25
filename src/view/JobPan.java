/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Grade;

/**
 *
 * Vista de puestos
 */
public class JobPan extends JPanel {

    private JLabel lblJobno;
    private JTextField txtJobno;
    private JLabel lblJname;
    private JTextField txtJname;
    private JLabel lblGradesal;
    private JComboBox cboGradesal;
    private JPanel buttonPanel;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClean;
    private JTable tblList;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private static JobPan singleton;

    public static JobPan getInstance(List<Grade> grades) {
        if (singleton == null) {
            singleton = new JobPan(grades);
        }
        return singleton;
    }

    public JobPan(List<Grade> grades) {
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 5, 5);
        constraints.anchor = GridBagConstraints.NORTHWEST;

        lblJobno = new JLabel("Clave");
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(lblJobno, constraints);

        txtJobno = new JTextField(5);
        txtJobno.setEnabled(false);
        constraints.gridx = 1;
        this.add(txtJobno, constraints);

        lblJname = new JLabel("Nombre");
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(lblJname, constraints);

        txtJname = new JTextField(20);
        constraints.gridx = 1;
        this.add(txtJname, constraints);

        lblGradesal = new JLabel("Grado");
        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(lblGradesal, constraints);

        cboGradesal = new JComboBox();
        constraints.gridx = 1;
        this.add(cboGradesal, constraints);
        cboGradesal.addItem("Selecciona una opción");

        for (Grade g : grades) {
            cboGradesal.addItem(g.getDescription());
        }

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        this.add(buttonPanel, constraints);

        btnSave = new JButton("Guardar");
        buttonPanel.add(btnSave);

        btnUpdate = new JButton("Actualizar");
        buttonPanel.add(btnUpdate);

        btnDelete = new JButton("Borrar");
        buttonPanel.add(btnDelete);

        btnClean = new JButton("Limpiar");
        buttonPanel.add(btnClean);

        tblList = new JTable();
        tblList.setEditingColumn(0);
        String[] columnsName = new String[]{"Clave", "Nombre", "Grado"};
        model = new DefaultTableModel(null, columnsName) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        model.setRowCount(0);
        tblList.setModel(model);

        scrollPane = new JScrollPane(tblList);
        scrollPane.setPreferredSize(new Dimension(750, 200));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        this.add(scrollPane, constraints);
    }

    public JTextField getTxtJobno() {
        return txtJobno;
    }

    public JTextField getTxtJname() {
        return txtJname;
    }

    public JComboBox getCboGradesal() {
        return cboGradesal;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getTblList() {
        return tblList;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

}
