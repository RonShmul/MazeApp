package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Sivan on 5/16/2017.
 */
public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException { //todo implement read

        int val = in.read();
        return val;
    }

    public int read(byte []b) throws IOException {

        int row = read() & (0xff);
        int col = read();
        int startRow = read()& (0xff);
        int startCol = read()& (0xff);
        int goalRow = read()& (0xff);
        int goalCol = read()& (0xff);

        b[0] = ((Integer)row).byteValue();
        b[1] = ((Integer)col).byteValue();
        b[2] = ((Integer)startRow).byteValue();
        b[3] = ((Integer)startCol).byteValue();
        b[4] = ((Integer)goalRow).byteValue();
        b[5] = ((Integer)goalCol).byteValue();


        int n =0;

        int i = 6;
        while(i < b.length) {
            int flag = 0;
            n = read()& (0xff);
            for(int j = 0; i < b.length && j < n; j++) {
                b[i] = 0;
                i++;
                flag = 1;
            }

            n = read()& (0xff);
            for(int j = 0; i < b.length && j < n; j++) {
                b[i] = 1;
                i++;
                flag = 1;
            }

            if(flag == 0)
                i++;
        }

        return -1;
    }
}
