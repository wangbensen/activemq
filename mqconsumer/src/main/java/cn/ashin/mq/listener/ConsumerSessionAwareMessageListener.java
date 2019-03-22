package cn.ashin.mq.listener;

import javax.jms.*;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import cn.ashin.mq.biz.MailBiz;
import cn.ashin.mq.params.MailParam;

import com.alibaba.fastjson.JSONObject;

/**用于 监听activemq的  实现的消息监听器 不同有不同的效果 spring整合activemq 提供了3中监听器
 * @author wbs
 * @version 2019年1月15日 下午2:45:37
 */
@Component
public class ConsumerSessionAwareMessageListener implements
		SessionAwareMessageListener<Message> {

	private static final Log log = LogFactory
			.getLog(ConsumerSessionAwareMessageListener.class);

	@Autowired
	private JmsTemplate activeMqJmsTemplate;
	@Autowired
	private Destination sessionAwareQueue;
	@Autowired
	private MailBiz bailBiz;
	// 实现这个接口就是获取指定队列的信息。
	public synchronized void onMessage(Message message, Session session) {
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
			final String ms = msg.getText();
			log.info("--->receive message:" + ms);
			MailParam mailParam = JSONObject.parseObject(ms, MailParam.class);// 转换成相应的对象
			if (mailParam == null) {
				return;
			}
			try {
				bailBiz.mailSend(mailParam);
				//int a=1/0;
			} catch (Exception e) {
				// 发送异常，重新放回队列  最好不要用，因为程序会一直跑下去 直到系统奔溃
//				activeMqJmsTemplate.send(sessionAwareQueue,
//						new MessageCreator() {
//							public Message createMessage(Session session)
//									throws JMSException {
//								return session.createTextMessage(ms);
//							}
//						});
				log.error("--->MailException:", e);
			}
		} catch (Exception e) {
			log.error("--->", e);
		}
	}
}