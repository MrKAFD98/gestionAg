package com.agriapp.model;

public class Intrant {
    private int id;
    private String nom;
    private String type;
    private double cout;
    private double quantite;

    public Intrant() {}

    public Intrant(int id, String nom, String type, double cout, double quantite) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.cout = cout;
        this.quantite = quantite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getCout() { return cout; }
    public void setCout(double cout) { this.cout = cout; }
    public double getQuantite() { return quantite; }
    public void setQuantite(double quantite) { this.quantite = quantite; }
}