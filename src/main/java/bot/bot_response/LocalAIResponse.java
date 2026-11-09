package bot.bot_response;

import bot.botbrain.BrainService;
import control_center.response.IResponse;

public class LocalAIResponse implements IResponse {
    private BrainService aBrainService;

    public LocalAIResponse (BrainService pBrainService) {
        this.aBrainService = pBrainService;
    }

    @Override 
    public boolean canHandle(String message) {
        return true;
    }

    @Override 
    public String execute(String message) {
        return this.aBrainService.think(message);
    }
}
