package com.ouhami.myapplication.beans;

public class Service {

    private Integer id;
    private String nom;

    public Service(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Service() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
