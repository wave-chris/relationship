package relationship.state;

import java.util.Objects;

public enum BlockRelationshipState {
    NONE,
    BLOCKED,
    GOT_BLOCKED,
    UNBLOCKED,
    GOT_UNBLOCKED;

    boolean isBlocking() {
        return Objects.equals(this, BLOCKED);
    }

    boolean isBlockedBy() {
        return Objects.equals(this, GOT_BLOCKED);
    }
}
