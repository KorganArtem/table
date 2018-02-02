/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.leasicar.authorization.AccessControl;
import ru.leasicar.workerSql.WorkerSQL;

/**
 *
 * @author korgan
 */
@WebServlet(name = "LD", urlPatterns = {"/LD"})
public class ListDriver extends HttpServlet {

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
        AccessControl ac = new AccessControl();
        if(ac.isLogIn(request.getSession().getId())){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<meta http-equiv='Cache-Control' content='no-cache'>");
                out.println("<table id='listDriverTabel' class='listDriver'>");
                ///////////////////////////////////////////////////////////////
                boolean delete = ac.checkPermission(ac.getUserId(request.getSession().getId()), "deletDriver");
                String colDel="";
                if(delete)
                    colDel="<td></td>";
                ///////////////////////////////////////////////////////////////
                WorkerSQL wsql = new WorkerSQL();
                Map listDriver = wsql.listDriver();
                Iterator<Map.Entry<String, Map>> entries = listDriver.entrySet().iterator();
                out.println("<thead><tr><td>Фамилия</td><td>Имя</td><td>Номер</td><td>Номер</td><td>Телефон</td><td>Лимит</td>"
                        + "<td>Баланс</td><td>Депозит</td><td class='noPrint'> </td><td class='noPrint'></td><td class='noPrint'></td><td class='noPrint'></td>"+colDel+"</tr></thead>");
                while (entries.hasNext()) {
                    Map.Entry<String, Map> entry = entries.next();
                    Map draverData = entry.getValue();
                    String day_off="";
                    if(draverData.get("dayOff")==null || draverData.get("dayOff").equals("1"))
                        day_off="<img src='img/day_off.png'/>";
                    else
                        day_off="<img src='img/wrk.png'/>";
                    String colorRow = "white";
                    if(Integer.parseInt((String) draverData.get("driver_current_debt"))<0)
                        colorRow="yellow";
                    if(Integer.parseInt((String) draverData.get("driver_current_debt"))<Integer.parseInt((String) draverData.get("driver_limit"))*-1)
                        colorRow="red";
                    String delButton = "";
                    if(delete)
                        delButton="<td onClick='delDriver("+entry.getKey()+")'>x</td>";
                    out.println("<tr class="+colorRow+">"
                            + "<td id='listDriverFirstName"+entry.getKey()+"'>"+draverData.get("driver_lastname")+"</td>"
                            + "<td id='listDriverLastName"+entry.getKey()+"'>"+draverData.get("driver_firstname")+"</td>"
                            + "<td id='listDriverCarNamber"+entry.getKey()+"'>"+draverData.get("driver_carnumber")+"</td>"
                            + "<td id='listDriverCarNamber"+entry.getKey()+"'>"+draverData.get("id_car")+"</td>"
                            + "<td>"+draverData.get("driver_phone_number")+"</td>"
                            + "<td>"+draverData.get("driver_limit")+"</td>"
                            + "<td>"+draverData.get("driver_current_debt")+"</td>"
                            + "<td>"+draverData.get("driver_deposit")+"<img onClick='addDeposit("+entry.getKey()+")' src='img/add.png'/></td>"
                            + "<td class='takeMoney noPrint' onClick='takePay("+entry.getKey()+")')><img src='img/takeMoney.png'/></td>"
                            + "<td class='editDriver noPrint' onClick='editDriver("+entry.getKey()+")')><img src='img/edit.png'/></td>"
                            + delButton        
                            + "<td class='wrkday noPrint'>"+day_off+"</td>"
                            + "<td class='wrkday noPrint' onClick='getReport("+entry.getKey()+")'>отчет</td>"
                                    + "</tr>");
                }
                out.println("</table>");
                //wsql.addAccrual();
                wsql.con.close();
            }
        }
        else{
            System.out.println("Go to login Page!");
            request.getRequestDispatcher("/").forward(request, response);
            return;
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
            Logger.getLogger(ListDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListDriver.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ListDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListDriver.class.getName()).log(Level.SEVERE, null, ex);
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
