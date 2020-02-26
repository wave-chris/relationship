package relationship.state;

import java.util.Objects;

public enum FriendshipState {
    NONE,
    SENT_REQUEST,
    RECEIVED_REQUEST,
    FRIEND,
    IGNORED,
    CANCELED,
    GOT_CANCELED,
    DECLINED,
    GOT_DECLINED,
    UNFRIENDED,
    GOT_UNFRIENDED;

    boolean isSentFriendRequest() {
        return Objects.equals(this, SENT_REQUEST);
    }

    boolean isReceivedFriendRequest() {
        return Objects.equals(this, RECEIVED_REQUEST);
    }

    boolean isFriend() {
        return Objects.equals(this, FRIEND);
    }

    boolean isIgnoring() {
        return Objects.equals(this, IGNORED);
    }
}
