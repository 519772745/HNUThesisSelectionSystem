package com.hbsd.rjxy.lunwen.syx.mannerStudentSubject.util;

import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class ExportExcel {
    /**
     * 用于在服务器运行端生成student&subject表的excel文件的方法
     * @param SSList  student&subject的列表
     * @Param realPath 服务器运行的路径
     * @return File-exce文件
     * @author 孙亦璇
     */
    public File exportExcel(List<StudentWithSubject>SSList,String realPath){
        try{
            /*设置输出目录和文件
            * 目录：在服务器运行地址的exportExcel文件夹下
            * 文件：xxx\exportExcel\SSExcel.xls
            * */
            File fileDir = new File(realPath+"exportExcel");
            if (!fileDir.exists())
                fileDir.mkdirs();
            String path=realPath+"exportExcel\\SSExcel.xls";
            System.out.println("本地路径："+path);
            File file=new File(path);
            if (file.exists())
                file.delete();
            file.createNewFile();
            System.out.println("文件已创建");
            OutputStream output=new FileOutputStream(file);

            /*设置表格标题*/
            List<String> rowName= Arrays.asList("编号", "学号", "学生姓名","论文选题","指导教师");

            /* 开始创建excel*/
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建表头
            HSSFSheet sheet = wb.createSheet("学生选题情况");

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
            int length = SSList.size();
            for (int i = 0; i < length; i++) {
                HSSFRow tempRow = sheet.createRow(i + 1);
                /*序号*/
                HSSFCell tempCell1 = tempRow.createCell(0);
                tempCell1.setCellValue(i+1);
                tempCell1.setCellStyle(cellrow);
                /*学号*/
                HSSFCell tempCell2 = tempRow.createCell(1);
                tempCell2.setCellValue(SSList.get(i).getStudent().getsId());
                tempCell2.setCellStyle(cellrow);
                /*姓名*/
                HSSFCell tempCell3 = tempRow.createCell(2);
                tempCell3.setCellValue(SSList.get(i).getStudent().getsName());
                tempCell3.setCellStyle(cellrow);
                /*选题*/
                HSSFCell tempCell4 = tempRow.createCell(3);
                tempCell4.setCellValue(SSList.get(i).getSubject().getSubName());
                tempCell4.setCellStyle(cellrow);
                /*导师*/
                HSSFCell tempCell5 = tempRow.createCell(4);
                tempCell5.setCellValue(SSList.get(i).getSubject().getTeacher().gettName());
                tempCell5.setCellStyle(cellrow);

            }
            //列宽自动适应，在所有单元格写入后调用
            sheet.autoSizeColumn(1, true);
            //生成文件
            wb.write(file);

            return file;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
