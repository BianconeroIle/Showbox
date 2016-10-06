package util;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.NewsDTO;

/**
 * Created by Vlade Ilievski on 10/6/2016.
 */

public class ReadRSS {
    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_LINK = "link";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_PUB_DATE = "pubDate";


    public static List<NewsDTO> getNewsFromFile(Context ctx) {

        // List of StackSites that we will return
        List<NewsDTO> newsItems;
        newsItems = new ArrayList<NewsDTO>();

        // temp holder for current StackSite while parsing
        NewsDTO curNews = null;
        // temp holder for current text value while parsing
        String curText = "";

        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // Open up InputStream and Reader of our file.
            FileInputStream fis = ctx.openFileInput("feedburner.com/thr/film");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            // point the parser to our file.
            xpp.setInput(reader);

            // get initial eventType
            int eventType = xpp.getEventType();

            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Get the current tag
                String tagname = xpp.getName();

                // React to different event types appropriately
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(KEY_ITEM)) {
                            // If we are starting a new <site> block we need
                            //a new StackSite object to represent it
                            curNews = new NewsDTO();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        //grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase(KEY_ITEM)) {

                            newsItems.add(curNews);
                        } else if (tagname.equalsIgnoreCase(KEY_TITLE)) {

                            curNews.setTitle(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_LINK)) {

                            curNews.setLink(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_DESCRIPTION)) {

                            curNews.setDescription(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_PUB_DATE)) {

                            curNews.setPubDate(curText);
                        }
                        break;

                    default:
                        break;
                }

                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the populated list.
        return newsItems;
    }
}



