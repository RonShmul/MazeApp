package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Ronshmul on 28/05/2017.
 */
public interface IClientStrategy {
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
