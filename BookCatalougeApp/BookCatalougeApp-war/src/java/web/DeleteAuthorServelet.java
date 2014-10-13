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
import java.util.Iterator;
import java.util.Set;
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
@WebServlet(name = "DeleteAuthorServelet", urlPatterns = {"/DeleteAuthorServelet"})
public class DeleteAuthorServelet extends HttpServlet {

    @EJB
    private BookEntityFacade bookEntityFacade;
    @EJB
    private AuthorEntityFacade authorEntityFacade;

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
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteAuthorServelet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteAuthorServelet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String AuthorID = request.getParameter("AuthorID");
        String BookID = request.getParameter("BookID");
        AuthorEntity A_elem = (AuthorEntity) authorEntityFacade.find(Long.parseLong(AuthorID));
        BookEntity B_elem = (BookEntity) bookEntityFacade.find(Long.parseLong(BookID));

        long Author_ID=Long.parseLong(AuthorID);
        authorEntityFacade.remove(A_elem);
        Set<AuthorEntity> Aet = B_elem.getAuthors();
        Iterator<AuthorEntity> iterator = Aet.iterator();
        while (iterator.hasNext()) {
            AuthorEntity AE = iterator.next();
            if (AE.getId() == Author_ID) {
                iterator.remove();
            }
        }            
            B_elem.setAuthors(Aet);
            bookEntityFacade.edit(B_elem);
            response.sendRedirect("DisplayAllBooks");
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>
    }
