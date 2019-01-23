import 'package:dio/dio.dart';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:wananzhuoapp_flutter/model/search_bean.dart';
import 'empty_widget.dart';

class SearchListWidget extends StatefulWidget {
  SearchListWidget({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => new SearchListState();
}

class SearchListState extends State<SearchListWidget> {
  bool isLoadingMore = false;
  List<SearchArticle> list = List();
  int page = 0;
  var _haveMoreData = true;
  var flutterPlugin = const MethodChannel("flutter.plugin");
  var timePlugin = const MethodChannel("flutter.plugin");

  /// 下拉刷新
  Future<Null> _onRefresh() async {
    this.page = 0;
    lodaData("哈哈");
  }

  ScrollController controller;

  _onItemClick(String url) {
    // flutterPlugin.invokeMethod("toast",{"msg":"item"});
    flutterPlugin.invokeMethod("jump", {"url": url});
  }

  @override
  void initState() {
    super.initState();
    controller = new ScrollController();
    controller.addListener(() {
      if (controller.position.pixels == controller.position.maxScrollExtent) {
        if (!isLoadingMore) {
          print("load more");
          this.page++;
          lodaData("安");
          isLoadingMore = true;
        }
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    int count = list.length + (_haveMoreData ? 1 : 0);

    return new Container(
      child: new RefreshIndicator(
          child: ListView.builder(
            scrollDirection: Axis.vertical,
            itemCount: count,
            itemBuilder: (context, index) {
              if (index == list.length) {
                return _buidlLoadMoreWidget();
              } else {
                return _getListItem(index);
              }
            },
            controller: controller,
          ),
          onRefresh: _onRefresh),
    );
  }

  Widget _getListItem(int index) {
    SearchArticle bean = this.list[index];

    ///去掉html中的高亮
    String title = this
        .list[index]
        .title
        .replaceAll(RegExp("(<em[^>]*>)|(</em>)"), "")
        .replaceAll("&mdash;", "-");

    return new GestureDetector(
      onTap: () {
        _onItemClick(bean.link);
      },
      child: new Card(
        child: new Container(
          padding: EdgeInsets.all(10),
          child: new Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              new RichText(
                text: new TextSpan(
                    text: title,
                    style: new TextStyle(
                        color: Colors.black,
                        fontSize: 16,
                        fontWeight: FontWeight.bold)),
              ),
              new Row(
                children: <Widget>[
                  new Text(
                    bean.niceDate,
                    textDirection: TextDirection.ltr,
                  ),
                  new Text(
                    bean.author,
                    textDirection: TextDirection.ltr,
                  ),
                ],
              ),
              new Text("${bean.superChapterName}/${bean.chapterName}",
                  textDirection: TextDirection.ltr)
            ],
          ),
        ),
      ),
    );
  }

  lodaData(String str) {
    _haveMoreData = true;
    Dio dio = new Dio();
    dio
        .post("http://www.wanandroid.com/article/query/$page/json?k=$str")
        .then((response) {
      SearchBean bean = SearchBean.fromJson(response.data);
      print("suc==count==${bean.data.datas.length}");
      if (bean.data.datas.length > 0) {
        setState(() {
          if (page == 0) {
            this.list.clear();
          }
          this.list.addAll(bean.data.datas);
          print("suc====$page");
        });
      } else {
        /// 和原生交互
        flutterPlugin.invokeMethod("toast", {"msg": "没有更多数据了……"});
//        /// 获取native的返回值，future
//        timePlugin.invokeMethod("getTime").then((value) {
//          print("str=======$value");
//        });
        setState(() {
          _haveMoreData = false;
        });
      }

      isLoadingMore = false;
    }).catchError((err) {
      print("err====$err");
      isLoadingMore = false;
    });
  }

  Widget _buidlLoadMoreWidget() {
    return Container(
      padding: const EdgeInsets.all(15),
      child: Center(
        child: Text("加载中……", textDirection: TextDirection.ltr),
      ),
    );
  }
}
