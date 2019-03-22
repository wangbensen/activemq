package cn.ashin.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wbs
 * @version 2019年1月15日 下午2:45:37 类说明
 */
public class MQConsumer {
	private static final Log log = LogFactory.getLog(MQConsumer.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("--->MQ context start error:", e);
			System.exit(0);
		}
	}
}
