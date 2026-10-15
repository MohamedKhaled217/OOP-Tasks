# 🔍 Code Review — Task 8: Social Media Platform (OOP)

> Full review of 8 Java files covering design, bugs, best practices, and actionable fixes.

---

## 📊 Overall Score: **6.5 / 10**

| Category | Rating | Notes |
|---|---|---|
| OOP Fundamentals | ⭐⭐⭐⭐ | Good use of encapsulation, classes are well-separated |
| Bug-Free Code | ⭐⭐ | Several `NullPointerException` risks and logic bugs |
| Naming & Readability | ⭐⭐⭐ | Mostly clear, but some inconsistencies & typos |
| Best Practices | ⭐⭐ | Missing `List` interfaces, `toString()`, `equals()`/`hashCode()` |
| Completeness | ⭐⭐⭐ | Stub methods remain; `Main.java` is untouched |

---

## ✅ What You Did Well

### 1. Defensive Copies on Getters
In [User.java](file:///d:/OOP-Tasks/Task-8/src/User.java#L10-L12) and [Timeline.java](file:///d:/OOP-Tasks/Task-8/src/Timeline.java#L17), you return **copies** of internal lists instead of the originals:
```java
return new ArrayList<>(friends); // ✅ Great — protects internal state
```
This is a textbook-correct encapsulation practice. Many students get this wrong.

### 2. Bidirectional Friendship Logic
In [User.java](file:///d:/OOP-Tasks/Task-8/src/User.java#L61-L73), `addFriend()` correctly updates **both** sides:
```java
this.friends.add(user);
user.friends.add(this); // ✅ Both users become friends — real-world correct
```

### 3. Input Validation
You consistently validate inputs before acting (e.g., null/blank checks in [User.createPost()](file:///d:/OOP-Tasks/Task-8/src/User.java#L50-L58) and [Post.editPost()](file:///d:/OOP-Tasks/Task-8/src/Post.java#L65-L72)). This is a good defensive programming habit.

### 4. Iterator for Safe Deletion
In [Post.deleteComment()](file:///d:/OOP-Tasks/Task-8/src/Post.java#L78-L89), you used an `Iterator` to safely remove elements while looping — avoiding `ConcurrentModificationException`. Excellent.

### 5. Privacy Check via `canView()`
The [canView()](file:///d:/OOP-Tasks/Task-8/src/User.java#L101-L104) method cleanly handles the self-view, public profile, and friend-access cases in one line. Nicely done.

### 6. Good Class Separation
Each concept (`User`, `Post`, `Comment`, `Notification`, `Timeline`, `FriendSuggestion`, `SocialMediaPlatform`) has its own class with clear responsibility. This shows solid OOP thinking.

---

## 🐛 Bugs & Critical Issues

### Bug 1: `NullPointerException` in `getUnreadNotifications()`
**File:** [SocialMediaPlatform.java:80-81](file:///d:/OOP-Tasks/Task-8/src/SocialMediaPlatform.java#L80-L81)
```java
ArrayList<Notification> arr = this.notifications.get(u.getUserId());
if(arr.isEmpty()) return res; // 💥 NPE if userId has no entry in the map!
```
If the user has **never received a notification**, `get()` returns `null`, and calling `.isEmpty()` on `null` crashes.

**Fix:**
```java
ArrayList<Notification> arr = this.notifications.get(u.getUserId());
if(arr == null || arr.isEmpty()) return res; // ✅ Safe
```

---

### Bug 2: Missing Space in String Concatenation (Multiple Files)
Several print statements are missing a **space before the name**, producing garbled output.

| File | Line | Output |
|---|---|---|
| [User.java](file:///d:/OOP-Tasks/Task-8/src/User.java#L83) | 83 | `"Ahmed**is** not your friend anymore"` → missing space |
| [User.java](file:///d:/OOP-Tasks/Task-8/src/User.java#L89) | 89 | `"Ahmed**is** your friend"` → missing space |
| [User.java](file:///d:/OOP-Tasks/Task-8/src/User.java#L93) | 93 | `"Ahmed**is** not your friend"` → missing space |
| [Post.java](file:///d:/OOP-Tasks/Task-8/src/Post.java#L35) | 35 | `user + "already liked"` → prints object hash, not name |
| [Post.java](file:///d:/OOP-Tasks/Task-8/src/Post.java#L71) | 71 | `postId + "content is edited"` → missing space |
| [Comment.java](file:///d:/OOP-Tasks/Task-8/src/Comment.java#L30) | 30 | `"Ahmed**liked** comment"` → missing space |
| [Comment.java](file:///d:/OOP-Tasks/Task-8/src/Comment.java#L39) | 39 | `"Ahmed**unliked** the comment"` → missing space |

**Fix Example:**
```java
// ❌ Before
System.out.println(user.getUserName() + "is not your friend anymore");

// ✅ After
System.out.println(user.getUserName() + " is not your friend anymore");
```

---

### Bug 3: Printing `User` Object Without `toString()`
**File:** [Post.java:35, 39, 44, 48](file:///d:/OOP-Tasks/Task-8/src/Post.java#L33-L49)

You print `user` directly (not `user.getUserName()`):
```java
System.out.println(user + "already liked the post"); // 💥 Prints "User@1a2b3c"
```
Since `User` has no `toString()` override, Java prints the default memory hash.

**Fix:** Either override `toString()` in `User`, or always use `user.getUserName()`.

---

### Bug 4: `Timeline` Gets a Stale Snapshot of Posts
**File:** [Timeline.java:12](file:///d:/OOP-Tasks/Task-8/src/Timeline.java#L12)
```java
this.posts = owner.getPosts(); // Returns a COPY at construction time
```
Since `getPosts()` returns a **defensive copy**, the `Timeline` will never see posts created **after** the Timeline was constructed. The news feed will be frozen in time.

**Fix — Option A:** Store a reference to the owner and call `getPosts()` each time:
```java
public ArrayList<Post> getTimelinePosts(User viewer){
    if(owner.canView(viewer)){
       return new ArrayList<>(owner.getPosts()); // ✅ Always fresh
    }
    return new ArrayList<>();
}
```
**Fix — Option B:** Have `getPosts()` return the actual list (but then you lose encapsulation — not recommended).

---

### Bug 5: `registerUser()` Has No Duplicate Check
**File:** [SocialMediaPlatform.java:15-20](file:///d:/OOP-Tasks/Task-8/src/SocialMediaPlatform.java#L15-L20)
```java
public void registerUser(User user){
    if(user != null) {
        this.users.put(user.getUserId(), user); // Silently overwrites if userId exists
        System.out.println(); // Prints empty line — probably a leftover
    }
}
```
If the same `userId` is registered twice, the old user is silently replaced.

**Fix:**
```java
public void registerUser(User user){
    if(user == null) return;
    if(this.users.containsKey(user.getUserId())){
        System.out.println("User with ID " + user.getUserId() + " already exists.");
        return;
    }
    this.users.put(user.getUserId(), user);
    System.out.println(user.getUserName() + " registered successfully.");
}
```

---

### Bug 6: `editPost()` Doesn't Set `isEdited` or `lastEditTime`
**File:** [Post.java:65-72](file:///d:/OOP-Tasks/Task-8/src/Post.java#L65-L72)

You declared `isEdited` and `lastEditTime` fields but **never use them**:
```java
private boolean isEdited;         // Never set to true
private LocalDateTime lastEditTime; // Never updated
```
**Fix:**
```java
public void editPost(String newContent){
    if(newContent == null || newContent.isBlank()){
        System.out.println("Post can not be empty");
        return;
    }
    this.content = newContent;
    this.isEdited = true;                    // ✅
    this.lastEditTime = LocalDateTime.now(); // ✅
    System.out.println(this.postId + " content is edited");
}
```

---

### Bug 7: `Post.count` ID Generation Is Not Thread-Safe & Post IDs Collide
**File:** [Post.java:7, 24](file:///d:/OOP-Tasks/Task-8/src/Post.java#L7-L26)
```java
this.postId = "Post-" + count; // First post is "Post-0"
count++;                        // Incremented AFTER assignment
```
This works in single-threaded code, but in multi-threaded scenarios two posts could get the same ID. Also, the convention of `count++` after assignment means the ID and count are always off by one — better to use `count++` inline.

**Minor fix for clarity:**
```java
this.postId = "Post-" + (count++); // Assign then increment in one step
```

---

## ⚠️ Design Issues & Code Smells

### 1. Using `ArrayList` Instead of `List` Interface
**Files:** Every single file.

You declare fields and parameters as `ArrayList<>` everywhere:
```java
private ArrayList<User> friends = new ArrayList<>(); // ❌
```
**Best Practice:** Program to the **interface**, not the implementation:
```java
private List<User> friends = new ArrayList<>(); // ✅
```
This makes it easy to swap `ArrayList` for `LinkedList` or `Collections.unmodifiableList()` later without changing any code that uses the field.

---

### 2. Missing `equals()` and `hashCode()` on `User`
You use `this.likes.contains(user)` and `this.friends.contains(user)` in many places. These rely on `equals()`. Without overriding it, Java compares by **memory reference** (`==`), not by `userId`.

If you ever create two `User` objects with the same `userId`, `contains()` will return `false` even though they represent the same user.

**Fix — Add to [User.java](file:///d:/OOP-Tasks/Task-8/src/User.java):**
```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return userId.equals(user.userId);
}

@Override
public int hashCode() {
    return userId.hashCode();
}
```

---

### 3. `System.out.println()` Used for Error Handling
Throughout the code, you print errors to the console and return `null` or just `return`:
```java
System.out.println("Post can not be empty");
return null;
```
In real applications, you should throw exceptions so the caller can handle the error:
```java
throw new IllegalArgumentException("Post content cannot be empty");
```
For a learning project this is acceptable, but know that production code should use **exceptions**, not print statements.

---

### 4. Inconsistent Field/Method Ordering in Classes
In [User.java](file:///d:/OOP-Tasks/Task-8/src/User.java), fields and getters are interleaved randomly:
```
Line 5-8:   fields
Line 10-12: getter
Line 14:    field
Line 16-18: getter
Line 20:    field
Line 22-24: getter
Line 26-29: fields
...
```
**Best Practice — Standard Java class layout:**
1. Static fields
2. Instance fields (all together)
3. Constructors
4. Public methods
5. Private/helper methods
6. Getters & Setters

---

### 5. `Notification.type` Should Be an `enum`
**File:** [Notification.java:6](file:///d:/OOP-Tasks/Task-8/src/Notification.java#L6)
```java
private String type;  // "like" / "comment" / "friedn req"  ← also a typo: "friedn"
```
Using a `String` for a fixed set of values is error-prone. Someone could pass `"Liek"` and you'd never catch it.

**Fix:**
```java
public enum NotificationType {
    LIKE, COMMENT, FRIEND_REQUEST
}
// Then in Notification:
private NotificationType type;
```

---

### 6. `SocialMediaPlatform.getTrendingPosts()` Mutates the Original List
**File:** [SocialMediaPlatform.java:54](file:///d:/OOP-Tasks/Task-8/src/SocialMediaPlatform.java#L54)
```java
allPosts.sort(...); // ❌ Permanently re-orders the master list!
```
Every call to `getTrendingPosts()` changes the order of `allPosts` for all future operations.

**Fix — Sort a copy:**
```java
public List<Post> getTrendingPosts(int cnt){
    List<Post> sorted = new ArrayList<>(allPosts); // Copy first
    sorted.sort(Comparator.comparing(Post::calcEnagement).reversed());
    int limit = Math.min(cnt, sorted.size());
    return new ArrayList<>(sorted.subList(0, limit));
}
```

---

### 7. `Main.java` Is Untouched Boilerplate
**File:** [Main.java](file:///d:/OOP-Tasks/Task-8/src/Main.java)

The main class still contains IntelliJ's default template with `"Hello and welcome!"` and a for-loop. None of your social media classes are instantiated or tested.

**Recommendation:** Write a demo that exercises your classes:
```java
public static void main(String[] args) {
    SocialMediaPlatform platform = new SocialMediaPlatform("MySocial");

    User alice = new User("u1", "alice", "alice@mail.com", "Alice");
    User bob = new User("u2", "bob", "bob@mail.com", "Bob");

    platform.registerUser(alice);
    platform.registerUser(bob);
    platform.createFriendship(alice, bob);

    Post post = alice.createPost("Hello World!");
    platform.addPost(post);
    post.addLike(bob);

    System.out.println("Trending: " + platform.getTrendingPosts(5));
}
```

---

### 8. Typo in Method Name
**File:** [Post.java:91](file:///d:/OOP-Tasks/Task-8/src/Post.java#L91)
```java
public int calcEnagement(){ // ❌ "Enagement" → should be "Engagement"
```

---

## 📋 Summary of All Issues

| # | Severity | File | Issue |
|---|---|---|---|
| 1 | 🔴 Critical | `SocialMediaPlatform` | `NullPointerException` in `getUnreadNotifications()` |
| 2 | 🔴 Critical | `Timeline` | Stale posts — defensive copy becomes frozen snapshot |
| 3 | 🟠 Major | `Post` | Printing `User` object without `toString()` — shows hash |
| 4 | 🟠 Major | Multiple | Missing spaces in string concatenation (garbled output) |
| 5 | 🟠 Major | `Post` | `isEdited` and `lastEditTime` are never set |
| 6 | 🟠 Major | `SocialMediaPlatform` | `getTrendingPosts()` mutates the master list |
| 7 | 🟠 Major | `SocialMediaPlatform` | `registerUser()` silently overwrites + empty println |
| 8 | 🟡 Medium | All | Using `ArrayList` instead of `List` interface |
| 9 | 🟡 Medium | `User` | Missing `equals()` / `hashCode()` |
| 10 | 🟡 Medium | `Notification` | `type` should be an `enum`, not a `String` |
| 11 | 🟡 Medium | `User` | Interleaved fields and getters — messy layout |
| 12 | 🟢 Minor | `Post` | Typo: `calcEnagement` → `calcEngagement` |
| 13 | 🟢 Minor | `Notification` | Typo in comment: `"friedn req"` |
| 14 | 🟢 Minor | `Main` | Default IntelliJ template — not connected to project |
| 15 | 🟢 Minor | All | `System.out` used for error handling instead of exceptions |

---

## 🎓 Key Lessons & Best Practices to Follow

### 1. **Program to Interfaces**
```java
// ❌ Don't
private ArrayList<User> friends = new ArrayList<>();
// ✅ Do
private List<User> friends = new ArrayList<>();
```

### 2. **Always Override `toString()`, `equals()`, `hashCode()`**
These are essential for any class used in collections (`contains()`, `HashMap` keys, debugging output).

### 3. **Watch Out for Defensive Copies Creating Stale Data**
Defensive copies are great for getters, but if you **store** the result, you lose sync with the original. Know when to copy and when to reference.

### 4. **Use Enums for Fixed Value Sets**
Strings like `"like"`, `"comment"`, `"friend_request"` are perfect candidates for `enum` types — they give you compile-time safety.

### 5. **Never Mutate a Collection You Didn't Mean To**
Sorting `allPosts` in place was accidental mutation. Always sort a copy if the original order matters.

### 6. **Test Your Code in `main()`**
Writing even a simple demo reveals bugs immediately. You would have caught the string spacing issues and `NullPointerException` instantly.

### 7. **Consistent Class Structure**
Follow the convention: fields → constructors → public methods → private methods → getters/setters. It makes your code scannable.

---

> [!TIP]
> Your OOP fundamentals (encapsulation, separation of concerns, defensive copies) are solid. Focus next on **null safety**, **`equals()`/`hashCode()`**, **programming to interfaces**, and **actually running your code** to catch runtime bugs. Keep going — you're on the right track! 💪
