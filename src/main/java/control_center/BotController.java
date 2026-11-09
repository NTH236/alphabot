package control_center;

import bot.AlphaBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class BotController {
    private AlphaBot alphaBot = new AlphaBot();

    public BotController() {
        // Constructor
    }

    public void startBot() {
        String token = alphaBot.getBotToken();
        if (token.equals("[SYSTEM-ERROR]")) {
            System.out.println(token);
            return;
        }

        try {
            JDABuilder.createDefault(token)
            // Cấp intent để đọc và lắng nghe tin nhắn trong Guild (Server)
            .enableIntents(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.DIRECT_MESSAGES
            )
            // Đăng ký Listener tiếp nhận tin nhắn
            .addEventListeners(new BotListenerAdapter(alphaBot.getResponseDispatcher()))
            .build();

            System.out.println("[ Hệ thống ]: Discord Bot đang khởi động và kết nối...");
        } catch (Exception e) {
            System.out.println(e);  
        }
    }
}
