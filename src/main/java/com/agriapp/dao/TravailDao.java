package com.agriapp.dao;

import com.agriapp.model.TravailAgricole;
import com.agriapp.util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravailDao {

    // Méthode ajoutée pour DashboardPanel
    public List<TravailAgricole> findAll() throws Exception {
        List<TravailAgricole> list = new ArrayList<>();
        String sql = "SELECT * FROM travail_agricole ORDER BY date_travail DESC";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TravailAgricole t = new TravailAgricole();
                t.setId(rs.getInt("id"));
                t.setCultureId(rs.getInt("culture_id"));
                t.setTypeTravail(rs.getString("type_travail"));
                t.setDateTravail(rs.getDate("date_travail"));
                t.setSuperficie(rs.getDouble("superficie"));
                list.add(t);
            }
        }
        return list;
    }

    public List<TravailAgricole> findByCulture(int cultureId) throws Exception {
        List<TravailAgricole> list = new ArrayList<>();
        String sql = "SELECT * FROM travail_agricole WHERE culture_id=? ORDER BY date_travail DESC";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, cultureId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TravailAgricole t = new TravailAgricole();
                    t.setId(rs.getInt("id"));
                    t.setCultureId(rs.getInt("culture_id"));
                    t.setTypeTravail(rs.getString("type_travail"));
                    t.setDateTravail(rs.getDate("date_travail"));
                    t.setSuperficie(rs.getDouble("superficie"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public TravailAgricole insert(TravailAgricole t) throws Exception {
        String sql = "INSERT INTO travail_agricole(culture_id, type_travail, date_travail, superficie) VALUES(?,?,?,?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getCultureId());
            ps.setString(2, t.getTypeTravail());
            ps.setDate(3, t.getDateTravail() == null ? null : new java.sql.Date(t.getDateTravail().getTime()));
            ps.setDouble(4, t.getSuperficie());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setId(rs.getInt(1));
            }
        }
        return t;
    }

    public void delete(int id) throws Exception {
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM travail_agricole WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}