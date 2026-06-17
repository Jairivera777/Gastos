import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CrearCuenta extends JFrame{
    BaseDatos baseDatos = new BaseDatos();
    public CrearCuenta(){
        setTitle("Crear Cuenta");
        setSize(900, 700);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new Login().setVisible(true);
            }
        });
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        JLabel[] labels = new JLabel[5];
        labels[0] = crearLabel("Rellene los siguientes campos:", 50, 30,20, 800, 70);
        add(labels[0]);
        labels[1] = crearLabel("Nombre de Usuario: ", 20, 2, 100, 200, 50);
        add(labels[1]);
        JTextField[] textos = new JTextField[2];
        textos[0] = crearFiel(100);
        labels[2] = crearLabel("Correo electronico: ", 20, 2, 200, 200, 50);
        textos[1] = crearFiel(200);
        labels[3] = crearLabel("Contraseña:", 20, 2, 300, 200, 50);
        JPasswordField[] password = new JPasswordField[2];
        password[0] = crearPass(300);
        labels[4] = crearLabel("Repita la Contraseña: ", 17, 2, 400, 200, 50);
        password[1] = crearPass(400);
        for (JLabel label : labels) add(label);
        for (int i=0;i<password.length;i++) {
            add(password[i]);
            add(textos[i]);
        }
        JButton boton = getButton(password[0], password[1], textos[0], textos[1]);
        boton.setBounds(400, 500, 200, 50);
        add(boton);
    }
    private JLabel crearLabel(String texto, int tamano, int x, int y, int ancho, int alto){
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, tamano));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.black);
        label.setBounds(x, y,ancho, alto);
        return label;
    }
    private JTextField crearFiel(int y){
        JTextField textField = new JTextField();
        textField.setBounds(250,y, 500, 50);
        textField.setFont(new Font("Arial", Font.BOLD, 15));
        return textField;
    }
    private JPasswordField crearPass(int y){
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(250,y,500,50);
        return passwordField;
    }
    private JButton getButton(JPasswordField contra1, JPasswordField contra2, JTextField usuario, JTextField correo){
        JButton boton = new JButton("Guardar");
        boton.setForeground(Color.WHITE);
        boton.setBackground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.addActionListener(e -> {
            char[] contrasena1 = contra1.getPassword(), contrasena2 = contra2.getPassword();
            if(!(usuario.getText().isEmpty())&&!(contrasena1.length==0)&& Arrays.equals(contrasena1, contrasena2)){
                if (!(Objects.equals(usuario.getText(), baseDatos.getUsuario(usuario.getText())))){
                    if (verifyEmail(correo.getText())){
                        String contrasena = new String(contra1.getPassword());
                        baseDatos.insertarDatosUsuario(usuario.getText(), correo.getText(), contrasena);
                        dispose();
                        new Login().setVisible(true);
                    }else JOptionPane.showMessageDialog(null, "El correo no funciona");
                }else JOptionPane.showMessageDialog(null, "El usuario ya existe");
            }else JOptionPane.showMessageDialog(null, "Debe ingresar un Usuario.");
        });
        return boton;
    }
    private boolean verifyEmail(String email){
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}
