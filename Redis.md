# Redis学习笔记
---
## 命令网站
    http://doc.redisfans.com/
---


## 命令操作

### SET KEY VALUE 增加一个键值对

---
### DEL KEY  删除一个键值对
---
### EXISTS key 判断键吃否存在
    存在返回整数 1  否则返回0
---
### EXPIRE KEY Time
    设置这个键的过期时间
#### TTL KEY 返回剩余时间 -1表示永久 -2表示过期
```bash
SET name "bobo"
TTL name  //返回-1 因为还没有设置时间 表永久
EXPIRE name 10 //意思是10秒后结束
TTL name //返回10  还剩下10秒就销毁 
//过了10秒
TTL name //返回 -2表示删除了
```
---
### TYPE key 获取键值数据类型
---
### INCR key 
    让当前的键值递增 并返回递增后的值
    该方法具有原子性 
---
### INCRBY key number
    增加指定整数 一次增加 number
---
### DECR DECRBY 与上面相同
---
    
### INCRBYFLOAT key numbers
    增加 双精度浮点数
    
---
### 尾部追加 APPEND key value  
    返回追加后字符串总长度
```bash
SET name "hello "
APPEND name "world!"
GET name //显示 hello world!

```
### STRLEN key 返回键值长度
---

### MGET MSET 同时设置多个值
    MSET key1 value1 key2 value 2
    MGET key1 key2
---

### 位操作
    GETBIT key offset

---
### 散列
类似对象,字段和字段值的映射  字段值只能是字符串
键 |   字段     | 字段值
---|--- | ---
 .  | color |  白色
car:1 | name  | 奥迪
 . | price | 90w
---
#### 赋值与取值

- HSET key field value
>    不区分插入和更新操作 
    如果之前字段不存在 执行插入操作 返回1
    之前字段存在 执行更新操作 返回0
>
- HGET key field 获取某个键的字段的字段值
- HMSET key field value [field value …]
- HMGET key field [field …]
- HGETALL key 返回键的所有字段和字段值


---
#### 判断字段是否存在 HEXISTS key field
    存在返回1  否则返回0
#### 字段不存在时赋值 HSETNX key field value
    和HSET类似 如果字段存在 HSETNX将不执行任何操作,如果不存在就赋值
#### 字段增加数字HINCRBY key field number
    如果字段不存在就自动建立该键,返回增值后的字段值
#### 删除字段 HDEL key field [Field ...]
#### 命令拾遗  HKEYS key | HVALS key
    直接获取键中所有字段名 或者 字段名对应的值
#### 获取字段数量 HLEN key
---
#### 列表类型list
> 内部是用双向列表实现的
##### 向列表两端增加元素 
######    LPUSH key value [value ...]
    向列表左边增加元素, 返回值是增加元素后列表长度
######    RPUSH key value [value ...]
    同上
##### 从列表两端弹出元素
    LPOP key 弹出列表左边第一个元素| RPOP key 反之
##### LLEN key 获取元素个数 键不存在返回0
##### LRANGE key start  stop 获取列表片段
   - index 从0开始 0表示最左边 -1表示最右边
   - -2 表示最右边第二个元素 以此类推
   - start > stop 返回空列表 stop大于索引范围,返回列表最右边元素
---
##### 删除列表中指定的值 LREM key count value
- 删除前count个值为value 的元素 返回值是删除元素个数
- count > 0 从左边 删除 前 count 个value 元素
- count < 0 从右边
- count = 0 删除所有为value元素
##### LINDEX key index 获取指定index元素值
##### LSET key index value 设置指定index元素值
---
#### LTRIM key start end 删除[start,end]之外的元素
#### LINSERT key BEFORE|AFTER num value
> 从左到右查找值为num的元素 然后根据 BEFORE|AFTER 插入该元素前面还是后面
#### RPOPLPUSH source destination 元素从一个列表转到另一个列表
> 先执行RPOP 然后执行LPUSH 

---
### 集合
> 在一个集合中不能有相同的元素,如果有相同就会被忽略
#### SADD key member [member ...] 增加元素
#### SREM key member[member ... ] 删除元素
#### SMEMBERS key 返回集合所有元素
#### SISMEMBER key member 判断元素是否在集合中
> 存在返回1 不存在返回0  复杂度O(1)
#### 集合间的运算
##### SDIFF key [key ...]
> 用来对多个集合执行差集  A B差集 A-B
##### SINTER  key [key ... ] 多个集合交集
##### SUNION key [key....] 多个集合并集
#### SCARD key 获取集合中元素个数
#### 集合运算并将结果存储到 destination中
- SDIFFSTORE destination key [key ...]
- SINTERSTORE destination key [key ...]
- SUNIONSTORE destination key [key ...]
#### 随机获得集合中的元素 SRANDMEMBER key [count]
> count 决定抽取多少个元素
> count 正数 随机获得count 个不重复元素 如果count 大于集合size 返回全部
> count 负数  随机返回|count|个元素 可能有相同
#### SPOP key 从集合随机弹出一个元素
---
### 有序集合
> 为每个元素都关联了一个分数 按分数高低排序
> 和列表类型相似 1.都是有序的  2.都可以获得某一范围的元素
> 区别:
- 列表通过链表,获取两端数据快, 元素增多后         访问中间较慢,更适合新鲜事或日志很少访问中间元素的应用
- 有序列集合用哈希表和跳跃表实现,中间部分也很快 O(long(N))
- 列表不能简单的调整某个元素的位置,有序集合可以
- 有序集合比列表更耗费内存
#### 增加元素 ZADD KEY score member [score member...]
> 向有序集合中加入一个元素 如果该元素存在就用新的分数代替旧分数
    返回值是新加入到集合中的元素个数 不包括之前存在的
#### 分数还可以是浮点数
    +inf -inf表示正负无穷
#### 获取元素的分数 ZSCORE key member
#### 获取排名在某个范围的元素列表
> ZRANGE key start stop [WITHSCORES]会按照元素从小到大 返回[start,top]之间元素
  索引都是0开始 负数表示从后向前找 类似LRANG  
  加上 WITHSCORES 会在元素后面显示分数
> ZREVRANGE key start stop [WITHSCORES] 大到小
#### 获得指定分数范围元素
     ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
> [80,100] 80 100  | (80,100] (80 100  加( 开区间
> 0 100 和 100 0不一样 顺序相反
#### 增加某个元素的分数 ZINCRBY key increment member 
> 返回值是更改后的分数  -4 表示减分 不存在就先创建赋值0后再操作
#### ZCARD key获取集合中元素的数量
#### ZCOUNT KEY min max 获取分数范围内元素个数
> 可以使用 (
#### ZREM key member[member ...] 
> 删除一个或多个元素 返回值是成功删除的元素数量
#### ZREMRANGEBYRANK key start stop 排名范围内删除元素
> 这个命令是按从小到大排序 [0]是最小
#### ZREMRANGEBYSCORE key min max 删除指定分数范围内所有元素
#### 获得元素排名
##### ZRANK key member 按从小到大获取指定元素排名[0]开始
##### ZREVRANK key member 相反
#### ZINTERSTORE destination num KEY [KEY ...] [WEIGHTS weight [weight …]]
[AGREGATE SUM|MIN|MAX]
> 在所有的key中取交集 num  存入到destination中 返回destination长度
- AGREGAT -> sum -> 存在destination的分数是多个集合中所有的分数和 默认
- AGREGAT -> min -> 存的是集合中最小值
- AGREGAT -> max ->反之
- WEIGHTS 是权重 后面参数数量对应key  每个分数会乘上集合的权重
---


## 事务
```bash
redis＞MULTI
OK
redis＞SADD "user:1:following" 2
QUEUED
redis＞SADD "user:2:followers" 1
QUEUED
redis＞EXEC
1) (integer) 1
2) (integer) 1
```
> MULTI 命令告诉Redis 下面命令属于同一个事务 等到发送EXEC命令才执行
> 如果EXEC前客户端断线了 Redis会清空事务队列,事务中所有命令都不会执行
> EXEC后断线也会执行
### 错误处理
> Redis 2.6.5之前 事务之中的语句有错误 就只执行正确的
> 运行错误.  比如用散列的命令 操作 集合, 这种错误会被接受 然后其他命令继续执行
    包括出错后的命令
### WATCH 命令介绍
> WATCH 可以监控一个或多个键, 在WATCH key 后 再修改这个值的话
> 之后的事务(MULTI ... EXEC) 之间的命令不会执行
> 执行exec后 wathc监控的就失效了 除非重新watch
> UNWATCH 取消
### 生存时间 EXPIRE key seconds(秒)
> 设置成功返回1  键不存在或者设置失败返回0
> PEXPIRE 单位是毫秒
>  watch检测一个有生存时间的键,过期后不会认为键被改变
### 获取剩余时间TTL key
> 返回-1 表示永久存在 -2已过期
### 取消生存时间PERSIST key
> 同时 set getset命令赋值的时候也会清除生存时间
> INCR LPUSH HSET ZREM都不会影响键生存时间
### SORT key [DESC] [LIMIT offset count]
- 默认从小到大 desc 大到小
- [LIMIT offset count] 表示跳过前 offset个元素冰获取之后的count个元素
- SORT 命令可以对列表类型 集合类型 有序类型键进行排序
- 对于有序集合 会忽略分数 只针对元素自身的值排序
- SORT key ALPHA 字典排序
---
### BY参数参考键
> 可以是字符串类型键 或者散列类型键的某个字段(key->字段名)
> 如果用了BY , 而是对每个元素使用元素的值替换参考键中的第一个“＊”并获取其值，然后依 据该值对元素排序
```bash
redis＞SORT tag:ruby:posts BY post:* ->time DESC
1) "12"
2) "26"
3) "6"
4) "2"
```
> 在上例中SORT命令会读取post:2、post:6、post:12、post:26几个散列键中的time字段的值    并以此决定tag:ruby:posts键中各个文章ID的顺序
--- 
> 字符串类型
```bash
redis＞LPUSH sortbylist 2 1 3
(integer) 3
redis＞SET itemscore:1 50
OK
redis＞SET itemscore:2 100
OK
redis＞SET itemscore:3 -10
OK
redis＞SORT sortbylist BY itemscore:* DESC
1) "2"
2) "1"
3) "3"
```
> 如果不包含"*" sort命令将不会进行排序
> 如果键值相同就会比较元素本身的值来决定顺序
> 如果参考键不存在或者是常量键名就默认参考键值为0
### GET参数
> get参数不影响排序,使sort命令返回的结果不再是元素自身的值,而是get中指定键值
> 也可以用占位符 *
```bash
redis＞SORT tag:ruby:posts BY post:＊-＞time DESC GET post:*-＞title
1) "Windows 8 app designs"
2) "RethinkDB - An open-source distributed database built with love"
3) "Uses for cURL"
4) "The Nature of Ruby"
```
> 可以有多个get参数 by只能一个
> 如果需要返回文章ID 用 GET #
### STORE key参数 保存sort后结果
> 存储后是列表类型,如果键存在就覆盖它
> 加上strore 后 sort命令返回值为结果的个数

--
## 任务队列
> 传递任务的队列 </br>
 任务队列进行交互的实体. 1.生产者 2.消费者 </br>
 用任务队列的好处
- 松耦合. 
- 易于扩展消费者可以有多个，而且可以分布在不同的服务器中
### BRPOP key [key ..] timeOut 命令
> 和RPOP类似 唯一区别是当列表中没有元素时BRPOP命令会一直阻
    塞住连接，直到有新元素加入 </br>
> 第二个是超时时间 单位秒,当超过了此时间仍然没哟新元素就返回nil
    ,如果超时时间是0 表示不限制等待时间,即如果没有新元素加入列表就会永远阻塞下去。</br>
#### 优先级
> BRPOP key1 key2 key3 0 如果多个键都有元素则按照从左到右的顺序取第一个键中的一个元素

---
## 管道
> 在执行多个命令时每条命令都需要等待上一条命令执行完（即收到Redis的返回结果）才
能执行，即使命令不需要上一条命令的执行结果</br>
> 公国管道可以一次性发送多条命令并在执行完后一次性将结果返回，当一组命令中每条命令都不依赖于之前命令的执行结果时就可以将这组命令一起通过管道发出。</br>
> 能够降低客户端和redis通信次数来减少往返时延
### 内部编码 
> 查看编码的命令 OBJECT ENCODING key
</br>
> redis每个键值都是使用一个redisObject结构体保存的

```
unsigned type:4; //0->String , 1->LIST , 2->SET, 3->ZSET, 4->HASH
unsigned notused:2; /＊ Not used ＊/
//encoding表示内部编码 0->RAW,1->INT, 2->HT(hash table),3->ZIPMAP
//4->LINKEDLIST, 5->ZIPLIST, 6->INTSET, 7->SKIPLIST
unsigned encoding:4;
unsigned lru:22; /＊ lru time (relative to server.lruclock) ＊/
int refcount; //存储该键值被引用数量
void *ptr;
}robj;

```


![image](https://m.qpic.cn/psb?/V1419nCY24mpkL/muNfSHVZ9Z9A.TWwoxVZBUn4tP2vKLaK5xkfNaEbUjw!/b/dFMBAAAAAAAA&bo=zgTUAgAAAAARByw!&rf=viewer_4)


#### 字符串类型
```bash
struct sdshdr {
int len; //字符串长度
int free; //buf剩余空间
char buf[]; //存储内容
};
```
> 执行SET KEY BO 存储键值需要的空间
</br> sizeof(redisObject) + siezeof(sdshdr) + strlen("BO")=30字节
</br>
Redis启动后会预先建立10000个分别存储从0到9999这些数字的redisObject类型变量作为共享
对象，如果要设置的字符串键值在这10000个数字内（如SET key1 123）则可以直接引用共享对
象而不用再建立一个redisObject了，也就是说存储键值占用的空间是0字节

---
#### 散列类型
> 内部编码方式: REDIS_ENCODING_HT或REDIS_ENCODING_ZIPLIST</br>
> 可以在配置中修改hash-max-ziplist-entries 512 || hash-max-ziplist-value 64
</br> 散列类型键的字段个数少于hash-max-ziplist-entrie且每个字段名和字段值的长
度都小于</br> hash-max-ziplist-value参数值（单位为字节）时，Redis就会使用REDIS_
ENCODING_ZIPLIST来</br> 存储该键，否则就会使用REDIS_ENCODING_HT
</br> 
> HT编码即哈希表,可以显示O(1)时间复杂度复制取之,其字段和字段值都是使用</br> 
redisObject储存的
</br> ZIPLIST编码类型是一种紧凑的编码格式，它牺牲了部分读取性能以
换取极高的空间利用率，适合在元素较少时使用。

--
## Lua语言
### 数据类型
![image](https://m.qpic.cn/psb?/V1419nCY24mpkL/O7.dBFeJ1Gcm*hS.*ior868TZSdpAP65BEWhthjinjI!/b/dDUBAAAAAAAA&bo=ugRaAgAAAAADB8Q!&rf=viewer_4)
#### 变量
> 分全局和局部变量, 全局无需声明就可以直接使用 默认值nil
,删除全局变量的方法时赋值nill
</br>redis只允许使用局部变量 local + 变量名
</br> 注释 单行--  多行 --[[   ]]
</br> 连接字符串类似java的 +  ,用.. print('hello' .. ' ' .. 'world!') --'hello world!'
</br> 取长度操作符  print(#'hello') -> 5
</br> if 条件 then  .... else ... end </nr>
while 条件 do  ... end  </br>
for i = 1, 100 do   sum = sum + i end
</br> 
```
a={}
a['name'] = 'bo'
print(a.name)

people = {
    name = 'bo'
    age = 22
}
print(people.name)

a={}
a[1]='bo'
a={'bo','x'}
print(a[1])
约定数组从1开始
```
</br> 函数定义  function(参数) ... end
### 持久化
> 两种方式将内存数据同步到硬盘中 : RDB  AOF
#### RDB
> 通过快照完成,符合条件Redis自动将内存数据快照储存在硬盘中,redis默认持久化方式
</br>
redis快照过程
- 使用fork函数赋值一份当前(父)进程的副本(子进程)
- 父进程继续接受处理客服端命令,子进程将内存中数据写入硬盘临时文件
- 子进程完成后会用 该临时文件 替换旧的RDB文件
</br>
fork函数发送时操作系统使用写时复制策略,即父子进程共享内存数据,父进程修改数据,子进程也会将这数据复制一份,保证同步.所以新的RDB文件存储的是知心fork一刻的内存数据
</br> redis在快照结束后才将旧文件替换新文件,任何时候RDB文件都是完整的

#### AOF
默认没有开启AOF  通过appendonly yes参数开启</br>
开启AOF持久化后每执行一条会更改Redis中的数据的命令，Redis就会将该命令写入硬
盘中的AOF文件

### 读写分离
> 通过复制可以实现读写分离以提高服务器的负载能力</br>
让主数据库只进行写操作, 从数据库负责读操作