/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero;

public class Usuario {
    private String username;
    private String password;
    private double balance;
    private double totalDeposited;
    private double totalWithdrawn;

    public Usuario(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.totalDeposited = 0;
        this.totalWithdrawn = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        totalDeposited += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            totalWithdrawn += amount;
            return true;
        }
        return false;
    }

    public String getTransactionSummary() {
        return String.format(
            "Total depositado: $%.2f\nTotal retirado: $%.2f\nSaldo actual: $%.2f",
            totalDeposited, totalWithdrawn, balance
        );
    }
}
