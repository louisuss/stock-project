package com.dobi.stockproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StocksRepository extends JpaRepository<Stocks, String> {
}
