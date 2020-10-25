package com.dobi.stockproject.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Entity
public class Stocks {
    @Id
    private String symbol;
    private String companyName;
    private LocalDate maxProfitDate;
    private LocalDate updatedDate;

    @Builder
    public Stocks(String symbol, String companyName) {
        this.symbol = symbol;
        this.companyName = companyName;
    }
}
