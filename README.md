# Bank Salad Pre Test 
- Get Maximum Profit Date for 180 days from today.

## - Environment
- Gradle Version: 4.10.2
- Spring Boot Version: 2.1.7.RELEASE
- Tool: IntelliJ
- Dependencies: lombok, web, thymeleaf, h2 database, spring data JPA
- Test: JUnit4
- 외부 API: Yahoofinance API (https://financequotes-api.com/) 
- Reference: 스프링 부트와 AWS로 혼자 구현하는 웹 서비스, 스프링 인 액션 4판

## - Detail
### Entity: 
- symbol(pk): 회사 심볼 
- companyName: 회사 이름 
- maxProfitDate: 최대이윤 날짜
- updatedDate: 최대이윤 갱신한 날짜. 같은 날 중복 검색 제거.

### Service: 
- getAllStocks: 모든 엔티티 호출
- saveStocks: csv 파일에서 얻은 모든 symbol, companyName 저장
- searchMaxProfit: 180일 전까지의 최대이윤 날짜 검색
- calculateMaxProfitDate: 외부 API 호출해서 최대이윤 날짜 계산

### Controller: 
- index: 메인 페이지 (검색창, 리스트 목록)
- showSymbolsAndCompanyName: symbol, company name 리스트 화면에 출력
- searchSymbol: 검색 실행, 검색 결과창 화면 전환 및 출력

### View
- index: 메인 화면
- search_result: 검색 결과 화면 

### Test
- Entity
- Service
- Controller