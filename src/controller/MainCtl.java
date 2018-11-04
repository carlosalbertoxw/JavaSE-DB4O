/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainFrm;

/**
 *
 * Controlador principal
 */
public class MainCtl implements ActionListener {

    private MainFrm mainFrm;

    public MainCtl() {
        mainFrm = new MainFrm();

        mainFrm.getEmployees().addActionListener(this);
        mainFrm.getEmployees().setActionCommand("EMPLOYEES");
        mainFrm.getJobs().addActionListener(this);
        mainFrm.getJobs().setActionCommand("JOBS");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("EMPLOYEES")) {
            new EmployeeCtl(mainFrm);
        } else if (e.getActionCommand().equals("JOBS")) {
            new JobCtl(mainFrm);
        }
    }

    public static void main(String[] args) {
        new MainCtl();
    }

}
