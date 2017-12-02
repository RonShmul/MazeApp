package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Sivan on 5/16/2017.
 */
public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) throws IOException {
        this.out = out;

    }

    /**write int to an OutputStream
     * @param b
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {

        out.write(b);

    }

    /**
     * compress byte[] array and write the compressed data to OutputStream
     * @param bArr
     * @throws IOException
     */
    public void write(byte[] bArr) throws IOException {

        int sumOfZero = 0;
        int sumOfOne = 0;

        //write to out the maze information: number of columns, number of rows etc..
        for ( int i = 0; i < 6; i++) {
            int temp = bArr[i] & (0xff);

            write(temp);
        }

        /*write to out the content of the maze array but compressed. first write the number of zeros and than the number of ones etc..
        if the number of zeros or the number of ones is 255 or up - end the while and continue.*/
        int i = 6;
        while(i < bArr.length) {
            int flag = 0;
            while(i < bArr.length && bArr[i] == 0) {
                sumOfZero = sumOfZero + 1;
                i++;
                flag = 1;
                if(sumOfZero >= 255)
                    break;
            }
            write(sumOfZero);
            sumOfZero = 0;
            while(i < bArr.length && bArr[i] == 1) {
                sumOfOne = sumOfOne + 1;
                i++;
                flag = 1;
                if(sumOfOne >= 255)
                    break;
            }
            write(sumOfOne);
            sumOfOne = 0;
            if(flag == 0)
                i++;

        }
    }
}
