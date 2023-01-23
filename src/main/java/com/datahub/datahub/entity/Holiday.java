package com.datahub.datahub.entity;

import com.datahub.datahub.entity.template.LongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)

public class Holiday extends LongEntity {

    @ManyToOne
    private User ownerId;

    @ManyToOne
    private User employeeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String date;
}
