package CouponsProject3.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


// + Order of the things:
// + every request the server get, firstly go to the filter, he check if he can continue or not between the checks is to see if this request hold a token. if he can continue he goes to "Servlet dispatcher", this Object belong to spring web and his gol is to navigate every request to the current controller by request method and url.
@Component
@Order(2) // +run AFTER CorsFilter
public class JwtFilter extends OncePerRequestFilter { // + OncePerRequestFilter - is class abstract of spring web and he can do on filet per time and not many filters on the same request like other class of filter.
    private final List<String> activeTokens;

    public JwtFilter(List<String> activeTokens) {
        this.activeTokens = activeTokens;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException { //this method must define . she is gets request from the website and return response if the client have token.
        try { // to see Header of Authorization enter to: inspect/network/request of "getAllToys"/ Header/Authorization.
            String token = request.getHeader("Authorization").replace("Bearer ", ""); // we take from the request the Header "authorization" that contain the token if the client hold a token, else he will return null. and take the redundant word "Bearer" and delete it.
            if (activeTokens.contains(token)) {
                DecodedJWT decodedRequestToken = JWT.decode(token);// (token original) entering to method decodedRequestToken to get the information clean, if the token we gets is not a valid token it will throw exception.
                // check info in token:
                String existToken = activeTokens.stream().filter(a -> (Objects.equals(a, token))).findFirst().toString();
                DecodedJWT decodedExistToken = JWT.decode(existToken);
                if (decodedRequestToken.getIssuer().equals("Coupons_company") && decodedRequestToken.getClaim("role").asString().equals(decodedExistToken.getClaim("role").asString())) {
                    if (decodedRequestToken.getClaim("role").asString().equals("ADMIN") && request.getServletPath().startsWith("admin"))
                        // all is well, move on
                        filterChain.doFilter(request, response);
                }// + move to next filter on the chain ,and send those two params. if this the last filter he will send the request to the dispatcher, the role of the dispatcher - to sort all the request to the current controller.
            } else {
                response.setStatus(401);
                response.getWriter().write("Unauthorized, please log in!");
            }
        } catch (Exception e) {
            // something is wrong with JWT - ERROR
            response.setStatus(401);
            response.getWriter().write("Unauthorized, please log in!"); // send to client message...
        }
    }

    //    TODO: add the login/sign up to the ignore list
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) { // + if the client send request of login or sign in we do not filtered it, and we right away send him to ("/users") and there he will get a token
        return request.getServletPath().startsWith("/admin_controller/login")
                || request.getServletPath().startsWith("/company_controller/login")
                || request.getServletPath().startsWith("/customer_controller/login");
    }
}