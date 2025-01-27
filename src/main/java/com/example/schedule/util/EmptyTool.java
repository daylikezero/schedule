package com.example.schedule.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyTool {

    @SuppressWarnings("rawtypes")
    public static boolean empty(Object obj) {
        if (obj instanceof String)
            return obj == null || "".equals(obj.toString().trim());
        else if (obj instanceof List)
            return obj == null || ((List) obj).isEmpty();
        else if (obj instanceof Map)
            return obj == null || ((Map) obj).isEmpty();
        else if (obj instanceof Set)
            return obj == null || ((Set) obj).isEmpty();
        else if (obj instanceof Object[])
            return obj == null || Array.getLength(obj) == 0;
        else
            return obj == null;
    }

    public static boolean empty(Object... args) {
        if (args == null) {
            return true;
        }

        for (Object o : args) {
            if (notEmpty(o)) {
                return false;
            }
        }

        return true;
    }

    public static boolean emptyAtLeastOne(Object... args) {
        if (args == null) {
            return true;
        }

        for (Object o : args) {
            if (empty(o)) {
                return true;
            }
        }

        return false;
    }

    public static boolean notEmpty(Object obj) {
        return !empty(obj);
    }

    public static boolean notEmpty(Object... args) {
        if (args == null) {
            return false;
        }

        for (Object o : args) {
            if (empty(o)) {
                return false;
            }
        }

        return true;
    }

    public static boolean notEmptyArray(Object[] args) {
        if (args == null) {
            return false;
        }

        for (Object o : args) {
            if (empty(o)) {
                return false;
            }
        }

        return true;
    }

    public static boolean notEmptyAtLeastOne(Object... args) {
        if (empty(args)) {
            return false;
        }

        for (Object o : args) {
            if (notEmpty(o)) {
                return true;
            }
        }
        return false;
    }
}
