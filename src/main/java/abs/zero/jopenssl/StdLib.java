package abs.zero.jopenssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public final class StdLib {

    private static final Logger LOG = LoggerFactory.getLogger(StdLib.class);

    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup STDLIB = LINKER.defaultLookup();

    private static final MemorySegment free_ADDRESS = STDLIB.find("free").get();
    private static final MethodHandle free_METHOD = LINKER.downcallHandle(free_ADDRESS, FunctionDescriptor.ofVoid(AddressLayout.ADDRESS));

    public static void free(MemorySegment ptrmem) {
        try {
            free_METHOD.invokeExact(ptrmem);
        } catch (Throwable e) {
            LOG.error("ERROR invoke {void free(void *ptrmem);}", e);
        }
    }

    public static final Consumer<MemorySegment> FREE_FOR_ARENA = StdLib::free;

    private static final MemorySegment strlen_ADDRESS = STDLIB.find("strlen").get();
    private static final MethodHandle strlen_METHOD = LINKER.downcallHandle(strlen_ADDRESS, FunctionDescriptor.of(AddressLayout.JAVA_LONG, AddressLayout.ADDRESS));

    public static long strlen(MemorySegment string) {
        try {
            return (long) strlen_METHOD.invokeExact(string);
        } catch (Throwable e) {
            LOG.error("ERROR invoke {size_t strlen( const char * string );}", e);

            throw new RuntimeException(e);
        }
    }

}
