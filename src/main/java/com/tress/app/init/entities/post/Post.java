package com.tress.app.init.entities.post;

import com.tress.app.init.entities.user.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="post_id")
    private int id;
    @Column(name="name")
    @NotEmpty(message = "*Please provide a name")
    private String name;
    @Column(name="body")
    @NotEmpty(message = "*Please provide a body")
    private String body;
    @Column(name="createdDate")
    private Date createdDate;
    @Column(name="urlImage")
    private String urlImage;
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "user_post", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
