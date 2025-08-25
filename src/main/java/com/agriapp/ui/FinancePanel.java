package com.agriapp.ui;

import com.agriapp.model.Finances;
import com.agriapp.service.FinanceService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FinancePanel extends JPanel {
    private final FinanceService service = new FinanceService();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{
            "ID", "Montant", "Date", "Categorie"
    }, 0);
    private final JTable table = new JTable(model);

    private final JTextField montant = new JTextField();
    private final JTextField date = new JTextField();
    private final JTextField categorie = new JTextField();

    public FinancePanel() {
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(1,3,8,8));
        form.add(labeled("Montant", montant));
        form.add(labeled("Date (yyyy-MM-dd)", date));
        form.add(labeled("Catégorie", categorie));

        JButton add = new JButton("Ajouter");
        JButton del = new JButton("Supprimer");
        JPanel actions = new JPanel(); actions.add(add); actions.add(del);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        add.addActionListener(e -> addRow());
        del.addActionListener(e -> deleteRow());

        refresh();
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private void refresh() {
        try {
            List<Finances> data = service.list();
            model.setRowCount(0);
            for (Finances f : data) model.addRow(new Object[]{ f.getId(), f.getMontant(), UiDates.fmt(f.getDateOperation()), f.getCategorie() });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRow() {
        try {
            Finances f = new Finances();
            f.setMontant(Double.parseDouble(montant.getText()));
            f.setDateOperation(UiDates.parse(date.getText()));
            f.setCategorie(categorie.getText());
            service.add(f);
            refresh();
            montant.setText(""); date.setText(""); categorie.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRow() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Sélectionnez une ligne"); return; }
        int id = (Integer) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Supprimer " + id + " ?", "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                service.delete(id);
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}