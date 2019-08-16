# black

[![jitpack](https://jitpack.io/v/Personinblack/black.svg)](https://jitpack.io/#Personinblack/black)

black is an extensive and object-oriented inventory framework designed for spigot.

[a live demonstration of how black works (outdated atm)](https://my.mixtape.moe/vemebo.webm)

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

in black, elements creates panes and panes creates pages.
later on those pages can be showed to players.

---

## how can i use this

before anything else, you have to prepare the blackness. you can do that by saying
`new Blackness().prepareFor(yourPluginInstance);` inside your onEnable method.

this is required because black needs to register events for your inventories.

<sub><sub>also check out the *working with build tools* section.

### now lets create some elements

an element is an object that can be placed inside a pane. you can think of elements as buttons but they
are not just handling clicks; they can be used for decorating your inventories too. they determine
how your inventories look and how they behave.

black comes with two element objects, `BasicElement` and `LiveElement`.

basic element is the simplest element. it asks for an icon (`ItemStack`) and
some targets (`Target`).

you should already know what an `ItemStack` is but what you may don't know is the `Target`.
`Target`s are your event handlers; they take events, run them through their `Requirement`s and
do stuff according to the `Requirement`s' output.

take a look at this example:

```java
final Element myFirstElement = new BasicElement(
    new ItemStack(Material.APPLE),  // this is the icon
    // this is a target which requires you to click on the apple with your keyboard button 1.
    new ClickTarget(event -> {
        event.player().sendMessage("you have clicked on the apple using the hotbar button 1");
    }, new HotbarButtonReq(1)),
    // and this is a target which only requires a drag event to be happened.
    new DragTarget(event -> {
        event.player().sendMessage("you have dragged more apples to the apple and apple sucks");
    }),
    // finally this is just a basic target, which can handle both clicks and drags.
    new BasicTarget(event -> {
        // let's just cancel all of them.
        event.cancel();
    })
);
```

live element is a group of elements (you can use your custom elements or basic elements).
what it does is it loops through every element you gave to it and displays them in the order
you gave with the amount of period you specified in between.

here is an example live element:

```java
// this is the element we created earlier.
final Element myFirstElement = theElementWeCreatedEarlier();

// another basic element.
final Element mySecondElement = new BasicElement(
    new ItemStack(Material.AIR),
    new BasicTarget(ElementBasicEvent::cancel)      // canceling all the events in a compact way.
);

// this is your plugin instance.
final Plugin myPlugin =
    Bukkit.getPluginManager().getPlugin("myPluginsName");

// live element inside live element??!!?
final Element myThirdElement = new LiveElement(
    myPlugin,
    5,
    myFirstElement, mySecondElement
);

/*
myPlugin is your plugin instance,
20 is the delay between each animation frame and
elements are the frames in order.
*/
final Element mySecondLiveElement = new LiveElement(
    myPlugin,
    20,
    myFirstElement, mySecondElement, myThirdElement
);
```

***you can't and you shouldn't***:

- edit any element after its creation. you can always create a new one and update your panes with it.
- get any information from an element. (like its icon's display name)

if you are asking the question `but why the f***??!!?`, check out `encapsulation`.

### then create some panes

a pane is a group of elements that has a size (height and length)
and a position (x and y locations). panes can be stacked on top of each other
like layers, inside pages.

there are two pane objects that comes with black. those are `BasicPane` and `LivePane`.

basic pane is the simplest pane. it asks for a x location (`int`), a y location (`int`),
a height (`int`) and a length (`int`).

need an example? here it comes...

```java
final Element backgroundElement = new BasicElement(
    new ItemStack(Material.STAINED_GLASS_PANE),
    new BasicTarget(ElementBasicEvent::cancel)
);

// this is just a list, nothing fancy or
// relevant to the topic.
final List<Element> playerHeads =
    new PlayerHeads(PlayerType.STAFF);

final Element exitButton = new BasicElement(
    new ItemStack(Material.BARRIER),
    new BasicTarget(event -> {
        event.cancel();
        event.closeView();
    })
);

/*
first two integers are x and y locations,
second two integers are height and length
and the last parameter is the element to
fill the pane with.

if you insert multiple elements, they will
be added to the pane just like when you call
the add method.
*/
final Pane myFirstPane = new BasicPane(
    0, 0,   // x and y locations
    1, 1,    // height and length
    backgroundElement // an element to fill the pane with
);

final Pane mySecondPane = new BasicPane(
    0, 0,
    6, 9
);

// looping through every player head in the list above
// and adding them to the second pane one by one.
playerHeads.forEach(mySecondPane::add);

/*
4 is the x location (center),
5 is the y location (bottom)
true is for shifting any other element that could be
at that location. if there is an element at the end
it will be vanished.
*/
mySecondPane.insert(
    exitButton,
    4, 5
    true
);
```

live pane is the animated version of the basic pane you can create out of basic panes.
its using the same concept as the live element so i am not going to write an example for it.

### and finally the page

a page is combination of panes or just one pane in a way that can be displayed on an inventory.
you can build one with a title (`String`), a size (`int`) and the panes to be displayed.

black only has one page object and that is the chest page but you can create
your own custom pages later.

here is an example chest page in case you need one:

```java
// panes from the previous example
final Pane myFirstPane =
    retriveTheFirstPane();
final Pane mySecondPane =
    retriveTheSecondPane();

/*
we inserted the first pane before the
second pane because, first pane is the
background pane and has to appear before
the second pane so second pane can be
on top of it.
*/
final Page thePage = new ChestPage(
    "Staff List", 54,
    myFirstPane, mySecondPane
);

thePage.showTo(
    Bukkit.getPlayerExact("Personinblack")
);
```

---

## what is this decorator pattern thingy

decorator pattern is one of the best design patterns for object-oriented
programming i have ever seen. it replaces the bad keyword called "extends", makes
your code composable and much more flexible than before.

wanna know how it works? well, do you remember the `LiveElement` stuff and how we have bunch of `BasicElement`s inside it? that is the decorator pattern. in this example we are decorating the
`BasicElement`s and making them live:

```java
final ItemStack frame1 = new ItemStack(Material.STONE);
final ItemStack frame2 = new ItemStack(Material.SPONGE);

final Element liveElement = new LiveElement(this, 20,
    new BasicElement(frame1, new BasicTarget(event -> {
        event.cancel();
        event.player().sendMessage("this is frame 1");
    })),
    new BasicElement(frame2, new BasicTarget(event -> {
        event.cancel();
        event.player().sendMessage("and this is frame 2");
    }))
);
```

so, the `LiveElement` asks for two `Element`s to work with. you can insert another `LiveElement`
but this `LiveElement` will also ask you for an `Element`. this makes sure that you will always
end up with a `BasicElement` (or your own `BasicElement` clone). `LiveElement` itself does not
reimplement the methods that already exists in `BasicElement` but it extends them. for example
the `displayOn` method of the `LiveElement` is calling `displayOn` methods of every frame
one by one.

you should now have the basic knowledge about how decorator pattern works. and you may want to
extend `BasicElement` or `LiveElement`'s usage by yourself too. let's see how you can do that:

```java
final class CustomElement implements Element {
    private final Element baseElement;

    public CustomElement(final Element baseElement) {
        this.baseElement = Objects.requireNonNull(baseElement);
    }

    @Override
    public void displayOn(final Inventory inventory, final int locX, final int locY) {
        baseElement.displayOn(inventory, locX, locY);
    }

    @Override
    public void accept(final InventoryInteractEvent event) {
        baseElement.accept(event);
    }

    @Override
    public boolean is(final ItemStack icon) {
        return baseElement.is(Objects.requireNonNull(icon));
    }

    @Override
    public boolean is(final Element element) {
        return baseElement.is(Objects.requireNonNull(element));
    }
}
```

first things first, we have an `Element` as a parameter thats called `baseElement`. this element
is what we are decorating. we are calling its methods inside our `CustomElement`'s methods.
this is important, we are not recoding the methods of `baseElement`, we are just extending them.

but hey! there is nothing custom here at the moment. so if you do something like this:

```java
final Element liveElement = new LiveElement(/*the parameters*/);
final Element customElement = new CustomElement(liveElement);
```

the `customElement` will act just like the `liveElement`.

so you have to customize this `CustomElement` and make it yours! compare `LiveElement` and
`BasicElement` to see how you can customize your `CustomElement`.

---

## working with build tools

you probably will want to use black with a build tool to shade it into your projects.
since i'm only familiar with `gradle` i will give an example for it. if any of you reading this
have knowledge about any other build tools, you can create a pull request and submit an example.

### gradle

so you want to use black with gradle and don't know where to start huh?
don't worry i got you covered.

first of all we need the gradle plugin called `shadow`. it can shade in any dependencies you have
into your jar files. we can set it up like this:

build.gradle

```groovy
plugins { id "com.github.johnrengelman.shadow" version "2.0.3" }

shadowJar {
    baseName = '<outputJarName>'
    classifier = null
}

build.dependsOn shadowJar
```

so what do we have here? we have the `build` task depending on the `shadowJar` task. by having that,
when we run the `build` task it will automatically run the `shadowJar` task. thats great!
we also have a `shadowJar` scope to configure the task. inside the scope we are giving a name to
our jar file and removing the `classifier`. you can also modify the `version` too if you want or
set it to *null* just like the `classifier`.

the plugins scope we have in the example, won't work if you are under gradle version 2.1.
so you have to do this:

build.gradle

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.jengelman.gradle.plugins:shadow:<latestVersionNumber>'
    }
}

apply plugin: 'com.github.johnrengelman.shadow'
```

it just adds the `shadow` plugin to your project.

we are done with the `shadow` and we can now add the `jitpack` repository to the project.
this is pretty easy, just do this:

build.gradle

```groovy
repositories {
    // there probably will be bunch of other repositories too like spigot.
    maven { url 'https://jitpack.io' }
}
```

last step! adding and configuring dependencies...

build.gradle

```groovy
compileOnly group: 'org.spigotmc', name: 'spigot-api', version: '<spigotVersion>'
compile group: 'com.github.Personinblack', name: 'black', version: '<latestReleaseTag>'
```

i included the `spigot-api` dependency in here for a reason. did you notice the difference between
the two dependencies? yes, the `spigot-api` does have `compileOnly`. but what does it mean? well,
it means that we don't want to shade `spigot-api` into our jar file because our server already
have it. `shadow` will see that and ignore the `spigot-api` dependency. you can do that with any
dependencies you don't want inside your jar file.

aaaannnd done! you can just run the `build` task by saying `gradle build` inside your project
folder where you have the `build.gradle` file. this will generate a jar file with `black` inside it.
you can check the jar file by opening it with an archive manager to see if `black` is in it.

---

please do feel free to ask any of your questions, share your ideas through issues tab above.

you can find me on [spigot](https://spigotmc.org/) as "Menfie" and on [discord](https://discordapp.com/)
as "Personinblack#6059"

---

<sub><sub><sup>stay black!
