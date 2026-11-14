# ITBDA4-14 Project 2 - Code and Datasets
**Student:** R.P Molefe (EDUV4838900)

| Folder | Question | Tools | Main files |
|---|---|---|---|
| `q1_mapreduce/` | Question 1 - Keyword search (row, column) with Hadoop MapReduce | Java, Maven, Hadoop 3.3.6 | `keyword-search/` (Mapper, Reducer, Driver), `run_q1.sh`, `corpus/` |
| `q2_lda/` | Question 2 - Topic modelling of news articles (LDA) | R | `Q2_LDA.Rmd`, `Q2_LDA.html`, `news.zip` |
| `q3_webtext/` | Question 3 - WebText API: scraping, preprocessing, summarisation | R | `Q3_WebText.Rmd`, `Q3_WebText.html` |
| `q4_etl/` | Question 4 - ETL: web scraping, cleaning, PostgreSQL analysis | R, PostgreSQL | `Q4_ETL.Rmd`, `Q4_ETL.html`, `data/`, `sql/analysis.sql` |

## Running the code
* **Question 1:** `bash q1_mapreduce/run_q1.sh <keyword>` or
  `hadoop jar keyword-search/target/keyword-search-1.0.jar za.eduvos.itbda.KeywordSearchDriver <input> <output> <keyword>`
* **Questions 2-4:** run `install_packages.R` once, then knit each `.Rmd` file in RStudio.
* **Question 4.3:** import `q4_etl/data/hn_posts_clean.csv` into PostgreSQL and run `q4_etl/sql/analysis.sql`.

## Data sources
* News corpus and MapReduce test documents - supplied with the module (Matanga, 2026).
* Wikipedia, "Big data" - CC BY-SA licence.
* Hacker News front page (news.ycombinator.com) - public pages.
