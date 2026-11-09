package bot;

import bot.botbrain.BrainService;
import bot.botbrain.static_answer.*;
import control_center.ResponseDispatcher;
import io.github.cdimascio.dotenv.Dotenv;

public class AlphaBot {
    private final String botToken = Dotenv.load().get("BOT_TOKEN");
    // private final long permission = parsePermission();

    private long parsePermission() {
        String p = Dotenv.load().get("PERMISSION");
        try {
            return p != null ? Long.parseLong(p.trim()) : 0L;
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    Info_Bot info_Bot = new Info_Bot();
    private ResponseDispatcher aResponse;

    public AlphaBot() {
        BrainService pBrainService = new BrainService();
        this.aResponse = new ResponseDispatcher(info_Bot, pBrainService);        
    }

    public String getBotToken() {
        String res = this.botToken;
        if (res == null) {
            return "[SYSTEM-ERROR]: Không tìm thấy bot token.";
        }
        return res;
    }

    // public long getPermission() {
    //     return this.permission;
    // }

    public String chatName() {
        return info_Bot.chatProfile();
    }

    public ResponseDispatcher getResponseDispatcher() {
        return this.aResponse;
    }

    public String getAnswer(String message) {
        String answer = this.aResponse.botResponse(message);
        if (answer != null) {
            return answer;
        } else {
            return "Xin lỗi, tôi không hiểu câu hỏi của bạn.";
        }
    }
    
}