package rbs.caryourday.androidlabs;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_forecast);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        new ForecastQuery().execute(null, null, null);

    }//end of onCreate();

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private TextView
                currentTempText = findViewById(R.id.currentTemp),
                minTempText = findViewById(R.id.minTemp),
                maxTempText = findViewById(R.id.maxTemp),
                windspeedText = findViewById(R.id.windSpeed),
                currentLocationText = findViewById(R.id.currentLocation);

        private String currentTemp, minTemp, maxTemp, windspeed, currentLocation;
        private ImageView weatherImage = findViewById(R.id.weatherImage);
        private Bitmap bitMap = null;
        private String iconName = null;

        /*Establishing Connection...*/

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the    query
                conn.connect();
                inputStream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                boolean set = false;
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (parser.getName().equalsIgnoreCase("current")) {
                            set = true;
                        } else if (parser.getName().equalsIgnoreCase("city") && set) {

                            currentLocation = parser.getAttributeValue(null, "name");

                        } else if (parser.getName().equalsIgnoreCase("temperature") && set) {

                            currentTemp = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(75);

                        } else if (parser.getName().equalsIgnoreCase("wind") && set) {


                        } else if (parser.getName().equalsIgnoreCase("speed") && set) {

                            windspeed = parser.getAttributeValue(null, "value");
                            publishProgress(75);


                        } else if (parser.getName().equalsIgnoreCase("weather") && set) {


                            iconName = parser.getAttributeValue(null, "icon");
                            //png has to be inside the file name to work not in parser
                            File file = getBaseContext().getFileStreamPath(iconName + ".png");


                            if (!file.exists()) {
                                saveImage(iconName);

                            } else {
                                Log.i(ACTIVITY_NAME, "Saved icon downloaded & exists, " + iconName + " is displayed.");
                                try {
                                    FileInputStream in = new FileInputStream(file);
                                    bitMap = BitmapFactory.decodeStream(in);
                                } catch (FileNotFoundException e) {
                                    Log.i(ACTIVITY_NAME, "Saved icon, " + iconName + " is not found.");
                                }
                            }
                            publishProgress(100);

                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (parser.getName().equalsIgnoreCase("current"))
                            set = false;
                    }
                    eventType = parser.next();
                }

            } catch (IOException e) {
                Log.i(ACTIVITY_NAME, "IOException: " + e.getMessage());
            } catch (XmlPullParserException e) {
                Log.i(ACTIVITY_NAME, "XmlPullParserException: " + e.getMessage());
            } finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.i(ACTIVITY_NAME, "IOException: " + e.getMessage());
                    }
                return null;
            }
        }//end of doInBackground()

        private void saveImage(String fname) {

            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://openweathermap.org/img/w/" + fname);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {

                    bitMap = BitmapFactory.decodeStream(connection.getInputStream());
                    FileOutputStream outputStream = openFileOutput(fname + ".png", Context.MODE_PRIVATE);
                    bitMap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                    outputStream.flush();
                    outputStream.close();

                    Log.i(ACTIVITY_NAME, "Weather icon is downloaded.");
                }
            } catch (Exception e) {

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }//end of saveImage*/

        /*Checking to see if images exist on local storage*/
        public boolean fileExistance(String fname) {
            Log.i(ACTIVITY_NAME, "In fileExistance");
            Log.i(ACTIVITY_NAME, getBaseContext().getFileStreamPath(fname).toString());
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            currentTempText.setText("Current Temp: " + String.format("%.1f", Double.parseDouble(currentTemp)) + "\u00b0");
            minTempText.setText("Min Temp: " + String.format("%.1f", Double.parseDouble(minTemp)) + "\u00b0");
            maxTempText.setText("Max Temp: " + String.format("%.1f", Double.parseDouble(maxTemp)) + "\u00b0");
            windspeedText.setText("WindSpeed:  " + String.format("%.1f ", Double.parseDouble(windspeed)) + " m/s");
            currentLocationText.setText("Location: " + currentLocation);
            weatherImage.setImageBitmap(bitMap);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }//end of inner class


}
