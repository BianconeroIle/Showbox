package util;

import android.content.Context;
import android.util.Log;
import android.widget.AbsListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.NewsDTO;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
    // The minimum number of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it's still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        // If it isn't currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount) {
            loading = onLoadMore(currentPage + 1, totalItemCount);
        }
    }

    // Defines the process for actually loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.
    public abstract boolean onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }

    /**
     * Created by Vlade Ilievski on 10/4/2016.
     */

    public static class SitesXmlDomParser {
        static final String KEY_TITLE = "title";
        static final String KEY_DESCRIPTION = "description";
        static final String KEY_LINK = "link";
        static final String KEY_PUB_DATE = "pubDate";
        static final String KEY_SITE = "site";
        static final String KEY_ORG_LINK = "feedburner:origLink";


        /*
         * High level method that will read the xml file and parse it
         * into a List of StackSite objects.
         */
        public static List<NewsDTO> getNewsFromFile(Context ctx){
            List<NewsDTO> newsItems = new ArrayList<NewsDTO>();

            String xml = readFile(ctx); // getting XML
            Document doc = getDomElement(xml); // getting DOM element

            NodeList nl = doc.getElementsByTagName(KEY_SITE);

            // looping through all site nodes <site>
            for (int i = 0; i < nl.getLength(); i++) {
                NewsDTO curStackSite = new NewsDTO();
                Element e = (Element) nl.item(i);

                curStackSite.setTitle(getValue(e,KEY_TITLE));
                curStackSite.setLink(getValue(e,KEY_LINK));
                curStackSite.setDescription(getValue(e,KEY_DESCRIPTION));
                curStackSite.setPubDate(getValue(e,KEY_PUB_DATE));


                //Log.i("StackSites", curStackSite.getName());
                newsItems.add(curStackSite);
            }

            return newsItems;
        }

        //Helper method to read the file and return its text.
        private static String readFile(Context ctx){
            String xml = "";
            try {
                FileInputStream fis = ctx.openFileInput("StackSites.xml");
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line;
                while((line = reader.readLine()) != null){
                    xml += line;
                    xml += "\n";
                }
                //Log.i("StackSites", xml);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return xml;
        }

        //Get a value from an XML Element.
        private static String getValue(Element item, String str) {
            NodeList n = item.getElementsByTagName(str);
            return getElementValue(n.item(0));
        }

        //Get an Element Value from an XML Node object.
        private static String getElementValue( Node elem ) {
            StringBuilder value = new StringBuilder();
            Node child;
            if( elem != null){
                if (elem.hasChildNodes()){
                    for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                        if( child.getNodeType() == Node.TEXT_NODE  ){
                            value.append(child.getNodeValue());

                        }
                    }
                    return value.toString();
                }
            }
            return "";
        }

        //Get dom Document object from raw text.
        private static Document getDomElement(String xml){
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {

                DocumentBuilder db = dbf.newDocumentBuilder();

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is);

            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            }
            // return DOM
            return doc;
        }
    }
}