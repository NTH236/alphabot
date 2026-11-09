package bot.bot_response;

import bot.botbrain.static_answer.Info_Bot;
import control_center.response.IResponse;

public class InfoResponse implements IResponse {
    private Info_Bot aInfo;

    public InfoResponse (Info_Bot pInfo) {
        this.aInfo = pInfo;
    }

    @Override 
    public boolean canHandle(String message) {
        return message.trim().equalsIgnoreCase("info");
    }

    @Override 
    public String execute(String message) {
        return "Tên: " + this.aInfo.getInfoFromProperties("BOT_NAME") + 
               " | Khung: " + this.aInfo.getInfoFromProperties("BOT_FRAME_1");
    }
}
