import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SocialMediaPlatform {
    private String platformName;
    private Map<String , User> users = new HashMap<>(); // userId -> User obj
    private ArrayList<Post> allPosts = new ArrayList<>();
    private Map<String, ArrayList<Notification>> notifications = new HashMap<>(); // userId -> [notifications]

    public SocialMediaPlatform(String platformName) {
        this.platformName = platformName;
    }
    public void registerUser(User user){
        if(user != null) {
            this.users.put(user.getUserId(), user);
            System.out.println();
        }
    }

    public User findUser(String username){
        for(User u : this.users.values()){
            if(u.getUserName().equals(username)) return u;
        }
        return null;
    }

    public User getUserById(String userId){
        if(this.users.containsKey(userId)){
            return this.users.get(userId);
        }
        return null;
    }

    public void createFriendship(User user1, User user2){
        if(user1 != null && user2 != null){
            user1.addFriend(user2);
        }
    }

    public void removeFriendship(User user1, User user2){
        if(user1 != null && user2 != null){
            user1.removeFriend(user2);
        }
    }

    public void addPost(Post post) {
        allPosts.add(post);
    }


    public ArrayList<Post> getTrendingPosts(int cnt){
        allPosts.sort(Comparator.comparing(Post::calcEnagement).reversed()); // sort by engagement
        int limit = Math.min(cnt, allPosts.size());
        return new ArrayList<>(allPosts.subList(0, limit));
    }

    public ArrayList<User> getMutualFriends(User user1, User user2){
        if(user1 == null || user2 == null) return new ArrayList<>();
        ArrayList<User> mutual = new ArrayList<>();
        for (User u : user1.getFriends()){
            if(user2.getFriends().contains(u)) mutual.add(u);
        }
        return mutual;
    }

    public void sendNotification(Notification noti){
        if(noti == null) return;
        String id = noti.getRecipient().getUserId();
        if(!this.notifications.containsKey(id)){   // user not exist
            this.notifications.put(id , new ArrayList<>()); // initialize the array
        }
        this.notifications.get(id).add(noti);
    }

    public ArrayList<Notification> getUnreadNotifications(User u){
        if(u == null) return new ArrayList<>();
        ArrayList<Notification> res = new ArrayList<>();
        ArrayList<Notification> arr = this.notifications.get(u.getUserId());
        if(arr.isEmpty()) return res;
        for(var x : arr){
            if(!x.checkRead()) res.add(x);
        }
        return res;
    }

    public User searchUsers(String query){
        for (var x : this.users.values()){
            if(x.getUserName().equals(query) || x.getDisplayName().equals(query)) return x;
        }
        return null;
    }

}
