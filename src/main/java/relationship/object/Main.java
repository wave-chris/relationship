package relationship.object;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        final String from = UUID.randomUUID().toString();
        final String to = UUID.randomUUID().toString();

        final Relationship fromTo = new Relationship(from, to);
        final Relationship toFrom = new Relationship(to, from);

        fromTo.block(toFrom);
        toFrom.block(fromTo);
        fromTo.sendFriendRequestTo(toFrom);
        toFrom.sendFriendRequestTo(fromTo);

        System.out.println(fromTo.getFriendship());
        System.out.println(fromTo.getBlockRelationship());
        System.out.println("---------------");
        System.out.println(toFrom.getFriendship());
        System.out.println(toFrom.getBlockRelationship());
    }
}
