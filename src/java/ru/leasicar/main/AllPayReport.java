/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.leasicar.authorization.AccessControl;
import ru.leasicar.workerSql.DriverSQL;
import ru.leasicar.workerSql.ReportSQL;

/**
 *
 * @author korgan
 */
@WebServlet(name = "DriverPayReport", urlPatterns = {"/AllPayReport"})
public class AllPayReport extends HttpServlet {

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
                DriverSQL dsql = new DriverSQL();
                int operatorId = 0;
                String begin = request.getParameter("begin");
                String end = request.getParameter("end");
                try{
                    operatorId = Integer.parseInt(request.getParameter("operatorId"));
                }
                catch(Exception ex){
                    System.out.println("operator ID is not passed! "+request.getParameter("operatorId"));
                }
                ReportSQL rsql = new ReportSQL();
                Map payList = new TreeMap<>(rsql.getAllPayList(operatorId, begin, end));
                out.println("<table id='driverReport' class='report'>");
                out.println("<thead><tr><td>Дата</td><td>Тип</td><td>Источник</td><td>Сумма</td><td>Оператор</td><td>Водитель</td></tr></thead>");
                Iterator<Map.Entry<String, Map>> entries = payList.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Map> entry = entries.next();
                    Map payRaw = entry.getValue();
                    out.println("<tr><td>"+payRaw.get("date")+"</td><td>"+payRaw.get("payTypeName")
                            +"</td><td>"+payRaw.get("payName")+"</td><td>"+payRaw.get("sum")
                            +"</td><td>"+payRaw.get("user")+"</td>" 
                            +"</td><td>"+payRaw.get("driver")+"</td></tr>");
                }
                out.println("</table><div>");
                Map<String, HashMap> payGroup = rsql.getGroupPayByOperator(operatorId, begin, end);
                Set keys = payGroup.keySet();
                for(Object key : keys){
                    out.println(payGroup.get(key).get("payName")+"  "+ payGroup.get(key).get("sum") +"<br>");
                }
                out.println("</div></div>");
                rsql.con.close();

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
            Logger.getLogger(AllPayReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AllPayReport.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AllPayReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AllPayReport.class.getName()).log(Level.SEVERE, null, ex);
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
