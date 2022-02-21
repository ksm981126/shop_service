$(function(){
    $(".delete").click(function(){
        if(!confirm("장바구니에서 삭제하시겠습니까?")) return;
        let seq =$(this).attr("data-seq");
        $.ajax({
            url:"/api/product/cart/delete?seq="+seq,
            type:"patch",
            success:function(msg){
                alert(msg);
                location.reload();
            }
        })
    })
    $(".increase, .decrease").click(function(){
        // let count =$(this).parent().parent().find(".count").html();
        let $count_box = $(this).closest(".count").find(".count_box");
        let $price_box = $(this).closest(".list_item").find(".price_area").find(".price");
        let $point_box = $(this).closest(".list_item").find(".price_area").find(".point span:last-child");
        let $origin_box = $(this).closest(".list_item").find(".price_area").find(".origin");

        let count = $count_box.html();
        
        if($(this).attr("id") == "count_up"){
            count++;
            if(count > $(this).attr("data-stock")){
                count = $(this).attr("data-stock");
                alert("최대 구매 가능 수량은" +$(this).attr("data-stock")+"개 입니다.");
            } 
            else{
                t_price += Number($(this).attr("data-price"));
            }
        }
        else{
            count--;
            if(count <=0) count =1;
            else t_price -= Number($(this).attr("data-price"));
        }

        $count_box.html(count);
        let total_price =Math.round($(this).attr("data-price") * count);
        let total_point =Math.round($(this).attr("data-point") * count);
        let total_origin =Math.round($(this).attr("data-origin") * count);
        let final_t_price =Math.round(t_price);
        let final_sum_price =Math.round((t_price+Number($(".total_di_price").attr("data-total-d-price"))));

        let regex =/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g;
        let formatted_price= total_price.toString().replace(regex, ",");
        let formatted_point= total_point.toString().replace(regex, ",");
        let formatted_origin= total_origin.toString().replace(regex, ",");
        let formatted_t_price= final_t_price.toString().replace(regex, ",");
        let formatted_sum_price=final_sum_price.toString().replace(regex, ",");

        $price_box.html(formatted_price+"원");
        $point_box.html(formatted_point+"원");
        $origin_box.html(formatted_origin+"원");
        $(".total_price").html(formatted_t_price+"원");
        $(".sum_price").html(formatted_sum_price+"원");

        $(".increase, .decrease").prop("disabled",true);
        $.ajax({
            url:"/api/product/cart/count?seq="+$(this).attr("data-cart-seq")+"&count="+count,
            type:"patch",
            success:function(r){
                console.log(r);
                $(".increase, .decrease").prop("disabled",false);
            }
        })        
    })
})