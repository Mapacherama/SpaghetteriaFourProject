package it.hva.dmci.ict.ewa.spaghetteria4.backend.services;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.UserDto;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service for interacting with User and UserRepository.
 *
 * @author Sam Toxopeus
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(PasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Transforms a DTO into a User and saves it
     * @param userDto - The user to save
     * @return the saved User
     */
    public boolean saveDto(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) == null) {
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            save(new User(userDto));
            if (userRepository.findByUsername(userDto.getUsername()) != null) return true;
        }
        return false;
    }

    public boolean updateInfoDto(UserDto userDto) {
        User u = userRepository.findByUsername(userDto.getUsername());
        if (u != null) {
            save(u.updateInfoFromDto(userDto));
            return userRepository.findByUsername(userDto.getUsername()) != null;
        }
        return false;
    }

    public boolean updatePasswordDto(UserDto userDto) {
        User u = userRepository.findByUsername(userDto.getUsername());
        if (u != null) {
            u.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            return save(u) != null;
        }
        return false;
    }

    /**
     * Saves a user in DB
     * If the user already exists, it is updated.
     * Otherwise it is inserted as new.
     *
     * @param u - The user to save
     * @return saved version of user
     */
    private User save(User u) {
        return userRepository.save(u);
    }

    /**
     * Returns user supplied by username, not case sensitive.
     *
     * @param username
     * @return user with specified username
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    /**
     * Returns all enabled branches, branch is defined as user that does not have admin-rights.
     *
     * @return list of branches
     */
    public List<User> getAllActiveBranches() {
        List<User> users = getAllBranches();
        users.removeIf(u -> !u.isEnabled());
        return users;
    }

    /**
     * Returns all branches, branch is defined as user that does not have admin-rights.
     *
     * @return list of branches
     */
    public List<User> getAllBranches() {
        List<User> users = userRepository.findAll();
        users.removeIf(u -> u.getRole().equalsIgnoreCase("ADMIN"));
        return users;
    }

    public List<User> getAllUsers(boolean activeOnly, boolean adminOnly) {
        if (activeOnly && adminOnly) return userRepository.findAllByEnabledAndAuthority(true,"ADMIN");
        else if (adminOnly) return userRepository.findAllByAuthority("ADMIN");
        else if (activeOnly) return userRepository.findAllByEnabled(true);
        return userRepository.findAll();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
