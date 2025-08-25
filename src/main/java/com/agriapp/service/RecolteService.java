package com.agriapp.service;

import com.agriapp.dao.RecolteDao;
import com.agriapp.model.Recolte;
import java.util.List;

public class RecolteService {
    private final RecolteDao dao = new RecolteDao();
    public List<Recolte> listByCulture(int cultureId) throws Exception { return dao.findByCulture(cultureId); }
    public Recolte add(Recolte r) throws Exception { return dao.insert(r); }
    public void delete(int id) throws Exception { dao.delete(id); }
}