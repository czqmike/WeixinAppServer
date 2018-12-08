package weixinapp.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.json.*;

import weixinapp.dao.GoodsDAO;

/**
 * @author czqmike
 * @date 2018年8月12日
 * @description 获取wx.uploadFile()上传的图片，并将数据库中此商品的image_url修改为当前url
 */
@WebServlet("/GetSubmittedImageServlet")
public class GetSubmittedImageServlet extends HttpServlet {
	private static final long serialVersionUID = 104L;
	private static final String serverip = "https://czqmike-server.cn";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSubmittedImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		request.setCharacterEncoding("utf-8");  //设置编码

        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String str = request.getSession().getServletContext().getRealPath("");
        //获取文件需要上传到的路径
        String path = request.getRealPath("/image");
        String pathStr = null;

        System.out.println("上传的图片路径:" + path);

        //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
        //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
        /**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，
		 * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
		 * 然后再将其真正写到 对应目录的硬盘上
         */
        factory.setRepository(new File(path));
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024*1024) ;
        //高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
		String filename = "";
        try {
            //可以上传多个文件
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);

//            System.out.println(list);
            for(FileItem item : list){
                //获取表单的属性名字
                String name = item.getFieldName();
                //如果获取的 表单信息是普通的 文本 信息
                if(item.isFormField()){
                    //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                    String value = item.getString() ;
                    request.setAttribute(name, value);
                    System.out.println(item + " : " + value);
                } else {
                    /**
					 * 以下三步，主要获取 上传文件的名字
                     */
                    //获取路径名
                    String value = item.getName() ;
                    //索引到最后一个反斜杠
                    int start = value.lastIndexOf("");
                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                    filename = value;
                    request.setAttribute(name, filename);
                    //真正写到磁盘上
                    //它抛出的异常 用exception 捕捉
                    //item.write( new File(path,filename) );//第三方提供的
                    //手动写的
                    str+="/image/"+filename;
                    pathStr="/image/"+filename;
                    System.out.println("[file path]:"+str);
                    File file=new File(str);
                    OutputStream out = new FileOutputStream(file);
                    InputStream in = item.getInputStream() ;
                    int length = 0 ;
                    byte [] buf = new byte[1024] ;

                    System.out.println("[count bytes]:"+item.getSize());

                    // in.read(buf) 每次读到的数据存放在   buf 数组中
                    while( (length = in.read(buf) ) != -1){
                        //在   buf 数组中 取出数据 写到 （输出流）磁盘上
                        out.write(buf, 0, length);
                    }
                    in.close();
                    out.close();
                }
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        PrintWriter printWriter=response.getWriter();

        JSONObject jsonobj = new JSONObject();
        try {
			jsonobj.put("image_url", serverip + "/image/" + filename);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        printWriter.print(jsonobj);
        printWriter.flush();
        printWriter.close();
	}
}
