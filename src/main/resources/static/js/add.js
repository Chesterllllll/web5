$(document).ready(function () {
    console.log("123")
    $("#phone")
        .focus(function () {
            console.log('focus')
            let obj = $(this)
            obj.bind("input propertychange", function () {
                const number = obj.val();
                $.ajax({
                    url: "/web5/checkphone?phone=" + number,
                    type: "get",

                    success: function (data) {
                        console.log('success')
                        if (data === "true") {
                            $("#phoneExist")
                                .css('display', 'inline')
                                .css('color', 'orange')
                                .text("号码已存在");
                        }
                        else
                        {
                            $("#phoneExist")
                                .css('display', 'none')
                                .css('color', 'orange')
                        }
                    },
                    error:function (){
                        alert("error!!!!")
                    }
                })
            })
        })
});
