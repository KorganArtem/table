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
import java.util.TreeMap;
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
@WebServlet(name = "DriverPayReport", urlPatterns = {"/DriverPayReport"})
public class DriverPayReport extends HttpServlet {

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
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<link rel='stylesheet' type='text/css'  href='css/datatabel.css'/>");
                out.println("<link rel='stylesheet' type='text/css' href='css/main.css'/>");
                out.println("<script src='https://code.jquery.com/jquery-1.12.4.js'></script>");
                out.println("<script type='text/javascript' src='https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js'></script>");
                out.println("</head>");
                out.println("<body><div id='driverReportDiv'>");
                int driverId = 0;
                try{
                    driverId = Integer.parseInt(request.getParameter("driverId"));
                }
                catch(Exception ex){
                    System.out.println("Driver ID is not passed!");
                }
                /* TODO output your page here. You may use following sample code. */
                if(driverId==0)
                    return;
                WorkerSQL wsql = new WorkerSQL();
                //Map payList = wsql.getPayList(driverId);
                Map payList = new TreeMap<>(wsql.getPayList(driverId));
                out.println("<table id='driverReport' class='report'>");
                out.println("<thead><tr><td>Дата</td><td>Тип</td><td>Источник</td><td>Сумма</td></tr></thead>");
                Iterator<Map.Entry<String, Map>> entries = payList.entrySet().iterator();
                String yandex="";
                String gett="";
                String uber="";
                String card="";
                String macdack="";
                String discount="";
                String fromDeposit="";
                while (entries.hasNext()) {
                    Map.Entry<String, Map> entry = entries.next();
                    Map payRaw = entry.getValue();
                    int type = Integer.parseInt((String) payRaw.get("type"));
                    String typePay = "";
                    switch(type){
                        case 1: 
                            typePay = "внесение";
                                break;
                        case 2: 
                            typePay = "Списание аренды";
                                break;
                        case 3: 
                            typePay = "Пополнение депозита";
                                break;
                    }
                    int source = Integer.parseInt((String) payRaw.get("source"));
                    String sourcePay = "";
                    switch(source){
                        case 1: 
                            sourcePay = "Наличными";
                                break;
                        case 2: 
                            sourcePay = "Яндекс";
                                break;
                        case 3: 
                            sourcePay = "Gett";
                                break;
                        case 4: 
                            sourcePay = "Uber";
                                break;
                        case 5: 
                            sourcePay = "На карту";
                                break;
                        case 6: 
                            sourcePay = "С депозита";
                        case 7: 
                            sourcePay = "Макдональд";
                                break;
                        case 8: 
                            sourcePay = "Скидка";
                                break;
                    }
                    out.println("<tr><td>"+payRaw.get("date")+"</td><td>"+typePay+"</td><td>"+sourcePay+"</td><td>"+payRaw.get("sum")+"</td></tr>");
                }
                out.println("</table>");
                out.println("<script src='js/main.js'></script>");
                out.println("<script>$('#driverReport').DataTable()</script>");
                out.println("</div></body></html>"); 
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
            Logger.getLogger(DriverPayReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DriverPayReport.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DriverPayReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DriverPayReport.class.getName()).log(Level.SEVERE, null, ex);
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
