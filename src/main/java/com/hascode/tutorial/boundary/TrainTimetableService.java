package com.hascode.tutorial.boundary;

import java.util.Collections;

import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import com.hascode.tutorial.entity.TrainStation;

public class TrainTimetableService {
	public static void main(String[] args) {
		final String from = "London";
		final String destination = "Bristol";

		System.out.println("searching for the shortest route from " + from + " to " + destination + "..");

		SessionFactory sessionFactory = new SessionFactory("com.hascode");
		final Session session = sessionFactory.openSession("http://localhost:7474");

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

		final Iterable<WeightedPath> paths = session.query(WeightedPath.class,
				"MATCH (from:TrainStation {name:'" + from + "'}), (to:TrainStation {name:'" + destination
						+ "'}), path=shortestPath((from)-[:LEADS_TO*]->(to)) RETURN path",
				Collections.<String, TrainStation> emptyMap());
		paths.forEach((path) -> {
			System.out.println("PATH " + path.weight());
			path.nodes().forEach((node) -> {
				System.out.println(node.getProperty("name"));
			});
		});
	}
}
