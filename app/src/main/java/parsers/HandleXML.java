package parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import modals.QueryData;
import utility.Utility;

/**
 * Created by C.limbachiya on 8/10/2016.
 */
public class HandleXML {

    private String toplevel = "toplevel";
    private String CompleteSuggestion = "CompleteSuggestion";
    private String suggestion = "suggestion";

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;


    public HandleXML(String url) {
        this.urlString = url;
    }

    public String getToplevel() {
        return toplevel;
    }

    public void setToplevel(String toplevel) {
        this.toplevel = toplevel;
    }

    public String getCompleteSuggestion() {
        return CompleteSuggestion;
    }

    public void setCompleteSuggestion(String completeSuggestion) {
        CompleteSuggestion = completeSuggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }


    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("suggestion")) {
                            suggestion = myParser.getAttributeValue(null, "data");
                            Utility.listQueryData.add(new QueryData(suggestion));
                        } else {
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream stream = conn.getInputStream();
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream, null);

            parseXMLAndStoreIt(myparser);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}