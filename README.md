# miaosha
miaosha scene


### api
- 初始化10个库存，开始秒杀`http://vs1:8081/stock/int`
- 查看剩余库存 `http://vs1:8081/stock/left`
- 添加100个库存`http://vs1:8081/stock/add`


- 查看服务的运行以及均衡策略`http://vs2:8090/ribbon/see/1`
- 秒杀核心方法`http://vs2:8090/miaosha`


### 待续
- 待完善一个循环等待的锁，对商品最大编号加锁，以保证库存并发添加
- `miaosha-test.jmx` jmeter需要更多线程测试
