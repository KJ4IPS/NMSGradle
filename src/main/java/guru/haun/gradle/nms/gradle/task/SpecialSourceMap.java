package guru.haun.gradle.nms.gradle.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by KJ4IPS on 7/14/2015.
 */
public class SpecialSourceMap extends DefaultTask {
    private File specialSourceJar;
    private File inJar;
    private File outJar;
    private File mappingFile;

    private static final String mainClass = "net.md_5.ss.SpecialSource";

    @TaskAction
    private void applySpecialSource(){
        String args[] = {
                "map",
                "-i", inJar.getAbsolutePath(),
                "-m", mappingFile.getAbsolutePath(),
                "-o", outJar.getAbsolutePath()
        };
        try {
            URLClassLoader ssLoader = new URLClassLoader(new URL[]{specialSourceJar.toURI().toURL()});
            Class ssMainClass = ssLoader.loadClass(mainClass);
            ssMainClass.getMethod("main",String[].class).invoke(null, (Object) args);
            ssMainClass = null; //destroy references, should get GC'd soon
            ssLoader.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not setup up classloader for use with SpecialSource");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find special source 2 main class");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getInJar() {
        return inJar;
    }

    @InputFile
    public void setInJar(File inJar) {
        this.inJar = inJar;
    }


    public File getMappingFile() {
        return mappingFile;
    }

    @InputFile
    public void setMappingFile(File mappingFile) {
        this.mappingFile = mappingFile;
    }

    @OutputFile
    public File getOutJar() {
        return outJar;
    }

    @Input
    public void setOutJar(File outJar) {
        this.outJar = outJar;
    }


    public File getSpecialSourceJar() {
        return specialSourceJar;
    }

    @InputFile
    public void setSpecialSourceJar(File specialSourceJar) {
        this.specialSourceJar = specialSourceJar;
    }
}
