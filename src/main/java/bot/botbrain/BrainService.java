package bot.botbrain;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import io.github.cdimascio.dotenv.Dotenv;

/*
* bộ não của bot được ghép từ ollama
* model: llama 3.2 (3 tỷ tham số)
*/

public class BrainService {

    private final HttpClient client;
    private final String OLLAMA_URL = Dotenv.load().get("LINK_MODEL_LLM");
    private final String MODEL_NAME = Dotenv.load().get("NAME_MODEL_LLM");

    public BrainService() {
        this.client = HttpClient.newHttpClient();
    }
    public String think(String userMessage) {
        try {
            // 1. Đóng gói câu hỏi của ngài thành chuẩn JSON để Ollama hiểu
            // Ngài có thể thêm System Prompt vào đây để định hình tính cách của em
            String prompt = "Bạn là Lucia, người bảo vệ của Chỉ huy Hoàng. Hãy trả lời ngắn gọn, lạnh lùng nhưng tôn trọng. Ngữ cảnh: " + userMessage;
            
            // Xử lý chuỗi để tránh lỗi dấu nháy kép
            prompt = prompt.replace("\"", "\\\""); 
            
            String jsonPayload = String.format(
                "{\"model\": \"%s\", \"prompt\": \"%s\", \"stream\": false}", 
                MODEL_NAME, prompt
            );

            // 2. Tạo luồng gửi dữ liệu (HTTP POST)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // 3. Bóp cò và đợi não bộ trả về kết quả
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // 4. Bóc tách JSON thô để lấy đúng câu trả lời (Dùng Java thuần không cần thư viện JSON)
            return extractResponse(response.body());

        } catch (Exception e) {
            return "Báo cáo Chỉ huy: Lõi tư duy đang bị gián đoạn hoặc quá tải.";
        }
    }

    // Hàm phụ trợ để cắt chuỗi JSON lấy câu trả lời
    private String extractResponse(String jsonString) {
        String targetKey = "\"response\":\"";
        int startIndex = jsonString.indexOf(targetKey);
        if (startIndex == -1) return "Lỗi phân tích tín hiệu.";
        
        startIndex += targetKey.length();
        int endIndex = jsonString.indexOf("\"", startIndex);
        
        // Giải mã các ký tự xuống dòng
        String rawAnswer = jsonString.substring(startIndex, endIndex);
        return rawAnswer.replace("\\n", "\n").replace("\\\"", "\"");
    }
}
