package abs.zero.jopenssl.crypto;

import abs.zero.jopenssl.LibraryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;

abstract class Libcrypto {

    protected static final Logger LOG = LoggerFactory.getLogger(Libcrypto.class);

    static {
        try {
            String libcryptoPath = LibraryUtils.findLibcryptoPath();
            if (libcryptoPath != null && !libcryptoPath.isBlank()) {
                System.load(libcryptoPath);
            } else {
                System.loadLibrary(LibraryUtils.LIBCRYPTO_OS_NAME);
            }
        } catch (IOException e) {
            LOG.error("Error load " + LibraryUtils.LIBCRYPTO_OS_NAME, e);
        }
    }

    protected static final Linker LINKER = Linker.nativeLinker();
    protected static final SymbolLookup LIBCRYPTO = SymbolLookup.loaderLookup();

}
