package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log;
import es.uniovi.sdi63.sdi2223entrega163.entities.Log.LogTypes;
import es.uniovi.sdi63.sdi2223entrega163.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LogsController {

    @Autowired
    private LogService logService;

    @RequestMapping("/logs/list")
    public String getListadoLogs(Model model, @RequestParam(name="type", required=false) String filterType) {
        List<Log> logsList;
        if (filterType != null && !filterType.isBlank()) {
            logsList = logService.getLogsByType( LogTypes.valueOf( filterType ));
        } else {
            logsList = logService.getLogs();
        }
        model.addAttribute( "logsList", logsList );
        return "user/logs";
    }

    @RequestMapping("/logs/list/update")
    public String filterLogs(Model model, @RequestParam(name="type", required=false) String filterType) {
        List<Log> logsList;
        if (filterType != null && !filterType.isBlank()) {
            logsList = logService.getLogsByType(LogTypes.valueOf( filterType ));
        } else {
            logsList = logService.getLogs();
        }
        model.addAttribute( "logsList", logsList );
        return "fragments/logsTable";
    }

    @RequestMapping(value = "/logs/delete")
    public String deleteLogs() {
        logService.deleteAllLogs();
        return "redirect:/logs/list";
    }

}
