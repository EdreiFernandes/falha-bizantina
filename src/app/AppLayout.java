package app;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class AppLayout extends JFrame implements ActionListener {
    private static final long serialVersionUID = 4937922016334810986L;

    private final int height = 700;
    private final int width = 600;
    private final int border = 10;

    private JTable usersTable;
    private JLabel timer;
    private JLabel wcStatus;
    private JPanel timerPanel;
    private JTable waitList;
    private JTextPane console;
    private JButton button;

    private Font font;

    public AppLayout() {
        font = new Font("Arial", Font.CENTER_BASELINE, 15);

        JPanel panelNorth = new JPanel(new BorderLayout());
        JPanel panelInfo = borderedPanel(panelNorth, height / 5, width);
        add(panelNorth, BorderLayout.NORTH);

        instantiateUsersTable(panelInfo);
        instantiateTimer(panelInfo);
        // instantiateWaitList(panelInfo);

        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelConsole = borderedPanel(panelCenter, height / 5, width);
        add(panelCenter, BorderLayout.CENTER);

        instantiateConsole(panelConsole);

        JPanel panelSouth = new JPanel(new BorderLayout());
        JPanel panelButton = borderedPanel(panelSouth, height / 5, width);
        add(panelSouth, BorderLayout.SOUTH);

        button = new JButton("Enter/Leave the bathroom");
        button.addActionListener(this);
        panelButton.add(button);

        instantiateFrame();
    }

    private void instantiateUsersTable(JPanel _panel) {
        String[] columnsNames = { "User", "Status", "Address" };

        DefaultTableModel tableData = new DefaultTableModel(null, columnsNames) {
            private static final long serialVersionUID = 4573641225227385105L;

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        usersTable = new JTable(tableData);
        usersTable.setPreferredScrollableViewportSize(new Dimension(500, 50));
        usersTable.setFillsViewportHeight(true);

        _panel.add(new JScrollPane(usersTable), BorderLayout.WEST);
    }

    private void instantiateTimer(JPanel _panel) {
        timerPanel = new JPanel(new BorderLayout());
        timerPanel.setBackground(Color.GREEN);
        timerPanel.setPreferredSize(new Dimension(100, 100));

        timer = new JLabel("0");
        timer.setFont(new Font("Arial", Font.BOLD, 40));
        timer.setHorizontalAlignment(SwingConstants.CENTER);
        timerPanel.add(timer, BorderLayout.CENTER);

        wcStatus = new JLabel("Free");
        wcStatus.setFont(font);
        wcStatus.setHorizontalAlignment(SwingConstants.CENTER);
        timerPanel.add(wcStatus, BorderLayout.NORTH);

        JLabel timerType = new JLabel("seconds");
        timerType.setFont(font);
        timerType.setHorizontalAlignment(SwingConstants.CENTER);
        timerPanel.add(timerType, BorderLayout.SOUTH);

        _panel.add(new JScrollPane(timerPanel), BorderLayout.EAST);
    }

    private void instantiateWaitList(JPanel _panel) {
        String[] columnsNames = { "Wait list" };

        Object[][] data = { { "item 1" }, { "item 2" }, { "item 3" }, { "item 4" } };

        waitList = new JTable(data, columnsNames);
        waitList.setPreferredScrollableViewportSize(new Dimension(100, 50));
        waitList.setFillsViewportHeight(true);

        _panel.add(new JScrollPane(waitList), BorderLayout.EAST);
    }

    private void instantiateConsole(JPanel _panel) {
        JLabel label = new JLabel("Console");
        console = new JTextPane();
        console.setEditable(false);

        _panel.add(label, BorderLayout.NORTH);
        _panel.add(new JScrollPane(console));
    }

    private JPanel borderedPanel(JPanel _panel, int _height, int _width) {
        _panel.setPreferredSize(new Dimension(_width, _height));

        JPanel borderNorth = new JPanel();
        borderNorth.setPreferredSize(new Dimension(_width, border));
        _panel.add(borderNorth, BorderLayout.NORTH);

        JPanel borderWest = new JPanel();
        borderWest.setPreferredSize(new Dimension(border, _height));
        _panel.add(borderWest, BorderLayout.WEST);

        JPanel borderSouth = new JPanel();
        borderSouth.setPreferredSize(new Dimension(_width, border));
        _panel.add(borderSouth, BorderLayout.SOUTH);

        JPanel borderEast = new JPanel();
        borderEast.setPreferredSize(new Dimension(border, _height));
        _panel.add(borderEast, BorderLayout.EAST);

        JPanel panelCenter = new JPanel(new BorderLayout());
        _panel.add(panelCenter);

        return panelCenter;
    }

    private void instantiateFrame() {
        ImageIcon icon = new ImageIcon("./image/icon.png");
        setIconImage(icon.getImage());
        setTitle("Fila App");
        setSize(height, width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTable getUsersTable() {
        return usersTable;
    }

    public JTextPane getConsole() {
        return console;
    }

    public JLabel getTimer() {
        return timer;
    }

    public void toggleTimer(boolean isBusy) {
        if (isBusy) {
            wcStatus.setText("Busy");
            timerPanel.setBackground(Color.RED);
        } else {
            timer.setText("0");
            wcStatus.setText("Free");
            timerPanel.setBackground(Color.GREEN);
        }
    }

    public String askForUsername() {
        String username = null;
        while (username == null || username.isEmpty()) {
            username = JOptionPane.showInputDialog("Enter your username: ");
        }
        return username;
    }

    @Override
    public void actionPerformed(ActionEvent _event) {
        App.getClient().SendMessage("ENTRY");
    }
}
