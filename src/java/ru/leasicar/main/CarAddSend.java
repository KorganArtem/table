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
import ru.leasicar.workerSql.CarSQL;

/**
 *
 * @author Artem
 */
@WebServlet(name = "CarAddSend", urlPatterns = {"/CAS"})
public class CarAddSend extends HttpServlet {

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
        
        AccessControl ac = new AccessControl();
        if(!ac.isLogIn(request.getSession().getId()))
            return;
        int userId = ac.getUserId(request.getSession().getId());
        try (PrintWriter out = response.getWriter()) {
            String carNumber = request.getParameter("carNumber");
            String carModel = request.getParameter("carModel");
            String carVIN = request.getParameter("carVIN");
            String carTransmission = request.getParameter("carTransmission");
            String carYear = request.getParameter("carYear");
            String carCost = request.getParameter("carCost");
            String carGlanasId = request.getParameter("carGlanasId");
            CarSQL wrk = new CarSQL();
            wrk.addCar(carNumber, carVIN,  carModel,  carTransmission,
            carYear, carCost, carGlanasId);
            System.out.println("INFO: Car added bu user "+userId+" ("
                    +" carNumber="+carNumber
                    +" carModel="+carModel
                    +" carVIN="+carVIN
                    +" carTransmission="+carTransmission
                    +" carYear="+carYear
                    +" carCost="+carCost
                    +" carGlanasId="+carGlanasId);
            
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
            Logger.getLogger(CarAddSend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CarAddSend.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CarAddSend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CarAddSend.class.getName()).log(Level.SEVERE, null, ex);
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
