/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * Modelo de grados
 */
public class Grade {

    private int gradeno;
    private String description;
    private int level;

    public int getGradeno() {
        return gradeno;
    }

    public void setGradeno(int gradeno) {
        this.gradeno = gradeno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
