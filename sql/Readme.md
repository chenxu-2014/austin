

docker run -d --privileged=true -p 6379:6379 -v /home/redis/conf/redis.conf:/etc/redis/redis.conf -v /home/redis/data:/data --name redis01 redis:4.0 redis-server /etc/redis/redis.conf --appendonly yes


参数说明：
--privileged=true：容器内的root拥有真正root权限，否则容器内root只是外部普通用户权限
-v /docker/redis/conf/redis.conf:/etc/redis/redis.conf：映射配置文件
-v /docker/redis/data:/data：映射数据目录
redis-server /etc/redis/redis.conf：指定配置文件启动redis-server进程
--appendonly yes：开启数据持久化


1.检查docker容器中是否启动了redis；命令：docker ps
2.如果没启动，则先启动服务；命令：docker run -p 6379:6379 --name redis -d redis （这里name后面的redis就是你在docker里面的redis服务映射的名称，可以叫任何名称，端口号也是）
3.根据查询出来的名称，比如你的redis服务就叫redis；命令：docker exec -it redis redis-cli
4.如果出现了127.0.0.1:6379 那就说明已经成功启动了docker内redis的客户端；（ps:6379是端口号）
5.然后直接输入redis的命令即可，比如 ping 那么redis 返回 pong则表示服务启动无误

redis01

qnfgamxskwocbeif

{"host":"smtp.qq.com","port":465,"user":"804880293@qq.com","pass":"qnfgamxskwocbeif","from":"804880293@qq.com","starttlsEnable":"true","auth":true,"sslEnable":true}