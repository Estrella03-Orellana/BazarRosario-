package persistencia;

import dominio.Factura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FacturaDAOTest {
    private FacturaDAO facturaDAO;

    @BeforeEach
    void setUp() {
        facturaDAO = new FacturaDAO();
    }

    @Test
    @DisplayName("Debe crear una factura exitosamente y retornarla con el ID y fecha generados")
    void create_Success() throws SQLException {
        // Arrange
        Factura newFactura = new Factura("usuarioPrueba", 150.75, null); // Fecha será generada por DAO

        // Act
        Factura createdFactura = facturaDAO.create(newFactura);

        // Assert
        assertNotNull(createdFactura, "La factura creada no debe ser nula");
        assertTrue(createdFactura.getCodFac() > 0, "El CodFac debe ser generado por la base de datos");
        assertEquals(newFactura.getNombreUsu(), createdFactura.getNombreUsu(), "El nombre de usuario debe coincidir");
        assertEquals(newFactura.getPago(), createdFactura.getPago(), 0.001, "El pago debe coincidir");
        assertNotNull(createdFactura.getFecha(), "La fecha debe ser generada y no nula");

        // Opcional: Verificar que el timestamp es reciente (dentro de una ventana de tiempo razonable)
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long differenceInSeconds = ChronoUnit.SECONDS.between(createdFactura.getFecha().toLocalDateTime(), now.toLocalDateTime());
        assertTrue(differenceInSeconds < 5, "La fecha de la factura debe ser muy reciente (dentro de 5 segundos)");

        // Verificar que la factura realmente existe en la DB recuperándola por ID
        Factura retrievedFactura = facturaDAO.getById(createdFactura.getCodFac());
        assertNotNull(retrievedFactura, "La factura debe poder ser recuperada por su ID");
        facturaDAO.delete(createdFactura);
    }

    @Test
    @DisplayName("Debe lanzar SQLException si se intenta crear una factura con NombreUsu nulo (violación de NOT NULL)")
    void create_NullNombreUsu_ThrowsException() {
        // Arrange
        Factura invalidFactura = new Factura(null, 200.0, new Timestamp(System.currentTimeMillis()));

        // Act & Assert
        SQLException thrown = assertThrows(SQLException.class, () -> facturaDAO.create(invalidFactura));
        assertTrue(thrown.getMessage().contains("Error al crear la factura"),
                "El mensaje de error debe indicar un problema al crear la factura");
    }

    // --- Pruebas para getById(int id) ---

    @Test
    @DisplayName("Debe obtener una factura por ID exitosamente")
    void getById_Success() throws SQLException {
        // Arrange
        Factura facturaToCreate = new Factura("usuarioGet", 500.0, new Timestamp(System.currentTimeMillis()));
        Factura created = facturaDAO.create(facturaToCreate);
        assertNotNull(created);
        int facturaId = created.getCodFac();

        // Act
        Factura foundFactura = facturaDAO.getById(facturaId);

        // Assert
        assertNotNull(foundFactura, "La factura no debe ser nula");
        facturaDAO.delete(foundFactura);
    }

    @Test
    @DisplayName("Debe retornar null cuando la factura no se encuentra por ID")
    void getById_NotFound_ReturnsNull() throws SQLException {
        // Arrange
        int nonExistentId = 999;

        // Act
        Factura foundFactura = facturaDAO.getById(nonExistentId);

        // Assert
        assertNull(foundFactura, "Debe retornar null para un ID inexistente");
    }


    // --- Pruebas para getFacturas(String nombre) ---

    @Test
    @DisplayName("Debe obtener todas las facturas de un usuario exitosamente")
    void getFacturas_Success() throws SQLException {
        // Arrange
        String user1 = "usuarioA";
        String user2 = "usuarioB";

        facturaDAO.create(new Factura(user1, 100.0, new Timestamp(System.currentTimeMillis())));
        facturaDAO.create(new Factura(user1, 250.50, new Timestamp(System.currentTimeMillis())));
        facturaDAO.create(new Factura(user2, 300.0, new Timestamp(System.currentTimeMillis())));
        facturaDAO.create(new Factura(user1, 75.0, new Timestamp(System.currentTimeMillis())));

        // Act
        ArrayList<Factura> results = facturaDAO.getFacturas(user1);
        ArrayList<Factura> results2 = facturaDAO.getFacturas(user2);

        // Assert
        assertNotNull(results);
        assertEquals(3, results.size(), "Debe encontrar 3 facturas para " + user1);

        // Verificar que todas las facturas son del usuario correcto
        assertTrue(results.stream().allMatch(f -> f.getNombreUsu().equals(user1)));
        assertTrue(results.stream().anyMatch(f -> f.getPago() == 100.0));
        assertTrue(results.stream().anyMatch(f -> f.getPago() == 250.50));
        assertTrue(results.stream().anyMatch(f -> f.getPago() == 75.0));

        for(Factura factura : results){
            facturaDAO.delete(factura);
        }
        for(Factura factura : results2){
            facturaDAO.delete(factura);
        }
    }

    @Test
    @DisplayName("Debe retornar una lista vacía si no se encuentran facturas para el usuario")
    void getFacturas_NoFacturasFound_ReturnsEmptyList() throws SQLException {
        // Arrange
        Factura facturaExistente = facturaDAO.create(new Factura("usuarioExistente", 100.0, new Timestamp(System.currentTimeMillis())));
        String nonExistentUser = "usuarioInexistente";

        // Act
        ArrayList<Factura> results = facturaDAO.getFacturas(nonExistentUser);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty(), "La lista debe estar vacía si no hay facturas para el usuario");

        facturaDAO.delete(facturaExistente);
    }

}