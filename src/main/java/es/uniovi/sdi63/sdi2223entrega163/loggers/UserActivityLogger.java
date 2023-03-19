package es.uniovi.sdi63.sdi2223entrega163.loggers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log;
import es.uniovi.sdi63.sdi2223entrega163.repositories.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class UserActivityLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserActivityLogger.class);

    @Autowired
    private LogRepository logRepository;

    public static void logActivity(String message) {
        LOGGER.info(message);
    }


    public void log(String type, String map, String method, String str) {
        String logDescription = "Map: " + map + " --Method: " + method;
        if(type=="LOGIN-EX" || type=="LOGIN-ERR" || type=="LOGIN-EX" || type=="LOGOUT"){
            logDescription += " --User: " + str;
        } else {
            if (!str.isEmpty()) {
                logDescription += " --Params: " + str;
            }
        }
        Log petitionLog = new Log();
        petitionLog.setTimestamp(Timestamp.from(Instant.now()));
        petitionLog.setType(type);
        petitionLog.setDescription(logDescription);
        logRepository.save(petitionLog);
    }


}
