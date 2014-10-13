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
@WebServlet(name = "SearchBook", urlPatterns = {"/SearchBook"})
public class SearchBook extends HttpServlet {

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
    @EJB
    private AuthorEntityFacade authorEntityFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String title = request.getParameter("title");
        String year = request.getParameter("year");
        String author = request.getParameter("author_id");
        String language = request.getParameter("language");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
           request.getRequestDispatcher("/UI/Submenu.jsp").include(request, response);
           
            out.println("<body bgcolor='#F4F4F4'>");
            out.println("<h1 align='center'>Search Books</h1>");
            out.println("<form>");
            out.println("<table align='center'>");

            out.println("<tr><td><input type='radio' name='search' value='title'>Search By Title</td>");

            out.println("<td><select name='title' id='title'>");
            out.println("<option value='--SELECT--'>--SELECT--</option>");
            List books = bookEntityFacade.findAll();
            for (Iterator it = books.iterator(); it.hasNext();) {
                BookEntity elem = (BookEntity) it.next();
                out.println("<option value='" + elem.getId() + "'>" + elem.getTitle() + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");

            out.println("<tr><td><input type='radio' name='search' value='year'>Search By Year</td>");

            out.println("<td><select name='year' id='year'>");
            out.println("<option value='--SELECT--'>--SELECT--</option>");
            List bookYears = bookEntityFacade.findAll();
            for (Iterator it = bookYears.iterator(); it.hasNext();) {
                BookEntity elem = (BookEntity) it.next();
                out.println("<option value='" + elem.getId() + "'>" + elem.getYear() + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");

            out.println("<tr><td><input type='radio' name='search' value='author'>Search By Author</td>");

            out.println("<td><select name='author_id' id='author_id'>");
            out.println("<option value='--SELECT--'>--SELECT--</option>");
            List authors_list = authorEntityFacade.findAll();
            for (Iterator it = authors_list.iterator(); it.hasNext();) {
                AuthorEntity elem = (AuthorEntity) it.next();
                out.println("<option value='" + elem.getId() + "'>" + elem.getName() + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");
            
            out.println("<tr><td><input type='radio' name='search' value='language'>Search By Language</td>");

            out.println("<td><select name='language' id='language'>");
            out.println("<option value='--SELECT--'>--SELECT--</option>");
            List book_lang = bookEntityFacade.findAll();
            for (Iterator it = book_lang.iterator(); it.hasNext();) {
                BookEntity elem = (BookEntity) it.next();
                out.println("<option value='" + elem.getId() + "'>" + elem.getLanguage() + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");

            out.println("<tr><td colspan='2'><br /><input type='submit'></td></tr>");
            out.println("</table>");

            if ((title != null) || (year != null) || (author != null)|| (language != null)) {
                System.out.println("BOOKID==" + title);
                System.out.println("YEAR==" + year);
                String key = null;
                if ((!title.equalsIgnoreCase("--SELECT--")) && (year.equalsIgnoreCase("--SELECT--")) && (author.equalsIgnoreCase("--SELECT--"))&& (language.equalsIgnoreCase("--SELECT--"))) {

                    key = title;
                } else if ((title.equalsIgnoreCase("--SELECT--")) && (!year.equalsIgnoreCase("--SELECT--")) && (author.equalsIgnoreCase("--SELECT--"))&& (language.equalsIgnoreCase("--SELECT--"))) {

                    key = year;
                } else if ((title.equalsIgnoreCase("--SELECT--")) && (year.equalsIgnoreCase("--SELECT--")) && (!author.equalsIgnoreCase("--SELECT--"))&& (language.equalsIgnoreCase("--SELECT--"))) {

                    AuthorEntity getAuthor = (AuthorEntity) authorEntityFacade.find(Long.parseLong(author));
                    Long book_id_by_author = getAuthor.getBook().getId();
                    key = String.valueOf(book_id_by_author);

                }else if ((title.equalsIgnoreCase("--SELECT--")) && (year.equalsIgnoreCase("--SELECT--")) && (author.equalsIgnoreCase("--SELECT--")) && (!language.equalsIgnoreCase("--SELECT--"))) {

                    key = language;
                }
                BookEntity bk_search = (BookEntity) bookEntityFacade.find(Long.parseLong(key));

                out.println("<table align='center' border='2'>");
                out.println("<tr><th>ISBN</th><th>Title</th><th>Price</th><th>Year</th><th>Language</th><th>Authors</th></tr>");
                out.println("<tr><td>" + bk_search.getIsbn() + "</td><td>" + bk_search.getTitle() + "</td><td>" + bk_search.getPrice() + "</td><td>" + bk_search.getYear() + "</td><td>" + bk_search.getLanguage() + "</td>");
                out.println("<td>");

                Set<AuthorEntity> A_Set = bk_search.getAuthors();
                for (Iterator its = A_Set.iterator(); its.hasNext();) {
                    AuthorEntity A_elem = (AuthorEntity) its.next();
                    out.println(A_elem.getName() + " <br>  ");

                }
                out.println("</td></tr>");
                out.println("</table>");
            }
           

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
