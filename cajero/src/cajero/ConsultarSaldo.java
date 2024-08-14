/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero;

import javax.swing.*;

public class ConsultarSaldo implements Runnable {
    private final Cajero cajero;

    public ConsultarSaldo(Cajero cajero) {
        this.cajero = cajero;
    }

    @Override
    public void run() {
        String message = cajero.currentUser.getTransactionSummary();
        JOptionPane.showMessageDialog(cajero, message, "Detalle de Saldo", JOptionPane.INFORMATION_MESSAGE);
    }
}
