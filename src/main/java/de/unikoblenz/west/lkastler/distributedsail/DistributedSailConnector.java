package de.unikoblenz.west.lkastler.distributedsail;

import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;

import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationSender;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;
/**
 * connects a SAIL storage with the middleware
 * @author lkastler
 */
public class DistributedSailConnector {
	
	protected Sail sail;
	protected MiddlewareServiceProvider<?,?> provider;
	// TODO implement notification system.
	protected NotificationSender<Notification> notifications;
	
	/**
	 * creates a DistributedSailConnector that connects given Sail implementation to the middleware by given MiddlewareServiceProvider.
	 * @param sail - implementation of the SAIL API to connect to the middleware. 
	 * @param provider - provides the connection to the middleware.
	 */
	public DistributedSailConnector(Sail sail, MiddlewareServiceProvider<?,?> provider) {
		this.sail = sail;
		this.provider = provider;
	}
	
	/**
	 * starts this DistributedSailConnector, initializing the SAIL implementation and connecting it to the middleware.
	 * @throws SailException - thrown if SAIL implementation could not be started.
	 */
	public void start() throws SailException {
		provider.start();
		sail.initialize();
	}
	
	/**
	 * stops this DistributedSailConnection and disconnects it from the middleware.
	 * @throws SailException - thrown if SAIL implementation could not be shut down.
	 */
	public void stop() throws SailException {
		provider.stop();
		sail.shutDown();
	}
}

