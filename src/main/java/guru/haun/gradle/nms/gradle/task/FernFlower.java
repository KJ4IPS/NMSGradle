package guru.haun.gradle.nms.gradle.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
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
public class FernFlower extends DefaultTask {
    private File fernFlowerJar;
    private File inDir;
    private File outDir;

    private static final String mainClass = "org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler";

    @TaskAction
    public void doDecomp(){
        String args[] = {
                "-dgs=1",
                "-hdc=0",
                "-rbr=0",
                "-asc=1",
                "-udv=0",
                inDir.getAbsolutePath(),
                outDir.getAbsolutePath()
        };
        try {
            URLClassLoader ssLoader = new URLClassLoader(new URL[]{fernFlowerJar.toURI().toURL()});
            Class ssMainClass = ssLoader.loadClass(mainClass);
            ssMainClass.getMethod("main",String[].class).invoke(null, (Object) args);
            ssMainClass = null; //destroy references, should get GC'd soon
            ssLoader.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not setup up classloader for use with fernflower");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find fernflower main class");
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


    public File getFernFlowerJar() {
        return fernFlowerJar;
    }

    @InputFile
    public void setFernFlowerJar(File fernFlowerJar) {
        this.fernFlowerJar = fernFlowerJar;
    }

    public File getInDir() {
        return inDir;
    }

    @InputDirectory
    public void setInDir(File inDir) {
        this.inDir = inDir;
    }

    @OutputDirectory
    public File getOutDir() {
        return outDir;
    }

    public void setOutDir(File outDir) {
        this.outDir = outDir;
    }
}
