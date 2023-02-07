package com.camelmultidb.repository.mssql.entity;

import com.camelmultidb.enumerator.Status;
import com.camelmultidb.enumerator.StatusConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "rental_new")
public class RentalNewEntity implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
    @SequenceGenerator(name = "rental_seq", sequenceName = "rental_seq", allocationSize = 50)
    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "rental_date")
    private Date rentalDate;

    @Column(name = "inventory_id")
    private Integer inventoryId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "status")
    @Convert(converter = StatusConverter.class)
    private Status status;

}
