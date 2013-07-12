package pl.edu.mimuw.graphs.api;

import org.neo4j.graphdb.RelationshipType;

public enum GraphRelationshipType implements RelationshipType {

	CONTAINS, CALLS
}
