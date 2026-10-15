import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Post {

    private static int count = 0;

    private String postId;
    private User author;
    private String content;
    private LocalDateTime timestamp;
    private ArrayList<User> likes = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private boolean isEdited;
    private LocalDateTime lastEditTime;

    public String getContent() {
        return content;
    }

    public Post(String content) {
        this.content = content;
        this.postId = "Post-" + count;
        count++;
        this.timestamp = LocalDateTime.now();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void addLike(User user){
        if(this.hasLiked(user)){
            System.out.println(user + "already liked the post");
            return;
        }
        this.likes.add(user);
        System.out.println(user + "liked the post " + this.postId);
    }

    public void removeLike(User user){
        if(!this.hasLiked(user)){
            System.out.println(user + "Did not like the post before");
            return;
        }
        this.likes.remove(user);
        System.out.println(user + "removed his like to " + this.postId);
    }

    public int getLikeCount(){
        System.out.println(this.postId + "has " + this.likes.size() + " likes");
        return this.likes.size();
    }

    public boolean hasLiked(User user){
        return this.likes.contains(user);
    }

    // review later after Comment class
    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void editPost(String newContent){
        if(newContent == null || newContent.isBlank()){
            System.out.println("Post can not be empty");
            return;
        }
        this.content = newContent;
        System.out.println(this.postId + "content is edited");
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void deleteComment(String commentId){
        Iterator<Comment> it = this.comments.iterator(); // iterator to loop on the array "by default it points to value before first element"
        while(it.hasNext()){
            Comment c = it.next();
            if(c.getCommentId().equals(commentId)){
                it.remove();
                System.out.println(commentId + " has been deleted successfully");
                return;
            }
        }
        System.out.println(this.postId + " does not have comment with id " + commentId);
    }

    public int calcEnagement(){
        return this.comments.size() + this.likes.size();
    }

    public void getPostSummary(){
        System.out.println("formatted post info");
    }
}
