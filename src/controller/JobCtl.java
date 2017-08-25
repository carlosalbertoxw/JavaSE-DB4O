/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.db4o.ObjectContainer;
import dao.JobDAO;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import model.Grade;
import model.Job;
import view.JobPan;
import view.MainFrm;

/**
 *
 * Controlador de puestos
 */
public class JobCtl implements ActionListener, MouseListener {

    private List<Grade> grades = new ArrayList();
    private JobDAO jobDAO;
    private JobPan jobPan;
    private Job job;
    private List<Job> jobs = new ArrayList();
    private static JobCtl singleton;

    public static JobCtl getInstance(MainFrm mainFrm) {
        singleton = null;
        if (singleton == null) {
            singleton = new JobCtl(mainFrm);
        }
        return singleton;
    }

    private JobCtl(MainFrm mainFrm) {
        Grade grade;
        grade = new Grade();
        grade.setGradeno(1);
        grade.setDescription("Directivo");
        grade.setLevel(1);
        grades.add(grade);
        grade = new Grade();
        grade.setGradeno(2);
        grade.setDescription("Lider Operativo");
        grade.setLevel(2);
        grades.add(grade);
        grade = new Grade();
        grade.setGradeno(3);
        grade.setDescription("Operativo");
        grade.setLevel(2);
        grades.add(grade);

        jobDAO = JobDAO.getInstance();
        jobPan = JobPan.getInstance(grades);
        job = new Job();

        mainFrm.getContainer().setVisible(false);
        mainFrm.getContainer().removeAll();
        mainFrm.getContainer().add(jobPan, BorderLayout.CENTER);
        mainFrm.getContainer().setVisible(true);

        jobPan.getBtnSave().addActionListener(this);
        jobPan.getBtnSave().setActionCommand("SAVE");
        jobPan.getBtnUpdate().addActionListener(this);
        jobPan.getBtnUpdate().setActionCommand("UPDATE");
        jobPan.getBtnDelete().addActionListener(this);
        jobPan.getBtnDelete().setActionCommand("DELETE");
        jobPan.getBtnClean().addActionListener(this);
        jobPan.getBtnClean().setActionCommand("CLEAN");
        jobPan.getTblList().addMouseListener(this);

        list();
    }

    private void save() {
        ObjectContainer container = jobDAO.loadConfiguration();
        int id = 0;
        jobs = jobDAO.readAll(container);
        for (Job j : jobs) {
            if (j.getJobno() > id) {
                id = j.getJobno();
            }
        }
        job.setJobno(id + 1);
        job.setJname(jobPan.getTxtJname().getText());
        for (Grade g : grades) {
            if (jobPan.getCboGradesal().getSelectedItem().toString().equals(g.getDescription())) {
                job.setGradesal(g.getGradeno());
                break;
            }
        }
        jobDAO.create(container, job);
        jobDAO.closeConfiguration(container);
    }

    private void update() {
        if (!jobPan.getTxtJobno().getText().isEmpty()) {
            job.setJobno(Integer.parseInt(jobPan.getTxtJobno().getText()));
            job.setJname(jobPan.getTxtJname().getText());
            for (Grade g : grades) {
                if (jobPan.getCboGradesal().getSelectedItem().toString().equals(g.getDescription())) {
                    job.setGradesal(g.getGradeno());
                    break;
                }
            }
            ObjectContainer container = jobDAO.loadConfiguration();
            jobDAO.update(container, job);
            jobDAO.closeConfiguration(container);
        }
    }

    private void delete() {
        if (!jobPan.getTxtJobno().getText().isEmpty()) {
            ObjectContainer container = jobDAO.loadConfiguration();
            jobDAO.delete(container, Integer.parseInt(jobPan.getTxtJobno().getText()));
            jobDAO.closeConfiguration(container);
        }
    }

    private void clean() {
        jobPan.getTxtJobno().setText("");
        jobPan.getTxtJname().setText("");
        jobPan.getCboGradesal().setSelectedIndex(0);
    }

    private void list() {
        ObjectContainer container = jobDAO.loadConfiguration();
        jobs = jobDAO.readAll(container);
        jobPan.getModel().setRowCount(0);
        for (Job job : jobs) {
            Object[] objects = new Object[3];
            objects[0] = job.getJobno();
            objects[1] = job.getJname();
            for (Grade g : grades) {
                if (job.getGradesal() == (g.getGradeno())) {
                    objects[2] = g.getDescription();
                    break;
                }
            }
            jobPan.getModel().addRow(objects);
        }
        jobPan.getTblList().setModel(jobPan.getModel());
        jobDAO.closeConfiguration(container);
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
        int fila = jobPan.getTblList().getSelectedRow();
        ObjectContainer container = jobDAO.loadConfiguration();
        job = jobDAO.read(container, Integer.parseInt(jobPan.getTblList().getValueAt(fila, 0).toString()));
        jobPan.getTxtJobno().setText(String.valueOf(job.getJobno()));
        jobPan.getTxtJname().setText(job.getJname());
        for (Grade g : grades) {
            if (job.getGradesal() == (g.getGradeno())) {
                jobPan.getCboGradesal().setSelectedItem(g.getDescription());
                break;
            }
        }
        jobDAO.closeConfiguration(container);
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
