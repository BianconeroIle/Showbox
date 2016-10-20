package ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.showbox.showbox.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import listeners.RecyclerItemStoriesClickListener;
import model.NewsDTO;
import ui.StoriesDetailsActivity;
import util.XMLDOMParser;

/**
 * Created by Vlade Ilievski on 9/13/2016.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    RecyclerView recycler_view;


    static final String URL = "http://feeds.feedburner.com/thr/film";

    // XML node names
    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_LINK = "link";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_PUB_DATE = "pubDate";
    static final String KEY_ORG_LINK = "feedburner:origLink";
    ProgressBar progressBarStories;

    public static final String TAG = HomeFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);
        initVariables(v);
        initListeners();
        Log.d(TAG, "onCreateView()");
        GetXMLTask task = new GetXMLTask(recycler_view, getContext());
        task.execute(new String[]{URL});

        return v;
    }

    public void initVariables(View v) {
        recycler_view = (RecyclerView) v.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setHasFixedSize(true);

    }

    public void initListeners() {
        recycler_view.addOnItemTouchListener(
                new RecyclerItemStoriesClickListener(getContext(), recycler_view, new RecyclerItemStoriesClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), StoriesDetailsActivity.class);
                        intent.putExtra("news_dto", ((HomeRecyclerAdapter) recycler_view.getAdapter()).getItems().get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }


    private static class GetXMLTask extends AsyncTask<String, Void, List<NewsDTO>> {
        private WeakReference<RecyclerView> recyclerViewWeakReference;
        private WeakReference<Context> contextWeakReference;

        public GetXMLTask(RecyclerView recyclerView, Context context) {
            this.recyclerViewWeakReference = new WeakReference<>(recyclerView);
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected List<NewsDTO> doInBackground(String... urls) {
            Log.d(TAG, "doInBackground=" + urls[0]);
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
            RecyclerView recyclerView = recyclerViewWeakReference.get();
            Context context = contextWeakReference.get();
            if (recyclerView != null && context != null) {

                HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter(items, context);
                recyclerView.setAdapter(homeRecyclerAdapter);
            }
        }


        private List<NewsDTO> parseXml(String xml) {
            Log.d(TAG, "xml=" + xml);
            List<NewsDTO> items = new ArrayList<>();
            XMLDOMParser parser = new XMLDOMParser();
            InputStream stream = new ByteArrayInputStream(xml.getBytes());
            Document doc = parser.getDocument(stream);

            //NodeList nodeList = doc.getElementsByTagName(KEY_ITEM);
            NodeList channelNodeList = doc.getElementsByTagName("channel");
            Node ch = channelNodeList.item(0);

            NodeList itemsNodeList = ((Element) ch).getElementsByTagName("item");

            NewsDTO newsItems = null;
            for (int i = 0; i < itemsNodeList.getLength(); i++) {
                newsItems = new NewsDTO();
                Element e = (Element) itemsNodeList.item(i);
                newsItems.setTitle(parser.getValue(e, KEY_TITLE));
                newsItems.setLink(parser.getValue(e, KEY_LINK));
                newsItems.setPubDate(parser.getValue(e, KEY_PUB_DATE));
                newsItems.setDescription(parser.getValue(e, KEY_DESCRIPTION));
                newsItems.setOriginalLink(parser.getValue(e, KEY_ORG_LINK));
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
        }


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


