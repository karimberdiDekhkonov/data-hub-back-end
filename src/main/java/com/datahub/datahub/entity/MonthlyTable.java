package com.datahub.datahub.entity;

import com.datahub.datahub.entity.template.LongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity

public class MonthlyTable extends LongEntity {

    @ManyToOne
    private User ownerId;

    @OneToOne
    private User employeeId;

    @Column(nullable = false)
    private int hours;

    @Column(nullable = false)
    private int salary;

    @Column(nullable = false)
    private String month;
}
