package ru.maximkulikov.playerForTwitchHeroes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

/**
 * Player-for-Twitch-Heroes-on-...-twitch-
 *
 * @author Maxim Kulikov
 * @since 23.08.2017
 */
public class Player extends PircBot {

    private String name;

    private String oauth;

    public static void main(String[] args) {

        Player player = new Player();

        Properties prop = player.load();

        player.config(prop);

        player.start(prop);


    }

    private void config(Properties prop) {

        if (prop != null) {

            setName(prop.getProperty(String.valueOf(Prop.NAME)));


        }
    }

    private Properties load() {

        InputStream input = null;

        try {
            File file = new File("config.properties");

            Boolean isfilecreated = file.createNewFile();
            System.out.println("File created: " + isfilecreated);


            input = new FileInputStream(file);
            // load a properties file

            Properties p = new Properties();

            p.load(input);

            return p;


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onConnect() {
        super.onConnect();
        joinChannel("#twitch_heroes");
    }

    @Override
    protected void onJoin(String s, String s1, String s2, String s3) {
        super.onJoin(s, s1, s2, s3);
        System.out.println(s + s1 + s2 + s3);
    }

    private void start(Properties prop) {

        String server = prop.getProperty(String.valueOf(Prop.SERVER));

        int port = Integer.parseInt(prop.getProperty(String.valueOf(Prop.PORT)));

        String oauth = prop.getProperty(String.valueOf(Prop.OAUTH));

        try {
            connect(server, port, oauth);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
    }
}
