package com.ecommerce.admin.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.ecommerce.admin.common.constants.DateConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Easy Excel 工具类
 * 用于导出和导入 Excel 文件
 */
public class EasyUtils {

    /**
     * 导出 Excel
     *
     * @param data
     * @param clazz
     * @param fileName
     * @param sheetName
     * @param response
     * @param <T>
     * @throws IOException
     */
    public static <T> void write(List<T> data, Class<T> clazz,
                                 String fileName, String sheetName,
                                 HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            String finalName =
                    URLEncoder.encode(fileName + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateConstants.DATE_TIME_FORMAT)), "UTF-8")
                            .replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + finalName + ".xlsx");

            EasyExcel.write(response.getOutputStream(), clazz)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(sheetName)
                    .doWrite(data);

        } catch (IOException e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            Map<String, Object> map = new HashMap<>();
            map.put("code", 600100);
            map.put("msg", "下载文件失败：" + e.getMessage());

            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 导入 Excel
     *
     * @param inputStream
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> read(InputStream inputStream, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        EasyExcel.read(inputStream, clazz, new AnalysisEventListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                if (Objects.nonNull(data)) {
                    result.add(data);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

            }
        }).sheet().doRead();

        return result;
    }
}
