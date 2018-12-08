# WeixinAppServer
</br>采用Servlet方式的微信小程序服务器端实现
</br>
</br>一、	数据库
</br>主键用下划线加粗体表示，外键用下划线表示
</br>添加了user表中的主键weixin_id， 添加了头像URL，每次登陆时需发送头像URL
</br>sex = 1表示为男性，2表示为女性，0表示未知
</br>1.	用户表user
</br>用户ID	用户名	性别	OpenID	微信号	电话	邮箱	地址	头像	信誉值
</br>int AI	varchar	int	varchar	varchar	varchar	varchar	varchar	varchar	int NN
</br>user_id	user_name	sex	open_id	weixin_no	tel	mail	address	avatar_url	credit_point
</br>
</br>
</br>2.	商品表goods
</br>交易类型只能为 one_price | auction
</br>p.s. counseling_time_map存储的是小程序中产生的预约时间JSON字符串
</br>商品ID	用户ID	商品名	商品
</br>细节	商品价格	全新	交易
</br>类型	商品
</br>分类	图片URL	商品状态	预约
</br>时间
</br>int AI	int NN	varchar NN	varchar	double NN	bool	varchar NN	int
</br>NN	varchar
</br>NN	bool
</br>NN	varchar
</br>(10000)
</br>good_id	user_id	good_
</br>name	good_
</br>detail	price	is_
</br>new	trade_type	type_
</br>id	image_
</br>url	sold	coun
</br>seling_
</br>time_
</br>map
</br>	
</br>
</br>
</br>3.	商品类型 good_type
</br>
</br>类型id	类型名	商品大类
</br>int AI	varchar NN	varchar NN
</br>type_id	type_name	main_class
</br>1	书籍	二手交易
</br>2	生活用品	二手交易
</br>3	其他	二手交易
</br>4	数学	线下辅导
</br>5	计算机	线下辅导
</br>6	英语	线下辅导
</br>7	其他	线下辅导
</br>
</br>4.	订单表order
</br>
</br>订单号	用户ID	商品ID	订单状态	交易时间
</br>int AI	int NN	int NN	bool NN	datetime NN
</br>order_id	user_id	good_id	done	tradetime
</br>
</br>5.	购物车shoppingcart
</br>用户ID	商品ID
</br>int 	int
</br>user_id	good_id
</br>
</br>6.	消息表message
</br>商品ID：这条留言是针对哪个商品的
</br>消息ID	商品ID	发送用户	接收用户	时间	消息内容
</br>int AI	int NN	int NN	int NN	datetime NN	varchar
</br>message_id	good_id	user_src	user_des	time	content
</br>
</br>7.	聊天记录表 conversation
</br>会话ID	发送用户	接收用户	时间	消息内容
</br>int AI NN	int NN	int NN	datetime NN	varchar(500)
</br>conv_id	user_src	user_des	time	content
</br>
</br>8.	聊天状态表 conv_status
</br>正在聊天：用于标识我的消息中应该有哪些人
</br>用户A
</br>（ID较小）	用户B
</br>（ID较大）	正在聊天
</br>int NN	int NN	bool NN
</br>user_lower	user_upper	chating
</br>
</br>二、需求分析
</br>1)	用户登陆进小程序时在user表中查找OpenID，如果有此用户，则返回其用户id，否则插入user表，然后返回其用户id
</br>2)	点击搜索商品时从goods表中选出相应项目
</br>3)	点击二手交易或线下辅导时，先从good_type表中选出对应的type_id，然后选出goods表中type_id在这些type_id中的项目（限制项目总数）
</br>4)	在发布页面点击发布时，将表单信息发送给Servlet，并且打包成一个good (Java Model Class），然后插入goods表
</br>5)	点击我卖出的：从goods表中选出用户id为这个用户，并且sold=true的记录返回
</br>6)	点击我买到的：从order表中选取good_id，然后用这（些）id到goods表中查询sold = true的商品并返回
</br>7)	购物车：在shoppingcart表中通过user_id查询good_id，再用good_id到goods表中查询商品并返回
</br>8)	我的消息：到message表中查询user_des为这个用户的消息，返回content 和 time
</br>9)	我的发布：到goods表中选出用户id为这个用户，并且sold=false的记录返回
</br>
</br>三、	分类及细化（每个大项对应一个DAO，每个条目对应一个函数）
</br>1.	user表
</br>a)	boolean selectByOpenId(String open_id)
</br>b)	boolean insert(User user)（传入一个Java Model Class）
</br>2.	goods表
</br>a)	ArrayList<Goods> selectByGoodName(String good_name)
</br>b)	ArrayList<Goods> selectByTypeId(int type_id)
</br>c)	ArrayList<Goods> selectByUserId(int user_id)
</br>d)	goods selectByGoodId(int good_id)
</br>e)	boolean insert(Goods goods)
</br>3.	goods_type表
</br>a)	HashSet<int> selectByMainClass(String main_class)
</br>4.	order表
</br>a)	ArrayList<Integer> selectByUserId(int user_id)
</br>b)	boolean insert(Order order)
</br>5.	shoppingcart表
</br>a)	ArrayList<Integer> selectGoodIdByUserId(int user_id)
</br>b)	boolean insert (Shoppingcart shoppingcart)
</br>6.	message表
</br>a)	ArrayList<String> selectByUserDes(int user_des)
</br>b)	boolean insert(Message message)
</br>
</br>
</br>四、	Servlet一览
</br>说明：所有Servlet以xxxServlet结尾，并且以Get开头的Servlet说明是服务器从客户端获取数据，以Give开头的说明是服务器给客户端数据，有少许不方便以Get或Give命名的见详细说明
</br>
</br>1.	AddToShoppingcartServlet
</br>a)	说明：将指定商品添加至某人的购物车，即插入一条记录到Shoppingcart表
</br>b)	参数：user_id, good_id
</br>c)	返回：成功时返回succes，失败时返回error并且HTTP状态码为303
</br>d)	serialVersionUID：1（以下简称UID）
</br>
</br>2.	BuyGoodServlet
</br>a)	说明：立即购买按钮的Servlet, 用于修改 goods.sold 为true，并插入一条记录到 order表中
</br>b)	参数： user_id, good_ids(JSON数组)
</br>c)	返回：成功时返回 success ，否则返回error，并且状态码为303
</br>d)	UID: 2
</br>
</br>3.	DeleteGoodServlet
</br>a)	说明：将指定商品从Goods表中删除（危险操作）
</br>b)	参数: good_id
</br>c)	返回: 成功时返回 success ，否则返回error，并且状态码为303
</br>d)	UID: 4
</br>
</br>4.	DeleteShoppingcartServlet
</br>a)	说明：删除某人的购物车的某些商品的Servlet
</br>b)	参数：user_id, good_ids（JSON数组）
</br>c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
</br>d)	UID：5
</br>
</br>5.	GetJsCodeServlet
</br>a)	说明：通过wx.login()提供的js_code向腾讯的服务器发送GET请求以获取OpenID，然后传回user_id，如果没在user表中找到此用户则插入后再传回其user_id
</br>b)	参数: avatar_url, nick_name, js_code （用户头像，用户昵称以及腾讯API提供的js_code字符串）
</br>c)	返回: 成功时返回user_id, 失败时什么都不返回
</br>d)	UID：101
</br>
</br>6.	GetMessageServlet
</br>a)	说明：向某个商品提交留言时使用的Servlet
</br>b)	参数：good_id(给哪个商品的留言), user_src(发消息的user_id), user_des(接受消息的user_id), content（消息内容）
</br>c)	返回：成功时返回 success ，否则返回error，并且状态码为303
</br>d)	UID：102
</br>
</br>7.	GetSubmittedGoodServlet
</br>a)	说明：获取小程序传过来的转成JSON字符串的good对象,并插入到good表（不包含图片）
</br>b)	参数：JSON字符串good，包含goodsName，userId, describe, price, is_new, tradeType, goodsClass, goodsType, img（数组）, sold, counseling_time_map(JSONString)
</br>c)	返回：成功时返回 success ，否则返回error，并且状态码为303
</br>d)	UID：103
</br>
</br>8.	GetSubmittedImageServlet
</br>a)	说明：获取wx.uploadFile()上传的图片，并将数据库中此商品的image_url修改为当前url
</br>b)	参数：image
</br>c)	返回：成功时返回图片路径，失败时什么都不返回
</br>d)	UID：104
</br>
</br>9.	GetUserInfoServlet
</br>a)	说明：更新用户信息的Servlet
</br>b)	参数：JSON字符串user，包含 userId, user_name, sex(只可能取1, 0 , -1，分别表示男，未知，女)，weixin_no, tel, mail, address
</br>c)	返回：成功时返回 success ，否则返回error，并且状态码为303
</br>d)	UID：105
</br>
</br>10.	GiveMessageServlet
</br>a)	说明：获取某用户收到的留言
</br>b)	参数：user_id
</br>c)	返回：成功时返回JSON数组 message，失败时返回空数组
</br>d)	UID：201
</br>
</br>11.	GiveOfflineCourseGoodsServlet
</br>a)	说明：获取线下辅导栏目的商品信息
</br>b)	参数：无
</br>c)	返回：成功时返回JSON数组goods，失败时返回空数组
</br>d)	UID：202
</br>
</br>
</br>12.	GiveSearchInfoServlet
</br>a)	说明：接受搜索关键字并返回商品数组
</br>b)	参数：字符串 keyword
</br>c)	返回：成功时返回JSON数组goods，失败时返回空数组
</br>d)	UID：203
</br>
</br>13.	GiveSecondHandGoodServlet
</br>a)	说明：返回二手交易栏目的商品信息
</br>b)	参数：无
</br>c)	返回：成功时返回JSON数组goods，失败时返回空数组
</br>d)	UID：204
</br>
</br>14.	GiveShoppingcartServlet
</br>a)	说明：返回某人的购物车信息
</br>b)	参数：user_id
</br>c)	返回：成功时返回JSON数组goods，失败时返回空数组
</br>d)	UID：205
</br>
</br>15.	GiveUserInfoServlet
</br>a)	说明：返回某人的详细信息，如微信号，昵称，头像等。
</br>b)	参数：user_id
</br>c)	返回：成功时返回JSON对象user，失败时返回空对象
</br>d)	UID：206
</br>
</br>16.	GiveWhatIBoughtServlet
</br>a)	说明：从order表中选取good_id，然后用这（些）id到goods表中查询商品并返回
</br>b)	参数：user_id
</br>c)	返回：成功时返回JSON数组goods，失败时返回空数组
</br>d)	UID：207
</br>
</br>17.	GiveWhatISoldServlet
</br>a)	说明：给出某人发布的，且sold为true的商品列表
</br>b)	参数：user_id
</br>c)	返回：成功时返回JSON数组goods，失败时返回空数组
</br>d)	UID：208
</br>
</br>18.	GiveWhatISubmitServlet
</br>a)	说明：给出某人发布的，且sold为false的商品列表
</br>b)	参数：user_id
</br>c)	返回：成功时返回JSON数组goods，失败时返回空数组
</br>d)	UID：209
</br>
</br>19.	GiveGoodMessageServlet
</br>a)	说明：给出商品下的留言信息，即数据库中message.good_id为此id的留言信息
</br>b)	参数：good_id
</br>c)	返回：成功时返回JSON数组message（附带发信人的用户名和头像），失败时返回空数组
</br>d)	UID：210
</br>
</br>20.	GiveGoodsServlet
</br>a)	说明：获取某些商品的信息
</br>b)	参数：good_ids（JSON数组）
</br>c)	返回：成功时返回goods（JSON数组），失败时返回空数组
</br>d)	UID：211
</br>
</br>
</br>21.	GivePushedGoodServlet
</br>a)	说明：获取推送给用户的商品信息 
</br>b)	参数：user_id
</br>c)	返回：成功时返回goods（JSON数组），失败时返回空数组
</br>d)	UID：212
</br>
</br>22.	UpdateGoodServlet
</br>a)	说明：更新商品信息的Servlet
</br>b)	参数：JSON对象 good，包含goodsId, goodsName, userId, describe, price, is_new, tradeType, goodsClass, goodsType, img, sold, counseling_time_map(JSONString)
</br>c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
</br>d)	UID：4
</br>
</br>23.	ConfirmGoodServlet
</br>a)	说明：点击确认收货时的Servlet（可以多个商品一起确认）
</br>b)	参数：user_id, good_ids（JSON数组）
</br>c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
</br>d)	UID：5
</br>
</br>24.	UpdateCreditPointServlet
</br>a)	说明：更新用户信誉值的Servlet
</br>b)	参数：user_id, incre（信誉值的增量，可为负数）
</br>c)	返回：成功时返回success，失败时返回error并将HTTP状态码设为303
</br>d)	UID: 6
</br>