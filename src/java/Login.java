/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.swing.JOptionPane;
import javax.servlet.http.HttpSession;  

/**
 *
 * @author acute
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
        try 
        {
             String name   = request.getParameter("uname");
             String pwd    = request.getParameter("pwd");                
           
              // JOptionPane.showMessageDialog(null,"Yess");     
               
          try
          {               
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");                
                
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost\\ACUTE-PC\\SQLEXPRESS:1433;databaseName=cloud","crysis","reloaded22");
                
                Statement stmt = con.createStatement();                 
                                             
                ResultSet rs = stmt.executeQuery("select name,password from Register where Name ='"+name+"'and Password ='"+pwd+"' ");   
                    
                {
                    if(rs.next())                        
                        {                
                         if(name.equals(rs.getString("Name")) &&  pwd.equals(rs.getString("Password")))                                            
                    
                                    {                                        
                                            HttpSession session=request.getSession();  
                                            session.setAttribute("uname",name);                                            
                                            JOptionPane.showMessageDialog(null,"Login Successful");
                                            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");                    
                                            rd.forward(request,response);
                                    }                                                        
                        } 
                           else   
                                    {      
                                         JOptionPane.showMessageDialog(null,"Invalid UserName or Password"); 
                                         RequestDispatcher rd = request.getRequestDispatcher("index.jsp");                    
                                         rd.forward(request,response);
                                    }                                                     
                }                      
          }
                catch(Exception e)
                {
                    
                    System.out.println(e);
                }    
          
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Invalid Username or Password</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Invalid Username or Password </h1>");
            out.println("</body>");
            out.println("</html>");
        }         
        finally 
        
        {            
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
            throws ServletException, IOException
        {
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
            throws ServletException, IOException 
        {
             processRequest(request, response);
        }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
        {
             return "Short description";
        }// </editor-fold>
}
