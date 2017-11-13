This plugin adds the Maven GAV-coordinates of your project to its `README.md` like this:

[comment]: <{gav-dependency-start}>

[Maven Dependency](https://github.com/StefanSchrass/gav-readmemd-maven-plugin "gav-readmemd-maven-plugin")
---
```
<dependency>
    <groupId>de.sschrass.maven</groupId>
    <artifactId>gav-readmemd-maven-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
[comment]: <{gav-dependency-end}>


# Setup
```
<build>
    <plugins>
        <plugin>
            <groupId>de.sschrass.maven</groupId>
            <artifactId>gav-readmemd-maven-plugin</artifactId>
            <version>1.0.0</version>
            <configuration>
                <readmemd>README.md</readmemd>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}</artifactId>
                <version>${project.version}</version>
            </configuration>
            <executions>
                <execution>
                <phase>install</phase>
                    <goals>
                        <goal>GavReadme</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

# Usage
In your `README.md` just add `"[comment]: <{gav-placeholder}>"` wherever you like, 
the plugin will replace this placeholder with the information provided.