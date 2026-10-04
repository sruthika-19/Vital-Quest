package gui;
import javax.swing.*;
import java.awt.*;
import util.Constants;

public class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius, Color bgColor) {
        this.radius = radius;
        setOpaque(false);
        setBackground(bgColor);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        // Subtle top highlight border for depth
        g2.setColor(new Color(255, 255, 255, 15));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        
        g2.dispose();
        super.paintComponent(g);
    }
}