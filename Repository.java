import java.util.*;
import java.text.SimpleDateFormat;

// This class simulates having a repository. It stores the commits in chronological order, with the
// most recent one being at the top of the repository.
public class Repository {
    private Commit head;
    private String name;
    private int size;

    // Behavior: 
    //   - This method constructs a repository object that is empty
    // Parameters:
    //   - name: the name of the repository
    // Exceptions:
    //   - if the name is null or empty, an IllegalArgumentException is thrown.
    public Repository(String name){
        if(name == null || name.equals("")){
            throw new IllegalArgumentException();
        }
        this.name = name;
        size = 0;
        head = null;
    }

    // Behavior: 
    //   - This method returns the most recent commit's unique identification number
    // Returns:
    //   - int: the id of the most recent commit
    public String getRepoHead(){
        if(head == null){
            return null;
        }
        return head.id;
    }

    // Behavior: 
    //   - This method returns the amount of commits in the repository
    // Returns:
    //   - int: the amount of commits in the repository
    public int getRepoSize(){
        return size;
    }

    // Behavior: 
    //   - This method displays the most recent commit and it's corresponding repository.
    //   - If the repository is empty, it's name and no commits are displayed.
    // Returns:
    //   - String: the messages of the commits in the repository
    public String toString(){
        if(this.getRepoSize() == 0){
            return name + " - No commits";
        }
        return name + " - Current head: " + head;
    }

    // Behavior: 
    //   - This method checks to see if the targetId of a commit exists as an Id of a commit in the
    //   - repository
    // Parameters:
    //   - targetId: the id to be checked if it is of a commit in the repository
    // Returns:
    //   - boolean: true if it is, false if it isn't
    // Exceptions:
    //   - if the id argument is null, an IllegalArgumentException is thrown
    public boolean contains(String targetId){
        nullCheck(targetId);
        Commit curr = head;
        while(curr != null){
            System.out.println(curr.id);
            System.out.println(targetId);
            if(curr.id.equals(targetId)){
                return true;
            }
            curr = curr.past;
        }
        return false;
    }

    // Behavior: 
    //   - Displays the most recent commits of a repository, however far back the client wants To
    //   - go. If the user wants to go too far back, aka farther back than the size of the repo,
    //   - then all the commits are shown.
    // Parameters:
    //   - n: the number of commits the user wants to see
    // Returns:
    //   - String: the display of each commit, each on a separate line
    // Exceptions:
    //   - n <= 0: if the given number is less than or equal to 0, an IllegalArgumentException is
    //   - thrown.
    public String getHistory(int n){
        if(n <= 0){
            throw new IllegalArgumentException();
        }
        String history = "";
        int count = 0;
        Commit curr = head;
        while(count < size && count < n && curr != null){
            history += curr.toString() + "\n";
            count++;
            curr = curr.past;
        }
        return history;
    }

    // Behavior: 
    //   - This method allows the client to push new commits into a repository
    // Parameters:
    //   - message: the message the client wants to include in their commit
    // Returns:
    //   - Commit: the most recent commit pushed by the client
    // Exceptions:
    //   - if the given message is null, an IllegalArgumentException is thrown.
    public String commit(String message){
        nullCheck(message);
        Commit commit = new Commit(message, head);
        head = commit;
        size++;
        return this.getRepoHead();
    }

    // Behavior: 
    //   - This method allows the user to pull commits from their repo, only if the id of the
    //   - commit they want to pull exists in the repository.
    // Parameters:
    //   - targetId: the id of the commit the user wants to remove from the repo
    // Returns:
    //   - boolean: true if the commit was removed, false otherwise
    // Exceptions:
    //   - if the given targetId is null, an IllegalArgumentException is thrown.
    public boolean drop(String targetId){
        nullCheck(targetId);
        Commit curr = head;
        if(curr == null){
            return false;
        }
        if(curr.id.equals(targetId)){
            head = curr.past;
            size--;
            return true;
        }
        while(curr.past != null){
            if(curr.past.id.equals(targetId)){
                curr.past = curr.past.past;
                size--;
                return true;
            }
            curr = curr.past;
        }
        return false;
    }

    // Behavior: 
    //   - Takes all the commits in the other repository and moves them into this repository, 
    //   - combining the two repository histories such that chronological order is preserved. That
    //   - is, after executing this method, this repository should contain all commits that were 
    //   - from this and other, and the commits should be ordered in timestamp order from most 
    //   - recent to least recent.
    // Parameters:
    //   - other: the repository that will be synchronized with this repository
    // Exceptions:
    //   - if the other repository is null, an IllegalArgumentException is thrown.
    public void synchronize(Repository other){
        if(other == null){
            throw new IllegalArgumentException();
        }
        this.size += other.size;
        if(this.head == null){
            this.head = other.head;
            other.head = null;
            other.size = 0;
        }
        else if(other.getRepoSize() != 0){
            if(this.head.timeStamp < other.head.timeStamp){
                Commit temp = this.head;
                this.head = other.head;
                other.head = other.head.past;
                this.head.past = temp;
            }
            Commit temp = this.head;
            while(other.head != null){
                if(temp.past == null){
                    temp.past = other.head;
                    other.head = null;
                }
                else if(temp.past.timeStamp < other.head.timeStamp){
                    Commit temp2 = temp.past;
                    temp.past = other.head;
                    other.head = other.head.past;
                    temp.past.past = temp2;
                }
                else {
                    temp = temp.past;
                }
            }
            other.size = 0;
        }
    }

    // Behavior: 
    //   - This method checks to see if a word is null.
    // Parameters:
    //   - word: the word to be checked
    // Exceptions:
    //   - if the word is null, an IllegalArgumentException is thrown.
    private void nullCheck(String word){
        if(word == null){
            throw new IllegalArgumentException();
        }
    }
    /**
     * DO NOT MODIFY
     * A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message,
     * and the time that the commit was made. A commit also stores
     * a reference to the immediately previous commit if it exists.
     *
     * Staff Note: You may notice that the comments in this 
     * class openly mention the fields of the class. This is fine 
     * because the fields of the Commit class are public. In general, 
     * be careful about revealing implementation details!
     */
    public static class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp
         * are automatically generated.
         * @param message A message describing the changes made in this commit. Should be non-null.
         * @param past A reference to the commit made immediately before this
         *             commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique
         * identifier and timestamp are automatically generated.
         * @param message A message describing the changes made in this commit. Should be non-null.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string
         * representation consists of this commit's unique identifier,
         * timestamp, and message, in the following form:
         *      "[identifier] at [timestamp]: [message]"
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
        * Resets the IDs of the commit nodes such that they reset to 0.
        * Primarily for testing purposes.
        */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}