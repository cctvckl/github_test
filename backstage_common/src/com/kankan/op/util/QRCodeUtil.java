package com.kankan.op.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xunlei.util.FileUtil;

/**
 * 生成二维码(QR Code)的工具类
 * 
 * @since 2013-11-22
 * @author hujiachao
 */
public class QRCodeUtil {

    public static BitMatrix getImageBitMatrix(String content) throws WriterException, UnsupportedEncodingException {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
        // 综合权衡之后确定使用M级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 设定外边距
        hints.put(EncodeHintType.MARGIN, 0);
        // 指定编码格式
        // 2013-11-22 说明：设置charset为utf-8然后在下面直接传入字符串是不行的
        // 在生成的文本前面会有一串其他的文本，然后估计是微信是用startWith("http://")这样来判断的
        // 微信就无法扫出这样的码。如果不按下面的方式转码，如果内容中有中文就会乱掉
        // hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        return new QRCodeWriter().encode(new String(content.getBytes("UTF-8"), "ISO-8859-1"), BarcodeFormat.QR_CODE, 300, 300, hints);
    }

    /**
     * 生成二维码的图片用于输出到前端
     */
    public static ChannelBuffer getImageBytes(String content, String format) throws WriterException, IOException {
        BitMatrix byteMatrix = getImageBitMatrix(content);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(byteMatrix, format, baos);
        return ChannelBuffers.wrappedBuffer(baos.toByteArray());
    }

    /**
     * 生成二维码文件
     * 
     * @param filePathName 预备生成的文件包含路径在内的完整文件名
     */
    public static void genImageFile(String content, String filePathName) throws WriterException, IOException {
        BitMatrix byteMatrix = getImageBitMatrix(content);
        File file = new File(filePathName);
        String format = FileUtil.getExtension(filePathName);
        MatrixToImageWriter.writeToFile(byteMatrix, format, file);
    }

    /**
     * 生成预备生成二维码的csv文件
     * 
     * @param filePathName 预备生成的文件包含路径在内的完整文件名
     */
    public static void genCsvFile(String content, String filePathName) throws WriterException, IOException {
        File file = new File(filePathName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes("UTF-8"));
        fos.close();
    }
}
