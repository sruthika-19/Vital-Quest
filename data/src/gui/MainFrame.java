package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import engine.GameEngine;
import model.*;
import storage.FileHandler;
import util.Constants;

public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    
    private GameEngine engine = new GameEngine();
    private FileHandler fileHandler = new FileHandler();
    private User currentUser; 

    public MainFrame() {
        setTitle("Vital Quest - Wellness Analysis");
        setSize(1150, 750);
        setMinimumSize(new Dimension(1050, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel.setBackground(Constants.BG_MAIN);
        
        // Static screens added once
        JPanel homePanel = createHomePanel();
        homePanel.setName("Home");
        mainPanel.add(homePanel, "Home");
        
        JPanel namePanel = createNamePanel();
        namePanel.setName("NameEntry");
        mainPanel.add(namePanel, "NameEntry");
        
        JPanel libPanel = createLibraryPanel();
        libPanel.setName("Library");
        mainPanel.add(libPanel, "Library");
        
        add(mainPanel);
    }

    // HELPER: Prevents CardLayout memory leaks/duplicates
    private void showDynamicCard(String cardName, JPanel panel) {
        for (Component comp : mainPanel.getComponents()) {
            if (cardName.equals(comp.getName())) {
                mainPanel.remove(comp);
                break;
            }
        }
        panel.setName(cardName);
        mainPanel.add(panel, cardName);
        cardLayout.show(mainPanel, cardName);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Constants.BG_MAIN);
        
        JLabel title = new JLabel("VITAL QUEST");
        title.setFont(Constants.TITLE_FONT); title.setForeground(Constants.ACCENT_PRIMARY);
        
        JLabel subtitle = new JLabel("MENTAL WELLNESS & SCENARIO ANALYSIS");
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 22)); subtitle.setForeground(Constants.TEXT_MAIN);

        JLabel tagline = new JLabel("Understand the factors. Analyze the situation. Reflect on wellbeing.");
        tagline.setFont(Constants.BODY_FONT); tagline.setForeground(Constants.TEXT_MUTED);

        JLabel desc = new JLabel("Explore fictional student situations, identify relevant wellbeing factors, and receive structured educational feedback.");
        desc.setFont(Constants.BODY_FONT); desc.setForeground(Constants.TEXT_MUTED);

        JLabel disclaimer = new JLabel("Educational awareness tool • Not a diagnostic system");
        disclaimer.setFont(new Font("Segoe UI", Font.ITALIC, 12)); disclaimer.setForeground(Constants.TEXT_MUTED);
        
        RoundedButton btnStart = new RoundedButton("START ANALYSIS", Constants.ACCENT_PRIMARY, Constants.BG_MAIN);
        btnStart.setPreferredSize(new Dimension(250, 45));
        btnStart.addActionListener(e -> cardLayout.show(mainPanel, "NameEntry"));
        
        RoundedButton btnLeaderboard = new RoundedButton("SCOREBOARD", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnLeaderboard.setPreferredSize(new Dimension(250, 45));
        btnLeaderboard.addActionListener(e -> showDynamicCard("Leaderboard", createLeaderboardPanel()));

        RoundedButton btnHowItWorks = new RoundedButton("HOW IT WORKS", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnHowItWorks.setPreferredSize(new Dimension(250, 45));
        btnHowItWorks.addActionListener(e -> showHowItWorksDialog());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; panel.add(title, gbc);
        gbc.gridy = 1; panel.add(subtitle, gbc);
        gbc.gridy = 2; gbc.insets = new Insets(10,0,5,0); panel.add(tagline, gbc);
        gbc.gridy = 3; gbc.insets = new Insets(0,0,40,0); panel.add(desc, gbc);
        gbc.gridy = 4; gbc.insets = new Insets(5,0,10,0); panel.add(btnStart, gbc);
        gbc.gridy = 5; panel.add(btnLeaderboard, gbc);
        gbc.gridy = 6; panel.add(btnHowItWorks, gbc);
        gbc.gridy = 7; gbc.insets = new Insets(40,0,0,0); panel.add(disclaimer, gbc);
        
        return panel;
    }

    private void showHowItWorksDialog() {
        String info = "1. Choose a scenario.\n" +
                      "2. Read the situation carefully.\n" +
                      "3. Identify the relevant factors.\n" +
                      "4. Select the assessment you consider most appropriate.\n" +
                      "5. Choose your confidence level.\n" +
                      "6. Explain your reasoning.\n" +
                      "7. Submit the analysis.\n" +
                      "8. Review your score and educational feedback.\n\n" +
                      "Important: VITAL QUEST uses fictional scenarios for educational awareness.\n" +
                      "It does not diagnose or treat mental-health conditions.";
        JOptionPane.showMessageDialog(this, info, "How It Works", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createNamePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Constants.BG_MAIN);
        
        RoundedPanel card = new RoundedPanel(15, Constants.BG_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(400, 200));

        JLabel lbl = new JLabel("ENTER ANALYST NAME");
        lbl.setForeground(Constants.TEXT_MAIN); lbl.setFont(Constants.HEADER_FONT);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField txtName = new JTextField();
        txtName.setMaximumSize(new Dimension(300, 35));
        txtName.setBackground(Constants.BG_PANEL); txtName.setForeground(Constants.TEXT_MAIN);
        txtName.setCaretColor(Constants.TEXT_MAIN);
        
        RoundedButton btnNext = new RoundedButton("CONTINUE", Constants.ACCENT_PRIMARY, Constants.BG_MAIN);
        btnNext.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNext.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            currentUser = new Student(name); 
            cardLayout.show(mainPanel, "Library");
        });

        card.add(Box.createVerticalStrut(20)); card.add(lbl);
        card.add(Box.createVerticalStrut(20)); card.add(txtName);
        card.add(Box.createVerticalStrut(25)); card.add(btnNext);
        panel.add(card); return panel;
    }

    private JPanel createLibraryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Constants.BG_MAIN);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        JLabel header = new JLabel("SCENARIO LIBRARY");
        header.setForeground(Constants.TEXT_MAIN); header.setFont(Constants.TITLE_FONT);
        
        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setBackground(Constants.BG_MAIN); grid.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        for (Scenario s : engine.getScenarioRepository().getAll()) {
            grid.add(createScenarioCard(s));
        }
        
        RoundedButton btnBack = new RoundedButton("HOME", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        
        panel.add(header, BorderLayout.NORTH); panel.add(grid, BorderLayout.CENTER); panel.add(btnBack, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createScenarioCard(Scenario s) {
        RoundedPanel card = new RoundedPanel(12, Constants.BG_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel catLabel = new JLabel(s.getCategory().toUpperCase() + " | " + s.getDifficulty().toUpperCase());
        catLabel.setForeground(Constants.ACCENT_SECONDARY); catLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));

        // FIXED: HTML break line instead of \n
        JLabel titleLabel = new JLabel("<html>SCENARIO " + s.getId() + "<br>" + s.getTitle().toUpperCase() + "</html>");
        titleLabel.setForeground(Constants.TEXT_MAIN); titleLabel.setFont(Constants.HEADER_FONT);

        JLabel factorLabel = new JLabel(s.getFactors().size() + " FACTORS");
        factorLabel.setForeground(Constants.TEXT_MUTED); factorLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));

        JTextArea desc = new JTextArea(s.getScenario());
        desc.setWrapStyleWord(true); desc.setLineWrap(true);
        desc.setEditable(false); desc.setOpaque(false);
        desc.setForeground(Constants.TEXT_MUTED); desc.setFont(Constants.BODY_FONT);

        RoundedButton btnOpen = new RoundedButton("OPEN SCENARIO →", Constants.BG_PANEL, Constants.ACCENT_PRIMARY);
        btnOpen.addActionListener(e -> showDynamicCard("Analysis", createAnalysisPanel(engine.getScenarioById(s.getId()))));

        card.add(catLabel); card.add(Box.createVerticalStrut(5));
        card.add(titleLabel); card.add(Box.createVerticalStrut(5));
        card.add(factorLabel); card.add(Box.createVerticalStrut(10));
        card.add(desc); card.add(Box.createVerticalStrut(10));
        card.add(btnOpen);
        return card;
    }

    private JPanel createAnalysisPanel(Scenario s) {
        JPanel analysisPanel = new JPanel(new BorderLayout(15, 15));
        analysisPanel.setBackground(Constants.BG_MAIN);
        analysisPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("ANALYSIS: " + s.getTitle().toUpperCase());
        header.setForeground(Constants.ACCENT_PRIMARY); header.setFont(Constants.HEADER_FONT);
        analysisPanel.add(header, BorderLayout.NORTH);

        JPanel columns = new JPanel(new GridLayout(1, 3, 15, 0));
        columns.setOpaque(false);

        // COLUMN 1: SCENARIO
        RoundedPanel leftPanel = new RoundedPanel(10, Constants.BG_CARD);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(createStyledLabel("SECTION 1: SCENARIO"), BorderLayout.NORTH);
        
        JTextArea scenarioText = new JTextArea(s.getScenario());
        scenarioText.setEditable(false); scenarioText.setLineWrap(true); scenarioText.setWrapStyleWord(true);
        scenarioText.setBackground(Constants.BG_CARD); scenarioText.setForeground(Constants.TEXT_MAIN);
        scenarioText.setFont(Constants.BODY_FONT);
        leftPanel.add(new JScrollPane(scenarioText) {{ setBorder(null); }}, BorderLayout.CENTER);

        // COLUMN 2: FACTORS
        RoundedPanel centerPanel = new RoundedPanel(10, Constants.BG_CARD);
        centerPanel.setLayout(new BorderLayout(0, 10));
        
        JPanel headerFactorPanel = new JPanel(new BorderLayout());
        headerFactorPanel.setOpaque(false);
        headerFactorPanel.add(createStyledLabel("SECTION 2: IDENTIFY FACTORS (Min 2)"), BorderLayout.NORTH);
        JLabel countLabel = new JLabel("FACTORS SELECTED: 0 / " + s.getFactors().size());
        countLabel.setForeground(Constants.ACCENT_SECONDARY);
        headerFactorPanel.add(countLabel, BorderLayout.SOUTH);
        centerPanel.add(headerFactorPanel, BorderLayout.NORTH);
        
        JPanel factorsList = new JPanel();
        factorsList.setLayout(new BoxLayout(factorsList, BoxLayout.Y_AXIS));
        factorsList.setBackground(Constants.BG_CARD);
        
        List<FactorCard> factorCards = new ArrayList<>();
        for (Factor f : s.getFactors()) {
            FactorCard fc = new FactorCard(f, countLabel, factorCards);
            factorCards.add(fc); factorsList.add(fc); factorsList.add(Box.createVerticalStrut(8));
        }
        centerPanel.add(new JScrollPane(factorsList) {{ setBorder(null); }}, BorderLayout.CENTER);

        // COLUMN 3: ASSESSMENT & REASONING
        RoundedPanel rightPanel = new RoundedPanel(10, Constants.BG_CARD);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(createStyledLabel("SECTION 3: ASSESSMENT"));
        
        JComboBox<Assessment> assessmentBox = new JComboBox<>(s.getAssessments().toArray(new Assessment[0]));
        styleComboBox(assessmentBox);
        
        JComboBox<String> confBox = new JComboBox<>(Constants.CONFIDENCE_LEVELS);
        styleComboBox(confBox);

        JLabel reasonLabel = new JLabel("Explain why you selected this assessment...");
        reasonLabel.setForeground(Constants.TEXT_MUTED);
        JTextArea reasoningText = new JTextArea("", 4, 20); // Starts cleanly empty
        reasoningText.setBackground(Constants.BG_PANEL); reasoningText.setForeground(Constants.TEXT_MAIN);
        reasoningText.setLineWrap(true);
        
        RoundedButton submitBtn = new RoundedButton("ANALYZE SCENARIO", Constants.ACCENT_PRIMARY, Constants.BG_MAIN);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        submitBtn.addActionListener(e -> {
            try {
                String reasoning = reasoningText.getText().trim();
                if (reasoning.split("\\s+").length < 5) throw new IllegalArgumentException("Reasoning must be at least 5 words.");
                
                List<Factor> selectedFactors = new ArrayList<>();
                for (FactorCard fc : factorCards) if (fc.isSelected()) selectedFactors.add(fc.getFactor());
                if (selectedFactors.size() < 2) throw new IllegalArgumentException("Select at least 2 factors."); 
                
                AnalysisResult result = engine.processAnalysis(s, selectedFactors, (Assessment)assessmentBox.getSelectedItem(), (String)confBox.getSelectedItem(), reasoning);
                fileHandler.saveScoreAsync(currentUser.getName(), s.getTitle(), result.totalScore);
                
                showDynamicCard("Result", createResultPanel(s, result));
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Incomplete Analysis", JOptionPane.WARNING_MESSAGE);
            }
        });

        rightPanel.add(Box.createVerticalStrut(15)); rightPanel.add(createStyledLabel("Select Interpretation:")); rightPanel.add(assessmentBox);
        rightPanel.add(Box.createVerticalStrut(15)); rightPanel.add(createStyledLabel("Confidence:")); rightPanel.add(confBox);
        rightPanel.add(Box.createVerticalStrut(15)); rightPanel.add(reasonLabel); rightPanel.add(new JScrollPane(reasoningText));
        rightPanel.add(Box.createVerticalStrut(20)); rightPanel.add(submitBtn);

        columns.add(leftPanel); columns.add(centerPanel); columns.add(rightPanel);
        analysisPanel.add(columns, BorderLayout.CENTER);

        RoundedButton btnBack = new RoundedButton("ABORT ANALYSIS", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Library"));
        analysisPanel.add(btnBack, BorderLayout.SOUTH);

        return analysisPanel;
    }

    private class FactorCard extends RoundedPanel {
        private boolean selected = false;
        private Factor factor;
        public FactorCard(Factor factor, JLabel countLabel, List<FactorCard> allCards) {
            super(8, Constants.BG_PANEL);
            this.factor = factor;
            setLayout(new BorderLayout());
            setMaximumSize(new Dimension(1000, 60));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            JTextArea text = new JTextArea(factor.getDescription());
            text.setWrapStyleWord(true); text.setLineWrap(true);
            text.setOpaque(false); text.setEditable(false); text.setFocusable(false);
            text.setForeground(Constants.TEXT_MAIN); text.setFont(Constants.BODY_FONT);
            add(text, BorderLayout.CENTER);

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selected = !selected;
                    setBackground(selected ? new Color(45, 212, 191, 50) : Constants.BG_PANEL);
                    setBorder(selected ? BorderFactory.createLineBorder(Constants.ACCENT_PRIMARY, 1) : BorderFactory.createEmptyBorder(15,15,15,15));
                    
                    int count = (int) allCards.stream().filter(FactorCard::isSelected).count();
                    countLabel.setText("FACTORS SELECTED: " + count + " / " + allCards.size());
                    repaint();
                }
            });
        }
        public boolean isSelected() { return selected; }
        public Factor getFactor() { return factor; }
    }

    private JPanel createResultPanel(Scenario s, AnalysisResult res) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Constants.BG_MAIN);
        panel.setBorder(new EmptyBorder(40, 60, 40, 60));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(5, 0, 5, 0);

        JLabel lblStatus = new JLabel("ANALYSIS COMPLETE", SwingConstants.CENTER);
        lblStatus.setFont(Constants.TITLE_FONT); lblStatus.setForeground(Constants.ACCENT_PRIMARY);
        
        JLabel lblScore = new JLabel("SCORE: " + res.totalScore + " / 100", SwingConstants.CENTER);
        lblScore.setFont(new Font("Segoe UI", Font.BOLD, 28)); lblScore.setForeground(Constants.TEXT_MAIN);

        RoundedPanel feedbackPanel = new RoundedPanel(10, Constants.BG_CARD);
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        
        feedbackPanel.add(createFeedbackRow("ASSESSMENT", res.assessmentScore + " / 50", Constants.TEXT_MAIN));
        feedbackPanel.add(createFeedbackRow("FACTORS", res.factorScore + " / 30", Constants.TEXT_MAIN));
        feedbackPanel.add(createFeedbackRow("REASONING", res.reasoningScore + " / 10", Constants.TEXT_MAIN));
        feedbackPanel.add(createFeedbackRow("CONFIDENCE", res.confidenceScore + " / 10", Constants.TEXT_MAIN));
        feedbackPanel.add(Box.createVerticalStrut(15));
        
        feedbackPanel.add(createFeedbackRow("YOUR ASSESSMENT:", res.userAssessmentDesc, Constants.ACCENT_SECONDARY));
        feedbackPanel.add(createFeedbackRow("EXPECTED ANALYSIS:", res.expectedAssessmentDesc + " (" + res.category + ")", Constants.SUCCESS));
        feedbackPanel.add(Box.createVerticalStrut(15));
        
        for (String idF : res.identifiedFactors) feedbackPanel.add(createFeedbackRow("✓ IDENTIFIED FACTOR:", idF, Constants.SUCCESS));
        for (String mF : res.missedFactors) feedbackPanel.add(createFeedbackRow("⚠ MISSED FACTOR:", mF, Constants.WARNING));
        for (String lrF : res.lessRelevantFactors) feedbackPanel.add(createFeedbackRow("⚠ LESS-RELEVANT:", lrF + " (Caused Penalty)", Constants.ERROR));
        feedbackPanel.add(Box.createVerticalStrut(15));
        
        feedbackPanel.add(createFeedbackRow("YOUR REASONING:", res.userReasoning, Constants.TEXT_MUTED));
        
        feedbackPanel.add(Box.createVerticalStrut(20));
        JLabel takeaway = new JLabel("<html><b>WELLNESS INSIGHT:</b><br>" + s.getWellnessInsight() + 
            "<br><br><i>DISCLAIMER: This scenario analysis is educational and does not provide psychological or medical diagnosis.</i></html>");
        takeaway.setForeground(Constants.ACCENT_SECONDARY); takeaway.setFont(Constants.BODY_FONT);
        feedbackPanel.add(takeaway);

        gbc.gridy = 0; centerPanel.add(lblStatus, gbc);
        gbc.gridy = 1; centerPanel.add(lblScore, gbc);
        gbc.gridy = 2; centerPanel.add(feedbackPanel, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);
        
        RoundedButton btnReplay = new RoundedButton("REPLAY", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnReplay.addActionListener(e -> showDynamicCard("Analysis", createAnalysisPanel(engine.getScenarioById(s.getId()))));
        
        RoundedButton btnNext = new RoundedButton("NEXT SCENARIO", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnNext.addActionListener(e -> {
            int currIndex = -1;
            for (int i=0; i<engine.getScenarioRepository().size(); i++) {
                if (engine.getScenarioRepository().get(i).getId().equals(s.getId())) currIndex = i;
            }
            if (currIndex != -1 && currIndex + 1 < engine.getScenarioRepository().size()) {
                showDynamicCard("Analysis", createAnalysisPanel(engine.getScenarioRepository().get(currIndex + 1)));
            } else {
                JOptionPane.showMessageDialog(this, "You have completed all available scenarios.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "Library");
            }
        });

        RoundedButton btnLib = new RoundedButton("SCENARIO LIBRARY", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnLib.addActionListener(e -> cardLayout.show(mainPanel, "Library"));
        
        RoundedButton btnHome = new RoundedButton("HOME", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnHome.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        btnPanel.add(btnReplay); btnPanel.add(btnNext); btnPanel.add(btnLib); btnPanel.add(btnHome);

        JScrollPane scrollResult = new JScrollPane(centerPanel);
        scrollResult.setBorder(null); scrollResult.getViewport().setBackground(Constants.BG_MAIN);
        
        panel.add(scrollResult, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFeedbackRow(String title, String value, Color valueColor) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        JLabel lTitle = new JLabel("<html><b>" + title + "</b></html>"); 
        lTitle.setForeground(Constants.TEXT_MUTED); lTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel lValue = new JLabel("<html>" + value + "</html>"); 
        lValue.setForeground(valueColor); lValue.setFont(Constants.BODY_FONT);
        row.add(lTitle, BorderLayout.WEST); row.add(lValue, BorderLayout.CENTER);
        return row;
    }

    private JPanel createLeaderboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Constants.BG_MAIN);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        String[] columns = {"RANK", "ANALYST", "SCENARIO", "SCORE"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        List<String[]> records = fileHandler.loadLeaderboard();
        int rank = 1;
        for (String[] rec : records) {
            model.addRow(new Object[]{rank++, rec[0], rec[1], rec[2]});
        }
        
        JTable table = new JTable(model);
        table.setBackground(Constants.BG_CARD); table.setForeground(Constants.TEXT_MAIN);
        table.setFont(Constants.BODY_FONT); table.setRowHeight(35);
        table.getTableHeader().setBackground(Constants.BG_PANEL);
        table.getTableHeader().setForeground(Constants.ACCENT_PRIMARY);
        
        DefaultTableCellRenderer center = new DefaultTableCellRenderer(); center.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<4; i++) table.getColumnModel().getColumn(i).setCellRenderer(center);
        
        RoundedButton btnBack = new RoundedButton("BACK", Constants.BG_PANEL, Constants.TEXT_MAIN);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        
        JLabel lblTitle = new JLabel("WELLNESS ANALYSIS SCOREBOARD", SwingConstants.CENTER);
        lblTitle.setForeground(Constants.ACCENT_PRIMARY); lblTitle.setFont(Constants.TITLE_FONT);
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnBack, BorderLayout.SOUTH);
        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Constants.TEXT_MUTED); l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return l;
    }
    private void styleComboBox(JComboBox<?> box) {
        box.setBackground(Constants.BG_PANEL); box.setForeground(Constants.TEXT_MAIN); box.setFont(Constants.BODY_FONT);
    }
}