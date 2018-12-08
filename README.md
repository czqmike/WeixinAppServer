# WeixinAppServer
采用Servlet方式的微信小程序服务器端实现

一、	数据库
主键用下划线加粗体表示，外键用下划线表示
添加了user表中的主键weixin_id， 添加了头像URL，每次登陆时需发送头像URL
sex = 1表示为男性，2表示为女性，0表示未知
1.	用户表user
用户ID	用户名	性别	OpenID	微信号	电话	邮箱	地址	头像	信誉值
int AI	varchar	int	varchar	varchar	varchar	varchar	varchar	varchar	int NN
user_id	user_name	sex	open_id	weixin_no	tel	mail	address	avatar_url	credit_point


2.	商品表goods
交易类型只能为 one_price | auction
p.s. counseling_time_map存储的是小程序中产生的预约时间JSON字符串
商品ID	用户ID	商品名	商品
细节	商品价格	全新	交易
类型	商品
分类	图片URL	商品状态	预约
时间
int AI	int NN	varchar NN	varchar	double NN	bool	varchar NN	int
NN	varchar
NN	bool
NN	varchar
(10000)
good_id	user_id	good_
name	good_
detail	price	is_
new	trade_type	type_
id	image_
url	sold	coun
seling_
time_
map
	


3.	商品类型 good_type

类型id	类型名	商品大类
int AI	varchar NN	varchar NN
type_id	type_name	main_class
1	书籍	二手交易
2	生活用品	二手交易
3	其他	二手交易
4	数学	线下辅导
5	计算机	线下辅导
6	英语	线下辅导
7	其他	线下辅导

4.	订单表order

订单号	用户ID	商品ID	订单状态	交易时间
int AI	int NN	int NN	bool NN	datetime NN
order_id	user_id	good_id	done	tradetime

5.	购物车shoppingcart
用户ID	商品ID
int 	int
user_id	good_id

6.	消息表message
商品ID：这条留言是针对哪个商品的
消息ID	商品ID	发送用户	接收用户	时间	消息内容
int AI	int NN	int NN	int NN	datetime NN	varchar
message_id	good_id	user_src	user_des	time	content

7.	聊天记录表 conversation
会话ID	发送用户	接收用户	时间	消息内容
int AI NN	int NN	int NN	datetime NN	varchar(500)
conv_id	user_src	user_des	time	content

8.	聊天状态表 conv_status
正在聊天：用于标识我的消息中应该有哪些人
用户A
（ID较小）	用户B
（ID较大）	正在聊天
int NN	int NN	bool NN
user_lower	user_upper	chating

二、需求分析
1)	用户登陆进小程序时在user表中查找OpenID，如果有此用户，则返回其用户id，否则插入user表，然后返回其用户id
2)	点击搜索商品时从goods表中选出相应项目
3)	点击二手交易或线下辅导时，先从good_type表中选出对应的type_id，然后选出goods表中type_id在这些type_id中的项目（限制项目总数）
4)	在发布页面点击发布时，将表单信息发送给Servlet，并且打包成一个good (Java Model Class），然后插入goods表
5)	点击我卖出的：从goods表中选出用户id为这个用户，并且sold=true的记录返回
6)	点击我买到的：从order表中选取good_id，然后用这（些）id到goods表中查询sold = true的商品并返回
7)	购物车：在shoppingcart表中通过user_id查询good_id，再用good_id到goods表中查询商品并返回
8)	我的消息：到message表中查询user_des为这个用户的消息，返回content 和 time
9)	我的发布：到goods表中选出用户id为这个用户，并且sold=false的记录返回

三、	分类及细化（每个大项对应一个DAO，每个条目对应一个函数）
1.	user表
a)	boolean selectByOpenId(String open_id)
b)	boolean insert(User user)（传入一个Java Model Class）
2.	goods表
a)	ArrayList<Goods> selectByGoodName(String good_name)
b)	ArrayList<Goods> selectByTypeId(int type_id)
c)	ArrayList<Goods> selectByUserId(int user_id)
d)	goods selectByGoodId(int good_id)
e)	boolean insert(Goods goods)
3.	goods_type表
a)	HashSet<int> selectByMainClass(String main_class)
4.	order表
a)	ArrayList<Integer> selectByUserId(int user_id)
b)	boolean insert(Order order)
5.	shoppingcart表
a)	ArrayList<Integer> selectGoodIdByUserId(int user_id)
b)	boolean insert (Shoppingcart shoppingcart)
6.	message表
a)	ArrayList<String> selectByUserDes(int user_des)
b)	boolean insert(Message message)


四、	Servlet一览
说明：所有Servlet以xxxServlet结尾，并且以Get开头的Servlet说明是服务器从客户端获取数据，以Give开头的说明是服务器给客户端数据，有少许不方便以Get或Give命名的见详细说明

1.	AddToShoppingcartServlet
a)	说明：将指定商品添加至某人的购物车，即插入一条记录到Shoppingcart表
b)	参数：user_id, good_id
c)	返回：成功时返回succes，失败时返回error并且HTTP状态码为303
d)	serialVersionUID：1（以下简称UID）

2.	BuyGoodServlet
a)	说明：立即购买按钮的Servlet, 用于修改 goods.sold 为true，并插入一条记录到 order表中
b)	参数： user_id, good_ids(JSON数组)
c)	返回：成功时返回 success ，否则返回error，并且状态码为303
d)	UID: 2

3.	DeleteGoodServlet
a)	说明：将指定商品从Goods表中删除（危险操作）
b)	参数: good_id
c)	返回: 成功时返回 success ，否则返回error，并且状态码为303
d)	UID: 4

4.	DeleteShoppingcartServlet
a)	说明：删除某人的购物车的某些商品的Servlet
b)	参数：user_id, good_ids（JSON数组）
c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
d)	UID：5

5.	GetJsCodeServlet
a)	说明：通过wx.login()提供的js_code向腾讯的服务器发送GET请求以获取OpenID，然后传回user_id，如果没在user表中找到此用户则插入后再传回其user_id
b)	参数: avatar_url, nick_name, js_code （用户头像，用户昵称以及腾讯API提供的js_code字符串）
c)	返回: 成功时返回user_id, 失败时什么都不返回
d)	UID：101

6.	GetMessageServlet
a)	说明：向某个商品提交留言时使用的Servlet
b)	参数：good_id(给哪个商品的留言), user_src(发消息的user_id), user_des(接受消息的user_id), content（消息内容）
c)	返回：成功时返回 success ，否则返回error，并且状态码为303
d)	UID：102

7.	GetSubmittedGoodServlet
a)	说明：获取小程序传过来的转成JSON字符串的good对象,并插入到good表（不包含图片）
b)	参数：JSON字符串good，包含goodsName，userId, describe, price, is_new, tradeType, goodsClass, goodsType, img（数组）, sold, counseling_time_map(JSONString)
c)	返回：成功时返回 success ，否则返回error，并且状态码为303
d)	UID：103

8.	GetSubmittedImageServlet
a)	说明：获取wx.uploadFile()上传的图片，并将数据库中此商品的image_url修改为当前url
b)	参数：image
c)	返回：成功时返回图片路径，失败时什么都不返回
d)	UID：104

9.	GetUserInfoServlet
a)	说明：更新用户信息的Servlet
b)	参数：JSON字符串user，包含 userId, user_name, sex(只可能取1, 0 , -1，分别表示男，未知，女)，weixin_no, tel, mail, address
c)	返回：成功时返回 success ，否则返回error，并且状态码为303
d)	UID：105

10.	GiveMessageServlet
a)	说明：获取某用户收到的留言
b)	参数：user_id
c)	返回：成功时返回JSON数组 message，失败时返回空数组
d)	UID：201

11.	GiveOfflineCourseGoodsServlet
a)	说明：获取线下辅导栏目的商品信息
b)	参数：无
c)	返回：成功时返回JSON数组goods，失败时返回空数组
d)	UID：202


12.	GiveSearchInfoServlet
a)	说明：接受搜索关键字并返回商品数组
b)	参数：字符串 keyword
c)	返回：成功时返回JSON数组goods，失败时返回空数组
d)	UID：203

13.	GiveSecondHandGoodServlet
a)	说明：返回二手交易栏目的商品信息
b)	参数：无
c)	返回：成功时返回JSON数组goods，失败时返回空数组
d)	UID：204

14.	GiveShoppingcartServlet
a)	说明：返回某人的购物车信息
b)	参数：user_id
c)	返回：成功时返回JSON数组goods，失败时返回空数组
d)	UID：205

15.	GiveUserInfoServlet
a)	说明：返回某人的详细信息，如微信号，昵称，头像等。
b)	参数：user_id
c)	返回：成功时返回JSON对象user，失败时返回空对象
d)	UID：206

16.	GiveWhatIBoughtServlet
a)	说明：从order表中选取good_id，然后用这（些）id到goods表中查询商品并返回
b)	参数：user_id
c)	返回：成功时返回JSON数组goods，失败时返回空数组
d)	UID：207

17.	GiveWhatISoldServlet
a)	说明：给出某人发布的，且sold为true的商品列表
b)	参数：user_id
c)	返回：成功时返回JSON数组goods，失败时返回空数组
d)	UID：208

18.	GiveWhatISubmitServlet
a)	说明：给出某人发布的，且sold为false的商品列表
b)	参数：user_id
c)	返回：成功时返回JSON数组goods，失败时返回空数组
d)	UID：209

19.	GiveGoodMessageServlet
a)	说明：给出商品下的留言信息，即数据库中message.good_id为此id的留言信息
b)	参数：good_id
c)	返回：成功时返回JSON数组message（附带发信人的用户名和头像），失败时返回空数组
d)	UID：210

20.	GiveGoodsServlet
a)	说明：获取某些商品的信息
b)	参数：good_ids（JSON数组）
c)	返回：成功时返回goods（JSON数组），失败时返回空数组
d)	UID：211


21.	GivePushedGoodServlet
a)	说明：获取推送给用户的商品信息 
b)	参数：user_id
c)	返回：成功时返回goods（JSON数组），失败时返回空数组
d)	UID：212

22.	UpdateGoodServlet
a)	说明：更新商品信息的Servlet
b)	参数：JSON对象 good，包含goodsId, goodsName, userId, describe, price, is_new, tradeType, goodsClass, goodsType, img, sold, counseling_time_map(JSONString)
c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
d)	UID：4

23.	ConfirmGoodServlet
a)	说明：点击确认收货时的Servlet（可以多个商品一起确认）
b)	参数：user_id, good_ids（JSON数组）
c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
d)	UID：5

24.	UpdateCreditPointServlet
a)	说明：更新用户信誉值的Servlet
b)	参数：user_id, incre（信誉值的增量，可为负数）
c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
d)	UID: 6
