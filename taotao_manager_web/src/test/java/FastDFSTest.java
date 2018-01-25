import com.taotao.common.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class FastDFSTest {


    @Test
    public void fastDFSTest() {

        try {
//            加载配置文件
            ClientGlobal.init("D:\\IdeaWorkspace\\taotao_parent\\taotao_manager_web\\src\\main\\resources\\resource\\fastdfs.conf");
//            创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
//            获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
//            创建一个StorageServer对象
            StorageServer storageServer = null;
//            创建一个StorageClient对象
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//            使用StorageCilent上传图片 参数一:文件的本地;路径  参数二:不带.的扩展名  参数三:源数据,图片的高度,宽度,作者,像素,时间戳等
            String[] strings = storageClient.upload_appender_file("C:\\Users\\Administrator\\Desktop\\头像\\zz.jpeg", "jpeg", null);
            System.out.println("192.168.84.129:80/"+strings[0]+"/"+strings[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fastDFSClientTest() throws  Exception{
        FastDFSClient client = new FastDFSClient("D:\\IdeaWorkspace\\taotao_parent\\taotao_manager_web\\src\\main\\resources\\resource\\fastdfs.conf");
        String uploadFile = client.uploadFile("C:\\Users\\Administrator\\Desktop\\头像\\20161004135831_fCXTs.thumb.700_0.jpeg", "jpeg");
        System.out.println("filePath:"+"192.168.84.129/"+uploadFile);
    }


}
