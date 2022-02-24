<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/assets/css/reset.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="/assets/css/header.css">
    <script src="/assets/plugins/jquery-3.4.1.min.js"></script>
    <script>
        var member_seq= "${login_user.mi_seq}"
        // url 원본을 가져오고 /기호를 기준으로 나눠서 배열 생성
        var url_array = window.location.href.split("/");
        //url 재구성을 위한 변수 생성
        var url ="";
        //http, , localhost:8756을 걸러냄
        for(let i=0; i<url_array.length; i++){
            if(i >=3){
                url+= "/"+url_array[i];
            }
        }
        //현재 접속한 페이지가 로그인이 아닐 경우
        if(url_array[url_array.length - 1] != "login" && url_array[url_array.length -1] != "join")
        // 세션에 이전 페이지의 url 정보를 기록한다.
        sessionStorage.setItem("prev_url", url);
        let search_type ='${type}'
    </script>
    <script src="/assets/js/header.js"></script>
</head>
<body>
    <header>
        <div class="header_top_wrap">
            <div class="header_top">
                <div class="user_menu">
                    <c:if test="${login_user == null}">
                        <a href="/member/login">로그인</a>
                        <a href="/member/join">회원가입</a>
                    </c:if>
                    <c:if test="${login_user != null}">
                        <a href="/member/detail?seq=${login_user.mi_seq}"><b>${login_user.mi_name}</b>님</a>
                        <a href="/member/logout" id="logout">로그아웃</a>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="gnb_wrap">
            <nav id="gnb">
                <div class="category_btn">
                    <p><i class="fas fa-bars"></i></p>
                </div>
                <ul class="cate_list">
        
                </ul>
                <div class="sub_area">
                    <div class="sub_1_area">
                    
                    </div>
                    <div class="sub_2_area">
        
                    </div>
                </div>
                <div class="search_area">
                    <a href="/" id="logo">
                        <img src="http://placehold.it/200x70">
                    </a>
                    <div class="search_box">
                        <select id="type">
                            <option value="none">선택</option>
                            <option value="name">제품명</option>
                            <option value="seller">판매자</option>
                            <option value="manufacturer">제조사</option>
                        </select>
                        <input type="text" id="keyword" value="${keyword}">
                        <button id="search_btn">검색</button>
                    </div>
                    <div class="myservice">
                        <a href="/member/order_list">
                            <i class="fas fa-list"></i><br>
                            <span>주문목록</span>
                        </a>
                        <a href="/member/cart" id="shopping_cart">
                            <i class="fas fa-shopping-cart"></i><br>
                            <span>장바구니</span>
                            <span class="cart_badge">0</span>
                        </a>
                    </div>
                </div>
            </nav>
        </div>
    </header>
</body>
</html>