<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
    <head>
    <%@include file="/WEB-INF/views/includes/header.jsp"%>
    <meta charset="UTF-8">
    <title>Document</title>
    <link rel="stylesheet" href="/assets/css/reset.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css"/>
    <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
    <link rel="stylesheet" href="/assets/css/index.css">
    <script src="/assets/js/index.js"></script>
</head>
<body>
    <main>
        <section class="main_banner_area">
            <div class="content">
                <!-- Slider main container -->
                <div class="swiper">
                    <!-- Additional required wrapper -->
                    <div class="swiper-wrapper">
                    <!-- Slides -->
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (1).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (1).png" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (2).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (3).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (4).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (5).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (6).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (7).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (8).jpg" alt="">
                    </div>
                    <div class="swiper-slide">
                        <img src="/assets/images/banner/banner img (9).jpg" alt="">
                    </div>
                    </div>
                    <!-- If we need pagination -->
                    <div class="swiper-pagination"></div>
                    <!-- If we need navigation buttons -->
                    <!-- <div class="swiper-button-prev"></div>
                    <div class="swiper-button-next"></div> -->
                        <div class="slider-btns">
                            <div class="slide-prev">
                                <i class="fas fa-chevron-left"></i>
                            </div>
                            <div class="slide-next">
                                <i class="fas fa-chevron-right"></i>
                            </div>
                        </div>
                    <!-- If we need scrollbar -->
                </div>
            </div>
        </section>
        <section class="s1 item_list">
            <h1>추천 제품</h1>
            <div class="list_contents">
                <c:forEach items="${recommend_list}" var="item">
                    <div class="list_item">
                        <div class="thumb" style="background-image:url(http://localhost:8756/image/product/${item.thumbnail})">
                        </div>
                        <a class="prod_name" href="/product/detail?index=${item.pi_seq}">
                            <span class="item_brand">[${item.mfi_name}]</span>
                            <span class="item_name">${item.pi_name}</span>
                        </a>
                        <div class="price_wrap">
                            <c:if test="${item.pi_discount_rate != 0}">
                                <div class="discount_rate_area">
                                    <p class="discount_rate">
                                        <fmt:formatNumber value="${item.pi_discount_rate}" pattern="###,###,###.#"/>%
                                    </p>
                                </div>
                            </c:if>
                            <div class="price_area">
                                <p class="price">
                                    <fmt:formatNumber value="${item.discounted_price}" pattern="###,###,###"/>원
                                </p>
                                <c:if test="${item.pi_discount_rate != 0}">
                                    <p class="origin_price">
                                        <fmt:formatNumber value="${item.pi_price}" pattern="###,###,###"/>원
                                    </p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
        <c:forEach items="${prod_map_list}" var="map">
            <section class="item_list">
                <h1>${map.title}</h1>
                <div class="list_contents">
                    <c:forEach items="${map.list}" var="item">
                        <div class="list_item">
                            <div class="thumb" style="background-image:url(http://localhost:8756/image/product/${item.thumbnail})">
                            </div>
                            <a class="prod_name" href="/product/detail?index=${item.pi_seq}">
                                <span class="item_brand">[${item.mfi_name}]</span>
                                <span class="item_name">${item.pi_name}</span>
                            </a>
                            <div class="price_wrap">
                                <c:if test="${item.pi_discount_rate != 0}">
                                    <div class="discount_rate_area">
                                        <p class="discount_rate">
                                            <fmt:formatNumber value="${item.pi_discount_rate}" pattern="###,###,###.#"/>%
                                        </p>
                                    </div>
                                </c:if>
                                <div class="price_area">
                                    <p class="price">
                                        <fmt:formatNumber value="${item.discounted_price}" pattern="###,###,###"/>원
                                    </p>
                                    <c:if test="${item.pi_discount_rate != 0}">
                                        <p class="origin_price">
                                            <fmt:formatNumber value="${item.pi_price}" pattern="###,###,###"/>원
                                        </p>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:forEach>

    </main>
    <%@include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>