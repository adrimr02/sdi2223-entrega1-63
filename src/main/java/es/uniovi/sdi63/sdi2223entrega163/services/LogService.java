package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.LogRepository;
import es.uniovi.sdi63.sdi2223entrega163.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Log> logs = new ArrayList<Log>();
        logRepository.findAll().forEach(logs::add);
        return logs;
    }

    public void deleteUsers(){
        logRepository.deleteAll();
    }

}
