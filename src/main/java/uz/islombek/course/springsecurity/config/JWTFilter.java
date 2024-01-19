package uz.islombek.course.springsecurity.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.islombek.course.springsecurity.service.JWTUtil;
import uz.islombek.course.springsecurity.service.PersonDetailService;

import java.io.IOException;

@Component
public class JWTFilter  extends OncePerRequestFilter {
    private final JWTUtil util;
    private final PersonDetailService personDetailService;

    @Autowired
    public JWTFilter(JWTUtil util, PersonDetailService personDetailService) {
        this.util = util;
        this.personDetailService = personDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization!=null && !authorization.isBlank() && authorization.startsWith("Bearer")){
            String token = authorization.substring(7);
            if (token.isBlank()){
                response.sendError(response.SC_BAD_REQUEST,
                        "Invalid JWT token in Bearer Header"); // 400 beramiz yoq bo'lsa
            }else{
                try {
                    String username = util.validateToken(token);
                    UserDetails userDetails = personDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch (JWTVerificationException e){
                    response.sendError(response.SC_BAD_REQUEST,"Invalid JWT Token");
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
