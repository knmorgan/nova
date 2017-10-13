package nova.game.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class responsible for maintaining global high scores.  The class
 * communicates with an online PHP page that stores the high scores
 * in a super advanced text file database.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class HighScoreManager
{
    private static final String SITE = "http://www.themorganator.com/nova/hs.php";
    private static String[] names = new String[10];
    private static int[] scores = new int[10];

    /* Private constructor - can't be instantiated */
    private HighScoreManager() { }

    /**
     * Returns an array of the names of the top 10 high scores.
     *
     * @return The array of names
     */
    public static String[] getNames()
    {
        return names;
    }

    /**
     * Returns an array of the top 10 high scores.
     *
     * @return The array of scores
     */
    public static int[] getScores()
    {
        return scores;
    }

    /**
     * Returns the name of the person with the highest score.
     *
     * @return String containing name of person with highest score
     */
    public static String getTopName()
    {
        return names[0];
    }

    /**
     * Returns the highest score value.
     *
     * @return The highest score.
     */
    public static int getTopScore()
    {
        System.out.println(scores[0]);
        return scores[0];
    }

    /**
     * Attempts to read the scores from the PHP page, and updates them.
     *
     * @param site The URL of the page to read the scores from
     * @throws IOException Thrown if invalid URL
     * @throws MalformedURLException Thrown if invalid URL
     */
    private static void readScores(String site) throws IOException, MalformedURLException
    {
        URL url = new URL(site);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        int index = 0;
        while((line = in.readLine()) != null && index < names.length)
        {
            line = line.replace("<br>", "").trim();
            names[index] = line.substring(0, line.lastIndexOf(" "));
            scores[index] = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));
            index++;
        }
    }

    /**
     * Refreshes the high scores and names.
     *
     * @throws IOException Should never happen.
     * @throws MalformedURLException Should never happen.
     */
    public static void refreshScores() throws IOException, MalformedURLException
    {
        readScores(SITE);
    }

    /**
     * Submits a new high score to the PHP page.
     *
     * @param name The name of the holder of the high score
     * @param score The new high score
     * @throws IOException Should never happen
     * @throws MalformedURLException Should never happen
     */
    public static void submitScore(String name, int score) throws IOException, MalformedURLException
    {
        readScores(SITE + "?score=" + name + "%20" + score);
    }
}
