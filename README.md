# black, black and black

black is an extensive and object-oriented inventory framework designed for spigot.

with black you can:
- create infinite amount of pages
- link those pages
- have panes inside pages for dividing your pages into sections
- layer those panes however you like (for like having a background pane below other panes)
- create animated elements with changing actions and icons

black does not contain:
- `static` but not `private` variables
- getters and setters
- `static` methods
- `null` references
- `extends` keyword on anything other than interfaces
- mutable objects
- any object without a purpose to live (data objects for example)
- any method that has more than one purpose
- any not `final` but `public` method without `@Override` annotation

# how can i use this?

first register it by saying
`new Black().prepareTheBlacknessFor(yourPluginsInstance);`

then you can start creating your inventories like me:

```java
private void showMinigameMenuTo(Player player) {
    final ItemStack findMatchIcon, leaderboardIcon, settingsIcon, exitIcon,
        backgroundIcon;
    final Element findMatchElement, leaderboardElement, settingsElement, exitElement,
        backgroundElement;

    /*
     * skippin' defining icons
     */

    findMatchElement = new BasicElement(findMatchIcon, (event) -> {
        event.setCancelled(true);
        findMatch(player);
    });

    /*
     * skippin' defining other elements
     */

    final Pane backgroundPane = new BasicPane(0, 0, 1, 9);
    backgroundPane.fill(backgroundElement);

    final Pane foregroundPane = new BasicPane(0, 0, 1, 9);
    // false means elements after 0, 0 won't be shifted
    foregroundPane.insert(findMatchElement, 0, 0, false);
    foregroundPane.insert(leaderboardElement, 0, 3, false);

    /*
     * skippin' inserting other elements
     */

    // adding background pane first makes it show before foreground and
    // makes it being overrided by foreground that way.
    new ChestPage("Minigame Menu", 9, backgroundPane,
        foregroundPane).showTo(player);
}
```

but to get this beauty you have to shadow/shade (extract it to your jar file) black into your plugin.
for gradle you can begin with this build.gradle example:


```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.jengelman.gradle.plugins:shadow:latestVersionNumber'
    }
}

apply plugin: 'com.github.johnrengelman.shadow'
apply plugin: 'java'

group = 'me.blackness.test'
version = '0.0.0'

sourceCompatibility = '1.8'
targetCompatibility = '1.8'

repositories {
    maven {
        url 'https://hub.spigotmc.org/nexus/content/repositories/snapshots/'
    }
    mavenCentral()
    mavenLocal()
}

compileJava {
    options.forkOptions.executable = 'javac'
    options.encoding = 'UTF-8'
}

dependencies {
    // compileOnly for not shading/shadowing
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

# what is decorator pattern and how can i use it
decorator pattern is one of the best design patterns for object-oriented
programming i have ever seen. it replaces the bad keyword called "extends", makes
your code composable and much more flexible than before.

how you can use it? well, for example if you want to create a live element you have
to do this:

```java
final Element liveElement = new LiveElement(this, 20,
    new BasicElement(frame1, (event) -> event.setCancelled(true)),
    new BasicElement(frame2, (event) -> {
        event.setCancelled(true);
        event.getPlayer().sendMessage("this is frame 2");
    })
);
```

in this example above, we have two basic elements inside a live element and that live element will
show those basic elements in the order we insert them.

you may also want to create your own custom element object. to do this you have to
implement the interface called "element" and in your ctor (contructor)
you can ask for another element. for example:

```java
final class CustomElement implements Element {
    private final Element baseElement;
    // custom variables

    public CustomElement(Element baseElement /* custom parameters */) {
        this.baseElement = baseElement;
    }

    // custom methods

    @Override
    public void accept(InventoryClickEvent event) {
        // custom stuff
        baseElement.accept(event);
    }

    @Override
    public void displayOn(Inventory inventory, int locX, int locY) {
        // custom stuff
        baseElement.displayOn(inventory, locX, locY);
    }

    @Override
    public boolean equals(ItemStack icon) {
        // custom checks
        return baseElement.equals(icon);
    }

    @Override
    public boolean equals(Element element) {
        // custom checks
        return baseElement.equals(element);
    }
}
```

and maybe use your new element like this:

```java
final Element customLiveElement = new LiveElement(this, 20,
    new CustomElement(
        new BasicElement(frame1, (event) -> event.setCancelled(true))
    ),
    new BasicElement(frame2, (event) -> {
        event.setCancelled(true);
        event.getPlayer().sendMessage("this is frame 2");
    })
);
```

----------

please do feel free to ask any of your questions, share your ideas through issues tab above.

you can find me on [spigot](https://spigotmc.org/) as "Menfie" and on [discord](https://discordapp.com/)
as "Personinblack#6059"

----------

<sub><sup>stay black!</sup><sub>
