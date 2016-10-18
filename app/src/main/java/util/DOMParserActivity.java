package util;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.showbox.showbox.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import adapter.HomeRecyclerAdapter;
import model.NewsDTO;

class DOMParserActivity extends Activity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    List<NewsDTO> newsItems;
    HomeRecyclerAdapter homeRecyclerAdapter;
    RecyclerView recycler_view;
    // All static variables
    static final String URL = "http://feeds.feedburner.com/thr/film";

    // XML node names
    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_LINK = "link";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_PUB_DATE = "pubDate";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);


    }


    public void onClick(View view) {
        GetXMLTask task = new GetXMLTask(this);
        task.execute(new String[]{URL});
    }

    public void onItemClick(AdapterView<?> adapter, View view, int position,
                            long id) {
        Toast.makeText(getApplicationContext(),
                newsItems.get(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    //private inner class extending AsyncTask
    private static class GetXMLTask extends AsyncTask<String, Void, List<NewsDTO>> {
        private WeakReference<Activity> context;

        public GetXMLTask(Activity context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        protected List<NewsDTO> doInBackground(String... urls) {
            String xml = null;
            xml = getXmlFromUrl(urls[0]);

            if (xml != null) {
                List<NewsDTO> items = parseXml(xml);
                return items;
            } else {
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<NewsDTO> items) {

            /*homeRecyclerAdapter = new HomeRecyclerAdapter(item,context);
            recycler_view.setAdapter(homeRecyclerAdapter);*/
        }


        private List<NewsDTO> parseXml(String xml) {
            XMLDOMParser parser = new XMLDOMParser();
            InputStream stream = new ByteArrayInputStream(xml.getBytes());
            Document doc = parser.getDocument(stream);

            NodeList nodeList = doc.getElementsByTagName(KEY_ITEM);

            ArrayList items = new ArrayList<NewsDTO>();
            NewsDTO newsItems = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                newsItems = new NewsDTO();
                Element e = (Element) nodeList.item(i);
                newsItems.setTitle(parser.getValue(e, KEY_TITLE));
                newsItems.setLink(parser.getValue(e, KEY_LINK));
                newsItems.setPubDate(parser.getValue(e, KEY_PUB_DATE));
                newsItems.setDescription(parser.getValue(e, KEY_DESCRIPTION));
                items.add(newsItems);
            }
            return items;
        }

        /* uses HttpURLConnection to make Http request from Android to download
         the XML file */
        private String getXmlFromUrl(String urlString) {
            StringBuffer output = new StringBuffer("");

            InputStream stream = null;
            java.net.URL url;
            try {
                url = new URL(urlString);
                URLConnection connection = url.openConnection();

                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(stream));
                    String s = "";
                    while ((s = buffer.readLine()) != null)
                        output.append(s);
                }
            } catch (MalformedURLException e) {
                Log.e("Error", "Unable to parse URL", e);
            } catch (IOException e) {
                Log.e("Error", "IO Exception", e);
            }

            return output.toString();

            //For applications targeting Froyo and previous versions
            /*  
            String xml = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xml;*/
        }
    }
}