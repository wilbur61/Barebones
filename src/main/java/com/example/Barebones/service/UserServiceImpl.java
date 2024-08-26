package com.example.Barebones.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Barebones.model.Role;
import com.example.Barebones.model.User;
import com.example.Barebones.repo.UserRegistrationDto;
import com.example.Barebones.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

   //@Autowired
   private UserRepository userRepository;

   //@Autowired
   private BCryptPasswordEncoder passwordEncoder;

   /*
   To convert your code from using field injection (where
   dependencies are injected directly into fields using
   @Autowired) to constructor injection (where dependencies
   are passed as constructor parameters).

   Steps Involved:

  Remove @Autowired annotations: Remove the @Autowired
  annotations from the fields in your UserServiceImpl class.
  Create a constructor: Create a constructor in your UserServiceImpl
  class that takes the required dependencies as parameters.
  Assign dependencies: Within the constructor, assign the passed
  parameters to the corresponding fields.
    */
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    /*
    This method overrides the loadUserByUsername method inherited
    from UserDetailsService. It's responsible for fetching user
    details during login based on the provided email.

     Find User: It calls userRepository.findByEmail(email) to find
     the user with the matching email address.
     */
    public User findByEmail(String email){
       return userRepository.findByEmail(email);
   }

   public User save(UserRegistrationDto registration){
       User user = new User();
       user.setFirstName(registration.getFirstName());
       user.setLastName(registration.getLastName());
       user.setEmail(registration.getEmail());
       user.setPassword(passwordEncoder.encode(registration.getPassword()));
       user.setRoles(Arrays.asList(new Role("ROLE_USER")));
       return userRepository.save(user);
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(email);
       if (user == null){
           throw new UsernameNotFoundException("Invalid username or password.");
       }
       System.out.println("==============================================");
       System.out.println("IN UserServiceImpl::UserDetails::loadUserByUsername");
       System.out.println("user "+ user.toString());
       System.out.println("==============================================");
       return new org.springframework.security.core.userdetails.User(user.getEmail(),
               user.getPassword(),
               mapRolesToAuthorities(user.getRoles()));
   }

   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
       return roles.stream()
               .map(role -> new SimpleGrantedAuthority(role.getName()))
               .collect(Collectors.toList());
   }
}