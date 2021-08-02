package com.exalt.reddit.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
public class Vote extends Auditable{

    @Id @GeneratedValue private Long id;

    @NonNull
    private short direction;

    @NonNull @ManyToOne private Link link;
}
