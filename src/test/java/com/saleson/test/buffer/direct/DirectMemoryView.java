package com.saleson.test.buffer.direct;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author saleson
 * @date 2022-03-18 12:53
 */
public class DirectMemoryView {

    public void directMemoryView() throws JMException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("java.nio:type=BufferPool,name=direct");
        MBeanInfo info = mbs.getMBeanInfo(objectName);
        for (MBeanAttributeInfo i : info.getAttributes()) {
            System.out.println(i.getName() + ":" + mbs.getAttribute(objectName, i.getName()));
        }
    }


    /**
     * JMX获取
     * 如果目标机器没有启动JMX，那么添加jvm参数：
     * -Dcom.sun.management.jmxremote.port=9999
     * -Dcom.sun.management.jmxremote.authenticate=false
     * -Dcom.sun.management.jmxremotAe.ssl=false
     * <p>
     * 重启进程
     * 然后本机通过JMX连接访问：service:jmx:rmi:///jndi/rmi://127.0.0.1:9999/jmxrmi
     */

    public void directMemoryViewByJmx() throws IOException, JMException {
        String jmxURL = "service:jmx:rmi:///jndi/rmi://127.0.0.1:9999/jmxrmi";
        JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
        Map map = new HashMap();
        String[] credentials = new String[]{"monitorRole", "QED"};
        map.put("jmx.remote.credentials", credentials);
        JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map);
        MBeanServerConnection mbsc = connector.getMBeanServerConnection();
        ObjectName objectName = new ObjectName("java.nio:type=BufferPool,name=direct");
        MBeanInfo mbInfo = mbsc.getMBeanInfo(objectName);
        for (MBeanAttributeInfo i : mbInfo.getAttributes()) {
            System.out.println(i.getName() + ":" + mbsc.getAttribute(objectName, i.getName()));
        }

    }
}
