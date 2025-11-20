package com.financetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Schema(
        description = "User email address - must be unique",
        example = "john.doe@email.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @NotBlank
    @Comment("Hashed user password")
    @Schema(
        description = "Hashed password - min 6 characters",
        example = "password123",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 6)
    @JsonIgnore
    private String password;

    @NotBlank
    @Column(name = "first_name")
    @Comment("User first name")
    @Schema(
        description = "User first name",
        example = "John",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Comment("User last name")
    @Schema(
        description = "User last name",
        example = "Doe",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String lastName;

    @Column(columnDefinition = "jsonb")
    private String settings = "{\"currency\": \"USD\", \"locale\": \"en\"}";

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> transactions = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();
}
