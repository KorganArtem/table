/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.leasicar.authorization.AccessControl;
import ru.leasicar.workerSql.DriverSQL;
import ru.leasicar.workerSql.CarSQL;

/**
 *
 * @author korgan
 */
@WebServlet(name = "ED", urlPatterns = {"/ED"})
public class EditDriver extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            AccessControl ac = new AccessControl();
            if(ac.isLogIn(request.getSession().getId())){
                int driverId = Integer.parseInt(request.getParameter("driverId"));
                DriverSQL dsql = new DriverSQL();
                CarSQL csql = new CarSQL();
                Map dataDriver = dsql.getAllDataDriver(driverId);
                String editClick = "onClick='editDriverSend("+dataDriver.get("driver_id")+")'";
                out.println("<form class='addDriverForm'>");
                out.println("<div class='formItem'><label>Имя</label><br><input type='text' id='driver_name' value='"+dataDriver.get("driver_firstname")+"'/></div>");
                out.println("<div class='formItem'><label>Фамилия</label><br><input type='text' id='driver_lastname' value='"+dataDriver.get("driver_lastname")+"'/></div>");
                out.println("<div class='formItem'><label>Позывной</label><br><input type='text' id='driver_callsign' value='"+dataDriver.get("driver_callsign")+"' /></div>");
                out.println("<div class='formItem'><label>Номер машины</label><br>"
                        + "<select id='carId' >");
                        /*+ "<input type='text' id='driver_carnumber' value='"+dataDriver.get("driver_carnumber")+"' />"*/
                out.println(csql.getFreeCarList(Integer.parseInt(dataDriver.get("driver_id").toString())));
                out.println("</select></div>"); 
                out.println("<div class='formItem'><label>Лимит</label><br><input type='text' id='driver_limit' value='"+dataDriver.get("driver_limit")+"' /></div>");  
                out.println("<div class='formItem'><label>Номер телефона</label><br><input type='text' id='driver_phone_number' value='"+dataDriver.get("driver_phone_number")+"' /></div>"); 
                if(ac.checkPermission(ac.getUserId(request.getSession().getId()), "editRent")){     
                    out.println("<div class='formItem'><label>Аренда</label><br><input type='text' id='driver_day_rent' value='"+dataDriver.get("driver_day_rent")+"' /></div>"); 
                    editClick =  "onClick='editDriverSendRP("+dataDriver.get("driver_id")+")'"; 
                    out.println("<div class='formItem'><label>График</label><br><select id='driver_schedule'>"+getOptions((String)dataDriver.get("driverDayOffPeriod"))+ "</select></div>");
                }
                out.println("<div class='formItem'><br><input type='button' "+editClick+" value='Изменить' />");              
                out.println("<input type='button' id='cancelEditDriver' value='Отмена' onClick='clearEditForm()' /></div>");
                out.println("</form>");
            }
            else{
                System.out.println("Go to login Page!");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            }
            ac.con.close();
        }
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
            Logger.getLogger(EditDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditDriver.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditDriver.class.getName()).log(Level.SEVERE, null, ex);
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

    private String getOptions(String dayOff) {
        String options = "";
        switch(dayOff){
            case "0": 
                options = "<option value='0' selected>Без выходных</option><option value='11'>10/1</option>";
                break;
            case "11": 
                options = "<option value='0'>Без выходных</option><option value='11' selected>10/1</option>";
                break;
        }
        return options;
    }

}
