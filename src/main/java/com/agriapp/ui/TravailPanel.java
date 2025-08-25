package com.agriapp.ui;

import com.agriapp.model.TravailAgricole;
import com.agriapp.service.TravailService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TravailPanel extends JPanel {
    private final TravailService service = new TravailService();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{
            "ID", "CultureID", "Type", "Date", "Superficie"
    }, 0);
    private final JTable table = new JTable(model);

    private final JTextField cultureId = new JTextField();
    private final JTextField type = new JTextField();
    private final JTextField date = new JTextField();
    private final JTextField superficie = new JTextField();

    public TravailPanel() {
        setLayout(new BorderLayout(8,8));

        // Formulaire
        JPanel form = new JPanel(new GridLayout(1,4,8,8));
        form.add(labeled("Culture ID", cultureId));
        form.add(labeled("Type", type));
        form.add(labeled("Date (yyyy-MM-dd)", date));
        form.add(labeled("Superficie (ha)", superficie));

        // Boutons
        JButton add = new JButton("Ajouter");
        JButton del = new JButton("Supprimer");
        JButton load = new JButton("Charger"); // <--- nouveau bouton

        JPanel actions = new JPanel();
        actions.add(add);
        actions.add(del);
        actions.add(load); // ajouter le bouton charger

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // Actions
        add.addActionListener(e -> addRow());
        del.addActionListener(e -> deleteRow());
        load.addActionListener(e -> {   // quand on clique sur Charger
            if (!cultureId.getText().isBlank()) {
                refresh(Integer.parseInt(cultureId.getText()));
            } else {
                JOptionPane.showMessageDialog(this, "Entrez un Culture ID !");
            }
        });
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private void refresh(int culture) {
        try {
            List<TravailAgricole> data = service.listByCulture(culture);
            model.setRowCount(0);
            for (TravailAgricole t : data) {
                model.addRow(new Object[]{
                        t.getId(),
                        t.getCultureId(),
                        t.getTypeTravail(),
                        UiDates.fmt(t.getDateTravail()),
                        t.getSuperficie()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRow() {
        try {
            int cId = Integer.parseInt(cultureId.getText());
            TravailAgricole t = new TravailAgricole();
            t.setCultureId(cId);
            t.setTypeTravail(type.getText());
            t.setDateTravail(UiDates.parse(date.getText()));
            t.setSuperficie(Double.parseDouble(superficie.getText()));
            service.add(t);
            refresh(cId);
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
                if (!cultureId.getText().isBlank()) refresh(Integer.parseInt(cultureId.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
