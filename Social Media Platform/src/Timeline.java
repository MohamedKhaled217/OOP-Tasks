import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Timeline {
    private User owner;
    private ArrayList<Post> posts ;

    public Timeline(User owner) {

        this.owner = owner;
        this.posts = owner.getPosts();  // owner need to be assigned first
    }

    public ArrayList<Post> getTimelinePosts(User viewer){
        if(owner.canView(viewer)){
           return new ArrayList<>(this.posts); // give user copy of the list not original one
        }
        return new ArrayList<>();
    }

    public ArrayList<Post> getNewsFeed(int count){
        ArrayList<Post> feed = new ArrayList<>();
        feed.addAll(this.posts);  // added owner posts
        for(var x : this.owner.getFriends()){
            feed.addAll(x.getPosts());  // added friends posts
        }
        // sort the posts "newest first"
        feed.sort(Comparator.comparing(Post::getTimestamp).reversed());
        if (count >= feed.size()) {
            return feed;
        }
        return new ArrayList<>(feed.subList(0, count)); // make sublist with size required
    }

    public ArrayList<Post> getPostsByDate(LocalDateTime startDate, LocalDateTime endDate){
        ArrayList<Post> p = new ArrayList<>();
        for(var x : this.posts){
            if (!x.getTimestamp().isAfter(endDate) && !x.getTimestamp().isBefore(startDate)){  // boundaries inclusive
                p.add(x);
            }
        }
        return p;
    }
    public ArrayList<Post> searchPosts(String keyword){
        ArrayList<Post> p = new ArrayList<>();
        for(var x : this.posts){
            if (x.getContent().contains(keyword)) p.add(x);
        }
        return p;
    }

}
