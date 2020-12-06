package de.aljoshavieth.kartenverkauf;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "TicketManager", urlPatterns = "/ticketmanager")
public class TicketManager extends HttpServlet {

    final Logger logger = Logger.getLogger("TicketManager");
    Kartenverkauf kartenverkauf = new Kartenverkauf();


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formName = request.getParameter("formName");
        String seatAsString = request.getParameter("seat");
        String name = request.getParameter("name");
        System.out.println("FORMNAME                  ---- " + formName);
        if(!isValidNumber(seatAsString)){
            //TODO open error page
            System.out.println("NOT A VALID NUMBER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return;
        }
        int seat = Integer.parseInt(seatAsString)-1;
        try {
            switch (formName) {
                case "sellTicket":
                    kartenverkauf.sellTicket(seat);
                    break;
                case "reserveTicket":
                    kartenverkauf.reserveTicket(seat, name);
                    break;
                case "sellReservedTicket":
                    kartenverkauf.sellReservedTicket(seat, name);
                    break;
                case "cancelTicket":
                    kartenverkauf.cancelTicket(seat);
                    break;
                case "cancelAllReservations":
                    kartenverkauf.cancelAllTicketReservations();
                    break;
                default:
                    forwardToErrorPage(request, response, "Unknown error");
            }
            response.sendRedirect(request.getContextPath() + "/success.html");
        } catch (TicketException e){
            forwardToErrorPage(request, response, e.getMessage());
        }



    }



    private boolean isValidNumber(String stringToCheck) {
        if(stringToCheck.length() >8){
            return false;
        } else {
            return stringToCheck.matches("-?\\d+");
        }
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        forwardToPage(request, response, "/error.jsp");
    }
}
