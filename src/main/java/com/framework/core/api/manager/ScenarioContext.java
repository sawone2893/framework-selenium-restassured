package com.framework.core.api.manager;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
	private static ThreadLocal<Map<String, Object>> context =
            ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    public static <T> T get(String key) {
        return (T) context.get().get(key);
    }

    public static void clear() {
        context.remove(); // IMPORTANT
    }
}
