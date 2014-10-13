/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.AuthorEntity;
import ejb.AuthorEntityFacade;
import ejb.BookEntity;
import ejb.BookEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
@WebServlet(name = "AddAuthor", urlPatterns = {"/AddAuthor"})
public class AddAuthor extends HttpServlet {

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
    @EJB
    private BookEntityFacade bookEntityFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JMSException {
        response.setContentType("text/html;charset=UTF-8");

        // String aid=request.getParameter("aid");
        String aname = request.getParameter("aname");
        String book_id = request.getParameter("book_id");

        if ((aname != null) && (book_id != "--SELECT--")) {

            // create BookEntity, that will be sent in JMS message
            BookEntity b_elem = (BookEntity) bookEntityFacade.find(Long.parseLong(book_id));

            AuthorEntity a = new AuthorEntity();

            a.setName(aname);
            a.setBook(b_elem);

            Set<AuthorEntity> a_set = b_elem.getAuthors();
            a_set.add(a);
            b_elem.setAuthors(a_set);
            bookEntityFacade.edit(b_elem);

            response.sendRedirect("DisplayAllBooks");

        }
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            request.getRequestDispatcher("/UI/Submenu.jsp").include(request, response);

            out.println("<body bgcolor='#F4F4F4'>");
            out.println("<h1 align='center'>Add Author</h1>");
            out.println("<form>");
            out.println("<table align='center'>");

            out.println("<tr><td>Book:</td><td>");
            out.println("<select name='book_id' id='book_id'>");
            out.println("<option value='--SELECT--'>--SELECT--</option>");
            List books = bookEntityFacade.findAll();
            for (Iterator it = books.iterator(); it.hasNext();) {
                BookEntity elem = (BookEntity) it.next();
                out.println("<option value='" + elem.getId() + "'>" + elem.getTitle() + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");

            out.println("<tr><td>Name:</td><td ><input type='text' name='aname'></td></tr>");
            out.println("<tr><td colspan='2'><br /><input type='submit'></td></tr>");
            out.println("</table>");
            out.println("</form>");
          
            
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
        try {
            processRequest(request, response);
        } catch (JMSException ex) {
            Logger.getLogger(AddAuthor.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JMSException ex) {
            Logger.getLogger(AddAuthor.class.getName()).log(Level.SEVERE, null, ex);
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
