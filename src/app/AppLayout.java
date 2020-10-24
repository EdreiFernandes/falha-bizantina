package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppLayout extends JFrame {

    private final int height = 700;
    private final int width = 600;
    private final int border = 10;

    public AppLayout() {
        JPanel panelNorth = new JPanel(new BorderLayout());
        borderedPanel(panelNorth, height / 5, width);
        add(panelNorth, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new BorderLayout());
        borderedPanel(panelCenter, height / 5, width);
        add(panelCenter, BorderLayout.CENTER);

        JPanel panelSouth = new JPanel(new BorderLayout());
        borderedPanel(panelSouth, height / 5, width);
        add(panelSouth, BorderLayout.SOUTH);

        instantiateFrame();
    }

    private void borderedPanel(JPanel _panel, int _height, int _width) {
        _panel.setBackground(Color.GRAY);
        _panel.setPreferredSize(new Dimension(_width, _height));

        JPanel borderNorth = new JPanel();
        borderNorth.setBackground(Color.BLACK);
        borderNorth.setPreferredSize(new Dimension(_width, border));
        _panel.add(borderNorth, BorderLayout.NORTH);

        JPanel borderWest = new JPanel();
        borderWest.setBackground(Color.BLACK);
        borderWest.setPreferredSize(new Dimension(border, _height));
        _panel.add(borderWest, BorderLayout.WEST);

        JPanel borderSouth = new JPanel();
        borderSouth.setBackground(Color.BLACK);
        borderSouth.setPreferredSize(new Dimension(_width, border));
        _panel.add(borderSouth, BorderLayout.SOUTH);

        JPanel borderEast = new JPanel();
        borderEast.setBackground(Color.BLACK);
        borderEast.setPreferredSize(new Dimension(border, _height));
        _panel.add(borderEast, BorderLayout.EAST);
    }

    private void instantiateFrame() {
        setTitle("Layout");
        setSize(height, width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AppLayout();
    }

}
