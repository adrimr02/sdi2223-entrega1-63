package es.uniovi.sdi63.sdi2223entrega163.repositories;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {

    @Query("select l from Log l order by timestamp desc")
    List<Log> getLogs();

    @Query("select l from Log l where l.type =?1 order by timestamp desc")
    List<Log> getLogsByType(String type);

}