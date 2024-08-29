package com.task_userservice.entity;

import com.task_userservice.constant.CommonEntityInterface;
import com.task_userservice.entity.Enum.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role  implements CommonEntityInterface<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RoleName name;

    @Override
    public String getName() {
        return name.name();
    }
    // Getters and Setters
}


