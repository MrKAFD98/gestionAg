package com.agriapp.dao;

import com.agriapp.model.Culture;
import com.agriapp.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CultureDao {

    public List<Culture> findAll() throws Exception {
        List<Culture> list = new ArrayList<>();
        String sql = "SELECT * FROM culture ORDER BY id DESC";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Culture c = new Culture();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setVariete(rs.getString("variete"));
                c.setDateSemis(rs.getDate("date_semis"));
                c.setDateRecoltePrevue(rs.getDate("date_recolte_prevue"));
                c.setStatut(rs.getString("statut"));
                list.add(c);
            }
        }
        return list;
    }

    public Culture insert(Culture c) throws Exception {
        String sql = "INSERT INTO culture(nom, variete, date_semis, date_recolte_prevue, statut) VALUES(?,?,?,?,?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNom());
            ps.setString(2, c.getVariete());
            ps.setDate(3, c.getDateSemis() == null ? null : new java.sql.Date(c.getDateSemis().getTime()));
            ps.setDate(4, c.getDateRecoltePrevue() == null ? null : new java.sql.Date(c.getDateRecoltePrevue().getTime()));
            ps.setString(5, c.getStatut());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
        return c;
    }

    public void update(Culture c) throws Exception {
        String sql = "UPDATE culture SET nom=?, variete=?, date_semis=?, date_recolte_prevue=?, statut=? WHERE id=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setString(2, c.getVariete());
            ps.setDate(3, c.getDateSemis() == null ? null : new java.sql.Date(c.getDateSemis().getTime()));
            ps.setDate(4, c.getDateRecoltePrevue() == null ? null : new java.sql.Date(c.getDateRecoltePrevue().getTime()));
            ps.setString(5, c.getStatut());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM culture WHERE id=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}