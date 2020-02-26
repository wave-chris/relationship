package relationship.state;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class BlockRelationship {
    private BlockRelationshipState state;
    private LocalDateTime blockedAt;
    private LocalDateTime gotBlockedAt;
    private LocalDateTime unblockedAt;
    private LocalDateTime gotUnblockedAt;

    public BlockRelationship() {
        this.state = BlockRelationshipState.NONE;
    }

    void setBlocked() {
        if (this.state.isBlocking()) {
            throw new RuntimeException("Already blocking");
        }

        this.state = BlockRelationshipState.BLOCKED;
        this.blockedAt = LocalDateTime.now();
    }

    void setGotBlocked() {
        if (this.state.isBlockedBy()) {
            throw new RuntimeException("Already blocked by");
        }

        if (!this.state.isBlocking()) {
            this.state = BlockRelationshipState.GOT_BLOCKED;
        }
        this.gotBlockedAt = LocalDateTime.now();
    }

    void setUnblocked(final boolean isOppositeBlocking) {
        if (!this.state.isBlocking()) {
            throw new RuntimeException("Didn't blocked");
        }

        this.state = (isOppositeBlocking ? BlockRelationshipState.GOT_BLOCKED : BlockRelationshipState.UNBLOCKED);
        this.unblockedAt = LocalDateTime.now();
    }

    void setGotUnblocked() {
        if (!this.state.isBlockedBy() && !this.state.isBlocking()) {
            throw new RuntimeException("Didn't get blocked");
        }

        if (!this.state.isBlocking()) {
            this.state = BlockRelationshipState.GOT_UNBLOCKED;
        }
        this.gotUnblockedAt = LocalDateTime.now();
    }

    boolean isBlocking() {
        return this.state.isBlocking();
    }
}
