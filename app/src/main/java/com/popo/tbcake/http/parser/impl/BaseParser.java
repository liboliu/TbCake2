package com.popo.tbcake.http.parser.impl;

import com.popo.tbcake.http.parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.ByteBuffer;
/**
 * 基本的Parser
 * Created by popo on 14-12-8.
 */
public abstract class BaseParser<T> implements Parser<T> {

    @Override
    public String parse(String s) throws Exception {
        return s;
    }

    @Override
    public String parse(InputStream in) throws Exception {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String str = null;
        try {
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            throw (e);
        } finally {
            try {
                in.close();
            } catch (Exception e2) {

            }

        }

        return sb.toString();
    }

    /**
     * use nio
     *
     * @param in
     * @param into
     * @return
     * @throws Exception
     */
    @Override
    public Boolean parseTofile(InputStream in, File into) throws Exception {

        try {
            if (into != null && into.canWrite()) {
                if (!into.exists()) {
                    into.createNewFile();
                }

                ReadableByteChannel readableByteChannel = Channels.newChannel(in);

                FileOutputStream fileOutputStream = new FileOutputStream(into);
                FileChannel outputChannel = fileOutputStream.getChannel();


            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                in.close();
            } catch (Exception e2) {

            }

        }

        return Boolean.FALSE;

    }

    /**
     * Channel copy method 1. This method copies data from the src
     * channel and writes it to the dest channel until EOF on src.
     * This implementation makes use of compact( ) on the temp buffer
     * to pack down the data if the buffer wasn't fully drained. This
     * may result in data copying, but minimizes system calls. It also
     * requires a cleanup loop to make sure all the data gets sent.
     */
    private static void channelCopy1(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
        // Prepare the buffer to be drained
            buffer.flip();
            // Write to the channel; may block
            dest.write(buffer);
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear( )
            buffer.compact();
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // Make sure that the buffer is fully drained
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    /**
     * Channel copy method 2. This method performs the same copy, but
     * assures the temp buffer is empty before reading more data. This
     * never requires data copying but may result in more systems calls.
     * No post-loop cleanup is needed because the buffer will be empty
     * when the loop is exited.
     */
    private static void channelCopy2(ReadableByteChannel src,
                                     WritableByteChannel dest)
            throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // Prepare the buffer to be drained
            buffer.flip();
            // Make sure that the buffer was fully drained
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }
            // Make the buffer empty, ready for filling
            buffer.clear();
        }
    }

}
