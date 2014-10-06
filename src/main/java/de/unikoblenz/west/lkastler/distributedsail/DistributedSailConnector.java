package de.unikoblenz.west.lkastler.distributedsail;

import java.util.LinkedList;
import java.util.List;

import info.aduna.iteration.CloseableIteration;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.DefaultSailResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.MiddlewareNotificationFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;

/**
 * connects a SAIL storage with the middleware
 * 
 * @author lkastler
 */
public class DistributedSailConnector implements
		ServiceHandler<SailInsertionRequest, DefaultSailResponse> {

	private static final Logger log = LoggerFactory
			.getLogger(DistributedSailConnector.class);

	protected final Sail sail;
	protected SailConnection sailConnect;
	protected String id;

	protected ServiceProvider<SailInsertionRequest, DefaultSailResponse> provider;

	// TODO implement notification system.

	/**
	 * creates a DistributedSailConnector that connects given Sail
	 * implementation to the middleware by given MiddlewareServiceProvider.
	 * 
	 * @param sail
	 *            - implementation of the SAIL API to connect to the middleware.
	 * @param provider
	 *            - provides the connection to the middleware.
	 * @throws SailException
	 *             - thrown if needed services could not be created
	 */
	public DistributedSailConnector(Sail sail,
			MiddlewareServiceFactory services,
			MiddlewareNotificationFactory notifications) throws SailException {
		
		this("", sail, services, notifications);
	}

	public DistributedSailConnector(String id, Sail sail,
			MiddlewareServiceFactory services,
			MiddlewareNotificationFactory notifications) throws SailException {
		
		this.id = id;
		this.sail = sail;

		try {
			provider = services.createServiceProvider(Configurator.CHANNEL_SAIL
					+ id, this);
		} catch (MiddlewareServiceException e) {
			throw new SailException(e);
		}
		log.debug("created");
	}

	/**
	 * starts this DistributedSailConnector, initializing the SAIL
	 * implementation and connecting it to the middleware.
	 * 
	 * @throws SailException
	 *             - thrown if SAIL implementation could not be started.
	 */
	public void start() throws SailException {
		log.debug("starting");

		sail.initialize();

		sailConnect = sail.getConnection();

		provider.start();

		log.debug("started");
	}

	/**
	 * stops this DistributedSailConnection and disconnects it from the
	 * middleware.
	 * 
	 * @throws SailException
	 *             - thrown if SAIL implementation could not be shut down.
	 */
	public void stop() throws SailException {
		log.debug("stopping");

		provider.stop();
		sailConnect.close();
		sail.shutDown();

		log.debug("stopped");
	}

	public DefaultSailResponse handleRequest(SailInsertionRequest request)
			throws Throwable {
		log.debug("[" + id + "] handle request: " + request);

		if (!sailConnect.isActive()) {
			sailConnect.begin();
			sailConnect.addStatement(request.getSubject(),
					request.getPredicate(), request.getObject(),
					new Resource[0]);
			sailConnect.commit();
		}

		return new DefaultSailResponse();
	}
	
	public List<String> getStoredTriples() throws SailException {
		LinkedList<String> result = new LinkedList<String>();
		
		log.debug("getting info from store");
		
		CloseableIteration<? extends Statement, SailException> statements = sailConnect.getStatements(null, null, null, false, new Resource[0]);
		
		while(statements.hasNext()) {
			Statement st = statements.next();
			result.add(st.toString());
		}
		
		statements.close();
		
		return result;
	}
}
