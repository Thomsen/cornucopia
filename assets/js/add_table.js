// Function.prototype.method = function addUserShow(jsonUserData) {
function addUserShow(jsonUserData) {
	// 计算 JavaScript 字符串，并把它作为脚本代码来执行。
	var userObj = eval(jsonUserData);

	// 获取html文档中的某个table
	var tableId = document.getElementById("showUserTable");

	for(i=0; i<userObj.length; i++) {
		var tr = tableId.insertRow(tableId.rows.length);
		var td1 = tr.insertCell(0);
		var td2 = tr.insertCell(1);
		var td3 = tr.insertCell(2);
		var td4 = tr.insertCell(3);

		td1.innerHTML = userObj[i].name;
		td2.innerHTML = userObj[i].sex;
		td3.innerHTML = userObj[i].date;
		td4.innerHTML = userObj[i].contact;
	}
}
