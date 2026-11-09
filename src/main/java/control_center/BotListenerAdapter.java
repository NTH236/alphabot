package control_center;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListenerAdapter extends ListenerAdapter {

    private ResponseDispatcher responseDispatcher;

    public BotListenerAdapter(ResponseDispatcher responseDispatcher) {
        this.responseDispatcher = responseDispatcher;
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Ignore messages from bots to prevent infinite loops
        if (event.getAuthor().isBot()) {
            return;
        }
        // Get the message content and pass it to the ResponseDispatcher
        String message = event.getMessage().getContentRaw();
        // Get the response from the ResponseDispatcher
        String response = responseDispatcher.botResponse(message);
        // if the response is a shutdown command, shut down the bot
        if (response.equals("[SYSTEM_SHUTDOWN]")) {
            event.getChannel().sendMessage("Đang ngắt kết nối. Tạm biệt Chỉ huy.").queue();
            event.getJDA().shutdown();
            System.exit(0);
        }
        // Send the response back to the channel
        event.getChannel().sendMessage(response).queue();
    }
}
