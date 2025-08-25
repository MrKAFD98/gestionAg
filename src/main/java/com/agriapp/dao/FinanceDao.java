package com.agriapp.dao;

import com.agriapp.model.Finances;
import com.agriapp.util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceDao {
    public List<Finances> findAll() throws Exception {
        List<Finances> list = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM finances ORDER BY date_operation DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Finances f = new Finances();
                f.setId(rs.getInt("id"));
                f.setMontant(rs.getDouble("montant"));
                f.setDateOperation(rs.getDate("date_operation"));
                f.setCategorie(rs.getString("categorie"));
                list.add(f);
            }
        }
        return list;
    }

    public Finances insert(Finances f) throws Exception {
        String sql = "INSERT INTO finances(montant, date_operation, categorie) VALUES(?,?,?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, f.getMontant());
            ps.setDate(2, f.getDateOperation() == null ? null : new java.sql.Date(f.getDateOperation().getTime()));
            ps.setString(3, f.getCategorie());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) f.setId(rs.getInt(1)); }
        }
        return f;
    }

    public void delete(int id) throws Exception {
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM finances WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}