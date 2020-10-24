package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AppLayout extends JFrame {

    private final int height = 700;
    private final int width = 600;
    private final int border = 10;

    private JTable usersTable;
    private JList<String> waitList;

    public AppLayout() {
        JPanel panelNorth = new JPanel(new BorderLayout());
        JPanel panelInfo = borderedPanel(panelNorth, height / 5, width);
        add(panelNorth, BorderLayout.NORTH);

        instantiateUsersTable(panelInfo);
        instantiateWaitList(panelInfo);

        JPanel panelCenter = new JPanel(new BorderLayout());
        borderedPanel(panelCenter, height / 5, width);
        add(panelCenter, BorderLayout.CENTER);

        JPanel panelSouth = new JPanel(new BorderLayout());
        borderedPanel(panelSouth, height / 5, width);
        add(panelSouth, BorderLayout.SOUTH);

        instantiateFrame();
    }

    private void instantiateUsersTable(JPanel _panel) {
        String[] columnsNames = { "User", "Status", "Address" };

        Object[][] data = { { "User1", "Conectado", "4240" }, { "User2", "Conectado", "4241" },
                { "User3", "Conectado", "4242" }, { "User4", "Conectado", "4243" } };

        usersTable = new JTable(data, columnsNames);
        usersTable.setPreferredScrollableViewportSize(new Dimension(500, 50));
        usersTable.setFillsViewportHeight(true);

        _panel.add(new JScrollPane(usersTable), BorderLayout.WEST);
    }

    private void instantiateWaitList(JPanel _panel) {
        String[] listItens = { "item 1", "item 2", "item 3", "item 4" };
        waitList = new JList<String>(listItens);
        waitList.setVisibleRowCount(5);

        waitList.setPreferredSize(new Dimension(100, 50));
        _panel.add(new JScrollPane(waitList), BorderLayout.EAST);
    }

    private JPanel borderedPanel(JPanel _panel, int _height, int _width) {
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

        JPanel panelCenter = new JPanel(new BorderLayout());
        _panel.add(panelCenter);

        return panelCenter;
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
