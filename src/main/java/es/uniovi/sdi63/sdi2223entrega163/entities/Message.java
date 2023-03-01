package es.uniovi.sdi63.sdi2223entrega163.entities;

import es.uniovi.sdi63.sdi2223entrega163.entities.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "messages", uniqueConstraints = { @UniqueConstraint( columnNames = { "conversation_id", "sender_id", "time" })})
public class Message extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String message;

    private LocalDateTime time;

    public Message() {}

    public Message(User sender, Conversation conversation, String message) {
        this.message = message;
        this.time = LocalDateTime.now();
        Associations.SendMessage.link( sender, conversation, this );
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals( o )) return false;
        Message message = (Message) o;
        return Objects.equals( conversation, message.conversation ) && Objects.equals( sender, message.sender ) && Objects.equals( time, message.time );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), conversation, sender, time );
    }
}
