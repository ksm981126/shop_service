$(function(){
    getReviewList(0,$("#review_table tbody"));

    $("#count_up, #count_down").click(function(){
        let count =$(".count_box").html();
        if($(this).attr("id") == "count_up"){
            count++;
            if(count > product_stock) count = product_stock;
        }
        else{
            count--;
            if(count <=0) count =1;
        }

        $(".count_box").html(count);
        let total_price =Math.floor(product_price * count);
        let total_point =Math.floor(product_point * count);
        let regex =/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g;
        let formatted_price= total_price.toString().replace(regex, ",");
        let formatted_point= total_point.toString().replace(regex, ",");

        $(".total_price span").html(formatted_price);
        $(".total_save span:last-child").html(formatted_point);
    })
    $("#shopping_bag").click(function(){
        if(member_seq == null || member_seq == ""){
            location.href="/member/login";
            return;
        }
        // if(confirm(""))
        // alert(member_seq+","+prod_seq+","+$(".count_box").html());
        let data = {
            scd_mi_seq:member_seq,
            scd_pi_seq:prod_seq,
            scd_count:$(".count_box").html()
        }
        $.ajax({
            url:"/api/product/cart",
            type:"put",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(r){
                alert(r.message);
                if(confirm(r.message+"\n장바구니로 이동하시겠습니까?")){
                    location.href="/member/cart";
                }
                // location.reload();
                $.ajax({
                    url:"/api/product/cart/count?member_seq="+member_seq,
                    type:"get",
                    success:function(cnt){
                        $(".cart_badge").html(cnt);
                        $(".cart_badge").css("display","inline-block");
                    }
                })
            }
        })
    })
})
let current_review_page=0;
function getReviewList(offset,target){
    $.ajax({
        url:"/api/product/review?pi_seq="+prod_seq+"&offset="+offset,
        type:"get",
        success:function(r){
            target.append("");
            for(let i=0; i<r.list.length; i++){
                if(r.list[i].ri_status == 1){r.list[i].ri_content = "<b class='blind'>관리자에 의해 블라인드 처리된 후기입니다.</b>";}
                if(r.list[i].ri_status == 2){r.list[i].ri_content = "<b class='report'>사용자 신고 접수로 인해 블라인드 처리 되었습니다.</b>";}

                let report_btn = ""
                if(r.list[i].report_able == true || r.list[i].report_able == null){
                    report_btn ='<button class="report" data-seq="'+r.list[i].ri_seq+'"><i class="fas fa-exclamation-circle"></i></button>'
                }
                else {
                    report_btn= '<button class="report" disabled><i class="fas fa-exclamation-circle"></i></button>';
                }
                let tag =
                '<tr>'+
                    '<td>'+r.list[i].ri_seq+'</td>'+
                    '<td>'+r.list[i].ri_content+'</td>'+
                    '<td>'+
                        '<div class="item_score">'+
                            '<div class="score_box">'+
                                '<div class="score_box_bar" style="width:'+(r.list[i].ri_score/5*100)+'%"></div>'+
                                '<img src="/assets/images/star_bg.png">'+
                            '</div>'+
                        '</div>'+
                    '</td>'+
                    '<td>'+nameMasking(r.list[i].mi_name)+'</td>'+
                    '<td>'+makeDate(r.list[i].ri_reg_dt)+'</td>'+
                    '<td>'+makeDate(r.list[i].ri_mod_dt)+'</td>'+
                    '<td>'+report_btn+'</td>'+
                '</tr>';
                target.append(tag);
            }
            $(".report").click(function(){
                if(!confirm("상품리뷰를 신고하시겠습니까?"))return;
                $.ajax({
                    url:"/api/product/review/report?ri_seq="+$(this).attr("data-seq"),
                    type:"patch",
                    success:function(r){
                        alert(r.msg);
                        if(r.status)
                        location.reload();
                    }
                })
            })
            $(".review_pager_area").html("");
            let page =Math.floor(r.count / 10)+ (r.count % 10>0? 1:0);
            for(let i=0; i<page; i++){
                let tag;
                if(current_review_page -1 ==i){
                    tag = '<button class="pager current" data-offset="'+(i*10)+'">'+(i+1)+'</button>';
                }
                else{
                    tag = '<button class="pager" data-offset="'+(i*10)+'">'+(i+1)+'</button>';
                }
                $(".review_pager_area").append(tag);
            }
            $(".pager").click(function(){
                if(current_review_page == $(this).html()) return;
                current_review_page =(this).html();

                let offset = $(this).attr("data-offset");
                getReviewList(offset, $("#review_table tbody"));
            })
        }
    })
}

function makeDate(dt){
    let date =new Date(dt);
    return date.getFullYear()+"."+leadingZero(date.getMonth()+1)+"."+leadingZero(date.getDate());
}
function leadingZero(n){
    return n<10 ? "0"+n : ""+n;
}
function nameMasking(name){
    let len =name.length;
    if(len == 2){
        return name[0]+"*";
    }
    let masked ="";
    for(let i=0; i<len; i++){
        if(i==0 || i==len-1) masked +=name[i];
        else masked += "*";
    }
    return masked;
}

