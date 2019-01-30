package com.example.georgekimutai.muvileo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class mResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Muvy> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage(){
        return page;
    }
    public void setPage(int page){
        this.page=page;
    }
    public List<Muvy> getResults(){
        return results;
    }
    public void setResults(List<Muvy> results){
        this.results=results;
    }
    public int getTotalResults(){
        return totalResults;
    }
    public void setTotalResults(int totalResults){
        this.totalResults=totalResults;
    }
    public int getTotalPages(){
        return totalPages;
    }
    public void setTotalPages(int totalPages){
        this.totalPages=totalPages;
    }
}
