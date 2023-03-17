package es.uniovi.sdi63.sdi2223entrega163.entities;

import es.uniovi.sdi63.sdi2223entrega163.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "logs")
public class Log extends BaseEntity {


    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}