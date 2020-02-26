package relationship.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RelationshipManager {
    private final List<Relationship> relationships = new ArrayList<>();

    public void sendFriendRequest(final String from, final String to) {
        final Relationship fromTo = this.findRelationshipByFromAndTo(from, to)
                .orElse(new Relationship(from, to));
        final Relationship toFrom = this.findRelationshipByFromAndTo(to, from)
                .orElse(new Relationship(to, from));

        final boolean blocking = fromTo.isBlocking();
        fromTo.setFriendRequestSent(toFrom.isBlocking());
        toFrom.setFriendRequestReceived(blocking);

        this.save(fromTo);
        this.save(toFrom);
    }

    public void cancelFriendRequest(final String from, final String to) {
        final Relationship fromTo = this.findRelationshipByFromAndTo(from, to).orElseThrow(RuntimeException::new);
        final Relationship toFrom = this.findRelationshipByFromAndTo(to, from).orElseThrow(RuntimeException::new);

        fromTo.setFriendRequestCanceled();
        toFrom.setFriendRequestGotCanceled();

        this.save(fromTo);
        this.save(toFrom);
    }

    public void acceptFriendRequest(final String from, final String to) {
        final Relationship fromTo = this.findRelationshipByFromAndTo(from, to).orElseThrow(RuntimeException::new);
        final Relationship toFrom = this.findRelationshipByFromAndTo(to, from).orElseThrow(RuntimeException::new);

        fromTo.setFriendRequestAccepted();
        toFrom.setFriendRequestGotAccepted();

        this.save(fromTo);
        this.save(toFrom);
    }

    public void declineFriendRequest(final String from, final String to) {
        final Relationship fromTo = this.findRelationshipByFromAndTo(from, to).orElseThrow(RuntimeException::new);
        final Relationship toFrom = this.findRelationshipByFromAndTo(to, from).orElseThrow(RuntimeException::new);

        fromTo.setFriendRequestDeclined();
        toFrom.setFriendRequestGotDeclined();

        this.save(fromTo);
        this.save(toFrom);
    }


    public void block(final String from, final String to) {
        final Relationship fromTo = this.findRelationshipByFromAndTo(from, to)
                .orElse(new Relationship(from, to));
        final Relationship toFrom = this.findRelationshipByFromAndTo(to, from)
                .orElse(new Relationship(from, to));

        fromTo.setBlockRelationshipBlocked();
        toFrom.setBlockRelationshipGotBlocked();

        this.save(fromTo);
        this.save(toFrom);
    }

    public void unblock(final String from, final String to) {
        final Relationship fromTo = this.findRelationshipByFromAndTo(from, to).orElseThrow(RuntimeException::new);
        final Relationship toFrom = this.findRelationshipByFromAndTo(to, from).orElseThrow(RuntimeException::new);

        fromTo.setBlockRelationshipUnblocked(toFrom.isBlocking());
        toFrom.setBlockRelationshipGotUnblocked();

        this.save(fromTo);
        this.save(toFrom);
    }

    /**
     * Repository
     */
    Optional<Relationship> findRelationshipByFromAndTo(final String from, final String to) {
        return this.relationships.stream()
                .filter(relationship -> relationship.areFromAndToEqualTo(from, to))
                .findAny();
    }

    private void save(final Relationship relationship) {
        if (this.findRelationshipByFromAndTo(relationship.getFrom(), relationship.getTo()).isPresent()) {
            return;
        }

        this.relationships.add(relationship);
    }
}
