package control_center;

import java.util.List;
import java.util.ArrayList;

import bot.bot_response.InfoResponse;
import bot.bot_response.LocalAIResponse;
import bot.bot_response.ShutdownResponse;
import bot.botbrain.BrainService;
import bot.botbrain.static_answer.Info_Bot;
import control_center.response.IResponse;


public class ResponseDispatcher {
    private List<IResponse> responses = new ArrayList<>();

    public ResponseDispatcher (Info_Bot infoResponse, BrainService brainService) {
        this.responses.add(new ShutdownResponse());
        this.responses.add(new InfoResponse(infoResponse));
        this.responses.add(new LocalAIResponse(brainService));
    }

    public String botResponse(String message) {
        for (IResponse iResponse : responses) {
            if (iResponse.canHandle(message)) {
                return iResponse.execute(message);
            }
        }
        return "Lỗi: Không có luồng nào xử lý được mệnh lệnh này.";
    }
}
