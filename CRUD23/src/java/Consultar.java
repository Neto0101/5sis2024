/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author coppel
 */
@WebServlet(urlPatterns = {"/Consultar"})
public class Consultar extends HttpServlet {

    private Connection con;
    private Statement set;
    private ResultSet rs;

    public void init(ServletConfig scg) throws ServletException {
        String url = "jdbc:mysql://localhost:3306/registro0101";
        String username = "root";
        String password = "po0101gh#segR";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            set = con.createStatement();
            System.out.println("Se conectó a la BD");
        } catch (Exception e) {
            System.out.println("No se conectó a la BD");
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consultar</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Tabla de Registro de Usuarios</h1>");
            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Nombre Completo</th>");
            out.println("<th>Edad</th>");
            out.println("<th>Correo</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            try {
                String query = "SELECT * FROM mregistro";
                rs = set.executeQuery(query);

                boolean dataFound = false;
                while (rs.next()) {
                    dataFound = true;
                    String id = rs.getString("id_usu");
                    String nombreCompleto = rs.getString("nom_usu") + " " + rs.getString("appat_usu") + " " + rs.getString("apmat_usu");
                    int edad = rs.getInt("edad_usu");
                    String correo = rs.getString("email_usu");

                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + nombreCompleto + "</td>");
                    out.println("<td>" + edad + "</td>");
                    out.println("<td>" + correo + "</td>");
                    out.println("</tr>");
                }

                if (!dataFound) {
                    out.println("<tr><td colspan='4'>No se encontraron datos.</td></tr>");
                }

            } catch (Exception e) {
                e.printStackTrace();
                out.println("<tr><td colspan='4'>Error al consultar la base de datos.</td></tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Servlet para consultar la tabla de estudiantes";
    }

    public void destroy() {
        try {
            if (rs != null) rs.close();
            if (set != null) set.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}