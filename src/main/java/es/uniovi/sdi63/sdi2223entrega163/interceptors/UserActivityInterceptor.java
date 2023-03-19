package es.uniovi.sdi63.sdi2223entrega163.interceptors;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log.LogTypes;
import es.uniovi.sdi63.sdi2223entrega163.loggers.UserActivityLogger;
import es.uniovi.sdi63.sdi2223entrega163.util.ParamFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserActivityInterceptor implements HandlerInterceptor {
    private UserActivityLogger logger;

    public UserActivityInterceptor(UserActivityLogger logger) {
        this.logger = logger;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String mapping = request.getRequestURI();
        String method = request.getMethod();
        String params = ParamFormatter.format(request.getParameterMap());
        logger.log( LogTypes.PET, mapping, method, params.toString().trim() );
        return true;
    }

}