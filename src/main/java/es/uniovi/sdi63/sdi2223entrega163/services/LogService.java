package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log;
import es.uniovi.sdi63.sdi2223entrega163.entities.Log.LogTypes;
import es.uniovi.sdi63.sdi2223entrega163.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @PostConstruct
    public void init() {
    }

    public List<Log> getLogs(){
        return logRepository.getLogs();
    }

    public List<Log> getLogsByType(LogTypes type){
        return logRepository.getLogsByType(type);
    }

    @Transactional
    public void deleteAllLogs(){
        logRepository.deleteAll();
    }

}
