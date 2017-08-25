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
import model.Department;
import model.Employee;
import model.Job;
import org.freixas.jcalendar.JCalendarCombo;

/**
 *
 * Vista de empleados
 */
public class EmployeePan extends JPanel {

    private JLabel lblEmpno;
    private JTextField txtEmpno;
    private JLabel lblEname;
    private JTextField txtEname;
    private JLabel lblLname;
    private JTextField txtLname;
    private JLabel lblJob;
    private JComboBox cboJob;
    private JLabel lblMgr;
    private JComboBox cboMgr;
    private JLabel lblHiredate;
    private JCalendarCombo calHiredate;
    private JLabel lblSal;
    private JTextField txtSal;
    private JLabel lblComm;
    private JTextField txtComm;
    private JLabel lblDeptno;
    private JComboBox cboDeptno;
    private JPanel buttonPanel;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClean;
    private JTable tblList;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private static EmployeePan singleton;

    public static EmployeePan getInstance(List<Job> jobs, List<Employee> employees, List<Department> departments) {
        if (singleton == null) {
            singleton = new EmployeePan(jobs, employees, departments);
        }
        return singleton;
    }

    private EmployeePan(List<Job> jobs, List<Employee> employees, List<Department> departments) {
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 5, 5);
        constraints.anchor = GridBagConstraints.NORTHWEST;

        lblEmpno = new JLabel("Clave");
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(lblEmpno, constraints);

        txtEmpno = new JTextField(5);
        txtEmpno.setEnabled(false);
        constraints.gridx = 1;
        this.add(txtEmpno, constraints);

        lblEname = new JLabel("Nombre");
        constraints.gridx = 2;
        this.add(lblEname, constraints);

        txtEname = new JTextField(20);
        constraints.gridx = 3;
        this.add(txtEname, constraints);

        lblLname = new JLabel("Apellido");
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(lblLname, constraints);

        txtLname = new JTextField(20);
        constraints.gridx = 1;
        this.add(txtLname, constraints);

        lblJob = new JLabel("Puesto");
        constraints.gridx = 2;
        this.add(lblJob, constraints);

        cboJob = new JComboBox();
        constraints.gridx = 3;
        this.add(cboJob, constraints);
        cboJob.addItem("Selecciona una opción");

        for (Job j : jobs) {
            cboJob.addItem(j.getJname());
        }

        lblMgr = new JLabel("Supervisor");
        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(lblMgr, constraints);

        cboMgr = new JComboBox();
        constraints.gridx = 1;
        this.add(cboMgr, constraints);
        cboMgr.addItem("Selecciona una opción");

        for (Employee e : employees) {
            cboMgr.addItem(e.getEname() + " " + e.getLname());
        }

        lblHiredate = new JLabel("Contratación");
        constraints.gridx = 2;
        this.add(lblHiredate, constraints);

        calHiredate = new JCalendarCombo();
        constraints.gridx = 3;
        this.add(calHiredate, constraints);

        lblSal = new JLabel("Salario");
        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(lblSal, constraints);

        txtSal = new JTextField(20);
        constraints.gridx = 1;
        this.add(txtSal, constraints);

        lblComm = new JLabel("Comisión");
        constraints.gridx = 2;
        this.add(lblComm, constraints);

        txtComm = new JTextField(20);
        constraints.gridx = 3;
        this.add(txtComm, constraints);

        lblDeptno = new JLabel("Departamento");
        constraints.gridx = 0;
        constraints.gridy = 4;
        this.add(lblDeptno, constraints);

        cboDeptno = new JComboBox();
        constraints.gridx = 1;
        this.add(cboDeptno, constraints);
        cboDeptno.addItem("Selecciona una opción");

        for (Department d : departments) {
            cboDeptno.addItem(d.getDescription());
        }

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 5;
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
        String[] columnsName = new String[]{"Clave", "Nombre", "Apellido", "Puesto", "Supervisor", "Contratación", "Salario", "Comisión", "Departamento"};
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
        constraints.gridy = 6;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        this.add(scrollPane, constraints);
    }

    public JTextField getTxtEmpno() {
        return txtEmpno;
    }

    public JTextField getTxtEname() {
        return txtEname;
    }

    public JTextField getTxtLname() {
        return txtLname;
    }

    public JComboBox getCboJob() {
        return cboJob;
    }

    public JComboBox getCboMgr() {
        return cboMgr;
    }

    public JCalendarCombo getCalHiredate() {
        return calHiredate;
    }

    public JTextField getTxtSal() {
        return txtSal;
    }

    public JTextField getTxtComm() {
        return txtComm;
    }

    public JComboBox getCboDeptno() {
        return cboDeptno;
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

    public JTable getTblList() {
        return tblList;
    }

    public DefaultTableModel getModel() {
        return model;
    }

}
