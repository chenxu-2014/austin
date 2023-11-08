#### kafka

- kafka查看有多少topic

  > $KAFKA_HOME/bin/kafka-topics.sh --list --bootstrap-server *** <kafka-server>:<port>***
  > kafka-topics --list --bootstrap-server <kafka-server>:<port>  *适用于Kafka 2.6.0及更高版本*

- kafka创建topic

  > $KAFKA_HOME/bin/kafka-topics.sh --create --topic ***topicName***--partitions 1 --zookeeper zookeeper:2181 --replication-factor 1

- kafka查看具体topic内容

  > $KAFKA_HOME/bin/kafka-topics.sh --zookeeper zookeeper:2181 --describe --topic ***topicName***

