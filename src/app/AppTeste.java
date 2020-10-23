package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class AppTeste extends JFrame {

    JPanel panel;
    JList<String> list;
    JTable table;

    public AppTeste() {
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        JButton btn = new JButton("teste");
        panel.add(btn, BorderLayout.WEST);
        add(panel, BorderLayout.SOUTH);
        // instantiateTable();
        // instantiateList();
        instantiateFrame();
    }

    private void instantiateList() {
        String[] colornames = { "black", "blue", "red", "white" };

        list = new JList<String>(colornames);
        list.setVisibleRowCount(5);
        add(new JScrollPane(list));
    }

    private void instantiateTable() {
        String[] columnsNames = { "Name", "Eye=Color", "Gender" };
        Object[][] data = { { "Bill", "Hazel", "Male" }, { "Mary", "Black", "Female" }, { "Rick", "Red", "Male" },
                { "Janice", "Yellow", "Female" } };

        table = new JTable(data, columnsNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 50));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.NORTH);
    }

    private void instantiateFrame() {
        // setLayout(new FlowLayout());
        setTitle("App");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AppTeste();
    }
}
