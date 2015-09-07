package gles.generator.internal;

import java.util.*;

import org.xml.sax.Attributes;

/**
 * Hold GL Enumerations
 * @author Alessandro Borges
 *
 */
public class GLenumGroup {
    public String vendor, group, comments, namespace, type;
    public String start, end;
    public Map<String, GLenum> enums = new HashMap<String, GLenum>(16);

    public GLenumGroup() {
    }

    public GLenumGroup(Attributes attributes) {
        vendor = attributes.getValue("vendor");
        group = attributes.getValue("group");
        comments = attributes.getValue("comments");
        namespace = attributes.getValue("namespace");
        type = attributes.getValue("type");
        start = attributes.getValue("start");
        end = attributes.getValue("end");
    }

    public void add(GLenum glEnum) {
        enums.put(glEnum.name, glEnum);
    }

    public GLenum get(String enumName) {
        return enums.get(enumName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("GLenums [");
        if (vendor != null) {
            builder.append("vendor=");
            builder.append(vendor);
            builder.append(", ");
        }
        if (group != null) {
            builder.append("group=");
            builder.append(group);
            builder.append(", ");
        }
        if (comments != null) {
            builder.append("comments=");
            builder.append(comments);
            builder.append(", ");
        }
        if (namespace != null) {
            builder.append("namespace=");
            builder.append(namespace);
            builder.append(", ");
        }
        if (type != null) {
            builder.append("type=");
            builder.append(type);
            builder.append(", ");
        }
        if (start != null) {
            builder.append("start=");
            builder.append(start);
            builder.append(", ");
        }
        if (end != null) {
            builder.append("end=");
            builder.append(end);
            builder.append(", ");
        }
        if (enums != null) {
            builder.append("enums=");
            builder.append(toString(enums.entrySet(), maxLen));
        }
        builder.append("]");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

}
