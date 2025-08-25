package com.agriapp.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import com.agriapp.model.Culture;
import com.agriapp.model.TravailAgricole;
import com.agriapp.model.Finances;
import com.agriapp.dao.CultureDao;
import com.agriapp.dao.TravailDao;
import com.agriapp.dao.FinanceDao;

/**
 * Onglet "Tableau de bord" : résumé des cultures, travaux et finances.
 * - Bouton "Rafraîchir" pour recharger les données depuis MySQL.
 * - Utilise les DAO existants (CultureDAO, TravailAgricoleDAO, FinancesDAO).
 *
 * NOTE : Adaptez les noms des packages/classes si votre projet diffère.
 */
public class DashboardPanel extends JPanel {

    private final JTextArea resume;
    private final JButton btnRafraichir;

    public DashboardPanel() {
        setLayout(new BorderLayout());

        // En-tête avec titre + bouton
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Tableau de bord", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        header.add(title, BorderLayout.WEST);

        btnRafraichir = new JButton("Rafraîchir");
        btnRafraichir.addActionListener(e -> chargerDonnees());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        right.add(btnRafraichir);
        header.add(right, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Zone de résumé
        resume = new JTextArea();
        resume.setEditable(false);
        resume.setLineWrap(false);
        resume.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(resume);
        add(scroll, BorderLayout.CENTER);

        // Chargement initial
        chargerDonnees();
    }

    private void chargerDonnees() {
        btnRafraichir.setEnabled(false);
        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                StringBuilder sb = new StringBuilder();
                sb.append("===== Tableau de bord de l'exploitation =====\n\n");

                try {
                    // 🌱 Cultures en cours
                    List<Culture> cultures = new CultureDao().findAll();
                    sb.append("🌱 Cultures en cours :\n");
                    if (cultures == null || cultures.isEmpty()) {
                        sb.append("  Aucune culture enregistrée.\n");
                    } else {
                        for (Culture c : cultures) {
                            sb.append(String.format("- %s | Variété: %s | Statut: %s | Récolte prévue: %s%n",
                                    safe(c.getNom()),
                                    safe(c.getVariete()),
                                    safe(c.getStatut()),
                                    String.valueOf(c.getDateRecoltePrevue())));
                        }
                    }

                    // 🛠️ Travaux à réaliser
                    List<TravailAgricole> travaux = new TravailDao().findAll();
                    sb.append("\n🛠️ Travaux à réaliser :\n");
                    if (travaux == null || travaux.isEmpty()) {
                        sb.append("  Aucun travail prévu.\n");
                    } else {
                        for (TravailAgricole t : travaux) {
                            sb.append(String.format("- %s | Date: %s | Superficie: %s ha%n",
                                    safe(t.getTypeTravail()),
                                    String.valueOf(t.getDateTravail()),
                                    String.valueOf(t.getSuperficie())));
                        }
                    }

                    // 💰 Finances (revenus - dépenses)
                    List<Finances> operations = new FinanceDao().findAll();
                    double revenus = 0.0;
                    double depenses = 0.0;
                    if (operations != null) {
                        for (Finances f : operations) {
                            String cat = f.getCategorie() == null ? "" : f.getCategorie().toLowerCase();
                            // Selon votre usage : "revenu", "vente" comptés comme revenus
                            if (cat.contains("revenu") || cat.contains("vente")) {
                                revenus += f.getMontant();
                            }
                            // "depense", "achat", "intrant" comptés comme dépenses
                            else if (cat.contains("depense") || cat.contains("achat") || cat.contains("intrant")) {
                                depenses += f.getMontant();
                            }
                            // sinon ignoré (ou adaptez selon vos conventions)
                        }
                    }
                    double solde = revenus - depenses;

                    sb.append("\n💰 Finances :\n");
                    sb.append(String.format("Revenus: %.2f FCFA%n", revenus));
                    sb.append(String.format("Dépenses: %.2f FCFA%n", depenses));
                    sb.append(String.format("Solde actuel: %.2f FCFA%n", solde));

                } catch (Exception ex) {
                    sb.append("\n⚠️ Erreur lors du chargement des données : ").append(ex.getMessage()).append("\n");
                }

                return sb.toString();
            }

            @Override
            protected void done() {
                try {
                    resume.setText(get());
                    resume.setCaretPosition(0);
                } catch (Exception ignored) {
                } finally {
                    btnRafraichir.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private static String safe(String v) {
        return v == null ? "" : v;
    }
}