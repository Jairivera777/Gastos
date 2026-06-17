import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    protected BaseDatos baseDatos = Login.getBaseDatos();
    protected JButton[] botones = new JButton[5];
    public Menu(){
        setTitle("Titulo");
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        botones[0] = crearBoton("Crear Cuenta Ahorros", 100, 100, 200, 100);
        botones[0].addActionListener(e -> {
            dispose();
            new MenuCrearCuenta();
        });
        botones[1] = crearBoton("Ver transacciones cuenta", 100, 200, 200, 100);
        botones[1].addActionListener(e -> {
            dispose();
            new MenuVerTransacciones();
        });
        botones[2] = crearBoton("Añadir dinero cuenta", 500, 100, 200, 100);
        botones[3] = crearBoton("Añadir gasto cuenta", 500, 200, 200, 100);
        botones[4] = crearBoton("Plan sugerido", 750, 150, 200, 100);
        for (JButton boton : botones) add(boton);
    }
    protected JButton crearBoton(String texto, int x, int y, int ancho, int alto){
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setForeground(Color.WHITE);
        boton.setBackground(Color.BLACK);
        return boton;
    }
    protected void borrar(){
        for(JButton boton : botones) remove(boton);
    }
}
