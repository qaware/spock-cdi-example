plugins {
    id("groovy")
}

repositories {
    mavenCentral()
}

dependencies {

    // CDI
    implementation("jakarta.platform:jakarta.jakartaee-api:8.0.0")

    if (project.hasProperty("useOpenWebBeans")) {
        // CDI implementation: openwebbeans
        testRuntimeOnly("org.apache.openwebbeans:openwebbeans-spi:2.0.20")
        testRuntimeOnly("org.apache.openwebbeans:openwebbeans-se:2.0.20")
    } else {
        // CDI implementation: WELD
        testRuntimeOnly("org.jboss.weld.se:weld-se-core:3.1.5.Final")
        testRuntimeOnly("org.jboss:jandex:2.2.2.Final")
    }

    // Common test libraries
    testImplementation("org.codehaus.groovy:groovy-all:2.4.9")
    testImplementation("org.spockframework:spock-core:1.1-groovy-2.4")
    testRuntimeOnly("cglib:cglib-nodep:3.2.5") // add code generation library for Spock-Mocking
    testRuntimeOnly("org.objenesis:objenesis:2.6") // add code generation library for Spock-Mocking

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")
}

// workaround for WELD: WELD does only look for beans in the same (classpath) folder as the beans.xml, but
// Gradle separates resources from classes by default
sourceSets {
    main {
        output.setResourcesDir(file("build/classes/java/main"))
    }
    test {
        output.setResourcesDir(file("build/classes/groovy/test"))
    }
}
