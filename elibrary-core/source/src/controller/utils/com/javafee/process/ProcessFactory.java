package com.javafee.process;

public class ProcessFactory {
    public static Process create(Class c) throws IllegalAccessException, InstantiationException {
        return (Process) c.newInstance();
    }
}
