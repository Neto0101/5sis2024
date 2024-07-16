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
import java.sql.PreparedStatement;


/**
 *
 * @author coppel
 */
@WebServlet(urlPatterns = {"/EliminarEstudiante"})
public class EliminarEstudiante extends HttpServlet {

    private Connection con;

    public void init(ServletConfig scg) throws ServletException {
        String url = "jdbc:mysql://localhost/registro0101";
        String username = "root";
        String password = "po0101gh#segR";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
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
            String id = request.getParameter("id_usu");

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Eliminar Estudiante</title>");            
            out.println("</head>");
            out.println("<body>");
            
            try {
                String query = "DELETE FROM mregistro WHERE id_usu = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, id);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    out.println("<h1>Estudiante eliminado exitosamente</h1>");
                } else {
                    out.println("<h1>No se encontró al estudiante con ID: " + id + "</h1>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h1>Error al eliminar el estudiante</h1>");
            }

            out.println("<a href='index.html'>Regresar al Formulario</a>");
            out.println("</body>");
            out.println("</html>");
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
        return "Servlet para eliminar estudiantes";
    }

    @Override
    public void destroy() {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}