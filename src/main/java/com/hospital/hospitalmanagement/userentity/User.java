package com.hospital.hospitalmanagement.userentity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp  // ✅ sets value automatically at insert
    private LocalDateTime createdAt;

    @UpdateTimestamp   // ✅ updates value automatically on update
    private LocalDateTime updatedAt;

    @Builder.Default
    private int status = 1;  // ✅ default to 1 if not explicitly set

    public enum Role {
        DOCTOR,
        PATIENT
    }
}
