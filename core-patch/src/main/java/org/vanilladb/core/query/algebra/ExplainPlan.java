package org.vanilladb.core.query.algebra;

import java.util.Set;

import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.storage.metadata.statistics.Histogram;

public class ExplainPlan implements Plan {
	private Plan p;

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
		// TODO: return new EaxplainScan(s, ???);
		return s;
	}

	@Override
	public long blocksAccessed() {
		return p.blocksAccessed();
	}

	@Override
	public Schema schema() {
		Schema schema = new Schema();
		schema.addField("query-plan", Type.VARCHAR(500));
		return schema;
	}

	@Override
	public Histogram histogram() {
		return p.histogram();
	}

	@Override
	public long recordsOutput() {
		return 1;
	}
}
