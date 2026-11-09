package control_center.response;

/*
    đóng vai vai trò lại interface cho xử lí tin nhắn trong trường hợp có nhiều luồng tin nhắn(giảm phụ thuộc)
*/

public interface IResponse {
    // kiểm tra luồng mà tin nhắn sẽ rẽ nhánh
    public boolean canHandle(String message);
    // thực thi tin nhắn với luồng tương ứng
    public String execute(String message);
}
