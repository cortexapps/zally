dependencies {
    compile(project(":zally-rule-api"))
    compile("com.github.zeitlinger.swagger-parser:swagger-parser:v2.0.14-z4")
    compile("io.github.config4k:config4k:0.4.1")
    compile("de.mpg.mpi-inf:javatools:1.1")

    compile(group="com.typesafe", name= "config", version= "1.4.0")

    testCompile("org.reflections:reflections:0.9.12")
}
