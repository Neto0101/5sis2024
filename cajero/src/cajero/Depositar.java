/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero;

import javax.swing.*;

public class Depositar implements Runnable {
    private final Cajero cajero;

    public Depositar(Cajero cajero) {
        this.cajero = cajero;
    }

    @Override
    public void run() {
        String amountStr = JOptionPane.showInputDialog("Ingrese la cantidad a depositar:");
        if (amountStr == null || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(cajero, "No se ingresó ninguna cantidad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(cajero, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cajero.getCurrentUser().deposit(amount);
            cajero.updateBalanceLabel();
            cajero.getUsuarioManager().updateUsuario(cajero.getCurrentUser());  // Guardar cambios
            JOptionPane.showMessageDialog(cajero, "Depósito exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(cajero, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
