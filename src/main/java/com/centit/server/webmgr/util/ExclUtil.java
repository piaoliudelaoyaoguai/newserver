package com.centit.server.webmgr.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 导出Excel公共方法
 * @version 1.0
 * 
 * @author lihao
 *
 */
public class ExclUtil{
    
    //显示的导出表的标题
    private String title;
    //统计
    private String countStr;
    //导出表的列名
    private String[] rowName ;
    
    private List<Object[]>  dataList = new ArrayList<Object[]>();
    
    //导出excl文件路径地址
    private String outPath ;
    
    //导出excl文件名称filename
    private String outFileName ;
    
    HttpServletResponse  response;
    
    //构造方法，传入要导出的数据
    public ExclUtil(String title, String countStr, String[] rowName, List<Object[]>  dataList, String outPath, String outFileName){
    	this.title = title;
        this.countStr = countStr;
        this.rowName = rowName;
        this.dataList = dataList;
        this.outPath = outPath;
        this.outFileName = outFileName;
    }
    
    /*
     * 导出数据
     * */
    public void export() throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表
            
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);
            
            rowm.setHeight((short) (25 * 35)); //设置第一行高度
            
            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook);                    //单元格样式对象
            
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.length-1)));  //CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);
            
            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);  // 在索引2的位置创建行(最顶端的行开始的第三行)
            rowRowName.setHeight((short) (25 * 25)); //设置第二行高度
            
            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                HSSFCell  cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }
            
            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){
                
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i+3);//创建所需的行数
                
                row.setHeight((short) (25 * 20)); //设置高度
                
                for(int j=0; j<obj.length; j++){
                    HSSFCell  cell = null;   //设置单元格的数据类型
//                    if(j == 0){
//                        cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);
//                        cell.setCellValue(i+1);    
//                    }else{
//                        cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
//                        if(!"".equals(obj[j]) && obj[j] != null){
//                            cell.setCellValue(obj[j].toString());                        //设置单元格的值
//                        }
//                    }
                    cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                    	cell.setCellValue(obj[j].toString());                        //设置单元格的值
                    }else{
                    	cell.setCellValue("");      
                    }
                    
                    if(j == 1){
                    	style.setWrapText(true);  //设置可换行
                        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);  //设置水平对齐的样式为左对齐;  
                    }
                    
                    
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
//                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
//                    HSSFRow currentRow;
//                    //当前行未被使用过
//                    if (sheet.getRow(rowNum) == null) {
//                        currentRow = sheet.createRow(rowNum);
//                    } else {
//                        currentRow = sheet.getRow(rowNum);
//                    }
//                    if (currentRow.getCell(colNum) != null) {
//                        HSSFCell currentCell = currentRow.getCell(colNum);
//                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//                            int length = currentCell.getStringCellValue().getBytes().length;
//                            if (columnWidth < length) {
//                                columnWidth = length;
//                            }
//                        }
//                    }
//                }
//                if(colNum == 0){
//                    sheet.setColumnWidth(colNum, (columnWidth-2) * 128);
//                }else{
//                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
//                }
//                sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                
                if(colNum == 0){  //姓名
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 512);
	            }else if(colNum == 1){  //单位
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 1024);
	            }else if(colNum == 2){  //职务
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 512);
	            }else if(colNum == 3){  //手机号
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 512);
	            }else if(colNum == 4){  //实际参会人
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 512);
	            }else if(colNum == 5){  //签到时间
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 512);
	            }else if(colNum == 6){  //备注
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 1024);
	            }else if(colNum == 7){  //报名情况
	                  sheet.setColumnWidth(colNum, (columnWidth+2) * 512);
	            }
                
            }
            
            //定义表格统计行：(放在表格后创建是因为：合并后单元格宽度等宽，若在表格前创建会影响表格宽度自适应)
            HSSFCellStyle cellStyle = this.getCountStyle(workbook);//获取列头样式对象
            int columnNumCount = rowName.length;
            HSSFRow rowRowNameCount = sheet.createRow(1);   // 在索引1的位置创建行(最顶端的行开始的第二行)
            rowRowNameCount.setHeight((short) (25 * 20)); //设置高度
            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNumCount;n++){
                HSSFCell  cellRowName = rowRowNameCount.createCell(n);  //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);  //设置列头单元格的数据类型
                if(n==0){
                	cellRowName.setCellValue(countStr); //设置列头单元格的值
                }
                cellRowName.setCellStyle(cellStyle);  //设置列头单元格样式
            }
            int lineStart = 1; //开始行号
            sheet.addMergedRegion(new CellRangeAddress(lineStart, lineStart, 0, (rowName.length-1)));  //CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
            
            if(workbook !=null){
                try
                {
//                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
//                    String headStr = "attachment; filename=\"" + fileName + "\"";
//                    response = getResponse();
//                    response.setContentType("APPLICATION/OCTET-STREAM");
//                    response.setHeader("Content-Disposition", headStr);
//                    OutputStream out = response.getOutputStream();
//                    workbook.write(out);
//                    FileOutputStream out = new FileOutputStream("D:/data/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls");
                	
                	//如果文件夹不存在则创建 
            		File outPathFolder = new File(outPath);
            		if(!outPathFolder.exists() && !outPathFolder.isDirectory()){       
            			outPathFolder.mkdir();
            		}
            		
                    FileOutputStream out = new FileOutputStream(outPath + outFileName);
                    workbook.write(out);
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    /* 
     * 列头单元格样式
     */    
      public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
          // 设置字体
          HSSFFont font = workbook.createFont();
          //设置字体大小
          font.setFontHeightInPoints((short)11);
          //字体加粗
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          //设置字体名字 
          font.setFontName("Courier New");
          //设置样式; 
          HSSFCellStyle style = workbook.createCellStyle();
          //设置底边框; 
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
          //设置底边框颜色;  
          style.setBottomBorderColor(HSSFColor.BLACK.index);
          //设置左边框;   
          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
          //设置左边框颜色; 
          style.setLeftBorderColor(HSSFColor.BLACK.index);
          //设置右边框; 
          style.setBorderRight(HSSFCellStyle.BORDER_THIN);
          //设置右边框颜色; 
          style.setRightBorderColor(HSSFColor.BLACK.index);
          //设置顶边框; 
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);
          //设置顶边框颜色;  
          style.setTopBorderColor(HSSFColor.BLACK.index);
          //在样式用应用设置的字体;  
          style.setFont(font);
          //设置自动换行; 
          style.setWrapText(false);
          //设置水平对齐的样式为居中对齐;  
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          //设置垂直对齐的样式为居中对齐; 
          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
          
          //设置单元格背景颜色
          style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
          style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          
          return style;
      }
      
     /*  
     * 列数据信息单元格样式
     */  
      public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
            // 设置字体
            HSSFFont font = workbook.createFont();
            //设置字体大小
            //font.setFontHeightInPoints((short)10);
            //字体加粗
            //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            //设置字体名字 
            font.setFontName("Courier New");
            //设置样式; 
            HSSFCellStyle style = workbook.createCellStyle();
            //设置底边框; 
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            //设置底边框颜色;  
            style.setBottomBorderColor(HSSFColor.BLACK.index);
            //设置左边框;   
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //设置左边框颜色; 
            style.setLeftBorderColor(HSSFColor.BLACK.index);
            //设置右边框; 
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            //设置右边框颜色; 
            style.setRightBorderColor(HSSFColor.BLACK.index);
            //设置顶边框; 
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            //设置顶边框颜色;  
            style.setTopBorderColor(HSSFColor.BLACK.index);
            //在样式用应用设置的字体;  
            style.setFont(font);
            //设置自动换行; 
            style.setWrapText(false);
            //设置水平对齐的样式为居中对齐;  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置垂直对齐的样式为居中对齐; 
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            return style;
      }
      
      /*  
       * 底部列数据信息单元格样式
       */  
      public HSSFCellStyle getCountStyle(HSSFWorkbook workbook) {
    	  // 设置字体
          HSSFFont font = workbook.createFont();
          //设置字体大小
          font.setFontHeightInPoints((short)11);
          //字体加粗
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          //设置字体名字 
          font.setFontName("Courier New");
          //设置样式; 
          HSSFCellStyle style = workbook.createCellStyle();
          //设置底边框; 
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
          //设置底边框颜色;  
//          style.setBottomBorderColor(HSSFColor.BLACK.index);
          //设置左边框;   
//          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
          //设置左边框颜色; 
          style.setLeftBorderColor(HSSFColor.BLACK.index);
          //设置右边框; 
//          style.setBorderRight(HSSFCellStyle.BORDER_THIN);
          //设置右边框颜色; 
          style.setRightBorderColor(HSSFColor.BLACK.index);
          //设置顶边框; 
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);
          //设置顶边框颜色;  
          style.setTopBorderColor(HSSFColor.BLACK.index);
          //在样式用应用设置的字体;  
          style.setFont(font);
          //设置自动换行; 
          style.setWrapText(false);
          //设置水平对齐的样式为左对齐;  
          style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
          //设置垂直对齐的样式为居中对齐; 
          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
          
          //设置单元格背景颜色
          style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
          style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          
          return style;
      }
}