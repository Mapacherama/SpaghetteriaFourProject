package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for User
 *
 * @author Sam Toxopeus
 */
public class UserDto {
    private @JsonProperty String username;
    private @JsonProperty String password;
    private @JsonProperty String role;
    private @JsonProperty Boolean enabled;

    @JsonCreator
    public UserDto(String username, String password, String role, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
