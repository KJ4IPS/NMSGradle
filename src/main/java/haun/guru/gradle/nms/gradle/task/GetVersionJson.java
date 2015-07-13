package haun.guru.gradle.nms.gradle.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by hauna on 7/13/15.
 */
public class GetVersionJson extends DefaultTask {
    private String revision;

    private static final String VERSION_BASE = "http://hub.spigotmc.org/versions/";

    @TaskAction
    public void doTask() throws IOException, URISyntaxException {

        File targetFile = new File(".mcver.json"); //TODO: either cache by name if simple integer, or at least dont DL every time

        URL versionURL;
        versionURL = new URL(VERSION_BASE).toURI().resolve(revision.concat(".json")).toURL();

        URLConnection connection = versionURL.openConnection();
        ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
        FileOutputStream fos = new FileOutputStream(targetFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
