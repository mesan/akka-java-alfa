package no.mesan.akka.common;

import java.io.Serializable;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class WikipediaArticleSummary implements Serializable {
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
