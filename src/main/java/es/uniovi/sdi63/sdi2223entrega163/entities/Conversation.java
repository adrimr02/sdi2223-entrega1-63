package es.uniovi.sdi63.sdi2223entrega163.entities;

import es.uniovi.sdi63.sdi2223entrega163.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "conversations", uniqueConstraints = { @UniqueConstraint( columnNames = { "buyer_id", "offer_id" }) })
public class Conversation extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @OneToMany(mappedBy = "conversation")
    private Set<Message> messages;

    public Conversation() {}

    public Conversation(User buyer, Offer offer) {
        Associations.OpenConversation.link( buyer, offer, this );
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals( o )) return false;
        Conversation that = (Conversation) o;
        return Objects.equals( buyer, that.buyer ) && Objects.equals( offer, that.offer );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), buyer, offer );
    }
}
