function userPage(data){
    return $axios({
        'url': '/financeManager/user/page',
        'method': 'post',
        'data': data,
    })
}

function updateUser(data){
    return $axios({
        'url': '/financeManager/user/updateUser',
        'method': 'post',
        'data': data,
    })
}

function deleteUser(data){
    return $axios({
        'url': '/financeManager/user/delete',
        'method': 'post',
        'data': data,
    })
}
function downloadUser(){
    return $axios({
        'url': '/financeManager/user/download',
        "method": 'get',
        "responseType": 'blob',
    })
}