package org.vanilladb.core.query.algebra;

import java.util.ArrayList;
import java.util.List;

public class ExplainTree {
    private List<ExplainTree> children;
    private String planType;
    private String details;
    private long blocks;
    private long recs;

    public ExplainTree(String type, String details, long blocks, long records) {
        this.children = new ArrayList<>();
        this.planType = type;
        this.details = details;
        this.blocks = blocks;
        this.recs = records;
    }

    public void addChildren(ExplainTree child) {
        this.children.add(child);
    }

    public List<ExplainTree> getChildren() {
        return this.children;
    }

    public String getPlanType() {
        return this.planType;
    }

    public String getDetails() {
        return this.details;
    }

    public long getBlocks() {
        return this.blocks;
    }

    public long getRecs() {
        return this.recs;
    }
}
