package eu.kudan.ar;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.kudan.kudan.ARAPIKey;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARImageTrackable;
import eu.kudan.kudan.ARImageTracker;

import static android.content.ContentValues.TAG;

/*

 This class is a demonstration of the simultaneous tracking feature of the iOS SDK.
 The framework has no hard limit on the number of markers that can be simultaneously detected and tracked.
  However, we have the option to limit the number of simultaneous detections which can improve performance and battery life.

 In this demo we split our standard lego marker into four pieces and add images of numbers as children of the pieces.
 We then add a stepper so that we can experiment with adjusting the maximum simultaneous tracking property.

 */

public class SimultaneousDetectionActivity extends ARActivity implements SeekBar.OnSeekBarChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simultaneous_detection);

        // Add the API Key, which can be found at https://wiki.kudan.eu/Development_License_Keys
        // This key will only work when using the eu.kudan.ar application Id
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey("sVmoznmKZ+4nFEHD6HoslwpC26PNuBZGHrikUwyon2BKSvza1yu2CqbSrae+pHPr1NHjhsf5pHQOZn8IEqXlqXFodGsrOJhxJANbMOdvnRLUi9/QWGqyRL9FViDmyohw6e5R7U4Ex8H7d7spLLvhfp5HFv56DgLr8c8sC2ipDtv9g1IjOTaY7UGxata3eulG2A/UkIdRv2NcotZXqan01xQUWFAislEwlGguParEYiwu11T4mqtU3dQBbfxpvxbczjdYz493YG3rAO2RHgT+5M5TJShJsz2irkNo71JD2Fzqf4AR2b4+7t1c55zKjegXzGS6Xa/rpNn9yiXUn7rUYIHNvN3cEQa9HsZiVxAV4vJgxFS+T/AxfWqKrEg1uj6xF5MsodZ2EkZ8mqliYIsxZqnFz+Re2HeWG8wvrEob0ZwRIO0TxppAemZc3HChTAPLcNt5gzeBk0oRP4wnrFAFFBDi8XjDocwTSVw++hWZb1qNHzt6bKLsMDRT057UVuuZB6M8f7EOQD79Oah0Vrx/3DUK6e9BEV8oGFNHtk1wyYEkg0i6RLhVSokGx//Qj36A4gCz3h1OjtfB0OuukbNq7xI1L/FcNQLmGYNGZwszARjGr9ESw1gVAkbQMxaV27uo/KoIq4+nR7RL8iT7t7NAaXCFIi24RR+7WGjTvKqWYjA=");

        // Add listener for Seekbar callback methods
        addListener();
    }

    @Override
    public void setup() {
        super.setup();

        // An array that contains the names of the images of the pieces of our lego marker
        String[] trackableImageNames = new String[] {"legoOne.jpg", "legoTwo.jpg", "legoThree.jpg", "legoFour.jpg"};

        // An array that contains the names of the images of pictures of numbers
        String[] imageNodeNames = new String[] {"oneImage.png", "twoImage.png", "threeImage.png", "fourImage.png"} ;

        // Create an ArrayList of trackables from an array of image names
        ArrayList<ARImageTrackable> trackables = createTrackables(trackableImageNames);

        // Create an ArrayLost of image nodes from an array of image names
        ArrayList<ARImageNode> imageNodes = createImageNodes(imageNodeNames);

        // Add the image nodes to the corresponding trackable
        addImageNodesToTrackables(imageNodes,trackables);

        // Add the trackables to the image tracker manager
        addTrackablesToManager(trackables);

    }

    private void addListener()
    {
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(this);
    }

    private ArrayList<ARImageTrackable> createTrackables (String[] imageNames){

        // Create an ArrayList of image trackables
        ArrayList<ARImageTrackable> trackables = new ArrayList<>();

        for (int i = 0 ; i < imageNames.length ; i++)
        {
            // Get the image name
            String trackableImageName = imageNames[i];

            // Create a trackable from that image, the first line sets the trackable's name
            ARImageTrackable trackable = new ARImageTrackable(trackableImageName);
            trackable.loadFromAsset(trackableImageName);

            // Add the trackable to the trackables array
            trackables.add(trackable);
        }

        return trackables;
    }

    private  ArrayList<ARImageNode> createImageNodes (String[] imageNames)
    {
        // Create an ArrayList of image nodes
        ArrayList<ARImageNode> imageNodes = new ArrayList<>();

        for (int i = 0; i < imageNames.length; i++)
        {
            // Get the image name
            String imageName = imageNames[i];

            // Create an imageNode from that image and set the name
            ARImageNode imageNode = new ARImageNode(imageName);
            imageNode.setName(imageName);

            // Add the imageNode to the array
            imageNodes.add(imageNode);
        }

        return imageNodes;
    }

    private void addImageNodesToTrackables(ArrayList<ARImageNode> imageNodes, ArrayList<ARImageTrackable> trackables)
    {
        // This method only works if there are the same number of image nodes as there are trackables to add those image nodes to
        if (imageNodes.size() == trackables.size())
        {
            for (int i = 0; i < imageNodes.size(); i++)
            {
                // For each trackable, add the corresponding image node
                ARImageTrackable trackable = trackables.get(i);
                ARImageNode imageNode = imageNodes.get(i);
                trackable.getWorld().addChild(imageNode);
            }
        }
    }

    private void addTrackablesToManager(ArrayList<ARImageTrackable>trackables)
    {
        ARImageTracker tracker = ARImageTracker.getInstance();

        // Add the trackables in the array to the trackable manager
        for (ARImageTrackable trackable : trackables)
        {
            tracker.addTrackable(trackable);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        // Adjusts the maximum number of trackables that can be simultaneously tracked

        // Note: If a number of trackables are currently being tracked, setting the maximum to a value below this number will not cause any trackables to be lost.

        // Instead it means that any new trackables will not be detected if the number that is currently being tracked is
        // equal to or above the maximum value


        String valueString = String.valueOf(seekBar.getProgress());

        if (valueString.equalsIgnoreCase("0")){
          valueString = valueString.concat(" (Unlimited) ");
        }
        TextView textView = (TextView)findViewById(R.id.textView);

        // Adjust our label text to reflect the new maximum simultaneous tracking number
        textView.setText(valueString);

        ARImageTracker.getInstance().setMaximumSimultaneousTracking(seekBar.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
