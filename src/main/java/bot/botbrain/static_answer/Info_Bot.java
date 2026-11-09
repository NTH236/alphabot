package bot.botbrain.static_answer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Info_Bot {
    private final Properties properties = new Properties();

    public Info_Bot() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                System.out.println("Lỗi: Không tìm thấy file cấu hình trên classpath.");
                return;
            }
            this.properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Lỗi: Không thể đọc file cấu hình.");
            e.printStackTrace();
        }
    }

    public String getInfoFromProperties(String key) {
        return this.properties.getProperty(key);
    }

    public String chatProfile() {
        return "[" + this.properties.getProperty("BOT_NAME") + " : " + this.properties.getProperty("BOT_FRAME_1") + "]";
    }
}
