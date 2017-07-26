package com.od.ssm.util;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.od.ssm.po.BookTable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;

public class ReadWriteExcelFile
{

    public static void readXLSFile(InputStream ExcelFileToRead) throws IOException
    {

        List<BookTable> list = new ArrayList<BookTable>();

//        InputStream ExcelFileToRead = new FileInputStream("D:/testRed.xls");
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();

        while (rows.hasNext())
        {
           BookTable bookTable = new BookTable();
           String temStr = "";
            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            //遍历读取每一格
            while (cells.hasNext())
            {
                cell = (HSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                {
                    //如何不为空字符串
                    if(!cell.getStringCellValue().equals("")) {
                        temStr=cell.getStringCellValue()+","+temStr;
                        System.out.print(cell.getStringCellValue() + " ");

                    }
                }
                else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                {
                    temStr=cell.getNumericCellValue()+","+temStr;
                    System.out.print(cell.getNumericCellValue() + " ");
                }
                else
                {
                    // U Can Handel Boolean, Formula, Errors
                }
                String[] strs = temStr.split(",");
                for(int i=0;i<strs.length;i++){
                   System.out.print(i);
                    System.out.println(strs[i]);
                }

            }
            System.out.println();
        }

    }

    public static void writeXLSFile() throws IOException
    {

        String excelFileName = "D:/123.xls";// name of excel file

        String sheetName = "Sheet1";// name of sheet

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);

        // iterating r number of rows
        for (int r = 0; r < 5; r++)
        {
            HSSFRow row = sheet.createRow(r);

            // iterating c number of columns
            for (int c = 0; c < 5; c++)
            {
                HSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell " + r + " " + c);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        // write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public  List<BookTable> readXLSXFile(InputStream ExcelFileToRead) throws IOException
    {
//        InputStream ExcelFileToRead = new FileInputStream("D:/booktable.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

        XSSFWorkbook test = new XSSFWorkbook();

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();
        rows.next();//跳过第一行

        List<BookTable> bookTableList = new ArrayList<BookTable>();
        while (rows.hasNext()) {

            String temStr = "";
            BookTable bookTable = new BookTable();



            row = (XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            while (cells.hasNext()) {

                cell = (XSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    //如何不为空字符串
                    if (!cell.getStringCellValue().equals("")) {
                        temStr = cell.getStringCellValue() + "," + temStr;
//                        System.out.print(temStr + " ||第一 new111");
                    }
                }

                     else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        temStr = cell.getNumericCellValue()+ "," + temStr;
//                        System.out.print(cell.getNumericCellValue()+ " ||第二 new222");
                    }
                    else {
                        // U Can Handel Boolean, Formula, Errors
                    }


                    System.out.println();
                }
            String[] strs = temStr.split(",");
            //这里因为手机号码读取出现 17765602448 -> 1.7765602448E10 的错误，这里需要修正一下格式
            StringBuffer sb = new StringBuffer(strs[2]);
            int o = sb.indexOf(".");
            sb.deleteCharAt(o);
            int t = sb.indexOf("E10");
            sb.delete(t,t+2);
            strs[2] = sb.toString();
           // 1.0  -> 1的处理
         String tem = strs[1].substring(0,1);
            //处理完成
            strs[1] = tem;
            //生成对象


            BookTable bt = new BookTable(strs[3],strs[2],strs[1],strs[0]);

            //存入list中
            bookTableList.add(bt);

        }

            return bookTableList;
        }


    public static void writeXLSXFile(HttpServletResponse response) throws IOException
    {

        String excelFileName = "D:/zuixinE.xlsx";// name of excel file

        String sheetName = "Sheet1";// name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        // iterating r number of rows
        for (int r = 0; r < 5; r++)
        {
            XSSFRow row = sheet.createRow(r);

            // iterating c number of columns
            for (int c = 0; c < 5; c++)
            {
                XSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell " + r + " " + c);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        // write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();


        try{
	System.out.println("下载文件");
	// path是指欲下载的文件的路径。
		File file = new File(excelFileName);
		// 取得文件名。
		String filename = file.getName();
		// 以流的形式下载文件。
		InputStream fis = new BufferedInputStream(new FileInputStream(excelFileName));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(filename.getBytes()));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(
				response.getOutputStream());
		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
	}catch(IOException ex) {
		ex.printStackTrace();
	}
System.out.println("测试一下");

    }

//    public static void main(String[] args) throws IOException
//    {
//        writeXLSFile();
////        readXLSFile();
//
////        writeXLSXFile();
////        readXLSXFile();
////        System.out.println(new Date());
//    }
}  