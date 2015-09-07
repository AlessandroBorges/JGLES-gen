package gles.generator.internal;

import java.math.BigInteger;
import java.text.NumberFormat;

import org.xml.sax.Attributes;

/**
 * 
 * @author Alessandro Borges
 *
 */
public class GLenum implements JavaPrinter, Comparable {

    public String name, value;

    public GLenum() {
    }

    public GLenum(Attributes attributes) {
        name = attributes.getValue("name");
        value = attributes.getValue("value");
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    /**
     * Print as java constant
     */
    @SuppressWarnings("unused")
    public String asJavaString() {
        String val = this.value.trim().replace("0x","");
        try {
            // try common place const int
            int v = Integer.parseInt (val,16);
            return "\t public static final int " + this.toString() + "\n";
        } catch (NumberFormatException exc) {
            
            try {
                // try long
                String valL = val.toLowerCase();
                long v = 0L;
                if(valL.contains("ffffffffffffffff")){
                    // max value
                    v = -1;
                }else{
                    v = Long.parseLong(valL,16);    
                } 
                return "\t public static final long  " + this.name + " = "+ v + "L;\n";
                
            } catch (NumberFormatException exc2) {
                // object !!!!
                String type = unWrappConst(val);                
                return "\t public static final " + type + " " + 
                          name + " = new " + type +"(0)\n"; 
            }
        }       
    }
    
    /**
     * 
     * @param s
     * @return
     */
    private static String unWrappConst(String s){
        String type = s.replace("(", "").replace("0)", "").replace(";", "").replace(")", "");
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GLenum other = (GLenum) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        //return "GLenum [" + (name != null ? "name=" + name + ", " : "") + (value != null ? "value=" + value : "") + "]";
        return "  " +  name + " = " + value +";";
    }

    @Override
    public int compareTo(Object o) {
        if(o == null || !(o instanceof GLenum))
            return 0;
            GLenum other = (GLenum)o;   
        return this.name.compareTo( other.name);
    }
    
//    
//    /**
//     * From: http://tool.oschina.net/uploads/apidocs/guava/src-html/com/google/
//     * common/primitives/UnsignedLongs.html Returns the unsigned {@code long}
//     * value represented by a string with the given radix.
//     * 
//     * @param s
//     *            the string containing the unsigned {@code long} representation
//     *            to be parsed.
//     * @param radix
//     *            the radix to use while parsing {@code s}
//     * @throws NumberFormatException
//     *             if the string does not contain a valid unsigned {@code long}
//     *             with the given radix, or if {@code radix} is not between
//     *             {@link Character#MIN_RADIX} and {@link Character#MAX_RADIX}.
//     */
//    public static long parseUnsignedLong(String s, int radix) {
//        if (s == null) {
//            throw new NumberFormatException("string is null ");
//        }
//        if (s.length() == 0) {
//            throw new NumberFormatException("empty string");
//        }
//        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
//            throw new NumberFormatException("illegal radix:" + radix);
//        }
//
//        int max_safe_pos = maxSafeDigits[radix] - 1;
//        long value = 0;
//        for (int pos = 0; pos < s.length(); pos++) {
//            int digit = Character.digit(s.charAt(pos), radix);
//            if (digit == -1) {
//                throw new NumberFormatException(s);
//            }
//            if (pos > max_safe_pos && overflowInParse(value, digit, radix)) {
//                throw new NumberFormatException("Too large for unsigned long: " + s);
//            }
//            value = (value * radix) + digit;
//        }
//
//        return value;
//    }
//
//    /**
//     * Returns true if (current * radix) + digit is a number too large to be
//     * represented by an unsigned long. This is useful for detecting overflow
//     * while parsing a string representation of a number. Does not verify
//     * whether supplied radix is valid, passing an invalid radix will give
//     * undefined results or an ArrayIndexOutOfBoundsException.
//     */
//    
//    private static boolean overflowInParse(long current, int digit, int radix) {
//        if (current >= 0) {
//            if (current < maxValueDivs[radix]) {
//                return false;
//            }
//            if (current > maxValueDivs[radix]) {
//                return true;
//            }
//            // current == maxValueDivs[radix]
//            return (digit > maxValueMods[radix]);
//        }
//
//        // current < 0: high bit is set
//        return true;
//    }
//
//    public static final long MAX_VALUE = -1L; // Equivalent to 2^64 - 1
//    private static final int[] maxSafeDigits = new int[Character.MAX_RADIX + 1];
//    private static final long[] maxValueDivs = new long[Character.MAX_RADIX + 1];
//    private static final int[] maxValueMods = new int[Character.MAX_RADIX + 1];
//    static {
//                BigInteger overflow = new BigInteger("10000000000000000", 16);
//                for (int i = Character.MIN_RADIX; i <= Character.MAX_RADIX; i++) {
//                  maxValueDivs[i] = divide(MAX_VALUE, i);
//                  maxValueMods[i] = (int) remainder(MAX_VALUE, i);
//                  maxSafeDigits[i] = overflow.toString(i).length() - 1;
//                }
//              }
//   
}
