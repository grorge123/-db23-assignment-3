package org.vanilladb.core.query.algebra;

import java.util.Set;

import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.storage.metadata.statistics.Histogram;

public class ExplainPlan implements Plan {
	private Plan p;
	private Schema dummy_schema;
	private Histogram dummy_hist;
    private long dummy_recordsOutput;
 
    /** Impl note:
	 * It's for sure that ExplainPlan has no parent plan, 
     * and no one will call for it's hist and schema.
     * Therefore keeping them dummy is enough.
	 */

	/**
	 * Creates a new explain node in the query tree, having nothing.
     * The result string is stored in ExplainScan.
	 * 
	 * @param p
	 *            the subquery
	 */
	public ExplainPlan(Plan p) {
		this.p = p;
	}

	/**
	 * Creates a explain scan for this query.
	 * 
	 * @see Plan#open()
	 */
	@Override
	public Scan open() {
		Scan s = p.open();
		// TODO: return new ExplainScan(s, ???);
	}

	@Override
	public long blocksAccessed() {
		return p.blocksAccessed();
	}

	@Override
	public Schema schema() {
		return dummy_schema;
	}

	@Override
	public Histogram histogram() {
		return dummy_hist;
	}

	@Override
	public long recordsOutput() {
		return dummy_recordsOutput;
	}
}
