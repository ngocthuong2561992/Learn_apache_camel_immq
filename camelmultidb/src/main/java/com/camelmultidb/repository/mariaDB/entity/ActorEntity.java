package com.camelmultidb.repository.mariaDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@OptimisticLocking(type = OptimisticLockType.DIRTY)
//@DynamicUpdate
@Entity
@Table(name = "actor")
//@NamedEntityGraph(
//    name = "ActorEntity",
//    attributeNodes = {
//        @NamedAttributeNode("films"),
//    }
//)
public class ActorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Integer actorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Version
    @JsonIgnore
    @Column(name = "version")
    private Integer version;

    @ManyToMany(
        fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    @JsonManagedReference
    private List<FilmEntity> films;

    public void addFilms(List<FilmEntity> data) {
        if(films == null) {
            films = new ArrayList<>();
        }
        films.addAll(data);
    }

}
