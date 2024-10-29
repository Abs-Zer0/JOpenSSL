package abs.zero.jopenssl.crypto;


import abs.zero.jopenssl.StdLib;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.nio.charset.StandardCharsets;

public final class OpenSSL extends Libcrypto {

    private static final MemorySegment _version_ADDRESS = LIBCRYPTO.find("OpenSSL_version").get();
    private static final MethodHandle _version_METHOD = LINKER.downcallHandle(_version_ADDRESS, FunctionDescriptor.of(AddressLayout.ADDRESS, AddressLayout.JAVA_INT));

    public static final int _VERSION = 0;
    public static final int _CFLAGS = 1;
    public static final int _BUILT_ON = 2;
    public static final int _PLATFORM = 3;
    public static final int _DIR = 4;
    public static final int _ENGINES_DIR = 5;
    public static final int _VERSION_STRING = 6;
    public static final int _FULL_VERSION_STRING = 7;
    public static final int _MODULES_DIR = 8;
    public static final int _CPU_INFO = 9;
    public static final int _WINCTX = 10;

    public static String _version(int t) {
        try {
            MemorySegment string = (MemorySegment) _version_METHOD.invokeExact(t);
            long strLength = StdLib.strlen(string);
            string = string.reinterpret(strLength);
            byte[] ascii = string.toArray(AddressLayout.JAVA_BYTE);

            return new String(ascii, StandardCharsets.US_ASCII);
        } catch (Throwable e) {
            LOG.error("ERROR invoke {const char *OpenSSL_version(int t);}", e);

            throw new RuntimeException(e);
        }
    }

}
