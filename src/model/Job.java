/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * Modelo de puestos
 */
public class Job {

    private int jobno;
    private String jname;
    private int gradesal;

    public int getJobno() {
        return jobno;
    }

    public void setJobno(int jobno) {
        this.jobno = jobno;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public int getGradesal() {
        return gradesal;
    }

    public void setGradesal(int gradesal) {
        this.gradesal = gradesal;
    }

}
