/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero;

import javax.swing.*;
import java.awt.*;

public class Cajero extends JFrame {
    Usuario currentUser;
    private UsuarioManager usuarioManager;

    private JLabel balanceLabel;
    private JLabel welcomeLabel; // Nuevo JLabel para mostrar el mensaje de bienvenida
    private JLabel integrantesLabel;
    private JButton depositButton, withdrawButton, checkBalanceButton, exitButton;

    public Cajero() {
        setTitle("Simulador de Cuenta Bancaria");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(200, 220, 240));

        usuarioManager = new UsuarioManager("usuariosData.txt");

        // Inicializamos el JLabel de bienvenida antes de la llamada a login
        welcomeLabel = new JLabel(); 

        if (!login()) {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos. La aplicación se cerrará.");
            System.exit(0);
        }

        balanceLabel = new JLabel(String.format("Saldo: $%.2f", currentUser.getBalance()));
        integrantesLabel = new JLabel("Integrantes: Ernesto Segundo Cruz");
        depositButton = new JButton("Depositar");
        withdrawButton = new JButton("Retirar");
        checkBalanceButton = new JButton("Consultar Saldo");
        exitButton = new JButton("Salir");

        styleButtons();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(welcomeLabel); // Agregamos el JLabel de bienvenida al panel
        panel.add(integrantesLabel);
        panel.add(balanceLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(depositButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(withdrawButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(checkBalanceButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(exitButton);

        add(panel);

        depositButton.addActionListener(e -> new Thread(new Depositar(this)).start());
        withdrawButton.addActionListener(e -> new Thread(new Retirar(this)).start());
        checkBalanceButton.addActionListener(e -> new Thread(new ConsultarSaldo(this)).start());
        exitButton.addActionListener(e -> exitApplication());
    }

    private boolean login() {
        String username = JOptionPane.showInputDialog("Ingrese su usuario:");
        String password = JOptionPane.showInputDialog("Ingrese su contraseña:");
        currentUser = usuarioManager.findUser(username, password);
        if (currentUser != null) {
            welcomeLabel.setText("Bienvenido, " + currentUser.getUsername() + "!"); // Actualizamos el mensaje de bienvenida
        }
        return currentUser != null;
    }

    private void styleButtons() {
        depositButton.setBackground(new Color(70, 130, 180));
        withdrawButton.setBackground(new Color(70, 130, 180));
        checkBalanceButton.setBackground(new Color(70, 130, 180));
        exitButton.setBackground(new Color(220, 50, 50));

        depositButton.setForeground(Color.WHITE);
        withdrawButton.setForeground(Color.WHITE);
        checkBalanceButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Estilo del JLabel de bienvenida
        integrantesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
    }

    public synchronized void updateBalanceLabel() {
        balanceLabel.setText(String.format("Saldo: $%.2f", currentUser.getBalance()));
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea cerrar sesión?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public UsuarioManager getUsuarioManager() {
        return usuarioManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cajero app = new Cajero();
            app.setVisible(true);
        });
    }
}
