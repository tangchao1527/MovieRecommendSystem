package com._520it;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * Created by 超哥 on 2019/6/15.
 */
public class Application {

    public static void main(String[] args) {
        String brokers = "192.168.137.129:9092";
        String zookeepers = "192.168.137.129:2181";

        // 输入和输出的topic
        String from = "log";
        String to = "recommender";

        // 定义kafka streaming的配置
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "logFilter");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, zookeepers);

        //创建kafka stream配置对象
        StreamsConfig config = new StreamsConfig(settings);

        //创建一个拓扑构造器
        TopologyBuilder builder = new TopologyBuilder();

        //定义流处理的拓扑结构
        builder.addSource("SOURCE",from)
               .addProcessor("PROCESSOR",()->new LogProcessor(), "SOURCE")
                .addSink("SINK", to, "PROCESSOR");

        KafkaStreams streams = new KafkaStreams(builder,config);

        streams.start();
        System.out.println("Kafka stream started!>>>>>>>>>>>");
    }
}
