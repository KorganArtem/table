/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.leasicar.authorization.AccessControl;

/**
 *
 * @author korgan
 */
@WebServlet(name = "AddDriver", urlPatterns = {"/AddDriver"})
public class AddDriver extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        AccessControl ac = new AccessControl(); 
        if(ac.isLogIn(request.getSession().getId())){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta http-equiv='Cache-Control' content='no-cache'>");
                out.println("<meta http-equiv='Cache-Control' content='private'>");
                //out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
                out.println("<script src='https://code.jquery.com/jquery-1.12.4.js'></script>");
                out.println("<script type='text/javascript' src='https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js'></script>");
                //out.println("<link rel='stylesheet' type='text/css' href='https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.css'/>");
                out.println("<link rel='stylesheet' type='text/css'  href='css/main.css'/>");
                out.println("<link rel='stylesheet' type='text/css'  href='css/datatabel.css'/>");
                out.println("<title>Servlet AddDriver</title>");            
                out.println("</head>");
                out.println("<body><div class='place'>");
                out.println("<div id='formDriver'><form class='addDriverForm'>");
                out.println("<h3>Добавление водителя</h3>");
                out.println("<div class='formItem'><label>Имя</label><br><input type='text' id='driver_name' /></div>");
                out.println("<div class='formItem'><label>Фамилия</label><br><input type='text' id='driver_lastname' /></div>");
                out.println("<div class='formItem'><label>Позывной</label><br><input type='text' id='driver_callsign' /></div>");
                out.println("<div class='formItem'><label>Номер машины</label><br><input type='text' id='driver_carnumber' /></div>"); 
                out.println("<div class='formItem'><label>Лимит</label><br><input type='text' id='driver_limit' /></div>");  
                out.println("<div class='formItem'><label>Номер телефона</label><br><input type='text' id='driver_phone_number' /></div>");    
                out.println("<div class='formItem'><label>Суточная Аренда</label><br><input type='text' id='driver_day_rent' /></div>");     
                out.println("<div class='formItem'><label>График</label><br><select id='driver_schedule'>"
                        + "<option value='0'>Без выходных</option>"
                        + "<option value='11'>10/1</option>"
                        + "</select></div>");              
                out.println("<div class='formItem'><br><input type='button' id='driver_add' value='Добавить' /></div>");
                out.println("</form></div>");
                out.println("<div id='clickBox'><input id='carListButton' type='button' value='Car list'/><input id='driverListButton' type='button' disabled='true' value='Driver list'/></div>");
                out.println("<div id='mainContainer'>"
                        + "<div id='listDriver'></div>"
                        + "<div id='carList'></div>"
                        + "</div>");
                out.println("<div id='editDriver'></div>");
                out.println("<div id='takePay'>"
                        + "<form class='takePayForm'>"
                        + "<input type='text' style='display: none' disabled id='typePay'/><br>"
                        + "<input type='text' disabled id='takePayDriverId'/><br>"
                        + "<input type='text' disabled id='takePayDriverName'/><br>"
                        + "<input type='text' id='takePayDriverSum'/><br>"
                        + "<select id='takePayDriverType'><br>"
                        + "<option value='1'>Наличными</option>"
                        + "<option value='2'>Яндекс</option>"
                        + "<option value='3'>Gett</option>"
                        + "<option value='4'>Uber</option>"
                        + "<option value='5'>На карту</option>"
                        + "<option id='fromDeposit' value='6'>С депозита</option>"
                        + "<option id='fromDeposit' value='7'>Макдональс</option>"
                        + "<option id='fromDeposit' value='8'>Скидка</option>"
                        + "</select>"
                        + "<input type='button' id='takePayButton' value='Принять'/>"
                        + "</form></div>");
                out.println("</div><div id='modal_form'><!-- Сaмo oкнo --> \n" +
                            "      <span id='modal_close'>X</span> <!-- Кнoпкa зaкрыть --> \n" +
                            "      <!-- Тут любoе сoдержимoе -->\n" +
                            "</div>\n" +
                            "<div id='overlay'></div</body>");
                out.println("<script src='js/main.js'></script>");
                out.println("<script>listDriverShow()</script>");
                out.println("</html>"); 
            }   
        }
        else{
            System.out.println("Go to login Page!");
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        ac.con.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
