package com.datahub.datahub.entity;

import com.datahub.datahub.entity.template.LongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class DailyTable extends LongEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private IndividualTable individualTableId;

    @Column(nullable = false)
    private String workingHours;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private int tip;

    @Column(nullable = false)
    private String tipReason;

    @Column(nullable = false)
    private int penalty;

    @Column(nullable = false)
    private String penaltyReason;

    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    private int day;
}
