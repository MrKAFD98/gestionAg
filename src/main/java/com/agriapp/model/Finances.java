package com.agriapp.model;

import java.util.Date;

public class Finances {
    private int id;
    private double montant;
    private Date dateOperation;
    private String categorie;

    public Finances() {}

    public Finances(int id, double montant, Date dateOperation, String categorie) {
        this.id = id;
        this.montant = montant;
        this.dateOperation = dateOperation;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public Date getDateOperation() { return dateOperation; }
    public void setDateOperation(Date dateOperation) { this.dateOperation = dateOperation; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
}