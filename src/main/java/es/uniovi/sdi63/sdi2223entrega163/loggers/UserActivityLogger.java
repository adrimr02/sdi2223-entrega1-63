package es.uniovi.sdi63.sdi2223entrega163.loggers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log;
import es.uniovi.sdi63.sdi2223entrega163.entities.Log.LogTypes;
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


    public void log(LogTypes type, String map, String method, String params) {
        if (type == LogTypes.PET) {
            generalLog( map, method, params );
        } else {
            authLog(type, map, method, params);
        }
    }

    private void authLog(LogTypes type, String map, String method, String params) {
        String logDescription = "";
        if (type == LogTypes.ALTA)
            logDescription += "Map: " + map + " --Method: " + method;

        logDescription += " --User: " + params;
        logRepository.save(new Log(type, logDescription));
    }

    private void generalLog(String map, String method, String params) {
        String logDescription = "Map: " + map + " --Method: " + method;
        if (!params.isBlank()) {
            logDescription += " --Params: " + params;
        }
        logRepository.save(new Log(LogTypes.PET, logDescription));
    }

}
