package com.nanib.language;

import java.util.List;
import java.util.Map;

class JNanibClass implements JNanibCallable {
    final String name;
    final JNanibClass superclass;
    private final Map<String, JNanibFunction> methods;

    JNanibClass(String name, JNanibClass superclass, Map<String, JNanibFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    JNanibFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethod(name);
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        JNanibInstance instance = new JNanibInstance(this);
        JNanibFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    @Override
    public int arity() {
        JNanibFunction initializer = findMethod("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }
}
