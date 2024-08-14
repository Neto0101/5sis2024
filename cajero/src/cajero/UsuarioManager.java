/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {
    private List<Usuario> usuarios = new ArrayList<>();
    private String filename;

    public UsuarioManager(String filename) {
        this.filename = filename;
        loadUsuariosFromFile(filename);
    }

    private void loadUsuariosFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    usuarios.add(new Usuario(username, password, balance));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }

    public Usuario findUser(String username, String password) {
        for (Usuario user : usuarios) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void saveUsuariosToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Usuario user : usuarios) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getBalance());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de usuarios: " + e.getMessage());
        }
    }

    public void updateUsuario(Usuario usuario) {
    // Primero, actualizamos la lista de usuarios para reflejar el cambio en el saldo
    for (int i = 0; i < usuarios.size(); i++) {
        if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
            usuarios.set(i, usuario); // Actualiza el usuario en la lista
            break;
        }
    }
    saveUsuariosToFile(); // Luego, guarda la lista de usuarios actualizada en el archivo
}

}


