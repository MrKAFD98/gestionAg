package com.agriapp.service;

import com.agriapp.dao.FinanceDao;
import com.agriapp.model.Finances;
import java.util.List;

public class FinanceService {
    private final FinanceDao dao = new FinanceDao();
    public List<Finances> list() throws Exception { return dao.findAll(); }
    public Finances add(Finances f) throws Exception { return dao.insert(f); }
    public void delete(int id) throws Exception { dao.delete(id); }
}