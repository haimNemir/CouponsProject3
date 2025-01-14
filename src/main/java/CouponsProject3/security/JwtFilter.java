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
import java.util.HashMap;

// + Order of the things:
// + every request the server get, firstly go to the filter, he check if he can continue or not between the checks is to see if this request hold a token. if he can continue he goes to "Servlet dispatcher", this Object belong to spring web and his gol is to navigate every request to the current controller by request method and url.
@Component
@Order(2) // +run AFTER CorsFilter
public class JwtFilter extends OncePerRequestFilter { // + OncePerRequestFilter - is class abstract of spring web and he can do on filet per time and not many filters on the same request like other class of filter.
    private final HashMap<String, String> activeTokens;

    public JwtFilter(HashMap<String, String> activeTokens) {
        this.activeTokens = activeTokens;
    }

    //TODO: 8/10 - check if here we redirect every client to his current controller (probably yes). and check if all as well here.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException { //this method must define . she is gets request from the website and return response if the client have token.
        try {
            // to see Header of Authorization enter to: inspect/network/request of "getAllCoupons"/ Header/Authorization.
            // we get the encoded token from request and remove the "Bearer" word from him:
            String encodedTokenFromRequest = request.getHeader("Authorization").replace("Bearer ", ""); // we take from the request the Header "authorization" that contain the token if the client hold a token, else he will return null. and take the redundant word "Bearer" and delete it.
            //we check if the token from the request exist in our list as !!value!! :
            if (activeTokens.containsValue(encodedTokenFromRequest)) {

                //we decoded the token from the request:
                DecodedJWT decodedTokenFromRequest = JWT.decode(encodedTokenFromRequest);// if the token does not exist he will throw exception.
                // check info in token:
                // gets encoded token from the list that equals to token from the request:
                String existTokenInTheList = activeTokens.values().stream().filter(value -> value.equals(encodedTokenFromRequest)).findFirst().orElse("");// we create new list of the values from the HashMap, and we use stream on it to find the exist token inside the "activeTokens". // we added .orElse()- so the value that returned is string and not <Optional>.
                //we decode the existing token from the list:
                DecodedJWT decodedExistToken = JWT.decode(existTokenInTheList);
                //Here we check what type of customer it is:
                //Admin:
                if (decodedTokenFromRequest.getClaim("role").asString().equals("Administrator") && request.getServletPath().startsWith("/admin_controller") || request.getServletPath().startsWith("/users/logout")) {// toString will not work here because he will return "Claim{value=Customer}" and not "Customer", and "asString()" will return the value himself of the Claim.
                    if (decodedTokenFromRequest.getIssuer().equals(decodedExistToken.getIssuer()))
                        filterChain.doFilter(request, response);
                } else if (decodedTokenFromRequest.getClaim("role").asString().equals("Company") && request.getServletPath().startsWith("/company_controller") || request.getServletPath().startsWith("/users/logout")) {
                    if (decodedTokenFromRequest.getIssuer().equals(decodedExistToken.getIssuer()))
                        filterChain.doFilter(request, response);
                } else if (decodedTokenFromRequest.getClaim("role").asString().equals("Customer") && request.getServletPath().startsWith("/customer_controller") || request.getServletPath().startsWith("/users/logout")) {
                    if(decodedTokenFromRequest.getIssuer().equals(decodedExistToken.getIssuer())) {

                        filterChain.doFilter(request, response);
                    }
                }
            } else {
                response.setStatus(401);
                response.getWriter().write("Unauthorized, please log in!");


            }
        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized, please log in!"); // send to client message...
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) { // + if the client send request of login or sign in we do not filtered it, and we right away send him to ("/users") and there he will get a token
        return request.getServletPath().startsWith("/users/login")
                || request.getServletPath().startsWith("//swagger-ui/index.html");
    }
}

//TODO:6/10- add a full compare between token from request and token from list, and also do some order in this class