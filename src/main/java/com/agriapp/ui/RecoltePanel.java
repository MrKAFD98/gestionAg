package com.agriapp.ui;

import com.agriapp.model.Recolte;
import com.agriapp.service.RecolteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RecoltePanel extends JPanel {
    private final RecolteService service = new RecolteService();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{
            "ID", "CultureID", "Date", "Quantité", "Prix vente"
    }, 0);
    private final JTable table = new JTable(model);

    private final JTextField cultureId = new JTextField();
    private final JTextField date = new JTextField();
    private final JTextField quantite = new JTextField();
    private final JTextField prix = new JTextField();

    public RecoltePanel() {
        setLayout(new BorderLayout(8,8));

        // 🔹 Formulaire en haut
        JPanel form = new JPanel(new GridLayout(1,4,8,8));
        form.add(labeled("Culture ID", cultureId));
        form.add(labeled("Date (yyyy-MM-dd)", date));
        form.add(labeled("Quantité", quantite));
        form.add(labeled("Prix vente", prix));

        // 🔹 Boutons
        JButton add = new JButton("Ajouter");
        JButton del = new JButton("Supprimer");
        JButton load = new JButton("Charger");
        JPanel actions = new JPanel();
        actions.add(add);
        actions.add(del);
        actions.add(load);

        // 🔹 Organisation
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // 🔹 Listeners
        add.addActionListener(e -> addRow());
        del.addActionListener(e -> deleteRow());
        load.addActionListener(e -> loadData());
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    // 🔹 Charger les récoltes pour un cultureId donné
    private void refresh(int culture) {
        try {
            List<Recolte> data = service.listByCulture(culture);
            model.setRowCount(0); // vider
            for (Recolte r : data) {
                model.addRow(new Object[]{
                        r.getId(),
                        r.getCultureId(),
                        UiDates.fmt(r.getDateRecolte()),
                        r.getQuantite(),
                        r.getPrixVente()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try {
            int cId = Integer.parseInt(cultureId.getText());
            refresh(cId);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Entrez un ID de culture valide", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRow() {
        try {
            int cId = Integer.parseInt(cultureId.getText());
            Recolte r = new Recolte();
            r.setCultureId(cId);
            r.setDateRecolte(UiDates.parse(date.getText()));
            r.setQuantite(Double.parseDouble(quantite.getText()));
            r.setPrixVente(Double.parseDouble(prix.getText()));
            service.add(r);
            refresh(cId); // recharger après ajout
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRow() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une ligne");
            return;
        }
        int id = (Integer) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Supprimer " + id + " ?", "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                service.delete(id);
                if (!cultureId.getText().isBlank()) {
                    refresh(Integer.parseInt(cultureId.getText()));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
