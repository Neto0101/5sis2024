/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero;

import javax.swing.*;

public class Retirar implements Runnable {
    private final Cajero cajero;

    public Retirar(Cajero cajero) {
        this.cajero = cajero;
    }

    @Override
    public void run() {
        String amountStr = JOptionPane.showInputDialog("Ingrese la cantidad a retirar:");
        if (amountStr == null || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(cajero, "No se ingresó ninguna cantidad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (cajero.getCurrentUser().withdraw(amount)) {
                cajero.updateBalanceLabel();
                cajero.getUsuarioManager().updateUsuario(cajero.getCurrentUser());  // Guardar cambios
                JOptionPane.showMessageDialog(cajero, "El dinero se retiró exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(cajero, "Saldo insuficiente", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(cajero, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
