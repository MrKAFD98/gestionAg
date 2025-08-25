package com.agriapp.dao;

import com.agriapp.model.Intrant;
import com.agriapp.util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntrantDao {
    public List<Intrant> findAll() throws Exception {
        List<Intrant> list = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM intrant ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Intrant i = new Intrant();
                i.setId(rs.getInt("id"));
                i.setNom(rs.getString("nom"));
                i.setType(rs.getString("type"));
                i.setCout(rs.getDouble("cout"));
                i.setQuantite(rs.getDouble("quantite"));
                list.add(i);
            }
        }
        return list;
    }

    public Intrant insert(Intrant i) throws Exception {
        String sql = "INSERT INTO intrant(nom, type, cout, quantite) VALUES(?,?,?,?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, i.getNom());
            ps.setString(2, i.getType());
            ps.setDouble(3, i.getCout());
            ps.setDouble(4, i.getQuantite());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) i.setId(rs.getInt(1)); }
        }
        return i;
    }

    public void update(Intrant i) throws Exception {
        String sql = "UPDATE intrant SET nom=?, type=?, cout=?, quantite=? WHERE id=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, i.getNom());
            ps.setString(2, i.getType());
            ps.setDouble(3, i.getCout());
            ps.setDouble(4, i.getQuantite());
            ps.setInt(5, i.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM intrant WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}