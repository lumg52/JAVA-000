import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

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
            decodeByte(bytes);
            aClass = defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    private void decodeByte(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (new Byte((byte) 255) - bytes[i]);
        }
    }

    private byte[] getClassByte(File file) throws IOException {
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        new FileInputStream(file).read(bytes);
        return bytes;
    }
}
