import java.util.ArrayList;

public class FriendSuggestion {

    private User suggestedUser;
    private ArrayList<User> mutualFriends;
    private int score;

    public FriendSuggestion(User suggestedUser, ArrayList<User> mutualFriends) {
        this.suggestedUser = suggestedUser;
        this.mutualFriends = mutualFriends;
        calculateScore();
    }

    public void calculateScore() {
        this.score = mutualFriends.size();
    }

    public String getSuggestionInfo() {
        return "Suggested User: " + suggestedUser.getDisplayName()
                + ", Mutual Friends: " + mutualFriends.size()
                + ", Score: " + score;
    }
}