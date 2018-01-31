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
@WebServlet(name = "CarList", urlPatterns = {"/CL"})
public class CarList extends HttpServlet {

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
                    out.println("<input type='button' value='Add Car' onClick='addCar()'/>");
                    out.println("<table id='listDriverTabel' class='listDriver'>");
                    ///////////////////////////////////////////////////////////////
                    boolean delete = ac.checkPermission(ac.getUserId(request.getSession().getId()), "deletDriver");
                    String colDel="";
                    if(delete)
                        colDel="<td></td>";
                    ///////////////////////////////////////////////////////////////
                    WorkerSQL wsql = new WorkerSQL();
                    Map carList = wsql.carList();
                    Iterator<Map.Entry<String, Map>> entries = carList.entrySet().iterator();
                    out.println("<thead><tr><td>Гос. Номер</td><td>Модель</td><td>VIN</td><td>Год выпуска</td>"
                            + "<td>КПП</td><td>Стоимость Аренды</td></tr></thead>");
                    while (entries.hasNext()) {
                        Map.Entry<String, Map> entry = entries.next();
                        Map carData = entry.getValue();
                        out.println("<tr><td class='edit' onClick='editShow("+carData.get("id")+")'>"+
                                carData.get("number")+"</td>");
                        
                        ////////////////////////// Model ///////////////////////
                        if(carData.get("model")!=null){
                            switch(carData.get("model").toString()){
                                case "1": 
                                    out.println("<td>Kio Rio</td>");
                                    break;
                                case "2": 
                                    out.println("<td>Kio Optima</td>");
                                    break;
                            }
                        }
                        else 
                            out.println("<td>Not</td>");
                        out.println("<td>"+carData.get("VIN")+"</td>"+
                                        "<td>"+carData.get("year")+"</td>");
                        ////////////////////////////////////////////////////////
                        
                        ////////////////////////// Transmission ////////////////
                        if(carData.get("transmission")!=null){
                            switch(carData.get("transmission").toString()){
                                case "1": 
                                    out.println("<td>МКПП</td>");
                                    break;
                                case "2": 
                                    out.println("<td>АКПП</td>");
                                    break;
                            }
                        }
                        else 
                            out.println("<td>Not</td>");
                        ////////////////////////////////////////////////////////
                        out.println("<td>"+carData.get("cost")+"</td></tr>");
                    }
                    out.println("</table>");
                    //wsql.addAccrual();
                    wsql.con.close();
                }
            else{
                System.out.println("Go to login Page!");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            }
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
            Logger.getLogger(CarList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CarList.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CarList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CarList.class.getName()).log(Level.SEVERE, null, ex);
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
