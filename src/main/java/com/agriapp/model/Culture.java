package com.agriapp.model;

import java.util.Date;

public class Culture {
    private int id;
    private String nom;
    private String variete;
    private Date dateSemis;
    private Date dateRecoltePrevue;
    private String statut;

    public Culture() {}

    public Culture(int id, String nom, String variete, Date dateSemis, Date dateRecoltePrevue, String statut) {
        this.id = id;
        this.nom = nom;
        this.variete = variete;
        this.dateSemis = dateSemis;
        this.dateRecoltePrevue = dateRecoltePrevue;
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getVariete() { return variete; }
    public void setVariete(String variete) { this.variete = variete; }

    public Date getDateSemis() { return dateSemis; }
    public void setDateSemis(Date dateSemis) { this.dateSemis = dateSemis; }

    public Date getDateRecoltePrevue() { return dateRecoltePrevue; }
    public void setDateRecoltePrevue(Date dateRecoltePrevue) { this.dateRecoltePrevue = dateRecoltePrevue; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "Culture{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", variete='" + variete + '\'' +
                ", dateSemis=" + dateSemis +
                ", dateRecoltePrevue=" + dateRecoltePrevue +
                ", statut='" + statut + '\'' +
                '}';
    }
}