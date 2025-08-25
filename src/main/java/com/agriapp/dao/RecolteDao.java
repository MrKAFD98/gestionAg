package com.agriapp.dao;

import com.agriapp.model.Recolte;
import com.agriapp.util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecolteDao {

    public List<Recolte> findByCulture(int cultureId) throws Exception {
        List<Recolte> list = new ArrayList<>();
        String sql = "SELECT * FROM recolte WHERE culture_id=? ORDER BY date_recolte DESC";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, cultureId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Recolte r = new Recolte();
                    r.setId(rs.getInt("id"));
                    r.setCultureId(rs.getInt("culture_id"));
                    r.setDateRecolte(rs.getDate("date_recolte"));
                    r.setQuantite(rs.getDouble("quantite"));
                    r.setPrixVente(rs.getDouble("prix_vente"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    public Recolte insert(Recolte r) throws Exception {
        String sql = "INSERT INTO recolte(culture_id, date_recolte, quantite, prix_vente) VALUES(?,?,?,?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getCultureId());
            ps.setDate(2, r.getDateRecolte() == null ? null : new java.sql.Date(r.getDateRecolte().getTime()));
            ps.setDouble(3, r.getQuantite());
            ps.setDouble(4, r.getPrixVente());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) r.setId(rs.getInt(1)); }
        }
        return r;
    }

    public void delete(int id) throws Exception {
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM recolte WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}