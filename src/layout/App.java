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
    private JLabel status = new JLabel();
    private JLabel console = new JLabel("texto");
    private JButton btnEnviar = new JButton("Enviar");
    private Font font = new Font("Arial", Font.BOLD, 20);

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Hello World");
        // TODO consumir client
    }

    public App() {
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

    private void buildConsoleLog(String _status, int _address) {
        console.setFont(font);
        console.setHorizontalAlignment(SwingConstants.CENTER);

        status.setText("Status: " + _status + " | Porta: " + _address);

        add(BorderLayout.NORTH, status);
        add(BorderLayout.CENTER, console);
    }

    public static void main(String[] args) {
        Server server = new Server(4240);
        App app = new App();

        app.buildConsoleLog(server.getStatus(), server.getAddress());
    }
}
