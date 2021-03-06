package de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository;

import de.unikoblenz.west.rdf.distributedsail.middleware.services.Request;

/**
 * a Request that contains a insertion task.
 * @author lkastler
 */
public class InsertionRequest implements Request {

	/** */
	private static final long serialVersionUID = 1L;

	private final long id;
	
	public InsertionRequest(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

}
