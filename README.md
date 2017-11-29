# black
-------------------
black is an extensive and object-oriented inventory framework designed for spigot.
with black you can:
- create infinite amount of pages
- link those pages
- have panes inside pages for dividing your pages into sections
- layer those panes however you like (for like having a background pane below other panes)
- create animated elements with changing actions and icons


# how can i use this?
let me explain it by an example
```java
private void showMinigameMenuTo(Player player) {
    final ItemStack findMatchIcon, leaderboardIcon, settingsIcon, exitIcon,
        backgroundIcon;
    final Element findMatchElement, leaderboardElement, settingsElement, exitElement,
        backgroundElement;

    /*
    .
    .   defining icons
    .
    */

    findMatchElement = new BasicElement(findMatchIcon, (event) -> {
        // cancel clicks
        event.setCancelled(true);
        findMatch(player);
    });

    /*
    .
    .   defining other elements
    .
    */

    final Pane backgroundPane = new BasicPane(0, 0, 1, 9);
    backgroundPane.fill(backgroundElement);

    final Pane foregroundPane = new BasicPane(0, 0, 1, 9);
    // false means elements after 0, 0 wont be shifted
    foregroundPane.insert(findMatchElement, 0, 0, false);
    foregroundPane.insert(leaderboardElement, 0, 3, false);
    /*
    .
    .   inserting other elements
    .
    */

    // adding background pane first makes it show before foreground and
    // makes it being overrided by foreground that way.
    new ChestPage("Minigame Menu", 9, backgroundPane,
        foregroundPane).showTo(player);
}
```

to get this beauty you have to shadow/shade black into your plugin. for gradle you can use this build.gradle format:

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.jengelman.gradle.plugins:shadow:2.0.1'
    }
}

apply plugin: 'com.github.johnrengelman.shadow'
apply plugin: 'java'

group = 'me.blackness.test'
version = '0.0.0'

sourceCompatibility = '1.8'
targetCompatibility = '1.8'

repositories {
    maven { url 'https://hub.spigotmc.org/nexus/content/repositories/snapshots/' }
    mavenCentral()
    mavenLocal()
}

compileJava {
    options.forkOptions.executable = 'javac'
    options.encoding = 'UTF-8'
}

dependencies {
    compileOnly group: 'org.spigotmc', name: 'spigot-api', version: 'spigotVersion'
    compile group: 'me.blackness', name: 'black', version: 'currentLatestVersion'
}

shadowJar {
    baseName = 'jarfilename'
    classifier = null
}

configurations {
    testCompile.extendsFrom compileOnly
}

build.dependsOn shadowJar

```
and run gradle task named "build" to generate your jar file.
