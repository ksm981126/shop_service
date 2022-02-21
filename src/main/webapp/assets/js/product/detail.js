$(function(){
    // alert(product_price+","+product_stock+","+product_point);
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