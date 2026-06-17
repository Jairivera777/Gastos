import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

public class Login extends JFrame {
    private static final BaseDatos baseDatos = new BaseDatos();
    public Login(){
        setTitle("Titulo");
        setSize(425, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        JLabel titulo = new JLabel("BIENVENIDO A \"Titulo\"");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.black);
        titulo.setBounds(0, 20, 400, 100);
        add(titulo);
        JLabel mensaje = new JLabel("Ingrese su Usuario y Contraseña");
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        mensaje.setBounds(0, 45, 400, 100);
        add(mensaje);
        JTextField usuario = new JTextField("Usuario o email");
        usuario.setForeground(Color.LIGHT_GRAY);
        usuario.setBounds(100, 120, 200, 20);
        usuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usuario.getText().equals("Usuario o email")) {
                    usuario.setText("");
                    usuario.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usuario.getText().isEmpty()) {
                    usuario.setText("Usuario o email");
                    usuario.setForeground(Color.lightGray);
                }
            }
        });
        JPasswordField contra = new JPasswordField("Contrasena");
        usuario.addActionListener(e -> contra.requestFocus());
        add(usuario);
        contra.setEchoChar((char) 0);
        contra.setForeground(Color.lightGray);
        contra.setBounds(100, 160, 200, 20);
        contra.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                char[] contrasena = "Contrasena".toCharArray();
                if (Arrays.equals(contra.getPassword(), contrasena)) {
                    contra.setEchoChar('•');
                    contra.setText("");
                    contra.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                char[] contrasena = contra.getPassword();
                if (contrasena.length == 0) {
                    contra.setEchoChar((char) 0);
                    contra.setText("Contrasena");
                    contra.setForeground(Color.lightGray);
                }
            }
        });
        JButton boton = getJButton(contra, usuario);
        contra.addActionListener(e -> boton.doClick());
        add(contra);
        add(boton);
        JButton crearCuenta = new JButton("Crear Cuenta");
        crearCuenta.setForeground(Color.WHITE);
        crearCuenta.setBackground(Color.BLACK);
        crearCuenta.setBounds(100, 300, 200, 50);
        crearCuenta.setFont(new Font("Arial", Font.BOLD, 20));
        crearCuenta.addActionListener(e -> {
            dispose();
            new CrearCuenta().setVisible(true);
        });
        add(crearCuenta);
        SwingUtilities.invokeLater(usuario::requestFocusInWindow);
    }
    private JButton getJButton(JPasswordField contra, JTextField usuario) {
        JButton boton = new JButton("Iniciar Sesion");
        boton.setForeground(Color.WHITE);
        boton.setBackground(Color.BLACK);
        boton.setBounds(100, 200, 200, 50);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.addActionListener(e -> {
            char[] contrasena1 = contra.getPassword();
            char[] contrasena2 = "Contrasena".toCharArray();
            if (!(usuario.getText().isEmpty() || contrasena1.length == 0) || Arrays.equals(contrasena1, contrasena2)){
                if (baseDatos.validarInfo(usuario.getText(), contra.getPassword())) iniciarMenu();
                else
                    JOptionPane.showMessageDialog(null, "Su usuario y/o contraseña es invalido.");
            } else
                JOptionPane.showMessageDialog(null, "Los campos estan vacios.");
        });
        boton.setFocusPainted(false);
        return boton;
    }
    private void iniciarMenu() {
        dispose();
        new Menu();
    }

    public static BaseDatos getBaseDatos() {
        return baseDatos;
    }
}
