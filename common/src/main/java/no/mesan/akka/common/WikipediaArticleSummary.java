package no.mesan.akka.common;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
/**
 * Created by arnfinng on 27/08/2015.
 */
public class WikipediaArticleSummary implements Serializable {
    private String title;
    private String url;
    private String summary;
    private Image image;
    private List<WikipediaArticleSummary> linkResults;

    public WikipediaArticleSummary(String url){
        this.url = url;
    }

    public WikipediaArticleSummary(String url, String title, String summary, Image image){
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
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isDone(){
        return image != null && !linkResults.isEmpty() && summary != null;
    }
    public void setLinkResults(List<WikipediaArticleSummary> linkResults) {
        this.linkResults = linkResults;
    }
}
