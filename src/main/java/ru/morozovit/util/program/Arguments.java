package ru.morozovit.util.program;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Arguments implements Serializable {
    private final String[] arguments;

    public Arguments() {
        this(new String[0]);
    }

    public Arguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String[] getArguments() {
        return arguments;
    }

    public String getArgument(int index) {
        return arguments[index];
    }

    public int getArgumentCount() {
        return arguments.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(arguments);
    }

    public Map<String, String> getArgumentsMap() {
        return getArgumentsMap("--");
    }

    public Map<String, String> getArgumentsMap(String prefix) {
        String prevArg = "";

        Map<String, String> map = new HashMap<>();

        for (String argument : arguments) {
            if (prevArg.startsWith(prefix)) {
                map.put(prevArg.substring(prefix.length()), argument);
            }

            prevArg = argument;

        }
        return map;
    }

    public boolean hasArgument(String argument) {
        boolean hasArgument;
        hasArgument = getArgumentsMap("--").containsKey(argument);
        if (!hasArgument) {
            hasArgument = Arrays.asList(arguments).contains(argument);
        }

        return hasArgument;
    }

    public boolean hasArgument(String prefix, String argument) {
        return getArgumentsMap(prefix).containsKey(argument);
    }

    public String getArgument(String argument) {
        return getArgumentsMap("--").get(argument);
    }

    public String getArgument(String prefix, String argument) {
        return getArgumentsMap(prefix).get(argument);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Arguments) return Arrays.equals(this.arguments, ((Arguments) o).arguments);
        return false;
    }
}
