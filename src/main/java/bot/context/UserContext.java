/*
nhận các thông tin về tin nhắn và người dùng khi có sự kiện gửi tin nhắn
*/
package bot.context;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UserContext {
    private final String userId;
    private final String userName;
    private final String channelName;
    private final boolean isOwner;
    private final String content;
    
    // Ngữ cảnh tin nhắn được Reply (nếu có)
    private final String refAuthorName;
    private final String refContent;
    public UserContext(MessageReceivedEvent event, String ownerId) {
        this.userId = event.getAuthor().getId();
        // Lấy Nickname trong server nếu có, không thì lấy tên Discord
        this.userName = event.getMember() != null ? event.getMember().getEffectiveName() : event.getAuthor().getName();
        this.channelName = event.getChannel().getName();
        this.isOwner = this.userId.equals(ownerId);
        this.content = event.getMessage().getContentRaw();
        // Kiểm tra xem tin nhắn này có bấm Reply tin nhắn khác không
        Message ref = event.getMessage().getReferencedMessage();
        if (ref != null) {
            this.refAuthorName = ref.getAuthor().getName();
            this.refContent = ref.getContentRaw();
        } else {
            this.refAuthorName = null;
            this.refContent = null;
        }
    }
    // --- CÁC HÀM GETTER DÙNG CHO PHÂN QUYỀN VÀ LOGIC ---
    public boolean isOwner() { return isOwner; }
    public String getContent() { return content; }
    public String getUserName() { return userName; }
    // --- ĐẦU RA CHO AI: BIẾN METADATA THÀNH NGỮ CẢNH LLM HIỂU ĐƯỢC ---
    public String toPromptContext() {
        StringBuilder sb = new StringBuilder();
        sb.append("[NGỮ CẢNH HIỆN TẠI]:\n");
        sb.append("- Kênh chat: #").append(channelName).append("\n");
        sb.append("- Người đang nói: ").append(userName);
        if (isOwner) {
            sb.append(" (Đây chính là Chỉ huy Hoàng - cấp trên tối cao của bạn)");
        } else {
            sb.append(" (Đây là thành viên/khách trong căn cứ, không phải Chỉ huy)");
        }
        sb.append("\n");
        // Nếu có Reply, đưa tin nhắn gốc vào để AI hiểu
        if (refContent != null) {
            sb.append("- Đang trả lời cho tin nhắn của [").append(refAuthorName).append("]: \"")
              .append(refContent).append("\"\n");
        }
        sb.append("- Lời thoại hiện tại: \"").append(content).append("\"");
        return sb.toString();
    }
}