package de.unikoblenz.west.lkastler.distributedsail;

import java.io.File;
import java.util.LinkedList;

import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.base.RepositoryBase;
import org.openrdf.repository.event.NotifyingRepository;
import org.openrdf.repository.event.RepositoryConnectionListener;
import org.openrdf.repository.event.RepositoryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implementation of the OpenRDF Repository API.
 * 
 * @author lkastler
 * 
 */
public class DistributedRepository extends RepositoryBase implements NotifyingRepository  {

	protected final Logger log = LoggerFactory.getLogger(DistributedRepository.class);
	
	protected DistributedRepositoryConnection connection = null;
		
	protected final MiddlewareServiceFactory factory;
	
	protected LinkedList<RepositoryListener> listeners = new LinkedList<RepositoryListener>();
	
	public DistributedRepository(final MiddlewareServiceFactory factory) {
		super();
		this.factory = factory;
	}
	
	public void setDataDir(File dataDir) {
		// TODO implement Repository.setDataDir
		throw new UnsupportedOperationException("implement Repository.setDataDir !");
	}

	public File getDataDir() {
		// TODO implement Repository.getDataDir
		throw new UnsupportedOperationException("implement Repository.getDataDir !");
	}

	public boolean isWritable() throws RepositoryException {
		// TODO implement Repository.isWritable
		throw new UnsupportedOperationException("implement Repository.isWritable !");
	}

	public RepositoryConnection getConnection() throws RepositoryException {
		if(connection == null) {
			connection = new DistributedRepositoryConnection(this, factory);
		}
		
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.Repository#getValueFactory()
	 */
	public ValueFactory getValueFactory() {
		
		return ValueFactoryImpl.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.event.NotifyingRepository#addRepositoryListener(org.openrdf.repository.event.RepositoryListener)
	 */
	public void addRepositoryListener(RepositoryListener listener) {
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.event.NotifyingRepository#removeRepositoryListener(org.openrdf.repository.event.RepositoryListener)
	 */
	public void removeRepositoryListener(RepositoryListener listener) {
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.event.NotifyingRepository#addRepositoryConnectionListener(org.openrdf.repository.event.RepositoryConnectionListener)
	 */
	public void addRepositoryConnectionListener(
			RepositoryConnectionListener listener) {
		// TODO implement NotifyingRepository.addRepositoryConnectionListener
		throw new UnsupportedOperationException("implement NotifyingRepository.addRepositoryConnectionListener !");
	}

	public void removeRepositoryConnectionListener(
			RepositoryConnectionListener listener) {
		// TODO implement NotifyingRepository.removeRepositoryConnectionListener
		throw new UnsupportedOperationException("implement NotifyingRepository.removeRepositoryConnectionListener !");
	}

	@Override
	protected void initializeInternal() throws RepositoryException {
		log.debug("initialized");
	}

	@Override
	protected void shutDownInternal() throws RepositoryException {
		log.debug("shut down");
	}

}
