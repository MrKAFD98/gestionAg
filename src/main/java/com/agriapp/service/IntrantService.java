package com.agriapp.service;

import com.agriapp.dao.IntrantDao;
import com.agriapp.model.Intrant;
import java.util.List;

public class IntrantService {
    private final IntrantDao dao = new IntrantDao();
    public List<Intrant> list() throws Exception { return dao.findAll(); }
    public Intrant add(Intrant i) throws Exception {
        if (i.getNom() == null || i.getNom().isBlank()) throw new IllegalArgumentException("Nom requis");
        return dao.insert(i);
    }
    public void update(Intrant i) throws Exception { dao.update(i); }
    public void delete(int id) throws Exception { dao.delete(id); }
}