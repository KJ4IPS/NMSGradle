package guru.haun.gradle.nms.gradle.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by KJ4IPS on 7/14/2015.
 * downloads the minecraft server from Mojang's S3 bucket
 */
public class DownloadMinecraftServer extends DefaultTask {
    private String version;
    private File target;


    @TaskAction
    public void downloadMc() throws IOException {
        URL targetUrl = new URL(String.format("https://s3.amazonaws.com/Minecraft.Download/versions/%1$s/minecraft_server.%1$s.jar", version));

        ReadableByteChannel dlChannel;
        FileOutputStream saveStream;

        dlChannel = Channels.newChannel(targetUrl.openStream());
        saveStream = new FileOutputStream(target);
        saveStream.getChannel().transferFrom(dlChannel, 0, Long.MAX_VALUE);

        dlChannel.close();
        saveStream.close();
    }


    public String getVersion() {
        return version;
    }

    @Input
    public void setVersion(String version) {
        this.version = version;
    }

    @OutputFile
    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        this.target = target;
    }
}
