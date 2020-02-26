package relationship.object;

import lombok.Getter;

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

    public void sendFriendRequestTo(final Relationship target) {
        if (this.blockRelationship.isBlocking()) {
            this.unblock(target);
        }

        this.friendship.sendFriendRequestTo(target.friendship);
    }

    public void cancelFriendRequestTo(final Relationship target) {
        this.friendship.cancelFriendRequestTo(target.friendship);
    }

    public void acceptFriendRequestFrom(final Relationship target) {
        this.friendship.acceptFriendRequestFrom(target.friendship);
    }

    public void declineFriendRequestFrom(final Relationship target) {
        this.friendship.declineFriendRequestFrom(target.friendship);
    }

    public void block(final Relationship target) {
        this.blockRelationship.block(target.blockRelationship);
        this.friendship.clearFriendshipWith(target.friendship);
        this.friendship.ignore();
    }

    public void unblock(final Relationship target) {
        this.blockRelationship.unblock(target.blockRelationship);
        this.friendship.clearFriendshipWith(target.friendship);
    }
}
