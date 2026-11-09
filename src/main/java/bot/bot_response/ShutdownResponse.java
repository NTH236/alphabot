package bot.bot_response;

import control_center.response.IResponse;

public class ShutdownResponse implements IResponse{
    @Override 
    public boolean canHandle(String message) {
        return message.trim().equalsIgnoreCase("exit") || message.trim().equalsIgnoreCase("quit");
    }

    @Override 
    public String execute(String message) {
        return "[SYSTEM_SHUTDOWN]";
    }
}
