package com.financetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email", unique = true),
    @Index(name = "idx_user_uuid", columnList = "uuid", unique = true),
    @Index(name = "idx_user_created", columnList = "createdAt")
})
@Comment("System users table")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @NotBlank
    @Email
    @Column(unique = true)
    @Comment("User email address (unique)")
    private String email;

    @NotBlank
    @Comment("Hashed user password")
    private String password;

    @NotBlank
    @Column(name = "first_name")
    @Comment("User first name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Comment("User last name")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("List of user transactions")
    private List<Transaction> transactions = new ArrayList<>();
}
