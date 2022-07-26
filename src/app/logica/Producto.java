/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.logica;

import app.persistencia.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author cahuc
 */
public class Producto {

    private int id;
    private String nombre;
    private int cantidad;
    private String categoria;
    private double precio;

    public Producto() {
    }

    public Producto(String nombre, int cantidad, String categoria, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.precio = precio;
    }

    public Producto(int id, String nombre, int cantidad, String categoria, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto: " + nombre + " con ID: " + id;
    }

    public List<Producto> listarProductos() {

        List<Producto> listaProductos = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();

        String sql = "SELECT * FROM producto";

        try {

            ResultSet resultado = conexion.consultarBD(sql);
            Producto p;

            while (resultado.next()) {

                p = new Producto();
                p.setId(resultado.getInt("id"));
                p.setNombre(resultado.getString("nombre"));
                p.setCategoria(resultado.getString("categoria"));
                p.setCantidad(resultado.getInt("cantidad"));
                p.setPrecio(resultado.getDouble("precio"));

                listaProductos.add(p);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }

        return listaProductos;

    }

    public Producto seleccionarProducto() {

        ConexionBD conexion = new ConexionBD();
        String sql = "SELECT * FROM producto WHERE id=" + id;

        ResultSet resultado = conexion.consultarBD(sql);
        Producto p = null;

        try {
            if ( resultado.next() ) {
                
                p = new Producto();
                p.setId( resultado.getInt("id") );
                p.setNombre( resultado.getString("nombre") );
                p.setCategoria( resultado.getString("categoria") );
                p.setCantidad(resultado.getInt("cantidad"));
                p.setPrecio(resultado.getDouble("precio"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;
        
    }

    public boolean guardarProducto() {

        ConexionBD conexion = new ConexionBD();

        String sql = String.format(Locale.ROOT,
                "INSERT INTO producto (nombre,cantidad,categoria,precio) VALUES ( '%s', %d, '%s', %.2f );",
                nombre, cantidad, categoria, precio);

        if (conexion.insertar(sql) == true) {
            conexion.closeConnection();
            return true;
        } else {
            return false;
        }

    }

    public boolean actualizarProducto() {
        ConexionBD conexion = new ConexionBD();

        // No utilizaremos String.format( ) para ver como sea crea de otra forma
        String sql = " UPDATE producto "
                + "SET nombre='" + nombre + "', categoria='" + categoria + "', "
                + "cantidad=" + cantidad + ", precio=" + precio + " WHERE id = " + id;

        if (conexion.actualizar(sql) == true) {
            conexion.closeConnection();
            return true;
        } else {
            return false;
        }

    }

    public boolean borrarProducto() {

        ConexionBD conexion = new ConexionBD();

        String sql = "DELETE FROM producto WHERE id = " + id;

        if (conexion.borrar(sql) == true) {
            conexion.closeConnection();
            return true;
        } else {
            return false;
        }

    }

}
