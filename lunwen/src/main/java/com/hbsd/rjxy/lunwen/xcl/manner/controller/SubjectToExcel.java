package com.hbsd.rjxy.lunwen.xcl.manner.controller;

import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.entity.Teacher;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class SubjectToExcel {
    public void createExcelFile(HttpServletResponse response,List<Subject> userList, List<String> rowName,String fName) {

            //TODO public String getParamValue(String name); 根据要打印的参数名获得对应的值

            //wb对象 开始创建excel
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建表头
            //TODO 自己改名字‘test1’
            HSSFSheet sheet = wb.createSheet("选题信息");

            //创建第一行
            HSSFRow row = sheet.createRow(0);
            //样式
            HSSFCellStyle cellF = wb.createCellStyle();
            HSSFCellStyle cellrow = wb.createCellStyle();
            //字体设置，font1为标题行，font2普通行
            HSSFFont font1 = wb.createFont();
            HSSFFont font2 = wb.createFont();
            font1.setFontName("宋体");
            font1.setBold(true);
            font2.setFontName("宋体");
            font1.setFontHeight((short) 200);

            //居中设置
            cellF.setAlignment(HorizontalAlignment.CENTER);//新版本居中设置
            cellF.setFont(font1);
            cellF.setBorderBottom(BorderStyle.THIN);
            cellF.setBorderLeft(BorderStyle.THIN);
            cellF.setBorderRight(BorderStyle.THIN);
            cellF.setBorderTop(BorderStyle.THIN);
            cellrow.setAlignment(HorizontalAlignment.CENTER);
            cellrow.setFont(font2);
            cellrow.setBorderBottom(BorderStyle.THIN);
            cellrow.setBorderLeft(BorderStyle.THIN);
            cellrow.setBorderRight(BorderStyle.THIN);
            cellrow.setBorderTop(BorderStyle.THIN);
            //写入标题行
            for (int i = 0; i < rowName.size(); i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(rowName.get(i));
                cell.setCellStyle(cellF);
            }

            //创建数据行
            int length = userList.size();
            for (int i = 0; i < length; i++) {
                HSSFRow tempRow = sheet.createRow(i + 1);
                for (int j = 0; j < rowName.size(); j++) {
                    HSSFCell tempCell = tempRow.createCell(j);
                    String temp = userList.get(i).getParamValue(rowName.get(j));
                    tempCell.setCellValue(temp);
                    tempCell.setCellStyle(cellrow);
                }

            }
            //列宽自动适应，在所有单元格写入后调用
            for (int i = 0; i < rowName.size(); i++) {
                // 调整每一列宽度
                sheet.autoSizeColumn((short) i);
                // 解决自动设置列宽中文失效的问题
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
            }

        //生成文件
                try {
                    this.setResponseHeader(response,fName);
                    OutputStream os=response.getOutputStream();
                    wb.write(os);
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
    //发送响应流
    public void  setResponseHeader(HttpServletResponse response,String fName){
        try {
            try {
                fName = new String(fName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fName +".xls");   //要保存的文件名
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
