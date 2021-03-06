package com.saleson.test.buffer.direct;

import org.junit.*;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * @author saleson
 * @date 2022-03-15 12:09
 */
public class DirectMemoryTest {


    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        System.out.println(String.format("Test %s %s, spent %d microseconds",
                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
    }

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            logInfo(description, "succeeded", nanos);
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            logInfo(description, "failed", nanos);
        }

        @Override
        protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
            logInfo(description, "skipped", nanos);
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, "finished", nanos);
        }
    };


    private Path fdirpath;
    private Path rfpath;
    private Path wfpath;

    @Before
    public void before() throws URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("file/cscw.txt");
        rfpath = Paths.get(url.toURI());
        fdirpath = rfpath.getParent();
        wfpath = Paths.get(fdirpath.toString(), "w.txt");
//        wfpath = Paths.get("/Users/saleson/Desktop/personal/t", "x.txt");
    }

    @Test
    public void testDirectByteBuffer() {
        // -verbose:gc -XX:+PrintGCDetails -server -Xms20m -Xmx20m -XX:MaxDirectMemorySize=256m
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);
    }

    @Test
    public void testFileChannelMmap() throws IOException {
        FileChannel readChannel = FileChannel.open(rfpath, StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(
                Paths.get(fdirpath.toString(), "w-testFileChannelMmap.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        MappedByteBuffer data = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, 1024);
        //????????????
        writeChannel.write(data);
        readChannel.close();
        writeChannel.close();
    }


    @Test
    public void testFileChannelTransfer() throws IOException {
        FileChannel readChannel = FileChannel.open(rfpath, StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(
                Paths.get(fdirpath.toString(), "w-testFileChannelTransfer.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        long len = readChannel.size();
        long position = readChannel.position();
        //????????????
//        long length = readChannel.transferTo(position, len, writeChannel);
        //?????????transferTo ?????????
        long length2 = writeChannel.transferFrom(readChannel, position, len);
        readChannel.close();
        writeChannel.close();
    }


    /**
     * filechannel?????????????????????????????????
     */
    @Test
    public void fileCopyWithFileChannel() throws IOException {
        // ??????fileInputStream???????????????
        FileChannel fileChannelInput = new FileInputStream(rfpath.toFile()).getChannel();
        // ??????fileOutputStream???????????????
        FileChannel fileChannelOutput = new FileOutputStream(
                Paths.get(fdirpath.toString(), "w-fileCopyWithFileChannel.txt").toFile())
                .getChannel();

        //???fileChannelInput???????????????????????????fileChannelOutput??????
        fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);
    }

    /**
     * BufferedInputStream??????????????????????????????????????????
     *
     */
    @Test
    public void bufferedCopy() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(rfpath.toFile()));
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(Paths.get(fdirpath.toString(), "w-bufferedCopy.txt").toFile()));
        byte[] buf = new byte[BUFFER_SIZE];
        while ((bis.read(buf)) != -1) {
//            System.out.println(new String(buf));
            bos.write(buf);
//            bos.flush();
        }
        bis.close();
        bos.close();
    }


    static final int BUFFER_SIZE = 1024;

    /**
     * ????????????????????????????????????
     */
    @Test
    public void fileReadWithMmap() {
        long begin = System.currentTimeMillis();
        byte[] b = new byte[BUFFER_SIZE];
        int len = (int) rfpath.toFile().length();
        MappedByteBuffer buff;
//        try (FileChannel channel = new FileInputStream(file).getChannel()) {
        try (FileChannel channel = FileChannel.open(rfpath, StandardOpenOption.READ)) {
            // ????????????????????????????????????????????????MappedByteBuffer
            buff = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            for (int offset = 0; offset < len; offset += BUFFER_SIZE) {
                if (len - offset > BUFFER_SIZE) {
                    buff.get(b);
                } else {
                    buff.get(new byte[len - offset]);
                }
            }
            System.out.println(buff.remaining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("file size:" + len);
        System.out.println("time is:" + (end - begin));
    }

    /**
     * HeapByteBuffer????????????
     */
    @Test
    public void fileReadWithByteBuffer() {

        long begin = System.currentTimeMillis();
//        try (FileChannel channel = new FileInputStream(file).getChannel();) {
        try (FileChannel channel = FileChannel.open(rfpath, StandardOpenOption.READ)) {
            // ??????HeapByteBuffer
            ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE);
            while (channel.read(buff) != -1) {
                buff.flip();
                buff.clear();
            }
            System.out.println(buff.remaining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("file size:" + rfpath.toFile().length());
        System.out.println("time is:" + (end - begin));
    }

}
