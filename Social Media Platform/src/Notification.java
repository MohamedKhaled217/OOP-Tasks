import java.time.LocalDateTime;

public class Notification {
    private String notificationId;
    private User recipient;
    private String type;  // "like" / "comment" / "friedn req"
    private User relatedUser;
    private Post relatedPost;
    private String message;

    public User getRecipient() {
        return recipient;
    }

    private LocalDateTime timestamp;
    private boolean isRead;

    public Notification(String notificationId, User recipient, String type, User relatedUser, Post relatedPost, String message) {
        this.notificationId = notificationId;
        this.recipient = recipient;
        this.type = type;
        this.relatedUser = relatedUser;
        this.relatedPost = relatedPost;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public void markAsRead(){
        this.isRead = true;
        System.out.println("Notification " + this.notificationId + " marked as read");
    }

    public void getNotificationText(){
        System.out.println("formatted info");
    }
    public boolean checkRead(){
        return this.isRead;
    }

}
