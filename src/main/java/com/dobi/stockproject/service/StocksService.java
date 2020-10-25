package com.dobi.stockproject.service;

import com.dobi.stockproject.domain.Stocks;
import com.dobi.stockproject.domain.StocksRepository;

import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.transaction.Transactional;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StocksService {
    private StocksRepository stocksRepository;

    public StocksService(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    public List<Stocks> getAllStocks() {
        return stocksRepository.findAll();
    }

    // csv 파일 통해 symbol company name 생성
    public void saveStocks() throws IOException {
        File csv = new File("/Users/louis/Downloads/stock-project/src/main/resources/static/symbols_list.csv");
        BufferedReader br = new BufferedReader(new FileReader(csv));
        String csvLine;

        while (br.readLine() != null) {
            csvLine = br.readLine();
            String[] tokens = csvLine.replaceAll(", |\"", "").split(",");
            String symbol = tokens[0];
            String companyName = tokens[1];

            try {
                this.stocksRepository.save(Stocks.builder().symbol(symbol).companyName(companyName).build());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    // StocksService 인터페이스 만들어서 구현 시 예외처리 상속 문제 때문에 외부 API 활용에 제약이 생김.
    // 컨트롤러에서 넘겨준 심볼을 통해 야후 API 참조해서 최대이윤날짜 계산
    public Stocks searchMaxProfit(String symbol) throws IOException {
        try {
            // 대문자로 변환
            symbol = symbol.toUpperCase();

            // 생성된 entity 없는 경우, entity 생성.
            if (!stocksRepository.existsById(symbol)) {
                saveStocks();
            }

            // 검색한 symbol 해당하는 symbol 있는지 검색
            Optional<Stocks> optional = stocksRepository.findById(symbol);
            Stocks stocks;
            if (optional.isPresent()) {
                stocks = optional.get();
            } else {
                // 1. 예외처리로 테스트 통과 가능
//                throw new IllegalArgumentException(symbol + " is NOT FOUND! Please check the symbols again! ");
//           2. return null; -> test 통과 못함.
                return null;
            }

            // 현재 시간 기준으로 같은 날이면 maxProfitDate 검색 결과가 같을 것이므로 기존의 검색 결과 활용
            // 포맷: yyyy-MM-dd
            LocalDate currentDate = LocalDate.now();

            if (stocks.getUpdatedDate() != null) {
                if (stocks.getUpdatedDate().isEqual(currentDate)) {
                    return stocks;
                }
            }
            // maxProfitDate 계산
            return calculateMaxProfitDate(symbol, stocks);
        } catch (NullPointerException e) {
            e.printStackTrace(); // entity 생성 과정관련 에러처리
        }
        return null;
    }

    public Stocks calculateMaxProfitDate(String symbol, Stocks stocks) throws IOException {
        // maxProfitDate 없거나 날짜가 다른 경우 검색 실행
        // 외부 API 호출
        Stock stock = YahooFinance.get(symbol);

        // 현재 기준 180일 이전의 날짜들에서 최대 이윤 계산
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, -180);

        List<HistoricalQuote> historicalQuotes = stock.getHistory(startDate, Interval.DAILY);

        // 데이터 포맷 변경
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        double maxProfit = 0.0;
        Calendar maxProfitDate = Calendar.getInstance();

        for (HistoricalQuote historicalQuote : historicalQuotes) {
            double nowMaxPrice = historicalQuote.getHigh().doubleValue();
            double nowLowPrice = historicalQuote.getLow().doubleValue();
            double nowProfit = nowMaxPrice - nowLowPrice;

            if (nowProfit > maxProfit) {
                maxProfit = nowProfit;
                maxProfitDate.setTime(historicalQuote.getDate().getTime());
            }
        }

        // Calendar -> LocalDate 타입 변경
        LocalDate date = LocalDate.parse(df.format(maxProfitDate.getTime()), DateTimeFormatter.ISO_DATE);
        stocks.setMaxProfitDate(date);
        stocks.setUpdatedDate(LocalDate.now());

        return stocks;
    }
}
