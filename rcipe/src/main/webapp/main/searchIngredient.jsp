<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">

.form-login {
	background-color: #DDDDDD;
	padding-top: 10px;
	padding-bottom: 20px;
	padding-left: 20px;
	padding-right: 20px;
	border-radius: 15px;
	border-color: #d2d2d2;
	border-width: 5px;
	box-shadow: 0 1px 0 #cfcfcf;
}
</style>
<div class="modal fade" id="searchIngredientInMenuInMenu" tabindex="-1"
	role="dialog" aria-labelledby="searchIngredientInMenuInMenu" aria-hidden="false">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header" style="border-color: white" align="right">
				<button type="button" class="close" data-dismiss="modal"
					id="searchIngredientClose" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title" align="center">재료상세검색</h3>
			</div>
			<div class="modal-body">
				<div class="row" style="margin-top: 2%;">
					<div class="col-md-12">
						<form id="searchIngredientInMenuForm" action="#">
							<span id="imaginary_container"> <span
								class="input-group stylish-input-group"> <input
									type="text" class="form-control" placeholder="Search"
									id="searchIngredientInMenuKeyword"> <span
									class="input-group-addon">
										<button type="submit" id="searchIngreButton">
											<span class="glyphicon glyphicon-search"></span>
										</button>
								</span>
							</span>
							</span>
						</form>
					</div>
				</div>
				<div class="row" style="border-color: white;margin-top:1%">
					<div  align="left" class="col-md-6">
						<label for="title"
							style="color: red; font-size: medium; margin: 1%">*검색할
							재료들을 추가하세요.</label>
					</div>
					<div align="right"  class="col-md-6">
						<button type="button" class="btn  btn-primary btn-lg "
							id="settingsearchIngredientInMenu" style="color: white; margin-top: 1%">해당 재료포함 검색</button>
					</div>
				</div>
				<div class="row" style="margin-top: 2%;">
					<div class="col-md-6" >
						<div class="form-login" style="background-color:#5bc0de;color:white;text-align:center;">
							<h4 align="center" style="font: bolder;margin-bottom:1%">음식재료 선택</h4>
						</div>
						<!-- <iframe height="600px" width="100%" style="border: none;" > -->
						<div class="row" style="margin-top: 2%;" id="searchIngre">
							<!-- 음식재료을 보여주는 부분 -->
						</div>
						<!-- </iframe> -->
					</div>
					<div class="col-md-6" >
						<div class="form-login" style="background-color:#d9534f;color:white;text-align:center;">
							<h4 align="center" style="font: bolder;;margin-bottom:1%">선택된 음식재료</h4>
						</div>
						<div class="row" style="margin-top: 2%;">
							<!-- <iframe height="600px" width="100%" style="border: none;"> -->
							<div id="ingredSearchIentAfter"></div>
							<!--선택된 음식을 보여주는 부분 -->
							<!-- </iframe> -->
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>
<script>
	$(document)
			.ready(
					function() {
						var ingredientCount = 0;
						var ingredientNumber = 0;
						//해당 ingredient의 PK를 집어놓고 search할경우 이곳에 값이 들어있을 경우 버튼을 disable로 출력한다.
						var ingredientIds = "/";
						;
						//해당 index
						var ingredientValues;
						//여기에 대한 로직을 구해야함 추가한 IngredientNumber에 대한 disable을 적용시켜야함
						$(document.body)
								.on(
										"submit",
										'#searchIngredientInMenuForm',
										function(event) {
											event.preventDefault();
											var str = $(
													"#searchIngredientInMenuKeyword")
													.val();
											chk1 = /[가-힝]/; //적어도 한개의 a-z 확인
											chk2 = /[a-z]{2}/i; //적어도 한개의 0-9 확인
											if (str == "") {
												alert("검색 키워드가 없습니다.");
												return;
											} else if (!chk1.test(str)
													&& !chk2.test(str)) {
												alert("검색 키워드가 올바르게 입력되지않았습니다.");
												return;
											}
											$("#searchIngre").empty();
											$
													.ajax({
														url : '../recipe/getIngredientList',
														type : 'POST',
														dataType : 'json',
														data : "keyword=" + str,
														success : function(data) {
															var list = data.list;
															var tag = "";
															for ( var i in list) {
																tag += "<div class='row' style='margin-top: 1%' align='right'>"
																		+ "<h4 align='center' class='control-label col-sm-5' id='name_"
																		+ list[i].ingredientNo
																		+ "' style='margin-right:1%'>"
																		+ list[i].ingredientName
																		+ "</h4>";
																if (ingredientIds
																		.indexOf("/"
																				+ list[i].ingredientNo
																				+ "/") !== -1) {
																	tag += "<button class='vcontrol-label col-sm-5 btn btn-info addIngredient'' value='"+list[i].ingredientNo+"'"
													+"id='button_"+list[i].ingredientNo+"' disabled='disabled'>추가</button></div>";
																} else {
																	tag += "<button class='vcontrol-label col-sm-5 btn btn-info addIngredient' value='"+list[i].ingredientNo+"'"
													+"id='button_"+list[i].ingredientNo+"'>추가</button></div>";
																}
															}
															if (tag == "") {
																alert("검색어를 포함하는 재료는 없습니다.");
															}
															$("#searchIngre")
																	.html(tag)
														},
														error : function(data) {
															alert("해당 검색결과가 존재하지 않습니다.");
														}
													});
										});
						$(document.body).on(
								'click',
								'.deleteIngredient',
								function() {
									var number = $(this).val();
									$("#button_" + number).removeAttr(
											"disabled", false);
									$("#ingredient_" + number).remove();
									ingredientIds = ingredientIds.split("/"
											+ number + "/")[0]
											+ "/"
											+ ingredientIds.split("/" + number
													+ "/")[1];
									ingredientCount--;
								});

						$(document.body)
								.on(
										'click',
										'.addIngredient',
										function() {
											var id = $(this).val();
											var name = $("#name_" + id).html();
											$(this).attr("disabled", true);
											ingredientNumber++;
											ingredientCount++;
											ingredientIds += id + "/";
											$("#ingredSearchIentAfter")
													.after(
															"<div class='row'  id='ingredient_"+id+"'>"
																	+ "<div class='form-group' style='margin-top: 1%'>"
																	+ "<input type='hidden' id='ingredient_"+ingredientNumber+"' value=''>"
																	+ "<h4 align='center' class='control-label col-sm-5' id='ingredientName_"+ingredientNumber+"'"
																	+"style='font: bolder;'>"
																	+ name
																	+ "</h4>"
																	+ "<button "
																	+"class='vcontrol-label col-sm-5 btn btn-danger deleteIngredient'"
																	+"value='"+id+"'>삭제</button>"
																	+ "</div>"
																	+ "</div>");
										});
						$(document.body)
								.on(
										'click',
										'#settingsearchIngredientInMenu',
										function() {
											ingredientValues = "";
											for (var i = 1; i <= ingredientNumber; i++) {
												var str = $(
														"#ingredientName_" + i)
														.html();
												if (str != undefined) {
													ingredientValues += $(
															"#ingredientName_"
																	+ i).html()
															+ ",";
												}

											}
											ingredientValues=ingredientValues.trim();
											ingredientValues=ingredientValues.substring(0,ingredientValues.length-1);
											$("#searchIngredientClose").click();
											$("#searchKeyword").val(ingredientValues);
											$("#searchForm").submit();
										});
						$("#searchIngredientInMenuForm").submit(function(event) {
							event.preventDefault();
							$("#searchIngreButton").click();
						})
					});
</script>
