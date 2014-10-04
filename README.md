Transportation Monitoring System in Vietnam
==========================

Hệ thống giám sát tình trạng an toàn giao thông


==========================
Cài đặt:
- Chạy ElasticSearch (Windows chạy bin/elasticsearch.bat)
- Chạy lệnh mvn clean install
- Chạy lệnh mvn assembly:assembly -DdescriptorId=jar-with-dependencies
- Vào thư mục target\vntransmon-release\vntransmon
  + Sử dụng crawler chạy baomoicrawler.bat
  + Sử dụng feeder chạy baomoifeeder.bat

Thiết lập:
- Mở file target\vntransmon-release\vntransmon\conf\crawler.properties
  + SIZE_POOL = 100 \\ Kích thước Pool
  + NUM_THREAD_FETCHER = 4 \\ Số lượng tiến trình chạy crawler
  + ELASTIC_SERVER = localhost \\ Địa chỉ đến Elastic server
  + INDEX_NAME = baomoi_db \\ Tên database
  + TYPE_NEWS_NAME = article \\ Tên bảng chứa các bài báo
  + BAOMOI_STARTID = 1011200 \\ ID bắt đầu thu thập trên Baomoi
  + BAOMOI_ENDID = 1012200 \\ ID kết thúc thu thập trên Baomoi
  + NUM_THREAD_FEEDER = 4 \\ Số lượng tiến trình chạy feeder
  + BAOMOI_REFRESH_TIME = 5 \\ Thời gian quay lại trang cần feed
