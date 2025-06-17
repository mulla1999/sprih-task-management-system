package com.sprih.task.management.system.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "User_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private long organizationId;
    private long userId;
    private long roleId;
    private String roleName;
    private String fullName;
    private String firstName;
    private String lastName;
    private String screenName;
    private String middleName;
    private String email;
}