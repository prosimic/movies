package com.seraphion.movies.model;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Response",
        "Search",
        "totalResults",
        "Error"
})
@Generated("jsonschema2pojo")
public class Response {
    @JsonProperty("Search")
    private List<Search> search;

    @JsonProperty("totalResults")
    private String totalResults;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    private String error;

    public Response() {
    }

    @JsonProperty("Search")
    public List<Search> getSearch() {
        return search;
    }

    @JsonProperty("Search")
    public void setSearch(List<Search> search) {
        this.search = search;
    }

    @JsonProperty("totalResults")
    public String getTotalResults() {
        return totalResults;
    }

    @JsonProperty("totalResults")
    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    @JsonProperty("Response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("Response")
    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("Error")
    public String getError() {
        return error;
    }

    @JsonProperty("Error")
    public void setError(String error) {
        this.error = error;
    }
}
