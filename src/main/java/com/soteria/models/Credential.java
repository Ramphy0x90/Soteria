package com.soteria.models;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    private com.soteria.models.Entity entity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String userName;
    private String password;

    public Credential() {}

    public Credential(User user, com.soteria.models.Entity entity, String userName, String password) {
        this.user = user;
        this.entity = entity;
        this.userName = userName;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public com.soteria.models.Entity getEntity() {
        return entity;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setEntity(com.soteria.models.Entity entity) {
        this.entity = entity;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id=" + id +
                ", entity=" + entity +
                ", user='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
