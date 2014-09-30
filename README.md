Transportation Monitoring System in Vietnam
==========================

Hệ thống giám sát tình trạng an toàn giao thông


==========================
Cài đặt:
- Chạy ElasticSearch (Windows chạy bin/elasticsearch.bat)
- Chạy lệnh mvn clean install
- Chạy lệnh mvn assembly:assembly -DdescriptorId=jar-with-dependencies
- Vào thư mục target\vntransmon-release\vntransmon chạy baomoicrawler.bat

Thiết lập:
- Mở file target\vntransmon-release\vntransmon\conf\crawler.properties

+ SIZE_POOL = 100 \\ Kích thước Pool
+ NUM_THREAD = 4 \\ Số lượng tiến trình chạy song song
+ ELASTIC_SERVER = localhost \\ Địa chỉ đến Elastic server
+ INDEX_NAME = baomoi_db \\ Tên database
+ TYPE_NEWS_NAME = article \\ Tên bảng chứa các bài báo
+ BAOMOI_STARTID = 1011200 \\ ID bắt đầu thu thập trên Baomoi
+ BAOMOI_ENDID = 1012200 \\ ID kết thúc thu thập trên Baomoi

