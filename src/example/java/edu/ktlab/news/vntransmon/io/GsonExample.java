package edu.ktlab.news.vntransmon.io;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;

public class GsonExample {
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		
		NewsRawDocument document = new NewsRawDocument();
		document.setTitle("Tuổi thơ khốn khó của chàng trai Việt cứu cháu bé Đài Loan");
		document.setSummary("\"Như mọi lần, Công dặn dò mẹ giữ gìn sức khỏe chờ ngày về trả nốt số nợ còn lại. Không ngờ đó là lần cuối cùng tôi nghe giọng nó\", bà Thoa nghẹn ngào kể về cậu con trai dũng cảm.");
		document.setDate("26/09/2014 19:45");
		document.setContent("Chàng trai Việt hi sinh cứu cháu bé Đài Loan Đang câu cá thì phát hiện cháu bé chới với dưới sông, Công liền nhảy xuống dòng nước xiết cứu cháu nhỏ. Đưa được cháu lên bờ, anh bị cuốn trôi do kiệt sức.Cuộc điện thoại cuối cùng Chiều 26/9, gần 2 tuần từ lúc hay tin, bà Nguyễn Thị Thoa vẫn ngồi thất thần, rớm nước mắt trong ngôi nhà mới ở làng quê Kim Thái (huyện Vụ Bản, Nam Định) khi có người hỏi thăm về Trần Ngọc Công. Cậu con trai duy nhất trong gia đình bà hi sinh ngày 13/9 sau khi cứu một cháu bé Đài Loan bị đuối nước ở thành phố Đài Nam. Toàn bộ anh chị em trong gia đình cùng bà con lối xóm đã bắt đầu dựng rạp chuẩn bị ngày đưa hài cốt của anh Công. Dù sự việc đã xảy ra được 2 tuần nhưng gia đình bà Nguyễn Thị Thoa (65 tuổi, mẹ đẻ anh Công) chưa nhận được thông tin chính thức về sự cố của con trai. Bà Thoa cùng toàn bộ người thân trong gia đình cũng chưa lập ban thờ cũng như di ảnh. Gia đình chỉ mới dựng một rạp nhỏ trước nhà làm nơi cho bà con lối xóm, bạn bè tới thăm hỏi và chia buồn. Theo bà Thoa, thì gia đình nhận được hung tin từ chiều tối ngày 13/9 từ một người bạn của con trai. Bà Nguyễn Thị Thoa (65 tuổi, mẹ đẻ Công) đau xót kể lại. Ảnh: Hoàn Nguyễn. Công làm đầu bếp cho một ông chủ bên ngoài. Người quen tại Đài Loan thông báo, thứ 7 ngày 13/9, Công được nghỉ và đi câu cá tại một con đập gần nơi làm việc. Khoảng 13h30 cùng ngày, Công phát hiện một bé trai khoảng 7 tuổi ngã xuống dưới dòng nước chảy xiết. Vứt cần câu tại chỗ, Công lao mình xuống dòng nước sâu hơn 5m cứu người. Cùng lúc đó có một người đàn ông mang quốc tịch Đài Loan cũng chạy đến. Tuy nhiên, khi đưa được cháu bé vào bờ cho người đàn ông kia thì Công đã đuối sức và bị dòng nước cuốn đi. Ngay sau đó, người dân sở tại đã tìm cách cứu Công nhưng không được. Lực lượng cứu hộ đã dùng các biện pháp nghiệp vụ và vớt được thi thể Công sau 2 giờ tìm kiếm. Nghe hung tin từ cô con gái lúc vừa đi làm đồng về, bà Thoa chết sững. \"Hai hôm trước tai nạn, nó gọi điện thoại về cho tôi khoe mua được chiếc quần mới. Cũng giống như mọi lần trước, Công luôn dặn dò mẹ giữ gìn sức khỏe chờ ngày trở về trả nốt số nợ còn lại. Không ngờ đó là lần cuối cùng tôi nghe giọng nó\", bà Thoa nghẹn ngào. Tuổi thơ gian khó Nhớ lại những ngày tháng tủi cực cùng cậu con trai duy nhất trong nhà, bà Thoa không kìm được nước mắt. Công là con út trong gia đình bố mất sớm, lại cách xa tuổi so với 3 chị gái nên phải gánh vác mọi công việc từ nhỏ. Để có thể phụ giúp kinh tế cho người mẹ tảo tần sớm hôm, cậu học trò này đã phải nghỉ học từ năm lên lớp 9. Sau nhiều năm bươn chải mưu sinh, Công quyết định đi xuất khẩu lao động với mong muốn cải thiện kinh tế gia đình. Trước quyết tâm của con trai, bà Thoa đã nhiều đêm thức trắng tìm cách vay mượn tiền khắp nơi. Chiếc sổ đỏ của gia đình cũng được bà cầm cố cho ngân hàng để đổi lại được một khoản tiền nhỏ. Vẫn không đủ 7.000 USD, bà Thoa tiếp tục vay mượn người thân để cố giúp con thực hiện được nguyện vọng. Bức hình lưu niệm của Trần Ngọc Công (bên phải ngoài cùng) với bạn bè ở nơi xa xứ. Ảnh: Gia đình cung cấp. Những ngày đầu sang đất khách quê người làm kinh tế, Công gặp vô vàn khó khăn khi công ty thường xuyên ít việc. \"Nhiều lúc không có việc làm, nó tranh thủ cuối tuần đi làm thêm để trang trải cuộc sống và tích cóp gửi tiền về cho tôi trả nợ\", kể tới đây, bà Thoa lại nấc nghẹn lên từng hồi. Chị Trần Thị Bình (40 tuổi, chị gái Công) cho hay, Công đi lao động xuất khẩu theo Công ty Sông Hồng (trụ sở tại Đầm Trấu, Hà Nội). Sau khi xảy ra sự cố, công ty này có điện thoại thông báo. Tuy nhiên, do Công ra ngoài làm tự do gần 1 năm nên phía công ty cho biết không còn trách nhiệm với Công. Ngày 26/9, gia đình đã liên hệ được với những người bạn bè của em trai bên Đài Loan hoàn tất thủ tục an táng. Ngay sau đó, hài cốt của Công sẽ được đưa về quê nhà ngày 28/9. Trao đổi với Zing.vn, ông Trần Công Hoàn, Phó chủ tịch HĐND xã Kim Thái cho hay, địa phương chưa nhận được thông báo chính thức bằng văn bản về sự cố khiến anh Trần Ngọc Công tử vong. Theo nguồn tin từ báo chí được biết Công gặp nạn khi cố gắng cứu một bé trai quốc tịch Đài Loan. Ông Hoàn cũng cho biết tại địa phương có hàng chục lao động xuất khẩu tại đài loan, riêng tại thôn 4 có 6-7 người. Đây là sự cố đáng tiếc xảy ra. Trước đó, trong cuộc họp báo chiều 25/9 tại Bộ Ngoại giao, Người phát ngôn Lê Hải Bình cho biết, mặc dù là lao động bất hợp pháp nhưng hành động cứu người của anh Trần Ngọc Công khiến người dân Đài Loan cảm phục. “Cộng đồng sở tại đánh giá đây là hành động cứu người rất xúc động. Bộ Ngoại giao Việt Nam đang phối hợp xác minh thông tin và sớm đưa thi thể của anh Công về nước càng sớm càng tốt”, ông Bình cho hay. Hoàn Nguyễn - Nguyễn Hòa");	
		document.setUrl("http://news.zing.vn/Tuoi-tho-khon-kho-cua-chang-trai-Viet-cuu-chau-be-Dai-Loan-post461756.html");
		
		String json = gson.toJson(document);
		System.out.println(json);
		
		document = gson.fromJson(json, NewsRawDocument.class);
		System.out.println(document.getDate());
	}

}