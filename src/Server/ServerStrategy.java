package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Ronshmul on 22/05/2017.
 */
public interface ServerStrategy {
    void serverStrategy(InputStream inFromClient, OutputStream outToClient);

}
