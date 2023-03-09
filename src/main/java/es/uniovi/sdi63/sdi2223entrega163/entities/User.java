package es.uniovi.sdi63.sdi2223entrega163.entities;

import es.uniovi.sdi63.sdi2223entrega163.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usersList")
public class User extends BaseEntity {

    @Column(unique = true)
    private String email;

    private String name;

    private String lastname;

    private String password;

    private String role;

    private double wallet;

    @Transient
    private String passwordConfirm;

    @OneToMany(mappedBy = "seller")
    private Set<Offer> createdOffers = new HashSet<>();

    @OneToMany(mappedBy = "buyer")
    private Set<Offer> buyedOffers = new HashSet<>();

    @OneToMany(mappedBy = "buyer")
    private Set<Conversation> conversations = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<Message> messages = new HashSet<>();

    public User(){}

    public User(String email, String name, String lastname) {
        this(email, name, lastname, "ROLE_USER");
    }

    public User(String email, String name, String lastname, String role) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.wallet = 100f;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Offer> getCreatedOffers() {
        return createdOffers;
    }

    public void setCreatedOffers(Set<Offer> createdOffers) {
        this.createdOffers = createdOffers;
    }

    public Set<Offer> getBuyedOffers() {
        return buyedOffers;
    }

    public void setBuyedOffers(Set<Offer> buyedOffers) {
        this.buyedOffers = buyedOffers;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getFullName() {
        return name + " " + lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals( o )) return false;
        User user = (User) o;
        return Objects.equals( email, user.email );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), email );
    }
}
