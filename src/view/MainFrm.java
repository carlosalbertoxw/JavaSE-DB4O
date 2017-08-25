/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * Vista principal
 */
public class MainFrm extends JFrame {

    private JMenuBar menuBar;
    private JMenu options;
    private JMenuItem employees;
    private JMenuItem jobs;
    private JPanel container;

    public MainFrm() {
        this.setSize(800, 500);
        this.setTitle("Aplicación");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        this.setJMenuBar(menuBar);

        options = new JMenu("Opciones");
        options.setBackground(Color.WHITE);
        menuBar.add(options);

        employees = new JMenuItem("Empleados");
        employees.setBackground(Color.WHITE);
        options.add(employees);

        jobs = new JMenuItem("Puestos");
        jobs.setBackground(Color.WHITE);
        options.add(jobs);

        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Color.WHITE);
        this.add(container);

        this.setVisible(true);
    }

    public JPanel getContainer() {
        return container;
    }

    public JMenuItem getEmployees() {
        return employees;
    }

    public JMenuItem getJobs() {
        return jobs;
    }
}
