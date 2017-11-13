This plugin adds the Maven Dependency to the README.md like this:

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


# Usage
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