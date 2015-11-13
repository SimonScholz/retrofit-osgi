# retrofit-osgi
This repository provides a conversion gradle build for retrofit to osgi and a sample Eclipse plug-ins to use it.

# Getting retrofit from a p2 update site
The retrofit and its gson converter bundles can be obtained from the following p2 update site:

https://dl.bintray.com/simon-scholz/retrofit-osgi/

This p2 update site can also be referenced by a target definition.
See also https://github.com/SimonScholz/retrofit-osgi/blob/master/retrofit-osgi-target/retrofit-osgi.target

# Convert desired retrofit artifacts to osgi

The retrofit-osgi-convert folder contains a build.gradle build file, which makes use of the org.standardout:bnd-platform:1.2.0 Gradle plugin to download all Retrofit + Retrofit GSON converter dependencies and converts these to OSGi bundles.

Since Retrofit and its converter artifacts have split packages, which usually cause problems in an OSGi runtime the given [build.gradle](https://github.com/SimonScholz/retrofit-osgi/blob/master/retrofit-osgi-convert/build.gradle) script adjusts the split package meta data for desired plugins. Also see http://wiki.osgi.org/wiki/Split_Packages.

Creating the desired bundles
----------------------------

Navigate to the retrofit-osgi-convert folder with your command line and use:

```
./gradlew bundles
```

Creating a p2 updatesite
----------------------

Navigate to the retrofit-osgi-convert folder with your command line and use:

```
./gradlew updateSite
```

# Make use of Retrofit in OSGi

Due to the split package problem(http://wiki.osgi.org/wiki/Split_Packages), there must be a third plugin, e.g., https://github.com/SimonScholz/retrofit-osgi/tree/master/retrofit-osgi, which dependends on bundles with split packages and reexports the split packages, e.g., the 'retrofit' package.

So the retrofit-osgi bundle, which combines the com.squareup.retrofit and com.squareup.retrofit.converter-gson can be used as dependency in another plugin, which wants to make use of Retrofit.

# Starting the sample application

In the retrofit-osgi-target project is a target file, where the proper target platform for the sample project can be set. After this is done the de.simonscholz.retrofit.app.product file in the de.simonscholz.retrofit.product project can be used to start the application, which loads the list of contributors of the platform.ui project.

![Image of the Retrofit Eclipse 4 Sample Application](https://cloud.githubusercontent.com/assets/7559962/11023612/75250c22-867e-11e5-8bc0-0dda1682d740.png)
