package com.agriapp.model;

import java.util.Date;

public class Recolte {
    private int id;
    private int cultureId;
    private Date dateRecolte;
    private double quantite;
    private double prixVente;

    public Recolte() {}

    public Recolte(int id, int cultureId, Date dateRecolte, double quantite, double prixVente) {
        this.id = id;
        this.cultureId = cultureId;
        this.dateRecolte = dateRecolte;
        this.quantite = quantite;
        this.prixVente = prixVente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCultureId() { return cultureId; }
    public void setCultureId(int cultureId) { this.cultureId = cultureId; }
    public Date getDateRecolte() { return dateRecolte; }
    public void setDateRecolte(Date dateRecolte) { this.dateRecolte = dateRecolte; }
    public double getQuantite() { return quantite; }
    public void setQuantite(double quantite) { this.quantite = quantite; }
    public double getPrixVente() { return prixVente; }
    public void setPrixVente(double prixVente) { this.prixVente = prixVente; }
}