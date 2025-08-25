package com.agriapp.model;

import java.util.Date;

public class TravailAgricole {
    private int id;
    private int cultureId;
    private String typeTravail;
    private Date dateTravail;
    private double superficie;

    public TravailAgricole() {}

    public TravailAgricole(int id, int cultureId, String typeTravail, Date dateTravail, double superficie) {
        this.id = id;
        this.cultureId = cultureId;
        this.typeTravail = typeTravail;
        this.dateTravail = dateTravail;
        this.superficie = superficie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCultureId() { return cultureId; }
    public void setCultureId(int cultureId) { this.cultureId = cultureId; }

    public String getTypeTravail() { return typeTravail; }
    public void setTypeTravail(String typeTravail) { this.typeTravail = typeTravail; }

    public Date getDateTravail() { return dateTravail; }
    public void setDateTravail(Date dateTravail) { this.dateTravail = dateTravail; }

    public double getSuperficie() { return superficie; }
    public void setSuperficie(double superficie) { this.superficie = superficie; }
}