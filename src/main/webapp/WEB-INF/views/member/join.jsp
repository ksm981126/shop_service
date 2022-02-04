<%@page language="java" contentType="text/html; encoding=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@include file="/WEB-INF/views/includes/header.jsp"%>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="/assets/js/join.js"></script>
</head>
<body>
    <main>
        <div class="join_form">
            <h1>회원가입</h1>
            <p>User Email</p>
            <input type="text" id="mi_email" placeholder="사용자 이메일">
            <p>Password</p>
            <input type="password" id="mi_pwd" placeholder="비밀번호">
            <p>Password Confirm</p>
            <input type="password" id="mi_pwd_confirm" placeholder="비밀번호 확인">
            <p>User Name</p>
            <input type="text" id="mi_name" placeholder="이름">
            <p>Birthday</p>
            <input type="text" id="mi_birth" placeholder="생년월일 (20220204)">
            <p>Gender</p>
            <select id="mi_gen">
                <option value="0">선택안함</option>
                <option value="1">남성</option>
                <option value="2">여성</option>
            </select>
            <p>Cell Phone Number</p>
            <input type="text" id="mi_phone" placeholder="전화번호 (01012345678)">
            <p>Address</p>
            <input type="text" id="mi_address" placeholder="주소">
            <div class="btns">
                <button id="join">회원가입</button>
                <button id="cancel">취소</button>
            </div>
        </div>
    </main>
    <%@include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>