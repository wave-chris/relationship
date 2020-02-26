package relationship.state;

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

    void setFriendRequestSent() {
        if (this.state.isSentFriendRequest()) {
            throw new RuntimeException("Already sent");
        }

        if (this.state.isReceivedFriendRequest()) {
            throw new RuntimeException("Already received");
        }

        if (this.state.isFriend()) {
            throw new RuntimeException("Already friend");
        }

        this.state = FriendshipState.SENT_REQUEST;
        this.requestedAt = LocalDateTime.now();
    }

    void setFriendRequestReceived() {
        if (this.state.isSentFriendRequest()) {
            throw new RuntimeException("Already sent");
        }

        if (this.state.isReceivedFriendRequest()) {
            throw new RuntimeException("Already received");
        }

        if (this.state.isFriend()) {
            throw new RuntimeException("Already friend");
        }

        if (this.state.isIgnoring()) {
            return;
        }

        this.state = FriendshipState.RECEIVED_REQUEST;
    }

    void setFriendRequestCanceled() {
        if (!this.state.isSentFriendRequest()) {
            throw new RuntimeException("Did't sent friend request");
        }

        this.state = FriendshipState.CANCELED;
    }

    void setFriendRequestGotCanceled() {
        if (!this.state.isReceivedFriendRequest()) {
            throw new RuntimeException("Did't receive friend request");
        }

        this.state = FriendshipState.GOT_CANCELED;
    }

    void setFriendRequestAccepted() {
        if (!this.state.isReceivedFriendRequest()) {
            throw new RuntimeException("Did't receive friend request");
        }

        this.state = FriendshipState.FRIEND;
        this.friendedAt = LocalDateTime.now();
    }

    void setFriendRequestGotAccepted() {
        if (!this.state.isReceivedFriendRequest()) {
            throw new RuntimeException("Did't receive friend request");
        }

        this.state = FriendshipState.FRIEND;
        this.friendedAt = LocalDateTime.now();
    }

    void setFriendRequestDeclined() {
        if (!this.state.isReceivedFriendRequest()) {
            throw new RuntimeException("Did't receive friend request");
        }

        this.state = FriendshipState.DECLINED;
    }

    void setFriendRequestGotDeclined() {
        if (!this.state.isSentFriendRequest()) {
            throw new RuntimeException("Did't send friend request");
        }

        this.state = FriendshipState.GOT_DECLINED;
    }

    void setUnfriended() {
        if (!this.state.isFriend()) {
            throw new RuntimeException("Not friend");
        }

        this.state = FriendshipState.UNFRIENDED;
        this.unfriendedAt = LocalDateTime.now();
    }

    void setGotUnfriended() {
        if (!this.state.isFriend()) {
            throw new RuntimeException("Not friend");
        }

        this.state = FriendshipState.GOT_UNFRIENDED;
        this.unfriendedAt = LocalDateTime.now();
    }

    void clear() {
        if (this.state.isFriend()) {
            this.setUnfriended();
            return;
        }

        if (this.state.isSentFriendRequest()) {
            this.setFriendRequestCanceled();
            return;
        }

        if (this.state.isReceivedFriendRequest()) {
            this.setFriendRequestDeclined();
            return;
        }

        if (this.state.isIgnoring()) {
            this.state = FriendshipState.NONE;
        }
    }

    public void getCleared() {
        if (this.state.isFriend()) {
            this.setGotUnfriended();
            return;
        }

        if (this.state.isSentFriendRequest()) {
            this.setFriendRequestGotCanceled();
            return;
        }

        if (this.state.isReceivedFriendRequest()) {
            this.setFriendRequestGotDeclined();
            return;
        }
    }

    public void ignore() {
        this.state = FriendshipState.IGNORED;
    }
}
