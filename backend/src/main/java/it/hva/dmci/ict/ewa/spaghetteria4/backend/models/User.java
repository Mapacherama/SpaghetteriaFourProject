package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation for Spring Security User, linked to the database
 * Can be created from a JSON Post (Not recommended, use a UserDto instead).
 *
 * @author Sam Toxopeus
 */
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name="username", nullable = false, unique = true)
    private @JsonProperty
    String username;

    @Column(name="password", nullable = false)
    private @JsonProperty
    String password;

    @Column(name="role", nullable = false)
    private @JsonProperty String authority;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private @JsonProperty Boolean enabled;

    @JsonCreator
    public User(@JsonProperty("username")String username, @JsonProperty("password")String password, @JsonProperty("role")String authority, @JsonProperty("enabled")Boolean enabled) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.enabled = enabled;
    }

    public User(UserDto u) {
        this(u.getUsername(),u.getPassword(),u.getRole(), u.getEnabled());
    }

    public User() {

    }

    public User updateInfoFromDto(UserDto userDto) {
        authority = userDto.getRole();
        enabled = userDto.getEnabled();

        return this;
    }

    /**
     * Wrap role(s) into an array
     * @return authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(authority));
        return role;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getRole() {
        return authority;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("{ \"username\":\"%s\", \"role\":\"%s\", \"enabled\":%s }", getUsername(), getRole(), isEnabled());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * Required overriders for Spring Security, currently not part of our stack
     *
     * @return true (otherwise the accounts dont work)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

}
