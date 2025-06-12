package persistencia;

import dominio.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAO {
    private ConnectionManager conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ProductoDAO(){
        conn = ConnectionManager.getInstance();
    }

    public Producto create(Producto producto) throws SQLException {
        Producto res = null;
        try{
            PreparedStatement ps = conn.connect().prepareStatement(
                    "INSERT INTO " +
                            "PRODUCTOS (CategoriaId, TipoProdu, NombreProdu, Precio, Stock, IsOferta)" +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    java.sql.Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, producto.getCategoriaId());
            ps.setString(2, producto.getTipoProdu());
            ps.setString(3, producto.getNombreProdu());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setByte(6, producto.getIsOferta());

            int affectedRows = ps.executeUpdate();

            if (affectedRows != 0) {
                ResultSet  generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado= generatedKeys.getInt(1);
                    res = getById(idGenerado);
                } else {
                    throw new SQLException("Fallo al crear producto, no se obtuvo el ID.");
                }
            }
            ps.close();
        } catch (SQLException e){
            throw new SQLException("Error al crear el producto: " + e.getMessage(), e);
        } finally {
            ps = null;
            conn.disconnect();
        }
        return res;
    }

    public boolean update(Producto producto) throws SQLException{
        boolean res = false;
        try{
            ps = conn.connect().prepareStatement(
                    "UPDATE PRODUCTOS " +
                            "SET CategoriaId = ?, TipoProdu = ?, NombreProdu = ?, Precio = ?, Stock = ?, IsOferta = ?" +
                            "WHERE ProdId = ?"
            );

            ps.setInt(1, producto.getCategoriaId());
            ps.setString(2, producto.getTipoProdu());
            ps.setString(3, producto.getNombreProdu());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setByte(6, producto.getIsOferta());
            ps.setInt(7, producto.getId());

            if(ps.executeUpdate() > 0){
                res = true;
            }
            ps.close();
        } catch (SQLException e){
            throw new SQLException("Error al modificar el producto: " + e.getMessage(), e);
        }finally {
            ps = null;
            conn.disconnect();
        }

        return res;
    }

    public boolean delete(Producto producto) throws SQLException {
        boolean res = false;
        try {
            ps = conn.connect().prepareStatement(
                    "DELETE FROM PRODUCTOS WHERE ProdId = ?"
            );
            ps.setInt(1, producto.getId());

            if(ps.executeUpdate() > 0){
                res = true;
            }
            ps.close();
        }catch (SQLException e){
            throw new SQLException("Error al eliminar el producto: " + e.getMessage(), e);
        } finally {
            ps = null;
            conn.disconnect();
        }
        return res;
    }

    public ArrayList<Producto> search(String name, int id) throws SQLException {
        ArrayList<Producto> records  = new ArrayList<>();

        try {
            ps = conn.connect().prepareStatement("SELECT ProdId, CategoriaId, TipoProdu, NombreProdu, Precio, Stock, IsOferta " +
                    "FROM PRODUCTOS " +
                    "WHERE NombreProdu LIKE ? AND CategoriaId = ?");

            ps.setString(1, "%" + name + "%");
            ps.setInt(2, id);

            rs = ps.executeQuery();

            while (rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt(1));
                producto.setCategoriaId(rs.getInt(2));
                producto.setTipoProdu(rs.getString(3));
                producto.setNombreProdu(rs.getString(4));
                producto.setPrecio(rs.getDouble(5));
                producto.setStock(rs.getInt(6));
                producto.setIsOferta(rs.getByte(7));
                records.add(producto);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            throw new SQLException("Error al buscar productos: " + ex.getMessage(), ex);
        } finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return records;
    }

    public ArrayList<Producto> searchName(String name) throws SQLException {
        ArrayList<Producto> records  = new ArrayList<>();

        try {
            ps = conn.connect().prepareStatement("SELECT ProdId, CategoriaId, TipoProdu, NombreProdu, Precio, Stock, IsOferta " +
                    "FROM PRODUCTOS " +
                    "WHERE NombreProdu LIKE ?");

            ps.setString(1, "%" + name + "%");

            rs = ps.executeQuery();

            while (rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt(1));
                producto.setCategoriaId(rs.getInt(2));
                producto.setTipoProdu(rs.getString(3));
                producto.setNombreProdu(rs.getString(4));
                producto.setPrecio(rs.getDouble(5));
                producto.setStock(rs.getInt(6));
                producto.setIsOferta(rs.getByte(7));
                records.add(producto);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            throw new SQLException("Error al buscar productos: " + ex.getMessage(), ex);
        } finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return records;
    }

    public ArrayList<Producto> getAll(int id) throws SQLException {
        ArrayList<Producto> records  = new ArrayList<>();

        try {
            ps = conn.connect().prepareStatement("SELECT ProdId, CategoriaId, TipoProdu, NombreProdu, Precio, Stock, IsOferta " +
                    "FROM PRODUCTOS " +
                    "WHERE CategoriaId = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt(1));
                producto.setCategoriaId(rs.getInt(2));
                producto.setTipoProdu(rs.getString(3));
                producto.setNombreProdu(rs.getString(4));
                producto.setPrecio(rs.getDouble(5));
                producto.setStock(rs.getInt(6));
                producto.setIsOferta(rs.getByte(7));
                records.add(producto);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            throw new SQLException("Error al buscar productos: " + ex.getMessage(), ex);
        } finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return records;
    }

    public ArrayList<Producto> getAllNoException() throws SQLException {
        ArrayList<Producto> records  = new ArrayList<>();

        try {
            ps = conn.connect().prepareStatement("SELECT * FROM PRODUCTOS");


            rs = ps.executeQuery();

            while (rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt(1));
                producto.setCategoriaId(rs.getInt(2));
                producto.setTipoProdu(rs.getString(3));
                producto.setNombreProdu(rs.getString(4));
                producto.setPrecio(rs.getDouble(5));
                producto.setStock(rs.getInt(6));
                producto.setIsOferta(rs.getByte(7));
                records.add(producto);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            throw new SQLException("Error al buscar productos: " + ex.getMessage(), ex);
        } finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return records;
    }

    public Producto getById(int id) throws SQLException{
        Producto producto = new Producto();
        try{
            ps = conn.connect().prepareStatement("SELECT ProdId, CategoriaId, TipoProdu, NombreProdu, Precio, Stock, IsOferta " +
                    "FROM PRODUCTOS " +
                    "WHERE ProdId = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                producto.setId(rs.getInt(1));
                producto.setCategoriaId(rs.getInt(2));
                producto.setTipoProdu(rs.getString(3));
                producto.setNombreProdu(rs.getString(4));
                producto.setPrecio(rs.getDouble(5));
                producto.setStock(rs.getInt(6));
                producto.setIsOferta(rs.getByte(7));
            } else {
                producto = null;
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new SQLException("Error al obtener un producto por id: " + e.getMessage(), e);
        }finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return producto;
    }
}
