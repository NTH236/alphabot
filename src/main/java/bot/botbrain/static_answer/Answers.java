/*
    mục đích lấy data câu trả lời cho bot từ file answer.txt
*/
package bot.botbrain.static_answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Answers {
    private final HashMap<String, String> answersMap;

    public Answers() {
        this.answersMap = new HashMap<>();
        loadAnswers();
    }

    private void loadAnswers() {
        try (BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream("answers.txt"),
                        java.nio.charset.StandardCharsets.UTF_8))) {
            if (inputStream == null) {
                System.out.println("Lỗi: Không tìm thấy file câu trả lời trên classpath.");
                return;
            }

            String line;
            while ((line = inputStream.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim().toLowerCase();
                        String value = parts[1].trim();
                        answersMap.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi: Không thể đọc file câu trả lời.");
            e.printStackTrace();
        }
    }

    public String getAnswerFromMap(String key) {
        return answersMap.get(key);
    }
}
