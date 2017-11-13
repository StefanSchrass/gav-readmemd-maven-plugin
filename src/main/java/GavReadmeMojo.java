import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
@Mojo(name = "GavReadme", defaultPhase = LifecyclePhase.INSTALL)
public class GavReadmeMojo extends AbstractMojo {
    @Parameter
    private File readmemd;
    @Parameter
    private String groupId;
    @Parameter
    private String artifactId;
    @Parameter
    private String version;


    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Updating file: " + readmemd.getAbsolutePath());
        writeReadme(gavReadmeFromContent(contentFromFile(readmemd)));
    }

    private String contentFromFile(final File file) {
        String content;
        try (Stream<String> lines = linesFromFile(file)) {
            content = lines.collect(Collectors.joining("\n"));
        }

        return content;
    }

    private Stream<String> linesFromFile(final File file) {
        Stream<String> lines = Stream.empty();
        try { lines = Files.lines(Paths.get(file.getAbsolutePath())); }
        catch (IOException e) { getLog().error("could not read " + file.getName(), e); }

        return lines;
    }

    private void writeReadme(final String readme) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(readmemd.getAbsolutePath()))) {
            writer.write(readme);
        }
        catch (IOException e) {
            getLog().error("could not write to file " + readmemd.getName(), e);
        }

    }

    private String gavReadmeFromContent(String content) {
        Pattern dependencyPattern = Pattern.compile("\\[comment\\]: \\<\\{gav-dependency-start\\}\\>.*\\[comment\\]: \\<\\{gav-dependency-end\\}\\>", Pattern.DOTALL);
        Matcher dependencyMatcher = dependencyPattern.matcher(content);

        if (dependencyMatcher.find()) {
            content = dependencyMatcher.replaceAll(gav());
        }

        Pattern placeholderPattern = Pattern.compile("\\[comment\\]: \\<\\{gav-placeholder\\}\\>");
        Matcher placeholderMatcher = placeholderPattern.matcher(content);

        if (placeholderMatcher.find()) {
            content = placeholderMatcher.replaceAll(gav());
        }


        return content;
    }

    private String gav() {
        return "[comment]: <{gav-dependency-start}>\n" +
                "\n" +
                "[Maven Dependency](https://github.com/StefanSchrass/gav-readmemd-maven-plugin \"gav-readmemd-maven-plugin\")\n" +
                "---\n" +
                "```\n" +
                "<dependency>\n" +
                "    <groupId>" + groupId + "</groupId>\n" +
                "    <artifactId>" + artifactId + "</artifactId>\n" +
                "    <version>" + version + "</version>\n" +
                "</dependency>\n" +
                "```\n" +
                "[comment]: <{gav-dependency-end}>\n";
    }
}
