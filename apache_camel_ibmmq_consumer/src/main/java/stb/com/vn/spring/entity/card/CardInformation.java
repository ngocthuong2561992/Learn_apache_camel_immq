package stb.com.vn.spring.entity.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import stb.com.vn.spring.entity.base.AuditingEntity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "card_information")
public class CardInformation extends AuditingEntity {
//    @Serial
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "cif_id")
    private String cifId;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "card_type")
    private String cardType;
}