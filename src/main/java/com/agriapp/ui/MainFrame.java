package com.agriapp.ui;

import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * Fenêtre principale de l'application Gestion Agricole.
 * Ajout de l'onglet "Tableau de bord" en premier.
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Gestion Agricole");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // ✅ Onglet Tableau de bord ajouté
        tabs.add("Tableau de bord", new DashboardPanel());

        // Ces panneaux doivent déjà exister dans votre projet.
        // Laissez-les tels quels si vos classes ont d'autres noms.
        tabs.add("Cultures", new CulturePanel());
        tabs.add("Travaux", new TravailPanel());
        tabs.add("Intrants", new IntrantPanel());
        tabs.add("Finances", new FinancePanel());
        tabs.add("Recoltes", new RecoltePanel());

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Look & Feel moderne (FlatLaf est déjà dans pom.xml)
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}