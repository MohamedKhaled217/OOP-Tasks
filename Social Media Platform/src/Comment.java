import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment {
    private String commentId;
    private User author;
    private String content;
    private LocalDateTime timestamp;

    public String getCommentId() {
        return commentId;
    }

    private ArrayList<User> likes = new ArrayList<>();
    private Post parentPost;

    public Comment(String commentId, User author, String content, Post parentPost) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.parentPost = parentPost;
        this.timestamp = LocalDateTime.now();
    }
    public void addLike(User user){
        if(this.likes.contains(user)){
            System.out.println(user + " Already liked the comment");
            return;
        }
        this.likes.add(user);
        System.out.println(user.getUserName() + "liked comment " + this.commentId);
    }

    public void removeLike(User user){
        if(!this.likes.contains(user)){
            System.out.println(user.getUserName() + " Did not like the comment");
            return;
        }
        this.likes.remove(user);
        System.out.println(user.getUserName() + "unliked the comment" + this.commentId);
    }

    public int getLikeCount(){
        int likes = this.likes.size();
        System.out.println(this.commentId + "has "+ likes + " likes");
        return likes;
    }

    public void getCommentInfo(){
        System.out.println("formatted comment info");
    }
}
