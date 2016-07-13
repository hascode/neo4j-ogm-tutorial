# Neo4j OGM Tutorial

A demonstration how to use [Neo4j OGM] for mapping graph nodes and relations to Java classes.

Please feel free to have a look at my blog at [www.hascode.com] for the full tutorial.

## Running the Examples

Simply run the examples using [Gradle] and the command-line.

### Train Timetable Example

```
gradle -PmainClass=com.hascode.tutorial.boundary.PermissionExample execute
```


### Complex Permission Example

```
gradle -PmainClass=com.hascode.tutorial.boundary.TrainTimetableExample execute
```

---------

**2016 Micha Kops / hasCode.com**

   [www.hascode.com]:http://www.hascode.com/
   [Gradle]:https://gradle.org/
   [Neo4j OGM]:https://github.com/neo4j/neo4j-ogm
