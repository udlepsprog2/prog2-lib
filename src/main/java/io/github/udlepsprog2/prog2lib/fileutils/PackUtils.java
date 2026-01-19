package io.github.udlepsprog2.prog2lib.fileutils;

/**
 * Utility methods to pack and unpack primitive values and simple strings
 * to/from a byte array using Big-Endian (most significant byte first) order.
 * <p>
 * Supported types: {@code boolean}, {@code byte}, {@code char}, {@code short},
 * {@code int}, {@code long}, {@code float}, {@code double}, and Strings with a
 * fixed maximum length (packed as {@code maxLength} UTF-16 code units with a
 * Big-Endian char layout and an optional null terminator when shorter).
 * </p>
 * <p>
 * Buffer and offset requirements: callers must provide a non-null {@code buffer}
 * large enough to hold the value(s) starting at {@code offset}. No explicit
 * validation is performed; invalid inputs may result in
 * {@link NullPointerException} or {@link ArrayIndexOutOfBoundsException}.
 * </p>
 * <p>
 * Endianness: all multi-byte types use Big-Endian byte order, matching
 * {@link java.io.DataOutput} / {@link java.io.DataInput} conventions.
 * </p>
 *
 * @author jmgimeno
 */

public class PackUtils {

    /**
     * Size in bytes of a packed boolean value. Booleans are encoded as a single byte: {@code 1} for
     * {@code true} and {@code 0} for {@code false}.
     */
    public static final int SIZEOF_BOOLEAN = 1;
    /**
     * Size in bytes of a single {@code byte} value.
     */
    public static final int SIZEOF_BYTE    = 1;
    /**
     * Size in bytes of a Java {@code char} (one UTF-16 code unit). Packed in Big-Endian order.
     */
    public static final int SIZEOF_CHAR    = 2;
    /**
     * Size in bytes of a {@code short}. Packed in Big-Endian order.
     */
    public static final int SIZEOF_SHORT   = 2;
    /**
     * Size in bytes of an {@code int}. Packed in Big-Endian order.
     */
    public static final int SIZEOF_INT     = 4;
    /**
     * Size in bytes of a {@code long}. Packed in Big-Endian order.
     */
    public static final int SIZEOF_LONG    = 8;
    /**
     * Size in bytes of a {@code float} when represented using its IEEE 754 bit pattern
     * (via {@link Float#floatToIntBits(float)}).
     */
    public static final int SIZEOF_FLOAT   = 4;
    /**
     * Size in bytes of a {@code double} when represented using its IEEE 754 bit pattern
     * (via {@link Double#doubleToLongBits(double)}).
     */
    public static final int SIZEOF_DOUBLE  = 8;

    private PackUtils() { }

    /**
     * Writes a boolean value at {@code buffer[offset]}.
     * <p>Encoding: {@code true → 1}, {@code false → 0}.</p>
     *
     * @param b the value to write
     * @param buffer destination byte array (must be non-null)
     * @param offset index at which to write (must be within bounds)
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code offset} is out of range
     */
    public static void packBoolean(boolean b, byte[] buffer, int offset) {
        if (b) {
            buffer[offset] = (byte) 1;
        } else {
            buffer[offset] = (byte) 0;
        }
    }

    /**
     * Reads a boolean from {@code buffer[offset]}.
     * <p>Decoding: returns {@code true} iff the stored byte equals {@code 1};
     * any other value yields {@code false}.</p>
     *
     * @param buffer source byte array (must be non-null)
     * @param offset index from which to read (must be within bounds)
     * @return {@code true} if the byte is {@code 1}; {@code false} otherwise
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code offset} is out of range
     */
    public static boolean unpackBoolean(byte[] buffer, int offset) {
        return buffer[offset] == (byte) 1;
    }

    /**
     * Writes a {@code char} (16-bit unsigned UTF-16 code unit) to
     * {@code buffer[offset..offset+1]} in Big-Endian order.
     *
     * @param c the char to write
     * @param buffer destination array (must be non-null)
     * @param offset starting index (must allow two bytes)
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static void packChar(char c, byte[] buffer, int offset) {
        buffer[offset    ] = (byte) (c >> 8);
        buffer[offset + 1] = (byte)  c;
    }

    /**
     * Reads a {@code char} from {@code buffer[offset..offset+1]} in Big-Endian order.
     *
     * @param buffer source array (must be non-null)
     * @param offset starting index (must allow two bytes)
     * @return the decoded char
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static char unpackChar(byte[] buffer, int offset) {
        return (char) (buffer[offset    ] << 8 |
                       buffer[offset + 1] & 0xFF);
    }

    /**
     * Writes a byte at {@code buffer[offset]}.
     *
     * @param b the byte to write
     * @param buffer the destination array
     * @param offset the index at which to write
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code offset} is out of range
     */
    public static void packByte(byte b, byte[] buffer, int offset) {
        buffer[offset] = b;
    }

    /**
     * Reads a byte from {@code buffer[offset]}.
     *
     * @param buffer the source array
     * @param offset the index from which to read
     * @return the byte that has been read
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code offset} is out of range
     */
    public static byte unpackByte(byte[] buffer, int offset) {
        return buffer[offset];
    }

    /**
     * Writes at most {@code maxLength} characters of {@code str} starting at
     * {@code buffer[offset]}, using the same layout as {@link #packChar(char, byte[], int)}
     * (UTF-16 code units, Big-Endian, 2 bytes per char).
     * <p>
     * If {@code str.length() < maxLength}, a null terminator ({@code '\0'}) is written
     * immediately after the last character and no further bytes are modified.
     * If {@code str.length() > maxLength}, the string is truncated to {@code maxLength}
     * characters and no terminator is written.
     * No length prefix is stored; callers must pass the same {@code maxLength} when unpacking.
     * </p>
     * <p>
     * Buffer requirement: at least {@code 2 * maxLength} bytes are needed starting at {@code offset}.
     * </p>
     *
     * @param str the source string (must be non-null)
     * @param maxLength maximum number of characters to write (code units)
     * @param buffer destination array
     * @param offset starting index
     * @throws NullPointerException if {@code buffer} or {@code str} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */

    public static void packLimitedString(
            String str, int maxLength, byte[] buffer, int offset) {

        for (int i = 0; i < maxLength; i++) {
            if ( i < str.length() ) {
                packChar(str.charAt(i), buffer, offset+2*i);
            } else {
                // We mark with a zero
                packChar('\0', buffer, offset+2*i);
                break;
            }
        }
    }

    /**
     * Reads at most {@code maxLength} characters starting at {@code buffer[offset]}
     * using the same char layout as {@link #unpackChar(byte[], int)}. Reading stops
     * when either {@code maxLength} characters are read or a null terminator ({@code '\0'})
     * is encountered, whichever comes first.
     *
     * @param maxLength maximum number of characters to read
     * @param buffer source array
     * @param offset starting index
     * @return the decoded string
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space to read
     */
    public static String unpackLimitedString(
            int maxLength, byte[] buffer, int offset) {
        StringBuilder sb = new StringBuilder(Math.min(maxLength, 16));
        for (int i = 0; i < maxLength; i++ ) {
            char c = unpackChar(buffer, offset + 2 * i);
            if (c != '\0') {
                sb.append(c);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * Writes an {@code int} to {@code buffer[offset..offset+3]} in Big-Endian order.
     *
     * @param n the int to be written
     * @param buffer the destination array
     * @param offset the starting position (must allow four bytes)
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */

    public static void packInt(int n, byte[] buffer, int offset ) {
        buffer[offset + 3] = (byte)  n       ;
        buffer[offset + 2] = (byte) (n >>  8);
        buffer[offset + 1] = (byte) (n >> 16);
        buffer[offset    ] = (byte) (n >> 24);
    }

    /**
     * Reads an {@code int} from {@code buffer[offset..offset+3]} in Big-Endian order.
     *
     * @param buffer the source array
     * @param offset the starting position (must allow four bytes)
     * @return the int that has been read
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */

    public static int unpackInt(byte[] buffer, int offset) {
        return ((buffer[offset    ]       ) << 24) |
               ((buffer[offset + 1] & 0xFF) << 16) |
               ((buffer[offset + 2] & 0xFF) <<  8) |
               ((buffer[offset + 3] & 0xFF)      ) ;
    }

    /**
     * Writes a {@code short} to {@code buffer[offset..offset+1]} in Big-Endian order.
     *
     * @param s the short to be written
     * @param buffer the destination array
     * @param offset the starting position (must allow two bytes)
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static void packShort(short s, byte[] buffer, int offset ) {
        buffer[offset    ] = (byte) (s >>  8);
        buffer[offset + 1] = (byte)  s       ;
    }

    /**
     * Reads a {@code short} from {@code buffer[offset..offset+1]} in Big-Endian order.
     *
     * @param buffer the source array
     * @param offset the starting position (must allow two bytes)
     * @return the short that has been read
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static short unpackShort(byte[] buffer, int offset) {
        return (short) (((buffer[offset    ]   <<  8) |
                         (buffer[offset + 1] & 0xFF)));
    }

    /**
     * Writes a {@code long} to {@code buffer[offset..offset+7]} in Big-Endian order.
     *
     * @param n the long to be written
     * @param buffer the destination array
     * @param offset the starting position (must allow eight bytes)
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static void packLong(long n, byte[] buffer, int offset)  {
        buffer[offset    ] = (byte) (n >> 56);
        buffer[offset + 1] = (byte) (n >> 48);
        buffer[offset + 2] = (byte) (n >> 40);
        buffer[offset + 3] = (byte) (n >> 32);
        buffer[offset + 4] = (byte) (n >> 24);
        buffer[offset + 5] = (byte) (n >> 16);
        buffer[offset + 6] = (byte) (n >>  8);
        buffer[offset + 7] = (byte)  n       ;
    }

    /**
     * Reads a {@code long} from {@code buffer[offset..offset+7]} in Big-Endian order.
     *
     * @param buffer the source array
     * @param offset the starting position (must allow eight bytes)
     * @return the long that has been read
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static long unpackLong(byte[] buffer, int offset) {
        return ((long)(buffer[offset    ]       ) << 56) |
               ((long)(buffer[offset + 1] & 0xFF) << 48) |
               ((long)(buffer[offset + 2] & 0xFF) << 40) |
               ((long)(buffer[offset + 3] & 0xFF) << 32) |
               ((long)(buffer[offset + 4] & 0xFF) << 24) |
               ((long)(buffer[offset + 5] & 0xFF) << 16) |
               ((long)(buffer[offset + 6] & 0xFF) <<  8) |
               ((long)(buffer[offset + 7] & 0xFF)      ) ;
    }

    /**
     * Writes a {@code float} by converting it to its IEEE 754 bit pattern
     * using {@link Float#floatToIntBits(float)} and packing the resulting int
     * to {@code buffer[offset..offset+3]} in Big-Endian order.
     *
     * @param f the float to be written
     * @param buffer the destination array
     * @param offset the starting position (must allow four bytes)
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static void packFloat(float f, byte[] buffer, int offset) {
        int bits = Float.floatToIntBits(f);
        packInt(bits, buffer, offset);
    }

    /**
     * Reads a {@code float} by unpacking an int from
     * {@code buffer[offset..offset+3]} in Big-Endian order and converting with
     * {@link Float#intBitsToFloat(int)}.
     *
     * @param buffer the source array
     * @param offset the starting position (must allow four bytes)
     * @return the decoded float
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static float unpackFloat(byte[] buffer, int offset) {
        int bits = unpackInt(buffer, offset);
        return Float.intBitsToFloat(bits);
    }

    /**
     * Writes a {@code double} by converting it to its IEEE 754 bit pattern
     * using {@link Double#doubleToLongBits(double)} and packing the resulting long
     * to {@code buffer[offset..offset+7]} in Big-Endian order.
     *
     * @param d the double to be written
     * @param buffer the destination array
     * @param offset the starting position (must allow eight bytes)
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static void packDouble(double d, byte[] buffer, int offset) {
        long bits = Double.doubleToLongBits(d);
        packLong(bits, buffer, offset);
    }

    /**
     * Reads a {@code double} by unpacking a long from
     * {@code buffer[offset..offset+7]} in Big-Endian order and converting with
     * {@link Double#longBitsToDouble(long)}.
     *
     * @param buffer the source array
     * @param offset the starting position (must allow eight bytes)
     * @return the decoded double
     * @throws NullPointerException if {@code buffer} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if there isn’t enough space
     */
    public static double unpackDouble(byte[] buffer, int offset) {
        long bits = unpackLong(buffer, offset);
        return Double.longBitsToDouble(bits);
    }
}
