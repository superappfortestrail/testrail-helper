package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class TestCaseResults  extends BaseEntity implements Serializable {
    @JsonProperty("results")
    private List<TestCaseResult> results = new ArrayList<TestCaseResult>();
    public List<TestCaseResult> getResults() { return results; }
    public void setResults(List<TestCaseResult> results) { this.results = results; }

    /**
     * Allows you to add a test result to the list of results that will be posted
     * @param result
     */
    public void addResult(TestCaseResult result) {
        results.add(result);
    }
}
