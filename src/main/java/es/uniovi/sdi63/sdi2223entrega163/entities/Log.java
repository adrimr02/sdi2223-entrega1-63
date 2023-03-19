package es.uniovi.sdi63.sdi2223entrega163.entities;

import es.uniovi.sdi63.sdi2223entrega163.entities.base.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "logs")
public class Log extends BaseEntity {

    public enum LogTypes {
        PET("PET"),
        ALTA("ALTA"),
        LOGIN_EX("LOGIN-EX"),
        LOGIN_ERR("LOGIN-ERR"),
        LOGOUT("LOGOUT");

        private String str;

        LogTypes(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogTypes type;

    @Column(nullable = false)
    private String description;

    public Log() {}

    public Log(LogTypes type, String description) {
        this.type = type;
        this.description = description;
        this.timestamp = Timestamp.from( Instant.now());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public LogTypes getType() {
        return type;
    }

    public void setType(LogTypes type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}