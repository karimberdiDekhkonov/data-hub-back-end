package com.datahub.datahub.entity;

import com.datahub.datahub.entity.template.LongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_id"})})
public class Company extends LongEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch= FetchType.LAZY)
    private User userId;
}
