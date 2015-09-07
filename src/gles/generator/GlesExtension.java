package gles.generator;

import java.util.Comparator;

/**
 * Under development
 * @author Alessandro Borges
 *
 */
public abstract class GlesExtension implements Comparator<GlesExtension> {

    /**
     * Store all extensions
     **/
    protected static String[] gleExtensions;
    protected static String[] eglExtensions;

    public String             nameExt   = "";
    public boolean            supported = false;
    private boolean           unchecked = true;
    private boolean           avail     = false;

    protected long            fnPtr;
    /**
     * cmd names
     */
    protected String[]        cmdNames;
    protected String[]        cmdFnPtrs;

    protected GlesExtension() {
    }

    public boolean isAvailable() {
        if (unchecked) check();
        return avail;
    }

    protected final void check() {
        // load all extensions
        // check availbility
        // EGlGetProcAddress and set value fnPtr
        // set avail and unchecked flags
    }

    // declare constants
    // public static final

    // declare commands commands

    @Override
    public int compare(GlesExtension o1, GlesExtension o2) {
        if (o2 == o1) return 0;
        if (o1 == null && o2 != null) return -1;
        if (o1 != null && o2 == null) return 1;

        int val = o1.nameExt.compareTo(o2.nameExt);
        return val;
    }

}
