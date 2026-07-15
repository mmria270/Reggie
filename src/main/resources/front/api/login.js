function loginApi(data) {
    return $axios({
        'url': '/user/login',
        'method': 'post',
        data
    })
}

function loginoutApi() {
    return $axios({
        'url': '/user/loginout',
        'method': 'post',
    })
}

// 新增短信接口
function sendMsgApi(data) {
    return $axios({
        url: '/user/sendMsg',
        method: 'post',
        data
    })
}

// 全局暴露
window.loginApi = loginApi
window.loginoutApi = loginoutApi
window.sendMsgApi = sendMsgApi