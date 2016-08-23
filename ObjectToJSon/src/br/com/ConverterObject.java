package br.com;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilson
 */
public class ConverterObject {

    public static String convert(Object object) {
        StringBuilder sb = new StringBuilder();
        initiateStringBuilder(sb, object);
        for (Method method : object.getClass().getDeclaredMethods()) {
            try {
                if (isGetter(method)) {
                    Object aux = method.invoke(object);
                    if (aux == null) {
                        appendStringBuilder(method.getName(), aux, sb);
                    } else if (Collection.class.isAssignableFrom(aux.getClass())
                            || aux.getClass().isArray()) {
                        sb.append(convertArray(aux, method.getName()));
                    } else {
                        appendStringBuilder(method.getName(), aux, sb);
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(ConverterObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeStringBuilder(sb);
        return writeJson(sb);
    }

    private static StringBuilder convertArray(Object object, String name) {
        Collection col = (Collection) object;
        StringBuilder sb = new StringBuilder();
        initiateStringBuilderFromArray(sb, col, name, false);
        col.stream().forEach((Object o) -> {
            if (o.getClass().getCanonicalName().startsWith("java.")) {
                appendStringBuilder(null, o, sb);
            } else {
                appendStringBuilderFromItem(sb, true);
                for (Method method : o.getClass().getDeclaredMethods()) {
                    try {
                        if (isGetter(method)) {
                            Object aux = method.invoke(o);
                            if (aux == null) {
                                appendStringBuilder(method.getName(), aux, sb);
                            } else if (Collection.class.isAssignableFrom(aux.getClass())
                                    || aux.getClass().isArray()) {
                                convertArray(aux, method.getName());
                            } else {
                                appendStringBuilder(method.getName(), aux, sb);
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(ConverterObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                appendStringBuilderFromItem(sb, false);
            }
        });
        closeStringBuilderFromArray(sb);
        return sb;
    }

    public static String convertArray(Collection col) {
        StringBuilder sb = new StringBuilder();
        initiateStringBuilderFromArray(sb, col, col.toArray()[0].getClass().getSimpleName().toLowerCase(), true);
        col.stream().forEach((Object o) -> {
            if (o.getClass().getCanonicalName().startsWith("java.")) {
                appendStringBuilder(null, o, sb);
            } else {
                appendStringBuilderFromItem(sb, true);
                for (Method method : o.getClass().getDeclaredMethods()) {
                    try {
                        if (isGetter(method)) {
                            Object aux = method.invoke(o);
                            if (aux == null) {
                                appendStringBuilder(method.getName(), aux, sb);
                            } else if (Collection.class.isAssignableFrom(aux.getClass())
                                    || aux.getClass().isArray()) {
                                convertArray(aux, method.getName());
                            } else {
                                appendStringBuilder(method.getName(), aux, sb);
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(ConverterObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                appendStringBuilderFromItem(sb, false);
            }
        });
        closeStringBuilderFromArray(sb);
        closeStringBuilderForArray(sb);
        return writeJson(sb);
    }

    private static boolean isGetter(Method method) {
        if (!method.getName().startsWith("is")
                && !method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }

    private static void initiateStringBuilder(StringBuilder sb, Object object) {
        sb.append("{");
        sb.append("\"");
        sb.append(object.getClass().getSimpleName().toLowerCase());
        sb.append("\" ");
        sb.append(": {");

    }

    private static void appendStringBuilderFromItem(StringBuilder sb, boolean isInit) {
        if (isInit) {
            sb.append("{");
        } else {
            sb.append("}, ");
        }
    }

    private static void initiateStringBuilderFromArray(StringBuilder sb, Collection col, String name, boolean isFromList) {
        if (isFromList) {
            sb.append("{");
        }
        sb.append("\"");
        sb.append(name.replaceAll("get", "").toLowerCase());
        sb.append("\" ");
        sb.append(":[");

    }

    private static void closeStringBuilder(StringBuilder sb) {
        sb.append("}}");
    }

    private static void closeStringBuilderForArray(StringBuilder sb) {
        sb.append("}");
    }

    private static void appendStringBuilder(String name, Object o, StringBuilder sb) {
        if (name != null) {
            sb.append("\"");
            sb.append(name.replace("get", "").toLowerCase());
            sb.append("\"");
            sb.append(" : ");
        }
        if (o instanceof String) {
            sb.append("\"");
            sb.append(o);
            sb.append("\"");
        } else {
            sb.append(o);
        }
        sb.append(", ");
    }

    private static void closeStringBuilderFromArray(StringBuilder sb) {
        sb.append("], ");
    }

    private static String writeJson(StringBuilder sb) {
        String json = sb.toString().replaceAll(", }", "}").replaceAll(", ]", "]");
        Logger.getLogger(ConverterObject.class.getName()).log(Level.INFO, "Json gerado: {0}", json);
        return json;
    }
}
