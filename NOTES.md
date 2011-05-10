= maven =

== Generate Scaffold ==
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DgroupId=com.spannerinworks.storycloud -DartifactId=storycloud

== Compile / Test / Make War ==
mvn clean package

== Jetty plugin ==
http://wiki.eclipse.org/Jetty/Feature/Jetty_Maven_Plugin

== configure pom.xml ==

<dependency>
  <groupId>org.mortbay.jetty</groupId>
  <artifactId>servlet-api</artifactId>
  <version>3.0.20100224</version>
  <scope>provided</scope>
</dependency> 

<build>
  .....
  <plugins>
    <plugin>
      <groupId>org.mortbay.jetty</groupId>
      <artifactId>jetty-maven-plugin</artifactId>
      <version>8.0.0.M2</version>
      <configuration>
        <scanIntervalSeconds>10</scanIntervalSeconds>
      </configuration>
    </plugin>

== eclipse config ==
 * create eclipse project files
 mvn eclipse:eclipse -DdownloadSources=true


* add maven repo to eclipse path
 mvn -Declipse.workspace=<path-to-eclipse-workspace> eclipse:add-maven-repo

* create M2_REPO variable in eclipse
 From the menu bar, select Preferences > Java > Build Path > Classpath Variables page

 Name: M2_REPO
 Value: /Users/ben/.m2/repository

== Run Jetty ==

mvn jetty:run # actually runs on the code base, not the built .war


