package com.dobi.stockproject.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StocksRepositoryTest {
    @Autowired
    StocksRepository stocksRepository;

    @Test
    public void stocksSaveLoad() {
        String symbol = "AAPL";
        String companyName = "Apple";
        Stocks stocks = Stocks.builder().symbol(symbol).companyName(companyName).build();

        String strDate = "2020-09-04";
        LocalDate maxProfitDate = LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
        LocalDate currentDate = LocalDate.now();
        stocks.setMaxProfitDate(maxProfitDate);
        stocks.setUpdatedDate(currentDate);

        stocksRepository.save(stocks);

        List<Stocks> stocksList = stocksRepository.findAll();

        Stocks stock = stocksList.get(0);
        assertThat(stock.getSymbol()).isEqualTo(symbol);
        assertThat(stock.getCompanyName()).isEqualTo(companyName);
        assertThat(stock.getMaxProfitDate()).isEqualTo(maxProfitDate);
        assertThat(stock.getUpdatedDate()).isEqualTo(currentDate);
    }
}
