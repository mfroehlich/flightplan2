package com.prodyna.stream.backend.mongo;

/**
 * Application entity with details about the application.
 */
public class Application {
    private String name;

    public Application() {
        // ok
    }

    public Application(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Application{" +
                "name='" + name + '\'' +
                '}';
    }
}
