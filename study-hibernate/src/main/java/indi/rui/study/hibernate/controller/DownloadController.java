package indi.rui.study.hibernate.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * @author: yaowr
 * @create: 2022-08-06
 */
@Slf4j
@RestController
@RequestMapping("/study-hibernate/download")
public class DownloadController {

    private static final String FILE_NAME = "普者黑.jpg";

    @RequestMapping(path = "normal", method = RequestMethod.GET)
    public void normal(HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        try {
            // 读取文件
            String filepath = Thread.currentThread().getContextClassLoader().getResource("pic/").getFile();
            File file = new File(filepath, FILE_NAME);
            // 设置下载http响应头
            String filename = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20");
            response.addHeader("Content-Disposition", "attachment; filename=" + filename + "; filename*=utf-8''" + filename);
            response.addHeader("Content-Length", "" + file.length());
            response.addHeader("Content-Type", request.getServletContext().getMimeType(file.getAbsolutePath()));
            in = new FileInputStream(file);
            out = response.getOutputStream();
            IOUtils.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException("下载文件失败", e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    @RequestMapping(path = "byResponseEntity", method = RequestMethod.GET)
    public ResponseEntity byResponseEntity(HttpServletRequest request, HttpServletResponse response) {
        // 读取文件
        String filepath = Thread.currentThread().getContextClassLoader().getResource("pic/").getFile();
        File file = new File(filepath, FILE_NAME);
        // 设置下载http响应头
        String filename = encodeFileName(request, file.getName());
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.addHeader("Content-Length", "" + file.length());
        response.addHeader("Content-Type", request.getServletContext().getMimeType(file.getAbsolutePath()));
        try {
            return ResponseEntity.ok(new UrlResource(Paths.get(file.getAbsolutePath()).toUri()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("下载文件失败");
        }
    }

    @RequestMapping(path = "noFileDownload", method = RequestMethod.GET)
    public void noFileDownload(HttpServletRequest request, HttpServletResponse response) {
        // 字节
        byte[] content = "下载内容".getBytes();
        // 设置下载http响应头
        String filename = encodeFileName(request, "文件.txt");
        response.addHeader("Content-Type", "text/plain");
        response.addHeader("Content-Disposition", "attachment; filename=" + filename + "; filename*=utf-8''" + filename);
        response.addHeader("Content-Length", "" + content.length);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(content);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("下载文件失败");
        }
    }


    /**
     * 文件名编码
     */
    public static String encodeFileName(HttpServletRequest request, String filename) {
        try {
            String userAgent = request.getHeader("User-Agent").toUpperCase();
            // ie情况处理
            if (userAgent.contains("MSIE") || userAgent.contains("TRIDENT") || userAgent.contains("EDGE")) {
                filename = URLEncoder.encode(filename, "UTF-8");
                // 这里的编码后，空格会被解析成+，需要重新处理
                filename = filename.replace("+", "%20");
            } else {
                filename = new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("文件名URL编码失败");
        }
        return filename;
    }
}
