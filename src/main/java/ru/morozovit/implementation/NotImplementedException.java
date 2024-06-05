package ru.morozovit.implementation;

import java.util.Optional;

public class NotImplementedException extends UnsupportedOperationException {
    @java.io.Serial
    private static final long serialVersionUID = 1475838191689358878L;


    private static String getRunningMethod() {
        Optional<StackWalker.StackFrame> stack = StackWalker
                .getInstance()
                .walk(s -> s.skip(2).findFirst());

        if (stack.isPresent()) {
            return stack.get().getMethodName();
        } else {
            return "Unknown";
        }

    }

    public NotImplementedException() {
        super("["+getRunningMethod()+"] is not implemented yet!");
    }
}
