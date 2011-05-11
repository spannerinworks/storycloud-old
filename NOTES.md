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

== install the app engine jars into the local maven repo ==
NB: some of these might not be needed

mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/appengine-api-1.0-sdk-1.5.0.jar -DgroupId=com.google.appengine -DartifactId=appengine-api-1.0-sdk -Dversion=1.5.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/appengine-api-labs-1.5.0.jar -DgroupId=com.google.appengine -DartifactId=appengine-api-labs -Dversion=1.5.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/appengine-jsr107cache-1.5.0.jar -DgroupId=com.google.appengine -DartifactId=appengine-jsr107cache -Dversion=1.5.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/orm/datanucleus-appengine-1.0.8.final.jar -DgroupId=org.datanucleus -DartifactId=datanucleus-appengine -Dversion=1.0.8.final -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/orm/datanucleus-core-1.1.5.jar -DgroupId=org.datanucleus -DartifactId=datanucleus-core -Dversion=1.1.5 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/orm/user/datanucleus-jpa-1.1.5.jar -DgroupId=org.datanucleus -DartifactId=datanucleus-jpa -Dversion=1.1.5 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/orm/geronimo-jpa_3.0_spec-1.1.1.jar -DgroupId=org.apache.geronimo.specs -DartifactId=geronimo-jpa_3.0_spec -Dversion=1.1.1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/orm/geronimo-jta_3.0_spec-1.1.1.jar -DgroupId=org.apache.geronimo.specs -DartifactId=geronimo-jta_3.0_spec -Dversion=1.1.1 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/orm/jdo2-api-2.3-eb.jar -DgroupId=javax.jdo -DartifactId=jdo2-api -Dversion=2.3-eb -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=/code/java/appengine-java-sdk-1.5.0/lib/user/jsr107cache-1.1.jar -DgroupId=net.sf.jsr107cache -DartifactId=jsr107cache -Dversion=1.1 -Dpackaging=jar -DgeneratePom=true

== add depedencies in pom ==

        <!-- Google App Engine API -->
        <dependency>
          <groupId>com.google.appengine</groupId>
          <artifactId>appengine-api-1.0-sdk</artifactId>
          <version>1.5.0</version>
        </dependency>
    
        <dependency>
          <groupId>com.google.appengine</groupId>
          <artifactId>appengine-api-labs</artifactId>
          <version>1.5.0</version>
        </dependency>
    
        <dependency>
          <groupId>com.google.appengine</groupId>
          <artifactId>appengine-jsr107cache</artifactId>
          <version>1.5.0</version>
        </dependency>
    
        <dependency>
          <groupId>org.datanucleus</groupId>
          <artifactId>datanucleus-core</artifactId>
          <version>1.1.5</version>
        </dependency>
    
        <dependency>
          <groupId>org.datanucleus</groupId>
          <artifactId>datanucleus-appengine</artifactId>
          <version>1.0.8.final</version>
          <scope>runtime</scope>
        </dependency>
    
        <dependency>
          <groupId>org.datanucleus</groupId>
          <artifactId>datanucleus-jpa</artifactId>
          <version>1.1.5</version>
          <scope>runtime</scope>
        </dependency>
    
        <dependency>
          <groupId>org.apache.geronimo.specs</groupId>
          <artifactId>geronimo-jta_1.1_spec</artifactId>
          <version>1.1.1</version>
          <scope>runtime</scope>
        </dependency>
    
        <dependency>
          <groupId>org.apache.geronimo.specs</groupId>
          <artifactId>geronimo-jpa_3.0_spec</artifactId>
          <version>1.1.1</version>
          <scope>runtime</scope>
        </dependency>
    
        <dependency>
          <groupId>javax.jdo</groupId>
          <artifactId>jdo2-api</artifactId>
          <version>2.3-eb</version>
          <scope>runtime</scope>
        </dependency>
    
        <dependency>
          <groupId>net.sf.jsr107cache</groupId>
          <artifactId>jsr107cache</artifactId>
          <version>1.1</version>
          <scope>runtime</scope>
        </dependency>
