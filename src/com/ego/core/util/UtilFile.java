/*----------javabean---------
 * @功能说明：文件操作便捷工具
 * @创建日期：2013-04-08:09:23
 * @最后修改日期：2013-04-08:15:23
 */
package com.ego.core.util;

import com.ego.core.lang.BasicRuntimeException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 文件操作便捷工具
 *
 * @author Administrator
 */
public class UtilFile {

    public static final char EXTENSION_SEPARATOR = '.';
    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final String[] IMAGES_SUFFIXES = {
        "bmp", "jpg", "jpeg", "gif", "png", "tiff"
    };

    /**
     * 获得文件大小
     *
     * @param pathAndFile 文件地址
     * @return
     */
    public static long getFileSize(String pathAndFile) {
        File file = new File(pathAndFile);
        return file.length();
    }

    /**
     * 创建文件，将指定流写入指定路径的文件里去.如果输入流为null，则创建空文件。
     *
     * <p>
     *
     * 如果文件创建失败返回false。但如果抛出 IOException文件创建成功，只是向文件写内容出现错误
     *
     * <p>
     * 如果指定的文件不存在则创建一个新的文件
     *
     * @param in
     * @param filePath 不能以“/”结尾
     * @return 创建成功返回true
     * @throws IOException
     */
    public static boolean createFile(InputStream in, String filePath) throws IOException {
        if (filePath == null) {
            return false;
        }
        File dest = new File(filePath);
        //如果目的文件不存在
        if (!dest.exists()) {
            //取得文件的文件夹
            int potPos = filePath.lastIndexOf('/') + 1;
            String folderPath = filePath.substring(0, potPos);
            //创建文件夹
            if (!createFolder(folderPath)) {
                return false;
            }
            dest = new File(filePath);
            try {
                dest.createNewFile();
            } catch (IOException ex) {
                return false;
            }
        }

        if (in != null) {
            streamToFile(in, dest);
        }
        return true;

    }

    /**
     * 将输入流写到文件里去
     *
     * @param in
     * @param dest 文件，不能是文件夹,且这个文件必须存在
     * @throws IOException
     */
    public static void streamToFile(InputStream in, File dest) throws IOException {
        FileOutputStream out = new FileOutputStream(dest);
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        //关闭流
        out.close();
        in.close();
    }

    
    /**
     * 把内容写入文件,指定如果文件存在是否允许覆盖
     *
     * @param filePath
     * @param fileContent
     * @param charsetName
     * @param overwrite
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static void write(String filePath, String fileContent, String charsetName, boolean overwrite) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        String charset = UtilValidate.isEmpty(charsetName) ? "UTF-8" : charsetName;
        File f = new File(filePath);
        //如果不覆盖并且不允许覆盖则退出程序
        if (f.exists()) {
            if (!overwrite) {
                return;
            }
        } else {
            //先创建夫目录
            new File(f.getParent()).mkdirs();
            //创建新文件
            f.createNewFile();
        }
        //将内容写入文件
        FileOutputStream fo = new FileOutputStream(f);
        OutputStreamWriter out = new OutputStreamWriter(fo, charset);
        out.write(fileContent);
        out.close();
        fo.close();

    }



    /**
     * 读取文件内容 默认是UTF-8编码
     *
     * @param filePath
     * @param charset
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String read(String filePath, String charset) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        charset = charset == null || charset.equals("") ? "UTF-8" : charset;
        StringBuilder sb = new StringBuilder();
        InputStreamReader read = new InputStreamReader(new FileInputStream(
                new File(filePath)), charset);
        BufferedReader reader = new BufferedReader(read);
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        read.close();
        read = null;
        reader.close();
        reader = null;
        return sb.toString();
    }

    /**
     * 删除文件或文件夹
     *
     * @param file
     * @param filePath
     */
    public static void delete(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    delete(files[i]);
                }
            }
            file.delete();
        }
    }

    /**
     * 删除文件或文件夹
     *
     * @param filePath 文件路径
     */
    public static void delete(String filePath) {
        delete(new File(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param filepath
     * @return
     */
    public static boolean exist(String filepath) {
        File file = new File(filepath);
        return file.exists();
    }

    /**
     * 在指定文件或文件目录中查找指定扩展文件名的文件（或遍历指定文件目录file下的所有目录）
     *
     * @param file 文件或在文件目录
     * @param exts 要查找的扩展文件名，可以多个，扩展文件名不带“.”
     * @return
     */
    public static List<File> findFilesByExt(File file, String... exts) {
        List<File> files = new ArrayList<File>();
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                findFilesByExt(tmp);
            }
        } else {
            for (String ext : exts) {
                if (file.getAbsolutePath().endsWith("." + ext)) {
                    files.add(file);
                }
            }

        }
        return files;
    }

    /**
     * 创建文件夹。如果指定的文件夹存在则直接返回true
     *
     * @param filePath 文件夹路径
     * @return 创建成功返回true
     */
    public static boolean createFolder(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return true;
            } else {
                return file.mkdirs();
            }
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * 得到文件的扩展名,不包括“.”
     *
     * @param fileName 文件名。可以是完整的路径
     * @return ，找到返回扩展名，否则返回空
     */
    public static String getFileExt(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = indexOfExtension(fileName);
        if (index == -1) {
            return "";
        } else {
            return fileName.substring(index + 1);
        }

    }

    /**
     * 复制文件。如果源文件是文件夹,则复制所有文件到目的地文件
     *
     * <p>
     *
     * Copy files.
     *
     * @param strPath the str path
     * @param dstPath the dst path
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void copyFile(String strPath, String dstPath) throws IOException {
        File src = new File(strPath);
        File dest = new File(dstPath);
        copyFile(src, dest);
    }

    /**
     * 复制文件.如果源文件是文件夹,则复制所有文件到目的地文件
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {

        if (src.isDirectory()) {
            //if(dest.exists()!=true)
            dest.mkdirs();
            String list[] = src.list();

            for (int i = 0; i < list.length; i++) {
                String src1 = src.getAbsolutePath() + File.separator + list[i];
                String dest1 = dest.getAbsolutePath() + File.separator + list[i];
                File source = new File(src1);
                File destionation = new File(dest1);
                copyFile(source, destionation);
            }
        } else {
            //dest.createNewFile();
            FileChannel sourceChannel = new FileInputStream(src).getChannel();
            FileChannel targetChannel = new FileOutputStream(dest).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
            sourceChannel.close();
            targetChannel.close();

        }
    }

    /**
     * 读取文件为字节数组
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] fileToByte(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        BufferedInputStream bufferedStream = new BufferedInputStream(in);
        byte buffer[] = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        while ((len = bufferedStream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        byte zipBytes[] = out.toByteArray();
        return zipBytes;
    }

    /**
     * 从zip文件中提取目录
     *
     * @param file 目标zip文件
     * @param dirName 要提取的目录名
     * @param extractTo 保存到的目录，如果这个目录不存在，则创建
     * @throws IOException
     */
    public static void extractDirFromZip(File file, String dirName, String extractTo) throws IOException {
        ZipEntry entry;
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file));
        ZipFile zipFile = new ZipFile(file);
        File extractToDir = new File(extractTo);
        extractToDir.mkdirs();
        while ((entry = zipIn.getNextEntry()) != null) {
            if (entry.getName().startsWith(dirName)) {
                if (entry.isDirectory() && (!entry.getName().equalsIgnoreCase(dirName + File.separator))) {
                    int prefixIndex = entry.getName().indexOf(dirName + File.separator) + (dirName + File.separator).length();
                    String subdir = entry.getName().substring(prefixIndex);
                    File newDir = new File(extractTo + File.separator + subdir);
                    newDir.mkdirs();
                } else if (!entry.isDirectory()) {
                    int prefixIndex = entry.getName().indexOf(dirName + File.separator) + (dirName + File.separator).length();
                    String subdir = entry.getName().substring(prefixIndex);
                    InputStream in = zipFile.getInputStream(entry);
                    File newFile = new File(extractTo + File.separator + subdir);
                    UtilFile.streamToFile(in, newFile);

                } else {
                }
            }
        }
    }

    /**
     * 充命名文件
     *
     * @param fName
     * @param nName
     */
    public static void renameFile(String fName, String nName) {
        File file = new File(fName);
        file.renameTo(new File(nName));
        file.delete();
    }

    /**
     * 获取相对路径
     *
     * @param baseDir
     * @param file
     * @return
     */
    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file)) {
            return "";
        }
        if (baseDir.getParentFile() == null) {
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        }
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
    }

    /**
     * 列出文件夹下所有文件，包括所有子目录中的文件
     *
     * @param dir
     * @return
     * @throws IOException
     * @deprecated
     */
    public static List<File> listAllFile(File dir)
            throws IOException {
        ArrayList<File> arrayList = new ArrayList();
        searchFiles(dir, arrayList, true, null);
        Collections.sort(arrayList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }
        });
        return arrayList;
    }

    /**
     * 列出目录下的所有文件，包括所有子目录
     *
     * @param fileOrDir
     * @param collector
     * @throws IOException
     */
    public static void searchFiles(File fileOrDir, List collector, boolean showhide, String regex)
            throws IOException {
        /*
         collector.add(fileOrDir);
         if ((!fileOrDir.isHidden()) && (fileOrDir.isDirectory())) {

         File[] subFiles = fileOrDir.listFiles();
         for (int i = 0, len = subFiles.length; i < len; i++) {
         searchFiles(subFiles[i], collector, showhide);
         }
         }
         */
        if (!fileOrDir.isDirectory()) {
            if ((fileOrDir.isHidden() && showhide) || !fileOrDir.isHidden()) {
                if (regex == null || (regex != null && fileOrDir.getName().matches(regex))) {
                    collector.add(fileOrDir);
                }
            }
        } else {
            File[] subFiles = fileOrDir.listFiles();
            for (int i = 0, len = subFiles.length; i < len; i++) {
                if ((fileOrDir.isHidden() && showhide) || !fileOrDir.isHidden()) {
                    searchFiles(subFiles[i], collector, showhide, regex);
                }
            }
        }
    }

    public static List<File> searchFiles(File dir, boolean showhide, String regex)
            throws IOException {
        ArrayList<File> arrayList = new ArrayList();
        searchFiles(dir, arrayList, showhide, regex);
        Collections.sort(arrayList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }
        });
        return arrayList;
    }

    public static List<File> searchFiles(File dir, String regex)
            throws IOException {

        return searchFiles(dir, true, regex);
    }

    public static List<File> searchFiles(File dir, boolean showhide)
            throws IOException {
        return searchFiles(dir, showhide, null);
    }

    public static List<File> searchFiles(File dir)
            throws IOException {

        return searchFiles(dir, true, null);
    }

    /**
     * 创建文件，如果文件所在目录不存在则创建目录
     *
     * @param dir 目录,不能为null否则抛出异常,不能以"/"结尾
     * @param file 文件名，不能以"/"开头
     * @return
     */
    public static File createFile(String dir, String file) {
       // Assert.notNull(dir);
       // Assert.notNull(file);
        return createFile(dir + File.separator + file);
    }

    /**
     * 创建文件，如果文件所在目录不存在则创建目录
     *
     * @param path
     * @return
     */
    public static File createFile(String path) {
      //  Assert.notNull(path);
        File f = new File(path);
        createParentFolder(f);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                throw new BasicRuntimeException(ex);
            }
        }
        return f;
    }

    /**
     * 创建父文件夹，如果不存在的话
     *
     * @param outputFile
     */
    public static void createParentFolder(File outputFile) {
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
    }

    /**
     * Returns the index of the last directory separator character.
     * <p>
     * This method will handle a file in either Unix or Windows format. The
     * position of the last forward or backslash is returned.
     * <p>
     * The output will be the same irrespective of the machine that the code is
     * running on.
     *
     * @param filename the filename to find the last path separator in, null
     * returns -1
     * @return the index of the last separator character, or -1 if there is no
     * such character
     */
    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        }
        int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }

    /**
     * Returns the index of the last extension separator character, which is a
     * dot.
     * <p>
     * This method also checks that there is no directory separator after the
     * last dot. To do this it uses {@link #indexOfLastSeparator(String)} which
     * will handle a file in either Unix or Windows format.
     * <p>
     * The output will be the same irrespective of the machine that the code is
     * running on.
     *
     * @param filename the filename to find the last path separator in, null
     * returns -1
     * @return the index of the last separator character, or -1 if there is no
     * such character
     */
    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = indexOfLastSeparator(filename);
        return lastSeparator > extensionPos ? -1 : extensionPos;
    }

    /**
     * 根据扩展名后缀判断 是否是图片附件
     *
     * @param ext
     * @return
     */
    public static boolean isImage(String ext) {
        if (ext == null || ext.trim().length() == 0) {
            return false;
        }

        return UtilValidate.contains(IMAGES_SUFFIXES, ext.toLowerCase());
    }

    /**
     * 根据包含扩展后缀名的文件名判断是否是图片文件
     *
     * @param filename
     * @return
     */
    public static boolean isImageFile(String filename) {
        if (filename == null || filename.trim().length() == 0) {
            return false;
        }

        return UtilValidate.contains(IMAGES_SUFFIXES, getFileExt(filename).toLowerCase());
    }

    /**
     * 格式化文件路径，即替换\\为\
     *
     * @param path
     * @return
     */
    public static String formatPath(String path) {
        if (UtilOs.OsInfo.isWindows()) {
            return (path.trim()).replace("\\\\", "\\");
        } else {
            return (path.trim()).replace("//", "/");
        }
    }

    /**
     * 获取文件大小的字符串
     *
     * @param size
     * @return
     */
    public String getSizeString(long size) {
        String ret = " byte";
        if (size > 1023) {
            ret = " KB";
            size = size / 1024;
            if (size > 1023) {
                ret = " MB";
                size = size / 1024;
                if (size > 1023) {
                    size = size / 1024;
                    ret = " GB";
                }
            }
        }
        return size + ret;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
