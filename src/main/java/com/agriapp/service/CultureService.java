package com.agriapp.service;

import com.agriapp.dao.CultureDao;
import com.agriapp.model.Culture;
import java.util.List;

public class CultureService {
    private final CultureDao dao = new CultureDao();

    public List<Culture> list() throws Exception { return dao.findAll(); }

    public Culture add(Culture c) throws Exception {
        if (c.getNom() == null || c.getNom().isBlank()) throw new IllegalArgumentException("Nom de la culture requis");
        return dao.insert(c);
    }

    public void update(Culture c) throws Exception {
        if (c.getId() <= 0) throw new IllegalArgumentException("ID invalide");
        dao.update(c);
    }

    public void delete(int id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("ID invalide");
        dao.delete(id);
    }
}