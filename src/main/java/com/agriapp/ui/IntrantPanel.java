package com.agriapp.ui;

import com.agriapp.model.Intrant;
import com.agriapp.service.IntrantService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IntrantPanel extends JPanel {
    private final IntrantService service = new IntrantService();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{
            "ID", "Nom", "Type", "Cout", "Quantite"
    }, 0);
    private final JTable table = new JTable(model);

    private final JTextField nom = new JTextField();
    private final JTextField type = new JTextField();
    private final JTextField cout = new JTextField();
    private final JTextField quantite = new JTextField();

    public IntrantPanel() {
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(1,4,8,8));
        form.add(labeled("Nom", nom));
        form.add(labeled("Type", type));
        form.add(labeled("Coût", cout));
        form.add(labeled("Quantité", quantite));

        JButton add = new JButton("Ajouter");
        JButton update = new JButton("Modifier");
        JButton del = new JButton("Supprimer");
        JPanel actions = new JPanel(); actions.add(add); actions.add(update); actions.add(del);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        add.addActionListener(e -> addRow());
        update.addActionListener(e -> updateRow());
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
            List<Intrant> data = service.list();
            model.setRowCount(0);
            for (Intrant i : data) model.addRow(new Object[]{ i.getId(), i.getNom(), i.getType(), i.getCout(), i.getQuantite() });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRow() {
        try {
            Intrant i = new Intrant();
            i.setNom(nom.getText());
            i.setType(type.getText());
            i.setCout(Double.parseDouble(cout.getText()));
            i.setQuantite(Double.parseDouble(quantite.getText()));
            service.add(i);
            refresh();
            nom.setText(""); type.setText(""); cout.setText(""); quantite.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRow() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Sélectionnez une ligne"); return; }
        try {
            Intrant i = new Intrant(
                    (Integer) model.getValueAt(row, 0),
                    (String) model.getValueAt(row, 1),
                    (String) model.getValueAt(row, 2),
                    Double.parseDouble(model.getValueAt(row, 3).toString()),
                    Double.parseDouble(model.getValueAt(row, 4).toString())
            );
            service.update(i);
            refresh();
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