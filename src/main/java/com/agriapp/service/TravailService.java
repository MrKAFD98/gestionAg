package com.agriapp.service;

import com.agriapp.dao.TravailDao;
import com.agriapp.model.TravailAgricole;
import java.util.List;

public class TravailService {
    private final TravailDao dao = new TravailDao();

    public List<TravailAgricole> listByCulture(int cultureId) throws Exception {
        return dao.findByCulture(cultureId);
    }

    public TravailAgricole add(TravailAgricole t) throws Exception {
        if (t.getCultureId() <= 0) throw new IllegalArgumentException("Culture obligatoire");
        return dao.insert(t);
    }

    public void delete(int id) throws Exception { dao.delete(id); }
}