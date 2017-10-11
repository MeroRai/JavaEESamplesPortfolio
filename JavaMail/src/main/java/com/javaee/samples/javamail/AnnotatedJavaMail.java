/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaee.samples.javamail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.MailSessionDefinition;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Susan Rai
 */
@WebServlet(name = "", urlPatterns = {"/AnonnatedJavaMail"})
@MailSessionDefinition(name = "java:comp/anonnatedJavaMail",
        host = "smtp.gmail.com",
        transportProtocol = "smtps",
        properties = {
            "mail.debug=true",})
public class AnnotatedJavaMail extends HttpServlet {

    @Resource(name = "java:comp/anonnatedJavaMail")
    private Session mailSession;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Annotated Session Example</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Simple JavaMail Annotated Session Example </h1>");

            String fromEmail = request.getParameter("formmailid");
            String password = request.getParameter("password");
            String toEmail = request.getParameter("tomailid");
            String subject = request.getParameter("subject");
            String emailMessage = request.getParameter("msg");

            Message message = new MimeMessage(mailSession);
            message.setSubject(subject);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setText(emailMessage);
            
            Transport transport = mailSession.getTransport();
            transport.connect(fromEmail, password);
            transport.sendMessage(message, message.getAllRecipients());
            
            out.println("<p> Message Sent SuccessFully to : " + toEmail + "</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (MessagingException ex) {
            Logger.getLogger(AnnotatedJavaMail.class.getName()).log(Level.SEVERE, null, ex);
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
