package com.hascode.tutorial.boundary;

import java.util.Collections;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import com.hascode.tutorial.entity.TrainStation;

public class TrainTimetableExample {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new SessionFactory("com.hascode");
        final Session session = sessionFactory.openSession();

        TrainStation london = new TrainStation("London");
        TrainStation brighton = new TrainStation("Brighton");
        TrainStation portsmouth = new TrainStation("Portsmouth");
        TrainStation bristol = new TrainStation("Bristol");
        TrainStation oxford = new TrainStation("Oxford");
        TrainStation gloucester = new TrainStation("Gloucester");
        TrainStation northampton = new TrainStation("Northampton");
        TrainStation southampton = new TrainStation("Southampton");

        london.addConnection(brighton, 52);
        brighton.addConnection(portsmouth, 49);
        portsmouth.addConnection(southampton, 20);
        london.addConnection(oxford, 95);
        oxford.addConnection(southampton, 66);
        oxford.addConnection(northampton, 45);
        northampton.addConnection(bristol, 114);
        southampton.addConnection(bristol, 77);
        northampton.addConnection(gloucester, 106);
        gloucester.addConnection(bristol, 35);

        session.save(london);
        session.save(brighton);
        session.save(portsmouth);
        session.save(bristol);
        session.save(oxford);
        session.save(gloucester);
        session.save(northampton);
        session.save(southampton);

        System.out.println(session.countEntitiesOfType(TrainStation.class) + " stations saved");
        getRoute("London", "Bristol", session);
        getRoute("London", "Southampton", session);
    }

    private static void getRoute(final String from, final String destination, final Session session) {
        System.out.printf("searching for the shortest route from %s to %s..\n", from, destination);
        final Iterable<TrainStation> stops = session.query(TrainStation.class,
                "MATCH (from:TrainStation {name:'" + from + "'}), (to:TrainStation {name:'" + destination
                        + "'}), path=shortestPath((from)-[:LEADS_TO*]->(to)) RETURN path",
                Collections.<String, TrainStation> emptyMap());
        System.out.printf("shortest way from %s to %s via\n", from, destination);
        stops.forEach((stop) -> {
            System.out.println(stop.getName());
        });
    }
}
