/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import ejb.AuthorEntity;
import ejb.BookEntity;
import ejb.BookEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
@WebServlet(name = "AddBook", urlPatterns = {"/AddBook"})
public class AddBook extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
//    @Resource(mappedName="jms/NewMessageBookFactory") 
//    private ConnectionFactory connectionFactory; 
//    
//    @Resource(mappedName="jms/NewMessageBook") 
//    private Queue queue;
    
     @EJB
    private BookEntityFacade bookEntityFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String year = request.getParameter("year");
        String price = request.getParameter("price");
        String language = request.getParameter("language");
       
        
        
        if ((title != null) && (isbn != null)) {
            
               
                BookEntity e = new BookEntity();
               
                e.setLanguage(language);
                e.setIsbn(isbn);
                e.setPrice(Double.parseDouble(price));
                e.setTitle(title);
                e.setYear(Integer.parseInt(year));
                
                bookEntityFacade.create(e);

                response.sendRedirect("AddAuthor");

            
        }
            PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            request.getRequestDispatcher("/UI/Submenu.jsp").include(request, response);
            
            out.println("<body bgcolor='#F4F4F4'>");
            out.println("<h1 align='center'>Add New Book</h1>");
            out.println("<form>");
            out.println("<table align='center'>");
            out.println("<tr><td>ISBN: </td><td><input type='text' name='isbn'></td></tr>");
            out.println("<tr><td>Title:</td><td> <input type='text' name='title'></td></tr>");
            out.println("<tr><td>Year: </td><td><input type='text' name='year'></td></tr>");
            out.println("<tr><td>Price: </td><td><input type='text' name='price'></td></tr>");
            out.println("<tr><td>Language: </td><td><input type='text' name='language'></td></tr>");
            
            out.println("<tr><td><input type='submit'></td></tr>");
            out.println("</table>");
            out.println("</form>");
            
            
        }finally {            
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
