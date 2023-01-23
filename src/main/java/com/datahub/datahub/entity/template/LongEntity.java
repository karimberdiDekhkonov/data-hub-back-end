package com.datahub.datahub.entity.template;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class LongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
