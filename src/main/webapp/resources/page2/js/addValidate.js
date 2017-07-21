function addValidate(editor){
    //<!-- JQ validation 验证表单信息 -->
    $('.fh5co-form.animate-box').validate({
        errorElement: 'span',

        rules: {
            password: {
                required: true,

            },
            phoneNumber: {
                required: true,
                isPhone: true,
                maxlength: 11,
                minlength: 11,
                number: true
            }
        },
        messages: {
            password: {
                required:"*请填写你的密码",

            },
            phoneNumber: {
                required: "*请填写你的手机号码",
                isPhone: "*请输入正确的手机号码",
                maxlength:"*请输入正确的手机号码",
                minlength:"*请输入正确的手机号码",
                number: "*请输入正确的手机号码"
            }
        },

        submitHandler: function(form) {//验证通过之后回调
            signInAjax(editor)
        },
        invalidHandler: function(form, validator) {  //验证不通过之后回调
            return false;
        }
    });
}