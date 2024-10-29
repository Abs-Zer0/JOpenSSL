package abs.zero.jopenssl;

public class JOpenSSLException extends RuntimeException {

    private String resource;

    public JOpenSSLException(String message, Throwable cause, String resource) {
        super(message + " [resource: " + resource + "]", cause);
        this.resource = resource;
    }

    public JOpenSSLException(String message, String resource) {
        super(message + " [resource: " + resource + "]");
        this.resource = resource;
    }

    public JOpenSSLException(Throwable cause, String resource) {
        super("Some error in " + resource, cause);
        this.resource = resource;
    }

    public JOpenSSLException(String message, Throwable cause) {
        super(message, cause);
    }

    public JOpenSSLException(String message) {
        super(message);
    }

    public JOpenSSLException(Throwable cause) {
        super(cause);
    }

    public JOpenSSLException() {
    }

    public String getResource() {
        return resource;
    }

}
