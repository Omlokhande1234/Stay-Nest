package com.StayNest.StayNest.Entity;


import com.StayNest.StayNest.Entity.Enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_user")
//Here we have used table annotation and provided table name to it as if not it would have taken
//our class name as default table name,i.e user but as the postgresql already consists of the
//user table it  will throw us a error.So to avoid this we have given this table a name as
//app_user
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


}
