# Kings Corner

This game came from [Rodolfo Lemes repository](https://github.com/RodolfoLemes/kings-corner), and my urge to play with gRPC.

A lot of suffering while studying and some errors which I came across. Here's the major topic I did to make it work:

### Apple M1 is not welcome:
With the help of [this thread](https://stackoverflow.com/questions/70072933/could-not-find-protoc-3-9-2-osx-on-flutter-android-build), I was able to use the libraries in my M1 air, like below.
```kotlin
if (project.hasProperty('protoc_platform')) {  
    artifact = "com.google.protobuf:protoc:3.7.0:${protoc_platform}"  
} else {  
    artifact = "com.google.protobuf:protoc:3.7.0"  
}
```
I added the `protoc_platform` variable to the `gradle.properties` file:
```protoc_platform=osx-x86_64```
