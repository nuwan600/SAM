/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import ejb.AuthorEntity;
import ejb.AuthorEntityFacade;
import ejb.BookEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
@WebServlet(name = "DisplayAllAuthors", urlPatterns = {"/DisplayAllAuthors"})
public class DisplayAllAuthors extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @EJB
    private AuthorEntityFacade authorEntityFacade;
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       
        try {
            /* TODO output your page here. You may use following sample code. */
            List authors_list = authorEntityFacade.findAll();
           request.getRequestDispatcher("/UI/Submenu.jsp").include(request, response);
           out.println("<body bgcolor='#F4F4F4'>");
            out.println("<h1 align ='center'>Display All Authors</h1>");
           
             out.println("<table align ='center' border = 1>");
                out.println("<thead>");
                    out.println("<tr>");
                        out.println("<th>AUTHOR NAME</th>");
                        out.println("<th>BOOK WRITTEN</th>");             
                out.println("</tr>");
                out.println("</thead>");
                
                out.println("<tbody>");
                for (Iterator it = authors_list.iterator(); it.hasNext();) {
                AuthorEntity elem = (AuthorEntity) it.next();
                out.println(" <tr>");
                
                out.println("<td>");
                out.println(elem.getName() + "<br /> ");
                out.println("</td>");
                
                out.println("<td>");
                out.println(elem.getBook().getTitle() + "<br /> ");
                out.println("</td>");
                out.println("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                
            
        } finally {
            out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
