package relationship.state;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Relationship {
    private String from;
    private String to;
    private Friendship friendship;
    private BlockRelationship blockRelationship;

    public Relationship(String from, String to) {
        this.from = from;
        this.to = to;
        this.friendship = new Friendship();
        this.blockRelationship = new BlockRelationship();
    }

    void setFriendRequestSent(final boolean isOppositeBlocking) {
        if (this.blockRelationship.isBlocking()) {
            this.setBlockRelationshipUnblocked(isOppositeBlocking);
        }

        this.friendship.setFriendRequestSent();
    }

    void setFriendRequestReceived(final boolean gotUnblocked) {
        if (gotUnblocked) {
            this.setBlockRelationshipGotUnblocked();
        }

        this.friendship.setFriendRequestReceived();
    }

    void setFriendRequestCanceled() {
        this.friendship.setFriendRequestCanceled();
    }

    void setFriendRequestGotCanceled() {
        this.friendship.setFriendRequestGotCanceled();
    }

    public void setFriendRequestAccepted() {
        this.friendship.setFriendRequestAccepted();
    }

    public void setFriendRequestGotAccepted() {
        this.friendship.setFriendRequestGotAccepted();
    }

    public void setFriendRequestDeclined() {
        this.friendship.setFriendRequestDeclined();
    }

    public void setFriendRequestGotDeclined() {
        this.friendship.setFriendRequestGotDeclined();
    }

    void setBlockRelationshipBlocked() {
        this.blockRelationship.setBlocked();
        this.friendship.clear();
        this.friendship.ignore();
    }

    void setBlockRelationshipGotBlocked() {
        this.blockRelationship.setGotBlocked();
        this.friendship.getCleared();
        this.friendship.ignore();
    }

    void setBlockRelationshipUnblocked(final boolean isOppositeBlocking) {
        this.blockRelationship.setUnblocked(isOppositeBlocking);
        this.friendship.clear();
    }

    void setBlockRelationshipGotUnblocked() {
        this.blockRelationship.setGotUnblocked();
        this.friendship.getCleared();
    }

    boolean isBlocking() {
        return this.blockRelationship.isBlocking();
    }

    boolean areFromAndToEqualTo(final String from, final String to) {
        return Objects.equals(this.from, from) && Objects.equals(this.to, to);
    }
}
