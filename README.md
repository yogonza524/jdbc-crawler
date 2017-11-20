# jdbc-crawler
Simple Crawler for get data from a URL through CSS selector into PostgreSQL Database

# Pre-requisites
1. PostgreSQL Database Server (installed or remote connection)
2. Maven (3.0+)
3. Java 7 (JDK 1.7) installed

# Installation

Under project folder execute
```bash
mvn install
```
Then you could crawl a website 

```bash
java -jar target/crawl-news-0.0.1-SNAPSHOT-jar-with-dependencies.jar -t postgresql -h localhost -d crawler -u postgres -p root -port 5432 -l 2 -url [URL_LINK] -tag [CSS_SELECTOR_WHITOUT_SPACES]
```
