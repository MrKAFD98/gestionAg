package com.agriapp.ui;

import com.agriapp.model.Culture;
import com.agriapp.service.CultureService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CulturePanel extends JPanel {
    private final CultureService service = new CultureService();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{
            "ID", "Nom", "Variété", "Date semis", "Date récolte prévue", "Statut"
    }, 0);
    private final JTable table = new JTable(model);

    private final JTextField nom = new JTextField();
    private final JTextField variete = new JTextField();
    private final JTextField dateSemis = new JTextField();
    private final JTextField dateRecolte = new JTextField();
    private final JTextField statut = new JTextField();

    public CulturePanel() {
        setLayout(new BorderLayout(8,8));

        JPanel form = new JPanel(new GridLayout(2,5,8,8));
        form.add(labeled("Nom", nom));
        form.add(labeled("Variété", variete));
        form.add(labeled("Date semis (yyyy-MM-dd)", dateSemis));
        form.add(labeled("Date récolte (yyyy-MM-dd)", dateRecolte));
        form.add(labeled("Statut", statut));

        JButton btnAdd = new JButton("Ajouter");
        JButton btnUpdate = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");
        JPanel actions = new JPanel();
        actions.add(btnAdd); actions.add(btnUpdate); actions.add(btnDelete);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addCulture());
        btnUpdate.addActionListener(e -> updateCulture());
        btnDelete.addActionListener(e -> deleteSelected());

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
            List<Culture> data = service.list();
            model.setRowCount(0);
            for (Culture c : data) {
                model.addRow(new Object[]{
                        c.getId(), c.getNom(), c.getVariete(), UiDates.fmt(c.getDateSemis()),
                        UiDates.fmt(c.getDateRecoltePrevue()), c.getStatut()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCulture() {
        try {
            Culture c = new Culture();
            c.setNom(nom.getText());
            c.setVariete(variete.getText());
            c.setDateSemis(UiDates.parse(dateSemis.getText()));
            c.setDateRecoltePrevue(UiDates.parse(dateRecolte.getText()));
            c.setStatut(statut.getText());
            service.add(c);
            refresh();
            nom.setText(""); variete.setText(""); dateSemis.setText(""); dateRecolte.setText(""); statut.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCulture() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Sélectionnez une ligne"); return; }
        try {
            Culture c = new Culture();
            c.setId((Integer) model.getValueAt(row, 0));
            c.setNom((String) model.getValueAt(row, 1));
            c.setVariete((String) model.getValueAt(row, 2));
            c.setDateSemis(UiDates.parse((String) model.getValueAt(row, 3)));
            c.setDateRecoltePrevue(UiDates.parse((String) model.getValueAt(row, 4)));
            c.setStatut((String) model.getValueAt(row, 5));
            service.update(c);
            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Sélectionnez une ligne"); return; }
        int id = (Integer) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Supprimer la culture " + id + " ?",
                "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                service.delete(id);
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}