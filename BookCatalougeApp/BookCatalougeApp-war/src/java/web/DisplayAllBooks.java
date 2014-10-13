/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.AuthorEntity;
import ejb.AuthorEntityFacade;
import ejb.BookEntity;
import ejb.BookEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
@WebServlet(name = "DisplayAllBooks", urlPatterns = {"/DisplayAllBooks"})
public class DisplayAllBooks extends HttpServlet {
    @EJB
    private AuthorEntityFacade authorEntityFacade;
    @EJB
    private BookEntityFacade bookEntityFacade;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            List books = bookEntityFacade.findAll();
            /*
             * TODO output your page here. You may use following sample code.
             */
            request.getRequestDispatcher("/UI/Submenu.jsp").include(request, response);
           out.println("<body bgcolor='#F4F4F4'>");
            out.println("<h1 align='center'>Display All Books</h1>");
            
            
             out.println("<table align = 'center' border = 1>");
                out.println("<thead>");
                    out.println("<tr>");
                        out.println("<th>ISBN</th>");
                        out.println("<th>TITLE</th>");
                        out.println("<th>PRICE</th>");
                        out.println("<th>YEAR</th>");
                        out.println("<th>LANGUAGE</th>");
                        out.println("<th>AUTHORS</th>");
                        out.println("<th>UPDATE BOOK</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                for (Iterator it = books.iterator(); it.hasNext();) {
                BookEntity elem = (BookEntity) it.next();
                out.println(" <tr>");
                
                out.println("<td>");
                out.println(elem.getIsbn() + "<br /> ");
                out.println("</td>");
                out.println("<td>");
                out.println(elem.getTitle() + "<br /> ");
                out.println("</td>");
                out.println("<td>");
                out.println(elem.getPrice() + "<br /> ");
                out.println("</td>");
                out.println("<td>");
                out.println(elem.getYear() + "<br /> ");
                out.println("</td>");
                out.println("<td>");
                out.println(elem.getLanguage() + "<br /> ");
                out.println("</td>");
                out.println("<td><div>");
                
                Set<AuthorEntity> A_Set=elem.getAuthors();
                for (Iterator its = A_Set.iterator(); its.hasNext();) {
                AuthorEntity A_elem = (AuthorEntity) its.next();
                 out.println("<form method='post' action='DeleteAuthorServelet'>"); 
                 out.println("<input type='hidden' name='AuthorID' value='"+A_elem.getId()+"'>");
                 out.println("<input type='hidden' name='BookID' value='"+elem.getId()+"'>");
                out.println(A_elem.getName() + "   ");
                out.println("<input type='submit' value='Delete'/> ");
                out.println("</form>");
                }
                out.println("</div></td>");
                 out.println("<td><div>");

                 out.println("<form method='post' action='EditBook'>");
                   
                        out.println("<input type='hidden' name='BookID' value='" + elem.getId() + "'>");
                        out.println("<input type='hidden' name='Isbn' value='" + elem.getIsbn()+ "'>");
                        out.println("<input type='hidden' name='Title' value='" + elem.getTitle()+ "'>");
                        out.println("<input type='hidden' name='Language' value='" + elem.getLanguage()+ "'>");
                        out.println("<input type='hidden' name='Year' value='" + elem.getYear()+ "'>");
                        out.println("<input type='hidden' name='Price' value='" + elem.getPrice()+ "'>");
                        
                        out.println("<input type='submit' value='Update Book'/> ");
                    
                    
                    out.println("</form>");
                

                out.println("</div></td>");
                
                
                out.println(" </tr>");
                }
                out.println("</tbody>");
           
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
