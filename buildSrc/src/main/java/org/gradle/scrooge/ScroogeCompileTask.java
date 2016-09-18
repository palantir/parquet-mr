package org.gradle.scrooge;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.twitter.scrooge.Compiler;
import com.twitter.scrooge.Main;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import scala.collection.JavaConversions;

public class ScroogeCompileTask extends DefaultTask {

    private File dest = new File("src/gen/scala/");
    private Iterable<File> files = Collections.singletonList(new File("src/main/thrift/"));
    private Iterable<String> opts = Collections.singletonList("--finagle");

    @OutputDirectory
    public File getDest() {
        return dest;
    }

    public void setDest(File destinationDirectory) {
        dest = destinationDirectory;
    }

    @InputFiles
    public Iterable<File> getThriftFiles() {
        return files;
    }

    public void setThriftFiles(Iterable<File> files) {
        this.files = files;
    }

    @Input
    @Optional
    public Iterable<String> getOpts() {
        return opts;
    }

    public void setOpts(List<String> options) {
        this.opts = options;
    }

    @TaskAction
    public void compile() {
        String destination = getDest().getAbsolutePath();
        Iterable<String> thriftFiles = Iterables.transform(files, new Function<File, String>() {
                    public String apply(File input) {
                        return input.getAbsolutePath();
                    }
                });
        Compiler compiler = new Compiler();
        compiler.destFolder_$eq(destination);

        List<String> args = ImmutableList.<String>builder().addAll(opts).addAll(thriftFiles).build();
        Main.parseOptions(compiler, JavaConversions.asScalaBuffer(args));
        compiler.run();
    }
}
