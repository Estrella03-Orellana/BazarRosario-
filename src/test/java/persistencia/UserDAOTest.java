package persistencia;

import dominio.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    void setUp(){
        userDAO = new UserDAO();
    }
    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void authenticate() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void createUser() throws SQLException {
        User user = new User( "admin", "12345", "admin@gmail.com", (byte) 1);
        User res = userDAO.create(user);
        assertNotEquals(res,null);
    }
}