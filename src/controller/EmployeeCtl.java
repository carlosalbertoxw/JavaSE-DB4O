/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.db4o.ObjectContainer;
import dao.EmployeeDAO;
import dao.JobDAO;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import model.Department;
import model.Employee;
import model.Job;
import view.EmployeePan;
import view.MainFrm;

/**
 *
 * Controlador de empleados
 */
public class EmployeeCtl implements ActionListener, MouseListener {

    private List<Job> jobs = new ArrayList();
    private JobDAO jobDAO;
    private List<Department> departments = new ArrayList();
    private EmployeeDAO employeeDAO;
    private EmployeePan employeePan;
    private List<Employee> employees = new ArrayList();
    ObjectContainer container = null;
    ObjectContainer container2 = null;

    public EmployeeCtl(MainFrm mainFrm) {
        jobDAO = new JobDAO();
        employeeDAO = new EmployeeDAO();

        container2 = jobDAO.loadConfiguration();
        container = employeeDAO.loadConfiguration();
        Department d;
        d = new Department();
        d.setDepno(1);
        d.setDescription("Ventas");
        departments.add(d);
        d = new Department();
        d.setDepno(2);
        d.setDescription("TI");
        departments.add(d);
        d = new Department();
        d.setDepno(3);
        d.setDescription("Administración");
        departments.add(d);

        jobs = jobDAO.readAll(container2);

        employees = employeeDAO.readAll(container);

        employeePan = new EmployeePan(jobs, employees, departments);

        mainFrm.getContainer().setVisible(false);
        mainFrm.getContainer().removeAll();
        mainFrm.getContainer().add(employeePan, BorderLayout.CENTER);
        mainFrm.getContainer().setVisible(true);

        employeePan.getBtnSave().addActionListener(this);
        employeePan.getBtnSave().setActionCommand("SAVE");
        employeePan.getBtnUpdate().addActionListener(this);
        employeePan.getBtnUpdate().setActionCommand("UPDATE");
        employeePan.getBtnDelete().addActionListener(this);
        employeePan.getBtnDelete().setActionCommand("DELETE");
        employeePan.getBtnClean().addActionListener(this);
        employeePan.getBtnClean().setActionCommand("CLEAN");
        employeePan.getTblList().addMouseListener(this);

        jobDAO.closeConfiguration(container2);
        employeeDAO.closeConfiguration(container);
        container = null;
        container2 = null;

        list();
    }

    private void list() {
        if (container == null) {
            container = employeeDAO.loadConfiguration();
        }
        if (container2 == null) {
            container2 = jobDAO.loadConfiguration();
        }
        employees = employeeDAO.readAll(container);
        jobs = jobDAO.readAll(container2);
        employeePan.getModel().setRowCount(0);
        for (Employee employee : employees) {
            Object[] objects = new Object[9];
            objects[0] = employee.getEmpno();
            objects[1] = employee.getEname();
            objects[2] = employee.getLname();
            for (Job j : jobs) {
                if (employee.getJob() == (j.getJobno())) {
                    objects[3] = j.getJname();
                    break;
                }
            }
            for (Employee e : employees) {
                if (employee.getMgr() == (e.getEmpno())) {
                    objects[4] = e.getEname() + " " + e.getLname();
                    break;
                }
            }
            objects[5] = employee.getHiredate();
            objects[6] = employee.getSal();
            objects[7] = employee.getComm();
            for (Department d : departments) {
                if (employee.getDeptno() == (d.getDepno())) {
                    objects[8] = d.getDescription();
                    break;
                }
            }
            employeePan.getModel().addRow(objects);
        }
        employeePan.getTblList().setModel(employeePan.getModel());
        if (container != null) {
            employeeDAO.closeConfiguration(container);
            container = null;
        }
        if (container2 != null) {
            jobDAO.closeConfiguration(container2);
            container2 = null;
        }
    }

    private void clean() {
        employeePan.getTxtEmpno().setText("");
        employeePan.getTxtEname().setText("");
        employeePan.getTxtLname().setText("");
        employeePan.getCboJob().setSelectedIndex(0);
        employeePan.getCboMgr().setSelectedIndex(0);
        employeePan.getCalHiredate().setDate(new Date());
        employeePan.getTxtSal().setText("");
        employeePan.getTxtComm().setText("");
        employeePan.getCboDeptno().setSelectedIndex(0);
    }

    private void save() {
        Employee employee = new Employee();
        if (container == null) {
            container = employeeDAO.loadConfiguration();
        }
        if (container2 == null) {
            container2 = jobDAO.loadConfiguration();
        }
        employees = employeeDAO.readAll(container);
        jobs = jobDAO.readAll(container2);
        int id = 0;
        employees = employeeDAO.readAll(container);
        for (Employee e : employees) {
            if (e.getEmpno() > id) {
                id = e.getEmpno();
            }
        }
        employee.setEmpno(id + 1);
        employee.setEname(employeePan.getTxtEname().getText());
        employee.setLname(employeePan.getTxtLname().getText());
        for (Job j : jobs) {
            if (employeePan.getCboJob().getSelectedItem().toString().equals(j.getJname())) {
                employee.setJob(j.getJobno());
                break;
            }
        }
        for (Employee e : employees) {
            if (employeePan.getCboMgr().getSelectedItem().toString().equals(e.getEname() + " " + e.getLname())) {
                employee.setMgr(e.getEmpno());
                break;
            }
        }
        employee.setHiredate(employeePan.getCalHiredate().getSelectedItem().toString());
        if (employeePan.getTxtSal().getText().equals("")) {
            employee.setSal(0.0);
        } else {
            try {
                employee.setSal(Double.parseDouble(employeePan.getTxtSal().getText()));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(employeePan, "El salario debe ser un dato númerico");
                employee.setSal(0.0);
            }
        }
        if (employeePan.getTxtComm().getText().equals("")) {
            employee.setComm(0.0);
        } else {
            try {
                employee.setComm(Double.parseDouble(employeePan.getTxtComm().getText()));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(employeePan, "La comisión debe ser un dato númerico");
                employee.setComm(0.0);
            }
        }
        for (Department d : departments) {
            if (employeePan.getCboDeptno().getSelectedItem().toString().equals(d.getDescription())) {
                employee.setDeptno(d.getDepno());
                break;
            }
        }
        employeeDAO.create(container, employee);
        if (container != null) {
            employeeDAO.closeConfiguration(container);
            container = null;
        }
        if (container2 != null) {
            jobDAO.closeConfiguration(container2);
            container2 = null;
        }
    }

    private void delete() {
        Employee employee = new Employee();
        if (!employeePan.getTxtEmpno().getText().isEmpty()) {
            if (container == null) {
                container = employeeDAO.loadConfiguration();
            }
            if (container2 == null) {
                container2 = jobDAO.loadConfiguration();
            }
            employeeDAO.delete(container, Integer.parseInt(employeePan.getTxtEmpno().getText()));
            if (container != null) {
                employeeDAO.closeConfiguration(container);
                container = null;
            }
            if (container2 != null) {
                jobDAO.closeConfiguration(container2);
                container2 = null;
            }
        }
    }

    private void update() {
        Employee employee = new Employee();
        if (!employeePan.getTxtEmpno().getText().isEmpty()) {
            if (container == null) {
                container = employeeDAO.loadConfiguration();
            }
            if (container2 == null) {
                container2 = jobDAO.loadConfiguration();
            }

            employees = employeeDAO.readAll(container);
            jobs = jobDAO.readAll(container2);

            employee.setEmpno(Integer.parseInt(employeePan.getTxtEmpno().getText()));
            employee.setEname(employeePan.getTxtEname().getText());
            employee.setLname(employeePan.getTxtLname().getText());
            for (Job j : jobs) {
                if (employeePan.getCboJob().getSelectedItem().toString().equals(j.getJname())) {
                    employee.setJob(j.getJobno());
                    break;
                }
            }
            for (Employee e : employees) {
                if (employeePan.getCboMgr().getSelectedItem().toString().equals(e.getEname() + " " + e.getLname())) {
                    employee.setMgr(e.getEmpno());
                    break;
                }
            }
            employee.setHiredate(employeePan.getCalHiredate().getSelectedItem().toString());
            if (employeePan.getTxtSal().getText().equals("")) {
                employee.setSal(0.0);
            } else {
                try {
                    employee.setSal(Double.parseDouble(employeePan.getTxtSal().getText()));
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(employeePan, "El salario debe ser un dato númerico");
                    employee.setSal(0.0);
                }
            }
            if (employeePan.getTxtComm().getText().equals("")) {
                employee.setComm(0.0);
            } else {
                try {
                    employee.setComm(Double.parseDouble(employeePan.getTxtComm().getText()));
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(employeePan, "La comisión debe ser un dato númerico");
                    employee.setComm(0.0);
                }
            }
            for (Department d : departments) {
                if (employeePan.getCboDeptno().getSelectedItem().toString().equals(d.getDescription())) {
                    employee.setDeptno(d.getDepno());
                    break;
                }
            }
            employeeDAO.update(container, employee);

            if (container != null) {
                employeeDAO.closeConfiguration(container);
                container = null;
            }
            if (container2 != null) {
                jobDAO.closeConfiguration(container2);
                container2 = null;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SAVE")) {
            save();
            list();
            clean();
        } else if (e.getActionCommand().equals("UPDATE")) {
            update();
            list();
            clean();
        } else if (e.getActionCommand().equals("DELETE")) {
            delete();
            list();
            clean();
        } else if (e.getActionCommand().equals("CLEAN")) {
            list();
            clean();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Employee employee = new Employee();
        if (container == null) {
            container = employeeDAO.loadConfiguration();
        }
        if (container2 == null) {
            container2 = jobDAO.loadConfiguration();
        }
        employees = employeeDAO.readAll(container);
        jobs = jobDAO.readAll(container2);
        int fila = employeePan.getTblList().getSelectedRow();
        employee = employeeDAO.read(container, Integer.parseInt(employeePan.getTblList().getValueAt(fila, 0).toString()));
        employeePan.getTxtEmpno().setText(String.valueOf(employee.getEmpno()));
        employeePan.getTxtEname().setText(employee.getEname());
        employeePan.getTxtLname().setText(employee.getLname());
        for (Job j : jobs) {
            if (employee.getJob() == (j.getJobno())) {
                employeePan.getCboJob().setSelectedItem(j.getJname());
                break;
            }
        }
        for (Employee em : employees) {
            if (employee.getMgr() == (em.getEmpno())) {
                employeePan.getCboMgr().setSelectedItem(em.getEname() + " " + em.getLname());
                break;
            }
        }
        employeePan.getCalHiredate().setSelectedItem(employee.getHiredate());
        employeePan.getTxtSal().setText(String.valueOf(employee.getSal()));
        employeePan.getTxtComm().setText(String.valueOf(employee.getComm()));
        for (Department d : departments) {
            if (employee.getDeptno() == (d.getDepno())) {
                employeePan.getCboDeptno().setSelectedItem(d.getDescription());
                break;
            }
        }
        if (container != null) {
            employeeDAO.closeConfiguration(container);
            container = null;
        }
        if (container2 != null) {
            jobDAO.closeConfiguration(container2);
            container2 = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
