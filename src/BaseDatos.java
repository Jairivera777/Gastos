import javax.swing.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class BaseDatos {
    public final Connection conexion;
    private String usuarioIngresado;

    public BaseDatos() {
        conexion = conectar();
    }
    private Connection conectar(){
        Connection conexion = null;
        String url = "jdbc:postgresql://localhost:5432/soy_gaston";
        String user = "cepeda";
        String contra = "1123437677";
        try{
            conexion = DriverManager.getConnection(url, user, contra);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error "+e.getErrorCode()+": "+e.getMessage());
        }
        return conexion;
    }
    public Connection getBaseDatos(){
        return this.conexion;
    }
    public boolean validarInfo(String usuario, char[] contra){
        if(conexion != null){
            String contrasena = new String(contra);
            String select = "SELECT name, email, password FROM \"user\" WHERE name = ? OR name = ?";
            try(PreparedStatement ps = conexion.prepareStatement(select)){
                ps.setString(1, usuario);
                ps.setString(2, usuario);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    this.usuarioIngresado = rs.getString("name");
                    String contraRegistrada = rs.getString("password");
                    return Objects.equals(usuarioIngresado, usuario)
                            && Objects.equals(contrasena, contraRegistrada);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error "+e.getErrorCode()+": "+e.getMessage());
            }
        }
        return false;
    }
    public String getUsuario(String usuario){
        String user = null;
        if(conexion!=null){
            String select = "SELECT name FROM \"user\" WHERE name = ?";
            try(PreparedStatement ps = conexion.prepareStatement(select)){
                ps.setString(1, usuario);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) user = rs.getString("name");
                return user;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error "+e.getErrorCode()+": "+e.getMessage());
            }
        }
        return null;
    }
    public void insertarDatosUsuario(String usuario, String email, String contraseña){
        if (conexion!=null){
            String insert = "INSERT INTO \"user\" (name, email, password) VALUES (?, ?, ?)";
            try(PreparedStatement ps = conexion.prepareStatement(insert)){
                ps.setString(1, usuario);
                ps.setString(2, email);
                ps.setString(3, contraseña);
                int filas = ps.executeUpdate();
                if (filas>0) JOptionPane.showMessageDialog(null, "Ha sido registrado con exito");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error "+e.getErrorCode()+": "+e.getMessage());
            }
        }
    }
}
