package persistencia;

import dominio.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ProductoDAOTest {
    private ProductoDAO productoDAO;

    @BeforeEach
    void setUp(){
        productoDAO = new ProductoDAO();
    }
    @Test
    @DisplayName("Debe crear un producto exitosamente y retornarlo con el ID generado")
    void create_Success() throws SQLException {
        // Arrange
        Producto newProduct = new Producto(1, "Electrónica", "Laptop", 1200.0, 10, (byte) 0);

        // Act
        Producto createdProduct = productoDAO.create(newProduct);

        // Assert
        assertNotNull(createdProduct, "El producto creado no debe ser nulo");
        assertTrue(createdProduct.getId() > 0, "El ID del producto debe ser generado por la base de datos");
        assertEquals(newProduct.getCategoriaId(), createdProduct.getCategoriaId(), "La categoría debe coincidir");
        assertEquals(newProduct.getTipoProdu(), createdProduct.getTipoProdu(), "El tipo de producto debe coincidir");
        assertEquals(newProduct.getNombreProdu(), createdProduct.getNombreProdu(), "El nombre debe coincidir");
        assertEquals(newProduct.getPrecio(), createdProduct.getPrecio(), 0.001, "El precio debe coincidir"); // Delta para double
        assertEquals(newProduct.getStock(), createdProduct.getStock(), "El stock debe coincidir");
        assertEquals(newProduct.getIsOferta(), createdProduct.getIsOferta(), "El estado de oferta debe coincidir");

        // Opcional: Verificar que el producto realmente existe en la DB
        Producto retrievedProduct = productoDAO.getById(createdProduct.getId());
        assertNotNull(retrievedProduct, "El producto debe poder ser recuperado por su ID");
        productoDAO.delete(retrievedProduct);
    }

    @Test
    @DisplayName("Debe lanzar SQLException si se intenta crear un producto con datos inválidos (simulado por DB)")
    void create_InvalidData_ThrowsException() {

        Producto invalidProduct = new Producto(1, "Electrónica", "NombreDeProductoExcesivamenteLargoParaPruebaDeErrorDeBaseDeDatosYQueDeberiaFallarAlInsertar", 100.0, 5, (byte) 0);
    }


    // --- Pruebas para update(Producto producto) ---

    @Test
    @DisplayName("Debe actualizar un producto existente exitosamente")
    void update_Success() throws SQLException {
        // Arrange
        Producto originalProduct = new Producto(1, "Electrónica", "Televisor", 500.0, 5, (byte) 0);
        Producto created = productoDAO.create(originalProduct);
        assertNotNull(created);
        int productId = created.getId();

        // Modificar el producto
        created.setPrecio(450.0);
        created.setStock(3);
        created.setIsOferta((byte) 1);
        created.setNombreProdu("Televisor LCD");

        // Act
        boolean result = productoDAO.update(created);

        // Assert
        assertTrue(result, "La actualización debe ser exitosa");

        // Verificar que el producto fue actualizado en la DB
        Producto updatedProduct = productoDAO.getById(productId);
        assertNotNull(updatedProduct);
        assertEquals(created.getPrecio(), updatedProduct.getPrecio(), 0.001);
        assertEquals(created.getStock(), updatedProduct.getStock());
        assertEquals(created.getIsOferta(), updatedProduct.getIsOferta());
        assertEquals(created.getNombreProdu(), updatedProduct.getNombreProdu());
        productoDAO.delete(updatedProduct);
    }

    @Test
    @DisplayName("Debe retornar false si se intenta actualizar un producto inexistente")
    void update_NonExistentProduct_ReturnsFalse() throws SQLException {
        // Arrange
        Producto nonExistentProduct = new Producto( 1, "Electro", "Inexistente", 100.0, 1, (byte) 0);
        nonExistentProduct.setId(999); // Simular un ID que no existe

        // Act
        boolean result = productoDAO.update(nonExistentProduct);

        // Assert
        assertFalse(result, "La actualización de un producto inexistente debe retornar false");
    }

    // --- Pruebas para delete(Producto producto) ---

    @Test
    @DisplayName("Debe eliminar un producto existente exitosamente")
    void delete_Success() throws SQLException {
        // Arrange
        Producto productToDelete = new Producto(2, "Hogar", "Silla", 80.0, 10, (byte) 0);
        Producto created = productoDAO.create(productToDelete);
        assertNotNull(created);
        int productId = created.getId();

        // Act
        boolean result = productoDAO.delete(created);

        // Assert
        assertTrue(result, "La eliminación debe ser exitosa");

        // Verificar que el producto ya no existe en la DB
        Producto retrievedProduct = productoDAO.getById(productId);
        assertNull(retrievedProduct, "El producto eliminado no debe poder ser recuperado");
    }

    @Test
    @DisplayName("Debe retornar false si se intenta eliminar un producto inexistente")
    void delete_NonExistentProduct_ReturnsFalse() throws SQLException {
        // Arrange
        Producto nonExistentProduct = new Producto( 0, null, null, 0, 0, (byte) 0);
        nonExistentProduct.setId(999); // Simular un ID que no existe

        // Act
        boolean result = productoDAO.delete(nonExistentProduct);

        // Assert
        assertFalse(result, "La eliminación de un producto inexistente debe retornar false");

    }

    // --- Pruebas para search(String name, int id) ---

    @Test
    @DisplayName("Debe buscar productos por nombre y categoría exitosamente")
    void search_Success() throws SQLException {
        // Arrange
        productoDAO.create(new Producto(1, "Electrónica", "Smart TV 55", 700.0, 5, (byte) 0));
        productoDAO.create(new Producto(1, "Electrónica", "TV portátil", 150.0, 2, (byte) 1));
        productoDAO.create(new Producto(2, "Hogar", "Mesa de TV", 200.0, 3, (byte) 0)); // Diferente categoría
        productoDAO.create(new Producto(1, "Electrónica", "Smartphone", 900.0, 10, (byte) 0)); // No contiene "TV"

        String searchTerm = "TV";
        int categoryId = 1;

        // Act
        ArrayList<Producto> results = productoDAO.search(searchTerm, categoryId);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size(), "Debe encontrar 2 productos que coincidan");

        // Verificar que los productos encontrados son correctos
        assertTrue(results.stream().anyMatch(p -> p.getNombreProdu().equals("Smart TV 55") && p.getCategoriaId() == 1));
        assertTrue(results.stream().anyMatch(p -> p.getNombreProdu().equals("TV portátil") && p.getCategoriaId() == 1));

        for(Producto product : results){
            productoDAO.delete(product);
        }
    }

    @Test
    @DisplayName("Debe retornar una lista vacía si no se encuentran productos para la búsqueda")
    void search_NoProductsFound_ReturnsEmptyList() throws SQLException {
        // Arrange
        productoDAO.create(new Producto(1, "Electrónica", "Monitor", 300.0, 10, (byte) 0));
        String searchTerm = "XYZ";
        int categoryId = 1;

        // Act
        ArrayList<Producto> results = productoDAO.search(searchTerm, categoryId);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty(), "La lista debe estar vacía si no hay coincidencias");

        for(Producto product : results){
            productoDAO.delete(product);
        }
    }

    // --- Pruebas para getAll(int id) ---

    @Test
    @DisplayName("Debe obtener todos los productos de una categoría exitosamente")
    void getAll_Success() throws SQLException {
        // Arrange
        productoDAO.create(new Producto(5, "Libros", "El Señor de los Anillos", 25.0, 10, (byte) 0));
        productoDAO.create(new Producto(5, "Libros", "Harry Potter", 30.0, 15, (byte) 0));
        productoDAO.create(new Producto(6, "Deportes", "Pelota", 10.0, 50, (byte) 0)); // Diferente categoría

        int categoryId = 5;

        // Act
        ArrayList<Producto> results = productoDAO.getAll(categoryId);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size(), "Debe encontrar 2 productos de la categoría 5");

        assertTrue(results.stream().anyMatch(p -> p.getNombreProdu().equals("El Señor de los Anillos")));
        assertTrue(results.stream().anyMatch(p -> p.getNombreProdu().equals("Harry Potter")));

        for(Producto product : results){
            productoDAO.delete(product);
        }
    }

    @Test
    @DisplayName("Debe retornar una lista vacía si no se encuentran productos para la categoría en getAll")
    void getAll_NoProductsFound_ReturnsEmptyList() throws SQLException {
        // Arrange
        productoDAO.create(new Producto(1, "Electrónica", "Mouse", 20.0, 100, (byte) 0));
        int categoryId = 99; // Categoría inexistente

        // Act
        ArrayList<Producto> results = productoDAO.getAll(categoryId);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty(), "La lista debe estar vacía si no hay productos en la categoría");

        for(Producto product : results){
            productoDAO.delete(product);
        }
    }

    // --- Pruebas para getById(int id) ---

    @Test
    @DisplayName("Debe obtener un producto por ID exitosamente")
    void getById_Success() throws SQLException {
        // Arrange
        Producto productToFind = new Producto(7, "Juguetes", "Muñeca", 15.0, 20, (byte) 0);
        Producto created = productoDAO.create(productToFind);
        assertNotNull(created);
        int productId = created.getId();

        // Act
        Producto foundProduct = productoDAO.getById(productId);

        // Assert
        assertNotNull(foundProduct, "El producto no debe ser nulo");

        productoDAO.delete(foundProduct);
    }

    @Test
    @DisplayName("Debe retornar null cuando el producto no se encuentra por ID")
    void getById_NotFound_ReturnsNull() throws SQLException {
        // Arrange
        // No creamos ningún producto
        int nonExistentId = 999;

        // Act
        Producto foundProduct = productoDAO.getById(nonExistentId);

        // Assert
        assertNull(foundProduct, "Debe retornar null para un ID inexistente");
    }


}