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
import java.sql.ResultSet;


/**
 *
 * @author coppel
 */
@WebServlet("/EditarEstudiante")
public class EditarEstudiante extends HttpServlet {

    private Connection con;

    public void init() throws ServletException {
        String url = "jdbc:mysql://localhost:3306/registro0101";
        String username = "root";
        String password = "po0101gh#segR";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
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
            String id = request.getParameter("id_usu");

            if (id == null || id.isEmpty()) {
                out.println("<h1>Error: Se requiere proporcionar el ID del estudiante</h1>");
                return;
            }

            try {
                String query = "SELECT * FROM mregistro WHERE id_usu=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // Datos actuales del estudiante
                    String nom_usu = rs.getString("nom_usu");
                    String appat_usu = rs.getString("appat_usu");
                    String apmat_usu = rs.getString("apmat_usu");
                    int edad_usu = rs.getInt("edad_usu");
                    String email_usu = rs.getString("email_usu");

                    // Formulario para editar con los datos actuales
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Editar Estudiante</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Editar Estudiante</h1>");
                    out.println("<form method='post'>");
                    out.println("<input type='hidden' name='id_usu' value='" + id + "'>");
                    out.println("Nombre: <input type='text' name='nom_usu' value='" + nom_usu + "'><br>");
                    out.println("Apellido Paterno: <input type='text' name='appat_usu' value='" + appat_usu + "'><br>");
                    out.println("Apellido Materno: <input type='text' name='apmat_usu' value='" + apmat_usu + "'><br>");
                    out.println("Edad: <input type='number' name='edad_usu' value='" + edad_usu + "'><br>");
                    out.println("Email: <input type='text' name='email_usu' value='" + email_usu + "'><br>");
                    out.println("<input type='submit' value='Guardar Cambios'>");
                    out.println("</form>");
                    out.println("<p><a href='index.html'>Regresar al Formulario Principal</a></p>");
                    out.println("</body>");
                    out.println("</html>");
                } else {
                    out.println("<h1>No se encontró ningún estudiante con el ID proporcionado</h1>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h1>Error al buscar el estudiante en la base de datos</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String id= request.getParameter("id_usu");
            String nom_usu = request.getParameter("nom_usu");
            String appat_usu = request.getParameter("appat_usu");
            String apmat_usu = request.getParameter("apmat_usu");
            String edad_usu_str = request.getParameter("edad_usu");
            String email_usu = request.getParameter("email_usu");

            if (id == null || nom_usu == null || appat_usu == null || apmat_usu == null || edad_usu_str == null || email_usu == null ||
                id.isEmpty() || nom_usu.isEmpty() || appat_usu.isEmpty() || apmat_usu.isEmpty() || edad_usu_str.isEmpty() || email_usu.isEmpty()) {
                out.println("<h1>Error: Todos los campos son obligatorios</h1>");
                return;
            }

            try {
                int edad_usu = Integer.parseInt(edad_usu_str);
                String query = "UPDATE mregistro SET nom_usu=?, appat_usu=?, apmat_usu=?, edad_usu=?, email_usu=? WHERE id_usu=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, nom_usu);
                ps.setString(2, appat_usu);
                ps.setString(3, apmat_usu);
                ps.setInt(4, edad_usu);
                ps.setString(5, email_usu);
                ps.setString(6, id);
                ps.executeUpdate();

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Estudiante Actualizado</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Estudiante actualizado correctamente</h1>");
                out.println("<p><a href='index.html'>Regresar al Formulario Principal</a></p>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h1>Error al actualizar el estudiante</h1>");
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}