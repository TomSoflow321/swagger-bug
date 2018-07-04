# Topic

This is a small project to demonstrate an issue in Springfox's Swagger UI which
fails to list the available controller/rest endpoints.

This issue occurs when using SpringBoot with enabled pre/post authorization filter
(using `@EnableGlobalMethodSecurity(prePostEnabled = true)`) and using an interface for the
rest annotations and an impl class for the actual endpoint implementation.

This authorization filter can be used to annotate the controller method with something like
`@PreAuthorize("hasAuthority('myRole')")` to check if the available `Authorization` object contains
the specified authority. This can be used to restrict the access for an endpoint to certain user roles.

# How to start the project

This project includes a Gradle wrapper, thus the host system doesn't need an own Gradle installed.

Linux

 `./gradlew build bootRun`
 
Windows

 `gradlew.bat build bootRun`

This builds the project and starts the embedded Tomcat server on port 8181.

Open your browser and open the URL [http://localhost:8181/api/swagger-ui.html](http://localhost:8181/api/swagger-ui.html) to
view the Swagger UI and the mapped controllers/endpoints.

Due to the bug this list is either empty or Swagger shows 
> No operations defined in spec! 

# Existing workarounds

There are two workarounds to bypass the issue, but each with their own downsides.

## Workaround 1

Deactivate the pre/post filter by using
```
@EnableGlobalMethodSecurity(prePostEnabled = true)
```
or
```
@EnableGlobalMethodSecurity
```
(default is `false`).

This causes more "manual" work to verify existing authorities in the incoming `Authorization` object.

## Workaround 2

Don't use interfaces and implementation classes to split the annotations from the endpoint implementation.
Having the annotations and the actual implementation in the same class avoids the issue.

Not a big issue, but this can bloat the controller class when having several endpoints in one class since
each endpoint needs a bunch of annotations to configure the request mapping, the api operations etc. etc.

## (Workaround 3 (doesn't work in this example project))

It is also possible to use a different mapping strategy then selecting classes by their base classes,
like done in the class `com.tomsoflow.swagger.bug.configuration.RestConfiguration`:
```
.apis(RequestHandlerSelectors.basePackage(SwaggerBugApplication.class.getPackage().getName()))
```

You could try to use:
```
.apis(RequestHandlerSelectors.any())
```
Or work with paths instead, like: 
```
.paths(PathSelectors.ant("/**"))
```
Both way don't work in this project for some reasons, but they might work for you. The downside is that both
can include several internal endpoints from Spring (like a health or error endpoint) or Swagger.

# Conclusion

There are existing workarounds to avoid the given issue, but it would be best if the issue can be fixed
to allow using pre/post filters and dedicated interfaces and classes to split annotations and implementations. 