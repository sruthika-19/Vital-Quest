package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import util.Constants;

public class RoundedButton extends JButton {
    private Color defaultBg;
    private Color hoverBg;
    private int radius = 8;

    public RoundedButton(String text, Color bg, Color fg) {
        super(text);
        this.defaultBg = bg;
        // Create a slightly lighter color for hover state
        this.hoverBg = new Color(Math.min(bg.getRed() + 20, 255), 
                                 Math.min(bg.getGreen() + 20, 255), 
                                 Math.min(bg.getBlue() + 20, 255));
        
        setForeground(fg);
        setFont(Constants.HEADER_FONT);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { setBackground(hoverBg); repaint(); }
            public void mouseExited(MouseEvent e) { setBackground(defaultBg); repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground() == null ? defaultBg : getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}