import com.tzh.dubbo_interface.DubboInterface;
import com.tzh.entity.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

//dubbo消费者
public class Dubbo_consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "*.xml" });
        System.out.println("准备消费");
        for (int i=1; i<=10;i++) {
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DubboInterface dubboInterface = (DubboInterface) context.getBean(DubboInterface.class);
            User user = dubboInterface.add();
            System.out.println(user.toString());
            if(i < 10) {
                System.out.println("消费" + i + "次");
            }else if(i == 10){
                System.out.println("完成消费任务，一共消费了"+i+"次");
            }
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
