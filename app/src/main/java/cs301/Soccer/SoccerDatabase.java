package cs301.Soccer;

import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable<String, SoccerPlayer> database = new Hashtable<String, SoccerPlayer>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {
        String key = firstName + " ## " + lastName;

        if (database.containsKey(key)){
            return false;
        }
        else{
            SoccerPlayer player = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            database.put(key, player);
            return true;
        }
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {

        String key = firstName + " ## " + lastName;
        if(database.containsKey(key)){
            database.remove(key);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        String key = firstName + " ## " + lastName;
        if(database.get(key) == null){
            return null;
        }
        else{
            return database.get(key);
        }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player == null){
            return false;
        }
        else {
            player.bumpGoals();
        }
        return true;
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);

        if(player == null){
            return false;
        }
        else{
            player.bumpYellowCards();
        }
        return true;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);

        if(player == null){
            return false;
        }
        else{
            player.bumpRedCards();
        }
        return true;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        if(teamName == null || database.isEmpty()){
            return database.size();
        }

        else{
            int count = 0;
            Enumeration<String> keyList = database.keys();
            for(int i = 1; i <= database.size(); i++){
                if(database.get(keyList.nextElement()).getTeamName().equals(teamName)){
                    count++;
                }
                if(!(keyList.hasMoreElements())){
                    break;
                }
            }
            return count;
        }

    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        SoccerPlayer player = null;
        if(idx > numPlayers(teamName)){
            return null;
        }
        // loop through enumeration
        Enumeration<String> keyList = database.keys();
        int count = 0;

        // NULL TEAM NAME LOOP THROUGH ALL PLAYERS
        if(teamName == null){
            for(int i = 0; i <= idx; i++){
                player = database.get(keyList.nextElement());
                if(!(keyList.hasMoreElements())){
                    break;
                }
            }
            return player;
        }
        // SEARCH THROUGH SPECIFIC TEAM
        else {
            for (int i = 0; i <= database.size(); i++) {

                player = database.get(keyList.nextElement());

                if (player.getTeamName().equals(teamName)) {
                    if (count == idx) {
                        return player;
                    }
                    count++;
                }
                if (!(keyList.hasMoreElements())) {
                    break;
                }
            }
        }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        return file.exists();
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            SoccerPlayer player = null;

            Enumeration<String> keyList = database.keys();
            for (int i = 0; i < database.size(); i++) {
                player = database.get(keyList.nextElement());
                pw.println(logString(player.getFirstName()));
                pw.println(logString(player.getLastName()));
                pw.println(logString(Integer.toString(player.getUniform())));
                pw.println(logString(Integer.toString(player.getGoals())));
                pw.println(logString(Integer.toString(player.getYellowCards())));
                pw.println(logString(Integer.toString(player.getRedCards())));
                pw.println(logString(player.getTeamName()));
                pw.println();
            }
            pw.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        return new HashSet<String>();
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if(database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
