package app;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class AppLayout extends JFrame implements ActionListener {

    private final int height = 700;
    private final int width = 600;
    private final int border = 10;

    private JTable usersTable;
    private JTable waitList;
    private JTextPane console;
    private JButton button;

    public AppLayout() {
        JPanel panelNorth = new JPanel(new BorderLayout());
        JPanel panelInfo = borderedPanel(panelNorth, height / 5, width);
        add(panelNorth, BorderLayout.NORTH);

        instantiateUsersTable(panelInfo);
        instantiateWaitList(panelInfo);

        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelConsole = borderedPanel(panelCenter, height / 5, width);
        add(panelCenter, BorderLayout.CENTER);

        JLabel label = new JLabel("Console");
        console = new JTextPane();
        panelConsole.add(label, BorderLayout.NORTH);
        panelConsole.add(console);

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

        // TODO popular com dados reais (User table)
        DefaultTableModel tableData = new DefaultTableModel(null, columnsNames) {
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

    private void instantiateWaitList(JPanel _panel) {
        String[] columnsNames = { "Wait list" };

        // TODO popular com dados reais (Wait list)
        Object[][] data = { { "item 1" }, { "item 2" }, { "item 3" }, { "item 4" } };

        waitList = new JTable(data, columnsNames);
        waitList.setPreferredScrollableViewportSize(new Dimension(100, 50));
        waitList.setFillsViewportHeight(true);

        _panel.add(new JScrollPane(waitList), BorderLayout.EAST);
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
        setTitle("Fila App");
        setSize(height, width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addToUsersTable(Object[] _data) {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.addRow(_data);
    }

    @Override
    public void actionPerformed(ActionEvent _event) {
        JOptionPane.showMessageDialog(null, "Hello World");
        // TODO ler comandos do butão
    }
}
