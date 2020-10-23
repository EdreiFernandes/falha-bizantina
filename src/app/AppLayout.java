package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AppLayout extends JFrame {
    private JPanel panel;
    private JTextArea console;
    private JButton button;
    private JList list;
    private JTable table;

    public AppLayout() {
        // panel
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // list
        String[] listItens = { "item 1", "item 2", "item 3", "item 4" };

        list = new JList<String>(listItens);
        list.setVisibleRowCount(5);
        panel.add(new JScrollPane(list), BorderLayout.EAST);

        // table
        String[] columnsNames = { "User", "Status", "Address" };
        Object[][] data = { { "Bill", "Conectado", "4240" }, { "Mary", "Conectado", "4241" },
                { "Rick", "Conectado", "4242" }, { "Janice", "Conectado", "4243" } };

        table = new JTable(data, columnsNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 50));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.WEST);

        add(panel, BorderLayout.NORTH);
        // console
        console = new JTextArea();
        add(console, BorderLayout.CENTER);

        // button
        button = new JButton("Botão");
        add(button, BorderLayout.SOUTH);

        instantiateFrame();
    }

    private void instantiateFrame() {
        setTitle("Layout");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AppLayout();
    }

}
