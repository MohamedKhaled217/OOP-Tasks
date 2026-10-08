import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    private String userId;
    private String userName;
    private String email;
    private String displayName;

    public ArrayList<User> getFriends() {
        return new ArrayList<>(friends); // return copy of it
    }

    private String bio;

    public String getDisplayName() {
        return displayName;
    }

    private String profilePicture;

    public String getUserId() {
        return userId;
    }

    private LocalDateTime dateJoined;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();
    private boolean isPrivate;

    public ArrayList<Post> getPosts() {
        return new ArrayList<>(posts);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getUserName() {
        return userName;
    }

    public User(String userId, String userName, String email, String displayName) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.displayName = displayName;
    }

    public Post createPost(String content){
        if(content == null || content.isBlank()){
            System.out.println("Post can not be empty");
            return null;
        }
        Post post = new Post(content);
        post.setAuthor(this);
        this.posts.add(post);
        return post;
    }

    public void addFriend(User user){
        if(this == user){
            System.out.println("You can't add yourself");
            return;
        }
            if(this.friends.contains(user)){
                System.out.println("You are already friends");
                return;
            }

        this.friends.add(user);
        user.friends.add(this);
        System.out.println("You and " + user.getUserName()+ " are now friends");
    }

    public void removeFriend(User user){
        if(!this.friends.contains(user)){
            System.out.println("This user is not your friend");
            return;
        }
        this.friends.remove(user);
        user.friends.remove(this);
        System.out.println(user.getUserName() + "is not your friend anymore");
    }

    public boolean isFriendWith(User user){
        for(var x : this.friends) {
            if (x.userId.equals(user.userId)) {
                System.out.println(user.getUserName() + "is your friend");
                return true;
            }
        }
        System.out.println(user.getUserName() + "is not your friend");
        return false;
    }

    public int getPostCount(){
        return this.posts.size();
    }

    public boolean canView(User viewer){
        if(this == viewer) return true;
        return !this.isPrivate || this.isFriendWith(viewer);
    }

    public void getUserProfile(){
        System.out.println("Formated Profile Info , implement later");
    }

}
