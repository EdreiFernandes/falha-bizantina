package layout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import server.Server;

public class App extends JFrame implements ActionListener {
    private JLabel status = new JLabel();
    private JLabel consoleLog = new JLabel("Olá");
    private JButton btnEnviar = new JButton("Enviar");
    private JPanel console = new JPanel();
    private JTextField destinatario = new JTextField("4240");
    private Font font = new Font("Arial", Font.BOLD, 20);

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Hello World");
        // TODO consumir client
    }

    public App() {
        btnEnviar.addActionListener(this);
        btnEnviar.setFont(font);
        add(BorderLayout.SOUTH, btnEnviar);

        console.setLayout(new BorderLayout());

        consoleLog.setFont(font);
        consoleLog.setHorizontalAlignment(SwingConstants.CENTER);
        destinatario.setFont(font);
        destinatario.setHorizontalAlignment(SwingConstants.CENTER);

        console.add(BorderLayout.SOUTH, destinatario);
        console.add(BorderLayout.CENTER, consoleLog);
        add(BorderLayout.CENTER, console);

        add(BorderLayout.NORTH, status);

        instantiateFrame();
    }

    private void instantiateFrame() {
        setTitle("App");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Server server = new Server(4240);
        App app = new App();

        app.status.setText("Status: " + server.getStatus() + " | Porta: " + server.getAddress());
    }
}
