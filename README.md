# Java Container - IoC

The Java Container is a lightweight library for managing dependencies and performing dependency injection, based on the [service container](https://laravel.com/docs/master/container) of [Laravel](https://laravel.com/).

[![Build Status](https://travis-ci.com/DemianD/java-container.svg?branch=master)](https://travis-ci.com/DemianD/java-container)
![Bintray](https://img.shields.io/bintray/v/demian/maven/java-container.svg)

## Binding

The `bind` method binds an interface to a concrete class. When the `Mail.class` is resolved, a `Mailgun.class` is returned.

```java
import static be.dem.container.Container.bind;

public void register() {
    bind(InterfaceB.class, ConcreteB.class);

    bind(Mail.class, Mailgun.class);
}
```

Once a singleton binding is resolved, the same object instance will be returned.

```java
import static be.dem.container.Container.bindSingleton;

public void register() {
    bindSingleton(Config.class);
}
```

## Resolving

There is no difference between resolving a basic binding or a singleton.

```java
import static be.dem.container.Container.resolve;

public void someMethod() {
    // mail is a Mailgun object
    Mail mail = resolve(Mail.class)
    
    // config is a singleton object
    Config config = resolve(Config.class)
}
```

## Installation

**Maven:**

```
<dependency>
  <groupId>be.dem.container</groupId>
  <artifactId>java-container</artifactId>
  <version>0.1.0</version>
  <type>pom</type>
</dependency>
```

**Gradle:**

```
compile 'be.dem.container:java-container:0.1.0'
```

## Development

There is a `Dockerfile` that specifies all the requirements to test, build and publish the library.

```bash
$ docker build -t java-container . 
$ docker run -it --rm -v ${PWD}:/usr/src/app --name java-container-app java-container
```

### Testing

```bash
$ ./gradlew test
```

### Building

```bash
$ ./gradlew build
```

## Todo

- Example showing automatically Constructor Argument Injection

## Credits

- [Robin Malfait](https://github.com/RobinMalfait)
- [Demian Dekoninck](https://github.com/DemianD)
- [All Contributors](../../contributors)

## License

The MIT License (MIT). Please see [License File](LICENSE.md) for more information.
