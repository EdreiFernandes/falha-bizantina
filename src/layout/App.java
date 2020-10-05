package layout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import server.Server;

public class App extends JFrame implements ActionListener {
    JLabel status = new JLabel("Status: Conectado | Porta: 4242");
    JLabel console = new JLabel("texto");
    JButton btnEnviar = new JButton("Enviar");

    Font font = new Font("Arial", Font.BOLD, 20);

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Hello World");
    }

    public App() {
        buildConsoleLog();

        btnEnviar.addActionListener(this);
        add(BorderLayout.SOUTH, btnEnviar);

        instantiateFrame();
    }

    private void instantiateFrame() {
        setTitle("App");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildConsoleLog() {
        console.setFont(font);
        console.setHorizontalAlignment(SwingConstants.CENTER);

        add(BorderLayout.NORTH, status);
        add(BorderLayout.CENTER, console);
    }

    public static void main(String[] args) {
        new Server(4242);
        new App();
    }
}
