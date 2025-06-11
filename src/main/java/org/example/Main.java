package org.example;

import presentacion.ConfiteriayPapeleriaV;
import presentacion.Login;
import presentacion.MainForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm mainForm  = new MainForm();
            mainForm.setVisible(true);
            Login login = new Login(mainForm);
            login.setVisible(true);

        });
    }


}