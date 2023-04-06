/*******************************************************************************
 * Copyright 2016, 2017 vanilladb.org contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.vanilladb.core.query.algebra;

import org.vanilladb.core.sql.Constant;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.VarcharConstant;

/**
 * The scan class corresponding to the <em>product</em> relational algebra
 * operator.
 */
public class ExplainScan implements Scan {
	private boolean executed;
	private int Records;
	private String result;
	private Schema schema;
	private ExplainTree explain;

	public ExplainScan(Scan s1, Schema schema, ExplainTree tree) {
		this.schema = schema;
		this.executed = false;
		s1.beforeFirst();
		this.explain = tree;
		this.Records = 0;
		while(s1.next()) this.Records++;
		s1.close();
		this.result = generate();
	}

	@Override
	public void beforeFirst() {
		this.executed = false;
	}

	@Override
	public boolean next() {
		if(this.executed) return false;
		this.executed = true;
		return true;
	}

	private String generate() {
		String ret = RecursiveGenerate(0, this.explain);
		ret = ret + "\nActual #recs: " + this.Records;
		return ret;
	}

	private String RecursiveGenerate(int depth, ExplainTree tree) {
		String ret = "";
		for(int i = 0; i < depth; i++) ret = ret + "\t";
		ret = ret + "->" + tree.getPlanType();
		if(tree.getDetails() != null) ret = ret + " " + tree.getDetails();
		ret = ret + " (#blks=" + tree.getBlocks() + ", #recs=" + tree.getRecs() + ")\n";
		for(ExplainTree child: tree.getChildren()) {
			ret = ret + RecursiveGenerate(depth + 1, child);
		}
		return ret;
	}

	@Override
	public void close() {
		//hello, i dont know what i should do here, so I just do this
	}

	@Override
	public Constant getVal(String fldName) {
		if(fldName.equals("query-plan")) {
			return new VarcharConstant(result);
		}
		else {
			throw new RuntimeException("field " + fldName + "not found\n");
		}
	}

	@Override
	public boolean hasField(String fldName) {
		return schema.hasField(fldName);
	}
}
