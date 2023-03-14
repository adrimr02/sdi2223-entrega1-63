package es.uniovi.sdi63.sdi2223entrega163.entities;

import es.uniovi.sdi63.sdi2223entrega163.entities.base.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "OFFERS", uniqueConstraints = { @UniqueConstraint( name = "OFFER_UQ", columnNames = {"title", "seller_id"}) })
public class Offer extends BaseEntity {

    public enum OfferState {

        AVAILABLE("offer.state.available"),

        //RESERVED("offer.state.reserved"),

        SOLD("offer.state.sold");

        private String str;

        OfferState(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }

    }

    private String title;

    private String details;

    private LocalDateTime date;

    private double price;

    private String imgPath;

    @Enumerated(EnumType.STRING)
    private OfferState state;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "offer")
    private Set<Conversation> conversations = new HashSet<>();

    public Offer(){}

    public Offer(String title, String details, double price, User seller) {
        this.title = title;
        this.details = details;
        this.price = price;
        this.state = OfferState.AVAILABLE;
        this.imgPath = "images/defaultImg.jpg";
        Associations.CreateOffer.link( seller, this );
    }

    public void buy(User buyer) throws IllegalStateException {
        if (buyer.equals( this.seller ))
            throw new IllegalStateException("You cannot buy your own offer");

        if (this.state != OfferState.AVAILABLE)
            throw new IllegalStateException("Offer is no available");

        if (buyer.getWallet() < this.price)
            throw new IllegalStateException("You don't have enough money");

        Associations.BuyOffer.link( buyer, this );
        buyer.setWallet( buyer.getWallet() - this.price );
        this.state = OfferState.SOLD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OfferState getState() {
        return state;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", state=" + state +
                ", seller=" + seller.getFullName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals( o )) return false;
        Offer offer = (Offer) o;
        return Objects.equals( title, offer.title ) && Objects.equals( seller, offer.seller );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), title, seller );
    }
}
