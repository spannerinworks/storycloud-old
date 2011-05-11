= maven =

== Generate Scaffold ==
mvn archetype:generate -DarchetypeCatalog=http://tapestry.apache.org

[INFO] No archetype defined. Using maven-archetype-quickstart (org.apache.maven.archetypes:maven-archetype-quickstart:1.0)
Choose archetype:
1: http://tapestry.apache.org -> quickstart (Tapestry 5.2.4 Quickstart Project)
2: http://tapestry.apache.org -> tapestry-archetype (Tapestry 4.1.6 Archetype)
Choose a number: : 1
Choose version: 
1: 5.0.19
2: 5.1.0.5
3: 5.2.4
Choose a number: 3: 3
Define value for property 'groupId': : com.spannerinworks
Define value for property 'artifactId': : storycloud
Define value for property 'version':  1.0-SNAPSHOT: : 
Define value for property 'package':  com.spannerinworks: : com.spannerinworks.storycloud
Confirm properties configuration:
groupId: com.spannerinworks
artifactId: storycloud
version: 1.0-SNAPSHOT
package: com.spannerinworks.storycloud
 Y: : 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------

== eclipse config ==
 * create eclipse project files
 mvn eclipse:eclipse -DdownloadSources=true


* add maven repo to eclipse path
 mvn -Declipse.workspace=<path-to-eclipse-workspace> eclipse:add-maven-repo

* create M2_REPO variable in eclipse
 From the menu bar, select Preferences > Java > Build Path > Classpath Variables page

 Name: M2_REPO
 Value: /Users/ben/.m2/repository

== build ==
mvn package
or: mvn clean package

== deploy to google app engine ==

/code/java/appengine-java-sdk-1.5.0/bin/appcfg.sh update target/storycloud

== versions ==

If the version changes you hane to select it:

https://appengine.google.com/deployment?app_id=storycloud

