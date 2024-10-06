package com.hllolluni.payment_service.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("payments")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    private String id;
    private Long userId;
    private String firstName;
    private String email;
    private Long created;
    private Boolean valid;

    public Payment(Long userId, String firstName, String email, Long created, Boolean valid) {
        this.userId = userId;
        this.firstName = firstName;
        this.email = email;
        this.created = created;
        this.valid = valid;
    }
}
