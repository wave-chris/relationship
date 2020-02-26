package relationship.state;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        final String from = UUID.randomUUID().toString();
        final String to = UUID.randomUUID().toString();

        final RelationshipManager relationshipManager = new RelationshipManager();

        relationshipManager.block(from, to);
        relationshipManager.block(to, from);
        relationshipManager.sendFriendRequest(from, to);
        relationshipManager.sendFriendRequest(to, from);

        relationshipManager.acceptFriendRequest(from, to);

        final Relationship fromTo = relationshipManager.findRelationshipByFromAndTo(from, to).get();
        final Relationship toFrom = relationshipManager.findRelationshipByFromAndTo(to, from).get();

        System.out.println(fromTo.getFriendship());
        System.out.println(fromTo.getBlockRelationship());
        System.out.println("---------------");
        System.out.println(toFrom.getFriendship());
        System.out.println(toFrom.getBlockRelationship());
    }
}
