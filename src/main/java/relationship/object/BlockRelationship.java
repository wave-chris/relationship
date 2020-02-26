package relationship.object;

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

    void block(final BlockRelationship target) {
        if (this.state.isBlocking()) {
            throw new RuntimeException("Already blocking");
        }

        if (target.state.isBlockedBy()) {
            throw new RuntimeException("Already blocked");
        }

        this.state = BlockRelationshipState.BLOCKED;
        this.blockedAt = LocalDateTime.now();

        if (!target.state.isBlocking()) {
            target.state = BlockRelationshipState.GOT_BLOCKED;
        }
        target.gotBlockedAt = LocalDateTime.now();
    }

    void unblock(final BlockRelationship target) {
        if (!this.state.isBlocking()) {
            throw new RuntimeException("Didn't block");
        }

        if (!target.state.isBlocking() && !target.state.isBlockedBy()) {
            throw new RuntimeException("Didn't get blocked");
        }

        this.state = (target.state.isBlocking() ? BlockRelationshipState.GOT_BLOCKED : BlockRelationshipState.UNBLOCKED);
        this.unblockedAt = LocalDateTime.now();

        if (!target.state.isBlocking()) {
            target.state = BlockRelationshipState.GOT_UNBLOCKED;
        }
        target.gotUnblockedAt = LocalDateTime.now();
    }

    boolean isBlocking() {
        return this.state.isBlocking();
    }
}
