package CouponsProject3.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // allow access to all origin paths like eny website in the web.
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, DELETE, POST, PUT, HEAD"); // allow requests methods, OPTION - contains every other request and go with the preflight.
        response.setHeader("Access-Control-Allow-Headers", "authorization, Origin, Accept, content-type, Access-Control-Request-Method, Access-Control-Request-Headers"); // allowing here witch type of header are allowed in our app, those header are not a default headers.
        if(request.getMethod().equals("OPTIONS")) // if the request is "preflight" send accepted and after that the client will send another request from type GET, POST...
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        else
            // move to next filter on the chain ,and send those two params . if this the last filter he will send the request to the dispatcher, the role of the dispatcher - to sort all the request to the current controller.
            filterChain.doFilter(request, response);
    }
}