import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.zip.ZipException;

public class Serializator {
    private InputStream in;

    public Serializator(InputStream in) {
        this.in = in;
    }

    public Serializator() {

    }

    public void serializeTo(OutputStream out) {

    }
}
