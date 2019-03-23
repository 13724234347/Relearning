/**
 * 获取url的参数的值,解决了中文乱码
 * 
 * @param name
 * @returns
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return null;
}

/**
 * 分页页码封装
 * 
 * @param pageCount
 *            总页数
 * @param pageNum
 *            当前页
 * @param totalCount           
 *            总条数
 * @param pageSize
 *            每页显示数据条数
 * @param num
 *            需要中间显示的页码个数
 * @param pageContent
 *            分页所放的ul的ID的值
 */

function myFunction(pageCount, pageNum, totalCount, pageSize, num, pageContent){
	$("#"+pageContent).append("<h6>一共"+pageCount+"页</h6>");
	$("#"+pageContent).append("&nbsp;&nbsp;<h6>当前第"+pageNum+"页</h6>");
	$("#"+pageContent).append("&nbsp;&nbsp;<h6>一共"+totalCount+"条记录</h6>");
	$("#"+pageContent).append("&nbsp;&nbsp;<h6>每页<select id='pageBar' style='border-radius:5px;height:30px;' onChange=changePageSize("+totalCount+","+pageNum+") ><option value='2'>2</option><option value='3'>3</option><option value='5'>5</option><option value='10'>10</option></select>条数据</h6>");
	// $("#"+pageContent).append("&nbsp;<font size='5'>跳转</font><input type='number' class='inputClass' id='jump' value='"+pageNum+"' style='width:30px;' maxlength='3' onchange='jumpChange("+pageCount+");'/><font size='5'>页</font>&nbsp;<button type='button' onclick='setInput(-1,"+pageSize+");'>确定</button>");
	$("#pageBar option").each(function(){//进行迭代匹配pageSize，找到下拉框的值将该值设置为默认显示
		if($(this).val() == pageSize){
			$(this).attr("selected","selected");
		}
	});
	
	var nums = pageNum - 1;//当前页数减1等于0表示为首页,不能再上一页,通常情况下是可以一直上一页的
	if(pageNum == 1){
		$("#"+pageContent).append("&nbsp;<button>首页</button>");
		$("#"+pageContent).append("<button>上一页</button>");
	}else{
		$("#"+pageContent).append("&nbsp;<button onclick=setInput(1,"+pageSize+");>首页</button>");
		$("#"+pageContent).append("<button onclick=setInput("+nums+","+pageSize+");>上一页</button>");
	}
	
	if(pageCount < num+4){//如果小于7则全部显示页码
		if(pageCount < num){//如果总页数<页码数，就有多少显示多少个页码
			for (var int = 1; int <= pageCount; int++) {
				$("#"+pageContent).append("<button onclick=setInput("+int+","+pageSize+");>"+int+"</button>");
			}
		}else{//如果不是则满足else条件
			if(pageNum <= 2){//当当前页数 <= 2 时前按钮不再变动（表示前2个按钮变动按钮）
				for (var int2 = 1; int2 <= num; int2++) {
					$("#"+pageContent).append("<button onclick=setInput("+int2+","+pageSize+");>"+int2+"</button>");
				}
			}else if(pageNum > pageCount-2){//当当前页数 > 总页数-2 时前按钮不再变动（表示后2个按钮变动按钮）
				for (var int3 = pageCount-2; int3 <= pageCount; int3++) {
					$("#"+pageContent).append("<button onclick=setInput("+int3+","+pageSize+");>"+int3+"</button>");
				}
			}else{//如果都不满足就表示需要根据不同的页数显示不同的页码数
				for (var int4 = pageNum-1; int4 <= pageNum+1; int4++) {//前两个页码数+当前页码数+加后两个页码数 需要动态
					$("#"+pageContent).append("<button onclick=setInput("+int4+","+pageSize+");>"+int4+"</button>");
				}
			}
		}
	}else{//如果大于等于7则满足条件
		if(pageNum > 2){//当前页数大于2满足条件
			$("#"+pageContent).append("<button onclick=setInput(1,"+pageSize+");>1</button>");
		}
		if(pageNum > 3){//当前页数大于3，也就是最少隔一个页码数
			$("#"+pageContent).append("<button>...</button>");
		}
		if(pageNum == 1){//当前页数等于1
			for (var int5 = 1; int5 <= pageNum+1; int5++) {
				$("#"+pageContent).append("<button onclick=setInput("+int5+","+pageSize+");>"+int5+"</button>");
			}
		}else if(pageNum == pageCount){//等于最大页数
			for (var int5 = pageCount-1; int5 <= pageNum; int5++) {
				$("#"+pageContent).append("<button onclick=setInput("+int5+","+pageSize+");>"+int5+"</button>");
			}
		}else{//如果前面都不满足则满足该条件，前后加一页
			for (var int5 = pageNum-1; int5 <= pageNum+1; int5++) {
				$("#"+pageContent).append("<button onclick=setInput("+int5+","+pageSize+");>"+int5+"</button>");
			}
		}
		if(pageNum <= pageCount-3){//相隔数为3满足条件
			$("#"+pageContent).append("<button><font>...</font></button>");
		}
		if(pageNum <= pageCount-2){//相隔数为2满足
			$("#"+pageContent).append("<button onclick=setInput("+pageCount+","+pageSize+");>"+pageCount+"</button>");
		}
	}
	
	var nums1 = pageNum + 1;
	if(pageNum == pageCount){//当当前页数等于最后一页则不能触发函数
		$("#"+pageContent).append("<button>下一页</button>");
		$("#"+pageContent).append("<button>尾页</button>");
	}else{//否则下一页就为当前页码数加1,尾页为最后一页（pageCount就为最后一页）
		$("#"+pageContent).append("<button onclick=setInput("+nums1+","+pageSize+");>下一页</button>");
		$("#"+pageContent).append("<button onclick=setInput("+pageCount+","+pageSize+");>尾页</button>");
	}
}

/**
 * 将值设置入隐藏框
 * @param pageNum
 * @param pageSize
 */
function setInput(pageNum, pageSize){
	if(pageNum == "-1"){//如果等于-1就表示
		pageNum = $("#jump").val();
	}
	if(pageNum != ""){//如果等于空则不执行
		$("#pageNum").val(pageNum);
		$("#pageSize").val(pageSize);
		tableData();
	}
}

//判断跳转指定页数输入框必须不能为负数，且最大为总页数
function jumpChange(pageCount){
	var jump = $("#jump").val();
	if(jump <= 0){//小于等于0将值设置为1
		$("#jump").val(1);
	}else if(jump > pageCount){//如果大于总页数
		$("#jump").val(pageCount);
	}
}

function changePageSize(totalCount,pageNum){//改变每页多少条数据
	var pageSize = $("#pageBar :selected").val();//获取下拉框已经修改的值
	$("#pageNum").val(1);//每次跳转都是第一页
	$("#pageSize").val(pageSize);//获取值设置下拉框
	tableData();
}