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
 * @author nuwan600
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            List books = bookEntityFacade.findAll();
            
    /*        BookEntity bobj=new BookEntity();
            bobj.setIsbn("eeee");
            bobj.setLanguage("sinhala");
            bobj.setPrice(6737);
            bobj.setTitle("thrikunamalee");
            bobj.setYear(1888);
            bookEntityFacade.create(bobj);

           AuthorEntity AE=new AuthorEntity();
           AE.setName("Dilan");
           AE.setBook(bobj);
           authorEntityFacade.create(AE);
           AuthorEntity AEE=new AuthorEntity();
           AEE.setName("Guru");
           AEE.setBook(bobj);
           authorEntityFacade.create(AEE); */

                       
           
            /*
             * TODO output your page here. You may use following sample code.
             */
  
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DisplayAllBooks</title>"); 
            out.println("<link rel='stylesheet' type='text/css' href='css.css' media='screen'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='container'>");
            out.println("<h1 align='center'>Display All Books in Book Catalog</h1>");
             out.println("<div id='header'>");
           
           out.println("</div>");
           out.println("<div id='menu'> ");
    out.println("<ul>");
     out.println(" <li><a href='AddBook' title=''>Add Book</a></li>");
     out.println(" <li><a href='ListAuthors' title=''>List Authors</a></li>");
   out.println(" </ul>");
  out.println("</div>");
  out.println("<div id='content'> ");
            out.println("<div style='text-align: right;'>");
            out.println("<form method='post' action='SearchResult'>");
                out.println("<SELECT name='elementCombo'>");
                out.println("<OPTION value='Author Name'>Author</OPTION>");
                out.println("<OPTION value='ISBN'>ISBN</OPTION>");
                out.println("<OPTION value='Title'>Title</OPTION>");
                out.println("<OPTION value='BID'>Book ID</OPTION>");
                out.println("</SELECT>");
                out.println("<input type='text' name='SearchInput'>");
                 out.println("<input type='hidden' name='Selectedval' value=''>");
                out.println("<INPUT type='submit' value='Search'/></br>");
            out.println("</form>");
            out.println("</div>");
             out.println("<table>");
                out.println("<thead>");
                    out.println("<tr>");
                        out.println("<th>Book ID</th>");
                        out.println("<th>ISBN</th>");
                        out.println("<th>TITLE</th>");
                        out.println("<th>PRICE</th>");
                        out.println("<th>YEAR</th>");
                        out.println("<th>LANGUAGE</th>");
                        out.println("<th>AUTHORS</th>");
                        out.println("<th>Update Book</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                for (Iterator it = books.iterator(); it.hasNext();) {
                BookEntity elem = (BookEntity) it.next();
                out.println(" <tr>");
                out.println("<td>");
                out.println(elem.getId() + "<br /> ");
                out.println("</td>");
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
               
                out.println("<td>");
                
                Set<AuthorEntity> A_Set=elem.getAuthors();
                for (Iterator its = A_Set.iterator(); its.hasNext();) {
                AuthorEntity A_elem = (AuthorEntity) its.next();
                 out.println("<form method='post' action='DeleteAuthorServelet'>"); 
                 out.println("<input type='hidden' name='AuthorID' value='"+A_elem.getId()+"'>");
                 out.println("<input type='hidden' name='BookID' value='"+elem.getId()+"'>");
                 out.println("<input type='hidden' name='whatType' value='allBooks'>");
                out.println(A_elem.getName() + "   <br/>");
                out.println("<input type='submit' value='Delete This Author'/> ");
                out.println("</form>");
                }
                out.println("</td>");
                 out.println("<td>");
                out.println("<form method='post' action='EditBookOperation'>");
                out.println("<input type='hidden' name='BookID' value='"+elem.getId()+"'>");
                out.println("<input type='submit' value='Update'/> ");
                out.println("</form>");
                out.println("</td>");
                
                out.println(" </tr>");
                }
                out.println("</tbody>");
            out.println("</table> <br/><br/><br/><br/>");
             out.println("<div style='clear: both;'></div>");
            out.println("</div>");
            out.println("<div id='footer'> <span>Copyright © 2014 | All Rights Reserved Bookstore </span> ");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
        String BookID= request.getParameter("BookID");
         String NISBN= request.getParameter("ISBN");
        String NTitle = request.getParameter("Title");
        String NPrice = request.getParameter("Price");
        String NYear = request.getParameter("Year");
        String NLanguage = request.getParameter("Language");
        
        BookEntity B_elem = (BookEntity) bookEntityFacade.find(Long.parseLong(BookID));
        B_elem.setIsbn(NISBN);
        B_elem.setLanguage(NLanguage);
        B_elem.setPrice(Double.parseDouble(NPrice));
        B_elem.setTitle(NTitle);
        B_elem.setYear(Integer.parseInt(NYear));
        bookEntityFacade.edit(B_elem);
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
