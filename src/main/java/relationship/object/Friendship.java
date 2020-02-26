package relationship.object;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class Friendship {
    private FriendshipState state;
    private LocalDateTime requestedAt;
    private LocalDateTime friendedAt;
    private LocalDateTime unfriendedAt;

    public Friendship() {
        this.state = FriendshipState.NONE;
    }

    void sendFriendRequestTo(final Friendship target) {
        if (this.didSendFriendRequestTo(target)) {
            throw new RuntimeException("Already sent friend request");
        }

        if (this.didReceiveFriendRequestFrom(target)) {
            throw new RuntimeException("Already received friend request");
        }

        if (this.isFriendWith(target)) {
            throw new RuntimeException("Already friend");
        }

        this.state = FriendshipState.SENT_REQUEST;
        this.requestedAt = LocalDateTime.now();

        if (!target.state.isIgnoring()) {
            target.state = FriendshipState.RECEIVED_REQUEST;
        }
    }

    void cancelFriendRequestTo(final Friendship target) {
        if (!this.didSendFriendRequestTo(target)) {
            throw new RuntimeException("Didn't send friend request");
        }

        this.state = FriendshipState.CANCELED;
        target.state = FriendshipState.GOT_CANCELED;
    }

    void acceptFriendRequestFrom(final Friendship target) {
        if (!this.didReceiveFriendRequestFrom(target)) {
            throw new RuntimeException("Didn't receive friend request");
        }

        this.state = FriendshipState.FRIEND;
        this.friendedAt = LocalDateTime.now();
        target.state = FriendshipState.FRIEND;
        target.friendedAt = LocalDateTime.now();
    }

    void declineFriendRequestFrom(final Friendship target) {
        if (!this.didReceiveFriendRequestFrom(target)) {
            throw new RuntimeException("Didn't receive friend request");
        }

        this.state = FriendshipState.DECLINED;
        target.state = FriendshipState.GOT_DECLINED;
    }

    void unfriend(final Friendship target) {
        if (!this.isFriendWith(target)) {
            throw new RuntimeException();
        }

        this.state = FriendshipState.UNFRIENDED;
        this.unfriendedAt = LocalDateTime.now();
        target.state = FriendshipState.GOT_UNFRIENDED;
        target.unfriendedAt = LocalDateTime.now();
    }

    void clearFriendshipWith(final Friendship target) {
        if (this.isFriendWith(target)) {
            this.unfriend(target);
            return;
        }

        if (this.didSendFriendRequestTo(target)) {
            this.cancelFriendRequestTo(target);
            return;
        }

        if (this.didReceiveFriendRequestFrom(target)) {
            this.declineFriendRequestFrom(target);
            return;
        }
    }

    void ignore() {
        this.state = FriendshipState.IGNORED;
    }

    private boolean isFriendWith(final Friendship target) {
        return this.state.isFriend() && target.state.isFriend();
    }

    private boolean didSendFriendRequestTo(final Friendship target) {
        return this.state.isSentFriendRequest() || target.state.isReceivedFriendRequest();
    }

    private boolean didReceiveFriendRequestFrom(final Friendship target) {
        return this.state.isReceivedFriendRequest() || target.state.isSentFriendRequest();
    }
}
