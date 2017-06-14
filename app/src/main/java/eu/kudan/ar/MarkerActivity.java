package eu.kudan.ar;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import eu.kudan.kudan.ARAPIKey;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARImageTrackable;
import eu.kudan.kudan.ARImageTrackableListener;
import eu.kudan.kudan.ARImageTracker;

public class MarkerActivity extends ARActivity implements ARImageTrackableListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Add the API Key, which can be found at https://wiki.kudan.eu/Development_License_Keys
        //This key will only work when using the eu.kudan.ar application Id
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey("sVmoznmKZ+4nFEHD6HoslwpC26PNuBZGHrikUwyon2BKSvza1yu2CqbSrae+pHPr1NHjhsf5pHQOZn8IEqXlqXFodGsrOJhxJANbMOdvnRLUi9/QWGqyRL9FViDmyohw6e5R7U4Ex8H7d7spLLvhfp5HFv56DgLr8c8sC2ipDtv9g1IjOTaY7UGxata3eulG2A/UkIdRv2NcotZXqan01xQUWFAislEwlGguParEYiwu11T4mqtU3dQBbfxpvxbczjdYz493YG3rAO2RHgT+5M5TJShJsz2irkNo71JD2Fzqf4AR2b4+7t1c55zKjegXzGS6Xa/rpNn9yiXUn7rUYIHNvN3cEQa9HsZiVxAV4vJgxFS+T/AxfWqKrEg1uj6xF5MsodZ2EkZ8mqliYIsxZqnFz+Re2HeWG8wvrEob0ZwRIO0TxppAemZc3HChTAPLcNt5gzeBk0oRP4wnrFAFFBDi8XjDocwTSVw++hWZb1qNHzt6bKLsMDRT057UVuuZB6M8f7EOQD79Oah0Vrx/3DUK6e9BEV8oGFNHtk1wyYEkg0i6RLhVSokGx//Qj36A4gCz3h1OjtfB0OuukbNq7xI1L/FcNQLmGYNGZwszARjGr9ESw1gVAkbQMxaV27uo/KoIq4+nR7RL8iT7t7NAaXCFIi24RR+7WGjTvKqWYjA=");

    }
    
    // This method should be overridden and all of the AR content setup placed within it
    // This method is called only at the point at which the AR Content is ready to be set up
    @Override
    public void setup() {
        super.setup();

        // Create our trackable with an image
        ARImageTrackable trackable = createTrackable("Marker","lego.jpg");

        // Get the trackable Manager singleton
        ARImageTracker trackableManager = ARImageTracker.getInstance();

        //Add image trackable to the image tracker manager
        trackableManager.addTrackable(trackable);

        // Create an image node using an image of the kudan cow
        ARImageNode cow = new ARImageNode("target.png");

        // Add the image node as a child of the trackable's world
        trackable.getWorld().addChild(cow);

        // Add listener methods that are defined in the ARImageTrackableListener interface
        trackable.addListener(this);
    }

    private ARImageTrackable createTrackable(String trackableName, String assetName)
    {
        // Create a new trackable instance with a name
        ARImageTrackable trackable = new ARImageTrackable(trackableName);

        // Load the  image for this marker
        trackable.loadFromAsset(assetName);

        return trackable;
    }

    // ARImageTrackableListener interface functions, these are called in response to various tracking events
    @Override
    public void didDetect(ARImageTrackable arImageTrackable) {
        Log.d("Marker","Did Detect");
    }

    @Override
    public void didLose(ARImageTrackable arImageTrackable) {
        Log.d("Marker","Did Lose");

    }

    @Override
    public void didTrack(ARImageTrackable arImageTrackable) {
        Log.d("Marker","Did Track");
    }
}
