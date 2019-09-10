package Triping.models;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "enabled")
    private boolean enabled;

    private String username;
    private String password;
    private String email;
    private byte[] userImage;
    private String salt;

    public User(){
        super();
        this.enabled = false;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() { return salt; }

    public void setSalt(String salt) { this.salt = salt; }

    public byte[] getUserImage() { return userImage; }

    public void setUserImage(byte[] userImage) { this.userImage = userImage; }

    public String getEmail() {return email; }

    public void setEmail(String email) {this.email = email;}
}
