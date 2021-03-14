package com.ironhack.business.models;

import com.ironhack.business.configurations.converter.MoneyConverter;
import com.ironhack.business.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.javamoney.moneta.Money;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Date;


@EnableAutoConfiguration
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @NotNull
    private String title;
    @Lob
    @NotNull
    private String description;
    @Lob
    @NotNull
    private String requirements;
    @Convert(converter = MoneyConverter.class)
    @Digits(integer = 19, fraction = 4)
    private Money salaryAmountMin;
    @Convert(converter = MoneyConverter.class)
    @Digits(integer = 19, fraction = 4)
    private Money salaryAmountMax;
    @Lob
    private String location;
    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.OPENED;
    @ManyToOne
    private ContractType contractType;
    @ManyToOne
    private Workday workday;
    @ManyToOne
    private RequestedProfessionalExperience requestedProfessionalExperience;
    @ManyToOne
    private YearsOfExperience yearsOfExperience;
    @ManyToOne
    private Business business;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;

}
