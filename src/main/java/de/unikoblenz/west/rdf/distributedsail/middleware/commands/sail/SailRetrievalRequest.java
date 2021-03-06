package de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail;

import java.util.Random;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

import de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository.RepositoryRetrievalRequest;

/**
 * implements the SailRequest interface for retrieval tasks.
 * 
 * @author lkastler
 */
public class SailRetrievalRequest implements SailRequest {

	/** */
	private static final long serialVersionUID = 1L;

	private static final Random rand = new Random(System.currentTimeMillis());
	
	private final long id;
	
	protected Resource subject;
	protected URI predicate;
	protected Value object;
	
	/**
	 * TODO add doc
	 * @param req
	 * @return
	 */
	public static SailRetrievalRequest create(RepositoryRetrievalRequest req){
		return new SailRetrievalRequest(req.getSubject(), req.getPredicate(), req.getObject());
	}

	/**
	 * @param subject
	 * @param predicate
	 * @param object
	 */
	protected SailRetrievalRequest(Resource subject, URI predicate, Value object) {
		super();
		
		id = rand.nextLong();
		
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	/**
	 * @return the subject
	 */
	public Resource getSubject() {
		return subject;
	}

	/**
	 * @return the predicate
	 */
	public URI getPredicate() {
		return predicate;
	}

	/**
	 * @return the object
	 */
	public Value getObject() {
		return object;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SailRetrievalRequest [subject=" + subject + ", predicate="
				+ predicate + ", object=" + object + "]";
	}

	public long getId() {
		return id;
	}
}
