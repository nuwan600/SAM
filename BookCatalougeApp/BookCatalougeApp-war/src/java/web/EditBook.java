/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.BookEntity;
import ejb.BookEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
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
@WebServlet(name = "EditBook", urlPatterns = {"/EditBook"})
public class EditBook extends HttpServlet {

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
    private BookEntityFacade bookEntityFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String year = request.getParameter("year");
        String price = request.getParameter("price");
        String language = request.getParameter("language");
        String book_id = request.getParameter("book_id");

        if ((title != null) && (isbn != null)) {
            BookEntity bk_edit = (BookEntity) bookEntityFacade.find(Long.parseLong(book_id));
            bk_edit.setLanguage(language);
            bk_edit.setIsbn(isbn);
            bk_edit.setPrice(Double.parseDouble(price));
            bk_edit.setTitle(title);
            bk_edit.setYear(Integer.parseInt(year));
            bookEntityFacade.edit(bk_edit);

            response.sendRedirect("DisplayAllBooks");

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

        response.setContentType("text/html;charset=UTF-8");
        String BookIDP = request.getParameter("BookID");
        String Isbn = request.getParameter("Isbn");
        String Title = request.getParameter("Title");
        String Language = request.getParameter("Language");
        String Year = request.getParameter("Year");
        String Price = request.getParameter("Price");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateBook</title>");
            out.println("</head>");
            out.println("<body bgcolor='#DEB887'>");

            out.println("<h1 align='center'>Update Book Details</h1>");
            out.println("<hr><hr>");
            out.println("<form>");
            
            out.println("<br>");
            out.println("<input type='hidden' name='book_id' value='" + BookIDP + "'><br/>");
            out.println("<input type='hidden' name='isbn' value='" + Isbn + "'><br/>");
            out.println("<table align='center'>");
            out.println("<tr><td>Title:</td> <td><input type='text' name='title' value='" + Title + "'><td/></tr>");
            out.println("<tr><td>Year: </td> <td><input type='text' name='year' value='" + Year + "'><td/></tr>");
            out.println("<tr><td>Price: </td> <td><input type='text' name='price' value='" + Price + "'><td/></tr>");
            out.println("<tr><td>Language: </td> <td><input type='text' name='language' value='" + Language + "'><td/></tr>");
            out.println("<tr><td><input type='submit'><td/></tr>");
            out.println("</table>");
            out.println("</form>");

            out.println("</body>");
              out.println("<a href='Home'>Back To Home</a>");
            
            out.println("<br><a href='DisplayAllBooks'>Click Display All Books</a>");
            out.println("</html>");
        } finally {
            out.close();
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
