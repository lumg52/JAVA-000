import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

@Slf4j
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> hello = myClassLoader.findClass("Hello");
        Method method = hello.getMethod("hello");
        method.invoke(hello.newInstance());
        log.info("类加载器:{}", hello.getClassLoader());
    }

    @Override
    protected Class<?> findClass(String name) {
        Class<?> aClass = null;
        String path = "Week_01/class/" + name + ".xlass";
        try {
            File file = new File(path);
            byte[] bytes = getClassByte(file);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (new Byte((byte) 255) - bytes[i]);
            }
            aClass = defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    private byte[] getClassByte(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
        ByteBuffer allocate = ByteBuffer.allocate(512);
        while (true) {
            int i = channel.read(allocate);
            if (i == 0 || i == -1) {
                break;
            }
            allocate.flip();
            writableByteChannel.write(allocate);
            allocate.clear();
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}
