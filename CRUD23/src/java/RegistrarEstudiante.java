/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author coppel
 */
public class RegistrarEstudiante extends HttpServlet {

    private Connection con;
    private PreparedStatement insertStmt;

    public void init() throws ServletException {
        String url = "jdbc:mysql://localhost:3306/registro0101 ";
        String username = "root";
        String password = "po0101gh#segR";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO mregistro (nom_usu, appat_usu,apmat_usu, edad_usu, email_usu) VALUES (?, ?, ?, ?, ?)";
            insertStmt = con.prepareStatement(query);
            System.out.println("Conexión exitosa a la BD");
        } catch (Exception e) {
            System.out.println("Error al conectar con la BD: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("No se pudo conectar con la base de datos.");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String nom = request.getParameter("nombre");
            String appat = request.getParameter("appat");
            String apmat = request.getParameter("apmat");
            String email = request.getParameter("email");
            int edad = Integer.parseInt(request.getParameter("edad"));

            try {
                insertStmt.setString(1, nom);
                insertStmt.setString(2, appat );
                 insertStmt.setString(3, apmat);
                insertStmt.setInt(4, edad);
                insertStmt.setString(5, email);
                insertStmt.executeUpdate();
                System.out.println("Registro exitoso");

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Registro de Estudiante</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>El estudiante ha sido registrado exitosamente</h1>");
                out.println("<a href='index.html'>Regresar al Formulario</a>");
                out.println("</body>");
                out.println("</html>");

            } catch (SQLException e) {
                System.out.println("Error al registrar estudiante: " + e.getMessage());
                e.printStackTrace();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error en registro</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Ha ocurrido un error al registrar el estudiante</h1>");
                out.println("<p>Contacte al administrador</p>");
                out.println("<a href='index.html'>Regresar al Formulario</a>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Registro de estudiantes en la base de datos";
    }

    public void destroy() {
        try {
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}