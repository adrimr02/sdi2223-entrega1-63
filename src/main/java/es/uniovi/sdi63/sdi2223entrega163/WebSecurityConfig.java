package es.uniovi.sdi63.sdi2223entrega163;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log.LogTypes;
import es.uniovi.sdi63.sdi2223entrega163.loggers.UserActivityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserActivityLogger logger;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").permitAll()
                    .antMatchers( "/user/**" ).hasAuthority( "ROLE_ADMIN" )
                    .antMatchers( "/offer/**" ).hasAuthority( "ROLE_USER" )
                    .anyRequest().authenticated()
                        .and().exceptionHandling().accessDeniedHandler( new CustomAccessDeniedHandler() )
                .and()
                .formLogin()
                .loginPage("/login")
                    .failureHandler( (request, response, exception) -> {
                        logger.log( LogTypes.LOGIN_ERR,
                                request.getRequestURI(),
                                request.getMethod(),
                                request.getParameter("username") );

                        response.sendRedirect( "/login?error" );
                    } ).successHandler( (request, response, authentication) -> {
                        logger.log( LogTypes.LOGIN_EX,
                            request.getRequestURI(),
                            request.getMethod(),
                            request.getParameter("username") );

                        response.sendRedirect( "/login-success" );
                    } )
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessHandler( (request, response, authentication) -> {
                        var username = ((UserDetails) authentication.getPrincipal()).getUsername();
                        logger.log( LogTypes.LOGOUT,
                                request.getRequestURI(),
                                request.getMethod(),
                                username );

                        response.sendRedirect( "/login-success" );
                    } )
                    .permitAll();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Component
    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                           AccessDeniedException accessDeniedException) throws IOException, ServletException {
            response.sendError( HttpServletResponse.SC_FORBIDDEN, "Access denied");
        }
    }
}
