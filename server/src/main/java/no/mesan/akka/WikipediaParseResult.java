package no.mesan.akka;
import java.awt.*;
import java.util.List;
/**
 * Created by arnfinng on 27/08/2015.
 */
public class WikipediaParseResult {
    private String title;
    private String url;
    private String summary;
    private Image image;
    private List<WikipediaParseResult> links;
    public WikipediaParseResult(String url){
        this.url = url;
    }

    public WikipediaParseResult(String url, String title, String summary, Image image){
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
    public void addLinkResults(WikipediaParseResult link) {
        this.links.add(link);
    }

    public boolean isDone(){
        return image != null && !links.isEmpty() && summary != null;
    }
}
