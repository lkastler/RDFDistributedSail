package de.unikoblenz.west.lkastler.distributedsail.middleware.handler;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailResponse;

/**
 * simple logger on sail side
 * @author lkastler
 */
public class SailLoggingHandler<R extends SailRequest, S extends SailResponse> extends
		LoggingHandler<R, S> {

	/**
	 * creates logging handler with default Response.
	 * @param defaultResponse - default response sent to everyone.
	 */
	public SailLoggingHandler(S defaultResponse) {
		super(defaultResponse);
	}

}
