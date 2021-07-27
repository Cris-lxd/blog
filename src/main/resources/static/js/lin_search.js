function Search(objId, inputId, color) {
  /* 方法说明
      此方法依赖于 jquery 开发的，必须先导入 jquery
      下拉框筛选时默认以第一个 option 的值 代表所有，即当选择第一个 option 时，返回所有数据，不进行搜索
 
    *@param{String} objId 需要被搜索内容表格的id或class
    *@param{String} inputId 搜索框的id或class || 下拉框的id或class
    *@param{String} color 搜索内容以什么颜色返回，不传默认为红色
   */



  // 表格搜索
  this.tableSearch = function () {
    $('#content-null').remove(); // 每次进入先移出掉上次搜索产生的tr
    this.objId.find('tr span').css({ // 每次搜索开始，先把所有字体颜色恢复初始状态
      'color': "black",
      'font-weight': 'normal'
    });


    var tableTrTdContent = this.objId.find('tr td:contains("' + this.inpIdContents + '")'); // 获取所有含有搜索内容的td，类似于集合存储       
    if (this.inpIdContents != '') { // 如果搜索内容为空，就不用去更改样式，直接还原所有

      if (tableTrTdContent.length == 0) { // 判断集合长度是否为0，为0则表示搜索的内容在表格里不存在


        this.objId.find('tr:not(:eq(0))').css({ // 先将所有tr隐藏
          display: "none"
        })

        var tableColspanNumber = this.objId.find('tr').eq(0).find('th').length || this.objId.find('tr').eq(0).find('td').length; // 获取表头的列数 
        var tr = $(`
    <tr id="content-null">
    <td colspan='${tableColspanNumber}' style="text-align: center;">暂无你搜索的内容</td>
    </tr>
    `); // 创建搜索不到时，显示的tr
        this.objId.append(tr)
      } else if (tableTrTdContent.length > 0) { // 集合长度不为0，则表示搜索的内容在表格里
        // console.log("我在")
        // console.log(tableTrTdContent)

        $('#content-null').remove();

        this.objId.find('tr:not(:eq(0))').css({ // 先将所有tr隐藏
          display: "none"
        })


        for (var a = 0; a < tableTrTdContent.length; a++) { // 遍历找到的td集合，进行每个渲染颜色
          // console.log(tableTrTdContent[a].parentNode)
          tableTrTdContent[a].parentNode.style.display = "table-row"; // 让含有搜索内容的 tr 进行显示
          var contents = tableTrTdContent.eq(a).text(); // 获取到含有搜索内容的td里的集体内容，即字符串
          var contentsArr = contents.split(this.inpIdContents); // 以搜索框中的内容将td的值进行分割成数组
          var contentArrFirst = contentsArr[0]; // 将数组里的第一个值取出
          for (var j = 1; j < contentsArr.length; j++) { // 将分割出来的内容进行染色后重新组合在一起
            contentArrFirst += `<span style=';color:${this.color};font-weight:bolder'>` + this.inpIdContents + "</span>" + contentsArr[j];
          };
          tableTrTdContent.eq(a).html(contentArrFirst); // 将td里的值从新解析成html
          console.log(tableTrTdContent.length - 1)
        }


      }
    } else {
      this.objId.find('tr:not(:eq(0))').css({
        display: "table-row"
      });

      $('#content-null').remove();
    }


  }

  // ul 搜索
  this.ulSearch = function () {
    $('#content-null').remove(); // 每次进入先移出掉上次搜索产生的tr
    this.objId.find('li span').css({ // 每次搜索开始，先把所有字体颜色恢复初始状态
      'color': "black",
      'font-weight': 'normal'
    });
    var liContent = this.objId.find('li:contains("' + this.inpIdContents + '")'); // 获取所有含有搜索内容的td，类似于集合存储       
    console.log(liContent)
    if (this.inpIdContents != '') { // 如果搜索内容为空，就不用去更改样式，直接还原所有

      if (liContent.length == 0) { // 判断集合长度是否为0，为0则表示搜索的内容在li里不存在


        this.objId.find('li').css({ // 先将所有tr隐藏
          display: "none"
        })

       
        var tr = $(`
  
    <li id="content-null">暂无你搜索的内容</li>
  
    `); // 创建搜索不到时，显示的tr
        this.objId.append(tr)
      } else if (liContent.length > 0) { // 集合长度不为0，则表示搜索的内容在表格里
        // console.log("我在")
        // console.log(liContent)

        $('#content-null').remove();

        this.objId.find('li').css({ // 先将所有tr隐藏
          display: "none"
        })
        console.log(liContent.eq(0).text())

        for (var a = 0; a < liContent.length; a++) { // 遍历找到的li集合，进行每个渲染颜色
          // console.log(liContent[a].parentNode)
          liContent[a].style.display = "block"; // 让含有搜索内容的 li 进行显示
          var contents = liContent.eq(a).text(); // 获取到含有搜索内容的li里的集体内容，即字符串
          var contentsArr = contents.split(this.inpIdContents); // 以搜索框中的内容将td的值进行分割成数组
          var contentArrFirst = contentsArr[0]; // 将数组里的第一个值取出
          for (var j = 1; j < contentsArr.length; j++) { // 将分割出来的内容进行染色后重新组合在一起
            contentArrFirst += `<span style=';color:${this.color};font-weight:bolder'>` + this.inpIdContents + "</span>" + contentsArr[j];
          };
          liContent.eq(a).html(contentArrFirst); // 将td里的值从新解析成html
          console.log(liContent.length - 1)
        }


      }
    } else {
      this.objId.find('li').css({
        display: "block"
      });

      $('#content-null').remove();
    }
  }
  // 初始化，判断需要搜索标签的类型
  this.init = function () {
    this.color = color || '#11a0e2';
    console.log(this.color)
    if (typeof $ == "undefined") { // 判断是否引入 jquery
      throw new Error("该搜索功能依赖于jquery插件，需要引入jquery");
    }


    if (typeof objId[0] == "undefined") { // 判断是通过jquery获取的id还是原生获取的id,需要把原生的转换成jquery
      this.objId = $(objId); // 需要搜索的对象的id       

    } else {
      this.objId = objId; // 需要搜索的对象的id          

    }

    if (typeof inputId[0] == "undefined") { // 判断搜索框获取的方式，转换成jquery获取
      var inp = $(inputId);
    } else {
      var inp = inputId;
    }


    if (inp[0].tagName == "SELECT") { // 如果是以下拉框来筛选,则下拉框第一个选项为显示所有信息，即不搜索
      if (inp.val().trim() == inp.find('option:first').val()) {
        this.inpIdContents = '' // 搜索的内容为空
      } else {
        this.inpIdContents = inp.val().trim() // 获取搜索框里的值,去除首尾空格

      }

    } else {
      this.inpIdContents = inp.val().trim() // 获取搜索框里的值,去除首尾空格

    }
    // console.log(inp[0].tagName)
    // console.log(this.inpIdContents)
    // console.log(typeof inp)
    this.objType = this.objId[0].tagName; // 获取需要被搜索对象的标签类型,将jquey转化为原生js获取标签类型
    // console.log(this.objId.find("tr th").length)
    // if (this.objId.find("tr th").length == 0) {// 判断表格是否有表头,没有就创建错误信息
    //   throw new Error("你要搜索的表格没有表头 <th> 标签，请规范好表格");
    // }

    console.log(this.objType)
    switch (this.objType) { // 判断搜索对象
      case 'TABLE':
        this.tableSearch();
        break; // 对象是表格，进行表格搜索
      case 'UL':
        this.ulSearch();
        break; // 对象是ul，进行ul搜索
    }
  }
  this.init()
}