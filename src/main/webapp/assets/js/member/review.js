$(function(){
    $("#save").click(function(){
        let score =$(".score:checked").val();
        let review_text =$("#review").val();
        
        let data={
            ri_content:review_text,
            ri_pi_seq: prod_seq,
            ri_oi_seq:order_seq,
            ri_score:score
        }
        $.ajax({
            url:"/member/review",
            type:"put",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(msg){
                alert(msg);
                location.href="/member/order_list"
            }
        })
    })
})