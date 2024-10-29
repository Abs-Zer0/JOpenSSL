package abs.zero.jopenssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public final class LibraryUtils {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryUtils.class);

    private LibraryUtils() {
        throw new UnsupportedOperationException(getClass() + " is static class");
    }


    public static final String OPENSSL_LIBRARIES_PATH = System.getProperty("openssl.library.path");
    public static final String LIBCRYPTO_OS_NAME = System.mapLibraryName("libcrypto");
    public static final String LIBSS_OS_NAME = System.mapLibraryName("libssl");


    public static String findLibcryptoPath() throws IOException {
        return findLibraryPath(LIBCRYPTO_OS_NAME);
    }

    public static String findLibsslPath() throws IOException {
        return findLibraryPath(LIBSS_OS_NAME);
    }


    private static String findLibraryPath(String libraryName) throws IOException {
        if (OPENSSL_LIBRARIES_PATH == null || OPENSSL_LIBRARIES_PATH.isBlank()) {
            return null;
        }

        if (libraryName == null || libraryName.isBlank()) {
            return null;
        }

        Path librariesDirectory = Path.of(OPENSSL_LIBRARIES_PATH);
        if (!Files.isDirectory(librariesDirectory)) {
            return null;
        }

        try (Stream<Path> list = Files.list(librariesDirectory)) {
            return list
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().equalsIgnoreCase(libraryName))
                    .map(Path::toString)
                    .findFirst()
                    .orElse(null);
        }
    }


    /*private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup CLASS_LOADER_LOOKUP = SymbolLookup.loaderLookup();

    public static MethodHandle findMethod(String methodName, FunctionDescriptor descriptor){
        MemorySegment methodAddress = CLASS_LOADER_LOOKUP.find(methodName).
    }


    private static MemorySegment findMethodAddress(String methodName){
        if(methodName==null||methodName.isBlank()){

        }
    }*/

}
