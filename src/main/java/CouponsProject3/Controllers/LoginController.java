//package CouponsProject3.Controllers;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import org.example.simpletoysserver.beans.User;
//import org.example.simpletoysserver.repositories.UserRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@RestController
//@RequestMapping("users")
//public class LoginController {
//    private UserRepository userRepository;
//    private List<String> activeTokens;
//
//
//    public LoginController(UserRepository userRepository, List<String> activeTokens) {
//        this.userRepository = userRepository;
//        this.activeTokens = activeTokens;
//    }
//
//    @GetMapping
//    public void logout(String token){
//        activeTokens.remove(token);
//    }
//
//    @PostMapping
//    public String signUp(@RequestBody User user) {
//        userRepository.save(user);
//        return "User " + user.getUsername() + " added";
//    }
//
//    @PostMapping("login")
//    public String login(String username, String password) {
//        User user = userRepository.findByUsernameAndPassword(username, password).orElseThrow(()->new NoSuchElementException("User not found"));
//        String token = createToken(user);
//        activeTokens.add(token);
//        return token;
//    }
//
//    private String createToken(User user) {
//        Date expires = new Date(2026, Calendar.NOVEMBER,10);
//        return JWT.create() // + create token, pay attention: token can be cracked it's not safe to save inside him a password. when you are create a token , JWT will take all the info and will convert it to "Hash" and after this you can get this hash and reconvert him to info.
//                .withIssuer("HaimNemirovski") // + define the author of the token, usually is the name of the company
//                .withIssuedAt(new Date()) // + define when the token created, those two params are necessaries.
//                .withClaim("username" , user.getUsername()) // + this method is for metadata inside the token and built with the method of "key, value" . from this line and so on, those methods are not necessaries.
//                .withClaim("role", "Customer") // + can create a bunch of metadata.
//                .withClaim("avatar", "https://mui.com/static/images/avatar/3.jpg") // + crating a picture of avatar, the link should reference to folder "images" in the server.
//                .withExpiresAt(expires) // + define expire date of token like after 30 minutes from the moment the server didn't get request from this user.
//                .sign(Algorithm.none()); // + sign the token, inside the braces "()" you need enter algorithm of the encryption, you have built in already, if you choose taking algorithm you need define password and in client side some logic that unencrypted it. you can define (Algorithm.non()) and in the server it will not be encrypted only in client beside the user, for him, it will be encrypted.
//                // + remember me - in login component of many companies : save the token also to the next session next day.
//                // + GWT.io - search in google to decode the encryption of the token.
//                // + the number after the name of the algorithm is defined the size of the characters in the encryption, as big so hard to crack
//
//    }
//}
