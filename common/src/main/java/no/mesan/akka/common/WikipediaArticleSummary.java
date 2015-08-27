package no.mesan.akka.common;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnfinng on 27/08/2015.
 */
public class WikipediaArticleSummary implements Serializable {
    private String title;
    private String url;
    private String summary;
    private String image;
    private List<WikipediaArticleSummary> linkResults = new ArrayList<>();

    public WikipediaArticleSummary(String url) {
        this.url = url;
    }

    public WikipediaArticleSummary(String url, String title, String summary, String image) {
        this.url = url;
        this.title = title;
        this.summary = summary;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLinkResults(List<WikipediaArticleSummary> linkResults) {
        this.linkResults = linkResults;
    }

    public List<WikipediaArticleSummary> getLinkResults() {
        return linkResults;
    }
}
